package com.azure.tools.bomgenerator;

import com.azure.tools.bomgenerator.models.BomDependency;
import com.azure.tools.bomgenerator.models.BomDependencyComparator;
import org.apache.maven.model.Dependency;
import org.apache.maven.model.DependencyManagement;
import org.apache.maven.model.Model;
import org.apache.maven.model.io.xpp3.MavenXpp3Reader;
import org.apache.maven.model.io.xpp3.MavenXpp3Writer;
import org.codehaus.plexus.util.xml.pull.XmlPullParserException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.TreeSet;
import java.util.regex.Matcher;
import java.util.stream.Collectors;

import static com.azure.tools.bomgenerator.Utils.AZURE_CORE_GROUPID;
import static com.azure.tools.bomgenerator.Utils.AZURE_CORE_TEST_LIBRARY;
import static com.azure.tools.bomgenerator.Utils.AZURE_PERF_LIBRARY_IDENTIFIER;
import static com.azure.tools.bomgenerator.Utils.AZURE_TEST_LIBRARY_IDENTIFIER;
import static com.azure.tools.bomgenerator.Utils.EXCLUSION_LIST;
import static com.azure.tools.bomgenerator.Utils.POM_TYPE;
import static com.azure.tools.bomgenerator.Utils.SDK_DEPENDENCY_PATTERN;
import static com.azure.tools.bomgenerator.Utils.SDK_NON_GA_PATTERN;

public class BomGenerator {
    private String outputFileName;
    private String inputFileName;
    private String pomFileName;
    private String externalDependenciesFileName;

    private static Logger logger = LoggerFactory.getLogger(BomGenerator.class);

    BomGenerator() {
    }

    public void setInputFile(String inputFileName) {
        this.inputFileName = inputFileName;
    }

    public void setOutputFile(String outputFileName) {
        this.outputFileName = outputFileName;
    }

    public void setPomFile(String pomFileName) {
        this.pomFileName = pomFileName;
    }

    public void setExternalDependenciesFile(String externalDependenciesFileName) {
        this.externalDependenciesFileName = externalDependenciesFileName;
    }


    public void generate() {
        TreeSet<BomDependency> inputDependencies = scan();
        TreeSet<BomDependency> externalDependencies = resolveExternalDependencies();

        // 1. Create the initial tree and reduce conflicts.
        // 2. And pick only those dependencies. that were in the input set, since they become the new roots of n-ary tree.
        DependencyAnalyzer analyzer = new DependencyAnalyzer(inputDependencies, externalDependencies);
        TreeSet<BomDependency> outputDependencies = analyzer.analyze();
        outputDependencies.retainAll(inputDependencies);

        // 2. Create the new tree for the BOM.
        analyzer = new DependencyAnalyzer(outputDependencies, externalDependencies);
        outputDependencies = analyzer.analyze();

        // 3. Validate that the current BOM has no conflicts.
        boolean validationPassed = analyzer.validate();

        // 4. Create the new BOM file.
        if(validationPassed) {
            rewriteBomFile();
            writeBom(outputDependencies);
        }
        else {
            logger.info("Validation for the BOM failed. Exiting...");
        }
    }

    private TreeSet<BomDependency> scan() {
        TreeSet<BomDependency> inputDependencies = new TreeSet<>(new BomDependencyComparator());
        try {
            for (String line : Files.readAllLines(Paths.get(inputFileName))) {
                if (line.startsWith("com.azure")) {
                    Matcher matcher = SDK_DEPENDENCY_PATTERN.matcher(line);
                    if (matcher.matches()) {
                        if (matcher.groupCount() == 4) {
                            String groupId = matcher.group(1);
                            String artifactId = matcher.group(2);
                            String version = matcher.group(3);

                            Matcher nonGAMatcher = SDK_NON_GA_PATTERN.matcher(version);
                            if (!nonGAMatcher.matches()) {
                                BomDependency dependency = new BomDependency(groupId, artifactId, version);
                                if (AZURE_CORE_GROUPID.equalsIgnoreCase(groupId)) {
                                    switch (artifactId) {
                                        case "azure-sdk-all":
                                        case "azure-sdk-parent":
                                        case "azure-client-sdk-parent":
                                            break;
                                        default:
                                            if (EXCLUSION_LIST.contains(artifactId)
                                                || artifactId.contains(AZURE_PERF_LIBRARY_IDENTIFIER)
                                                || (artifactId.contains(AZURE_TEST_LIBRARY_IDENTIFIER) && !artifactId.equalsIgnoreCase(AZURE_CORE_TEST_LIBRARY))) {
                                                logger.info("Skipping dependency {}:{}", groupId, artifactId);
                                                continue;
                                            }

                                            inputDependencies.add(dependency);
                                            break;
                                    }
                                }
                            }
                        }
                    }
                }
            }
        } catch (IOException exception) {
            exception.printStackTrace();
        }

        return inputDependencies;
    }

    private TreeSet<BomDependency> resolveExternalDependencies() {
        TreeSet<BomDependency> externalDependencies = new TreeSet<>(new BomDependencyComparator());
        MavenXpp3Reader reader = new MavenXpp3Reader();
        try {
            Model model = reader.read(new FileReader(this.pomFileName));
            DependencyManagement management = model.getDependencyManagement();
            List<Dependency> externalBomDependencies = management.getDependencies().stream().filter(dependency -> dependency.getType().equals(POM_TYPE)).collect(Collectors.toList());
            for (Dependency externalDependency : externalBomDependencies) {
                externalDependencies.addAll(Utils.getPomFileContent(externalDependency));
            }

        } catch (XmlPullParserException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException exception) {
            exception.printStackTrace();
        }

        return externalDependencies;
    }

    private void rewriteBomFile() {
        MavenXpp3Reader reader = new MavenXpp3Reader();
        try {
            Model model = reader.read(new FileReader(this.pomFileName));
            DependencyManagement management = model.getDependencyManagement();
            List<Dependency> dependencies = management.getDependencies();
            dependencies.sort(new DependencyComparator());
            management.setDependencies(dependencies);
            MavenXpp3Writer writer = new MavenXpp3Writer();
            writer.write(new FileWriter(this.pomFileName), model);
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    private void writeBom(TreeSet<BomDependency> bomDependencies) {
        MavenXpp3Reader reader = new MavenXpp3Reader();
        try {
            Model model = reader.read(new FileReader(this.pomFileName));
            DependencyManagement management = model.getDependencyManagement();
            List<Dependency> externalBomDependencies = management.getDependencies().stream().filter(dependency -> dependency.getType().equals(POM_TYPE)).collect(Collectors.toList());


            List<Dependency> dependencies = bomDependencies.stream().map(bomDependency -> {
                Dependency dependency = new Dependency();
                dependency.setGroupId(bomDependency.getGroupId());
                dependency.setArtifactId(bomDependency.getArtifactId());
                dependency.setVersion(bomDependency.getVersion());
                return dependency;
            }).collect(Collectors.toList());
            dependencies.addAll(externalBomDependencies);

            dependencies.sort(new DependencyComparator());
            management.setDependencies(dependencies);

            // Now that we have the new dependencies.
            MavenXpp3Writer writer = new MavenXpp3Writer();
            writer.write(new FileWriter(this.outputFileName), model);
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }
}
