package com.azure.tools.bomgenerator;

import com.azure.tools.bomgenerator.models.BomDependency;
import com.azure.tools.bomgenerator.models.BomDependencyComparator;
import com.azure.tools.bomgenerator.models.BomDependencyNoVersion;
import com.azure.tools.bomgenerator.models.BomDependencyNonVersionComparator;
import org.jboss.shrinkwrap.resolver.api.maven.Maven;
import org.jboss.shrinkwrap.resolver.api.maven.MavenArtifactInfo;
import org.jboss.shrinkwrap.resolver.api.maven.MavenFormatStage;
import org.jboss.shrinkwrap.resolver.api.maven.MavenResolvedArtifact;
import org.jboss.shrinkwrap.resolver.api.maven.MavenResolverSystemBase;
import org.jboss.shrinkwrap.resolver.api.maven.MavenStrategyStage;
import org.jboss.shrinkwrap.resolver.api.maven.PomEquippedResolveStage;
import org.jboss.shrinkwrap.resolver.api.maven.PomlessResolveStage;
import org.jboss.shrinkwrap.resolver.api.maven.ScopeType;
import org.jboss.shrinkwrap.resolver.api.maven.coordinate.MavenDependency;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.Collectors;

import static com.azure.tools.bomgenerator.Utils.RESOLVED_EXCLUSION_LIST;

public class DependencyAnalyzer {
    private TreeSet<BomDependency> inputDependencies = new TreeSet<>(new BomDependencyComparator());
    private TreeSet<BomDependency> externalDependencies = new TreeSet<>(new BomDependencyComparator());
    private TreeSet<BomDependency> bomEligibleDependencies = new TreeSet<>(new BomDependencyComparator());
    private TreeSet<BomDependency> bomIneligibleDependencies = new TreeSet<>(new BomDependencyComparator());

    private TreeMap<BomDependencyNoVersion, HashMap<String, Collection<BomDependency>>> nameToVersionToChildrenDependencyTree = new TreeMap<>(new BomDependencyNonVersionComparator());
    private static Logger logger = LoggerFactory.getLogger(BomGenerator.class);

    DependencyAnalyzer(TreeSet<BomDependency> inputDependencies, TreeSet<BomDependency> externalDependencies){
        if(inputDependencies != null) {
            this.inputDependencies.addAll(inputDependencies);
        }
        if(externalDependencies != null) {
            this.externalDependencies.addAll(externalDependencies);
        }
    }

    public TreeSet<BomDependency> getBomEligibleDependencies() {
        return this.bomEligibleDependencies;
    }

    public void analyzeAndReduce() {
        analyze();
        this.bomEligibleDependencies.retainAll(this.inputDependencies);
    }

    public boolean analyzeAndValidate() {
        return analyze();
    }

    private boolean analyze() {
        resolveTree();
        return filterConflicts();
    }

    private void resolveTree() {
        for (MavenDependency gaLibrary : inputDependencies) {
            try {
                MavenResolvedArtifact mavenResolvedArtifact = null;

                mavenResolvedArtifact = getMavenResolver()
                    .addDependency(gaLibrary)
                    .resolve()
                    .withoutTransitivity()
                    .asSingleResolvedArtifact();

                BomDependency parentDependency = new BomDependency(mavenResolvedArtifact.getCoordinate());
                MavenArtifactInfo[] dependencies = mavenResolvedArtifact.getDependencies();

                addDependencyToDependencyTree(parentDependency, null, nameToVersionToChildrenDependencyTree);

                for (MavenArtifactInfo dependency : dependencies) {
                    if (dependency.getScope() == ScopeType.TEST) {
                        continue;
                    }
                    if(RESOLVED_EXCLUSION_LIST.contains(dependency.getCoordinate().getArtifactId())) {
                        // This is coming from the BOM, we may not include this.
                        continue;
                    }

                    BomDependency childDependency = new BomDependency(dependency.getCoordinate());
                    addDependencyToDependencyTree(childDependency, parentDependency, nameToVersionToChildrenDependencyTree);
                }
            }
            catch(Exception ex) {
                System.out.println(ex);
            }
        }
    }

    private static MavenResolverSystemBase<PomEquippedResolveStage, PomlessResolveStage, MavenStrategyStage, MavenFormatStage> getMavenResolver() {
        return Maven.configureResolver().withMavenCentralRepo(true);
    }

    private static void addDependencyToDependencyTree(BomDependency dependency, BomDependency parentDependency, TreeMap<BomDependencyNoVersion, HashMap<String, Collection<BomDependency>>> dependencyTree) {
        if (!dependencyTree.containsKey(dependency)) {
            dependencyTree.put(new BomDependencyNoVersion(dependency.getGroupId(), dependency.getArtifactId()), new HashMap<>());
        }

        HashMap<String, Collection<BomDependency>> versionToParents = dependencyTree.get(dependency);
        if(!versionToParents.containsKey(dependency.getVersion())) {
            versionToParents.put(dependency.getVersion(), new ArrayList<>());
        }

        if(parentDependency != null) {
            versionToParents.get(dependency.getVersion()).add(parentDependency);
        }
    }


    private void makeDependencyInEligible(BomDependency dependency) {
        if(nameToVersionToChildrenDependencyTree.containsKey(dependency)) {
            HashMap<String, Collection<BomDependency>> versionToDependency = nameToVersionToChildrenDependencyTree.get(dependency);
            bomIneligibleDependencies.add(dependency);

            // Make all the dependencies that include these also ineligible.
            versionToDependency.get(dependency.getVersion()).forEach(parent -> makeDependencyInEligible(parent));
        }
    }

    private boolean resolveConflicts() {
        AtomicBoolean hasConflict = new AtomicBoolean(false);
        // TODO: Consider having a non-core plan of picking the highest version with the maximum dependant children.
        nameToVersionToChildrenDependencyTree.keySet().stream().forEach(
            key -> {
                Map<String, Collection<BomDependency>> versionToDependency = nameToVersionToChildrenDependencyTree.get(key);
                if (versionToDependency.size() > 1) {
                    hasConflict.set(true);
                    // We have multiple versions of this coming up, we will let the latest version win.
                    List<String> versionList = versionToDependency.keySet().stream().sorted(new DependencyVersionComparator()).collect(Collectors.toList());
                    String latestVersion = versionList.get(versionList.size() - 1);

                    logger.info("Multiple version of the dependency {} included", key);
                    logger.info("\tPicking the latest version for BOM: {}", latestVersion);

                    BomDependency dependency = new BomDependency(key.getGroupId(), key.getArtifactId(), latestVersion);
                    if (!externalDependencies.contains(dependency)) {
                        bomEligibleDependencies.add(dependency);
                    }
                    for (int index = 0; index < versionList.size() - 1; index++) {
                        String version = versionList.get(index);
                        makeDependencyInEligible(new BomDependency(dependency.getGroupId(), dependency.getArtifactId(), version));
                    }
                }
            });

        // There may still be some intersection between eligible and non eligible dependencies.
        bomEligibleDependencies.removeAll(bomIneligibleDependencies);

        return hasConflict.get();
    }

    private boolean filterConflicts() {
        boolean hasconflict = resolveConflicts();
        nameToVersionToChildrenDependencyTree.keySet().stream().forEach(
            key -> {
                HashMap<String, Collection<BomDependency>> versionToDependency = nameToVersionToChildrenDependencyTree.get(key);

                if (versionToDependency.size() == 1) {
                    BomDependency dependency = new BomDependency(key.getGroupId(), key.getArtifactId(), versionToDependency.keySet().stream().findFirst().get());
                    if (!bomIneligibleDependencies.contains(dependency)
                        && !externalDependencies.contains(dependency)) {
                        // No conflict, the library can be added to the list.
                        bomEligibleDependencies.add(dependency);
                    }
                }
            });

        return hasconflict;
    }
}
