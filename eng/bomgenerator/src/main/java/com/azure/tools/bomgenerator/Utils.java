package com.azure.tools.bomgenerator;

import com.azure.tools.bomgenerator.models.BomDependency;
import com.azure.tools.bomgenerator.models.BomDependencyNoVersion;
import org.apache.maven.model.Dependency;
import org.apache.maven.model.DependencyManagement;
import org.apache.maven.model.Model;
import org.apache.maven.model.io.xpp3.MavenXpp3Reader;
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

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.TreeMap;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class Utils {
    public static final String COMMANDLINE_INPUTFILE = "inputfile";
    public static final String COMMANDLINE_OUTPUTFILE = "outputfile";
    public static final String COMMANDLINE_POMFILE = "pomfile";
    public static final String COMMANDLINE_EXTERNALDEPENDENCIES = "externalDependencies";
    public static final String COMMANDLINE_GROUPID = "groupid";
    public static final String COMMANDLINE_EXCLUSIONLIST = "exclusionList";
    public  static final Pattern COMMANDLINE_REGEX = Pattern.compile("-(.*)=(.*)");
    public static final List<String> EXCLUSION_LIST = Arrays.asList("azure-spring-data-cosmos", "azure-spring-data-cosmos-test");
    public static final Pattern SDK_DEPENDENCY_PATTERN = Pattern.compile("(.+):(.+);(.+);(.+)");
    public static final Pattern EXTERNAL_DEPENDENCY_PATTERN = Pattern.compile("(.+):(.+);(.+)");
    public static final Pattern SDK_NON_GA_PATTERN = Pattern.compile("(.+)-(.+)");
    public static final String AZURE_CORE_GROUPID = "com.azure";
    public static final String AZURE_TEST_LIBRARY_IDENTIFIER = "-test";
    public static final String AZURE_PERF_LIBRARY_IDENTIFIER = "-perf";
    public static final String AZURE_CORE_TEST_LIBRARY = "azure-core-test";

    public static final String CONFLICTING_DEPENDENCIES = "conflict";
    public static final String BOM_ELIGIBLE = "bom";
    public static final HashSet<String> EXTERNAL_BOM_DEPENDENCIES = new HashSet<String>(Arrays.asList(
        "io.projectreactor",
        "com.fasterxml.jackson",
        "io.netty",
        "io.projectreactor.netty"
    ));

    public static final HashSet<String> RESOLVED_EXCLUSION_LIST = new HashSet<>(Arrays.asList(
       "junit-jupiter-api"
    ));

    public static final String POM_TYPE = "pom";


    public static List<BomDependency> getPomFileContent(Dependency dependency) {
        HttpURLConnection connection = null;
        try {
            String[] groups = dependency.getGroupId().split("[.]");
            URL url = null;
            if(groups.length == 2) {
                url = new URL("https://repo1.maven.org/maven2" + "/" + groups[0] + "/" + groups[1] + "/" + dependency.getArtifactId() + "/" + dependency.getVersion() + "/" + dependency.getArtifactId() + "-" + dependency.getVersion() + ".pom");
            }
            else if (groups.length == 3) {
                url = new URL("https://repo1.maven.org/maven2" + "/" + groups[0] + "/" + groups[1] + "/" + groups[2] + "/" + dependency.getArtifactId() + "/" + dependency.getVersion() + "/" + dependency.getArtifactId() + "-" + dependency.getVersion() + ".pom");
            }

            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setRequestProperty("accept", "application/xml");
            connection.setConnectTimeout(5000);
            connection.setReadTimeout(5000);
            int responseCode = connection.getResponseCode();
            if (HttpURLConnection.HTTP_OK == responseCode) {
                InputStream responseStream = connection.getInputStream();

                MavenXpp3Reader reader = new MavenXpp3Reader();
                Model model = reader.read(responseStream);
                DependencyManagement management = model.getDependencyManagement();

                return management.getDependencies().stream().map(dep -> {
                    String version = getPropertyName(dep.getVersion());

                    while(model.getProperties().getProperty(version) != null) {
                        version = getPropertyName(model.getProperties().getProperty(version));
                    }

                    if(version == null) {
                        version = dep.getVersion();
                    }

                    BomDependency bomDependency = new BomDependency(dep.getGroupId(), dep.getArtifactId(), version);
                    return bomDependency;
                }).collect(Collectors.toList());
            }
        } catch (Exception exception) {
        } finally {
            if (connection != null) {
                // closes the input streams and discards the socket
                connection.disconnect();
            }
        }
        return null;
    }

    private static String getPropertyName(String propertyValue) {
        if(propertyValue.startsWith("${")) {
            return propertyValue.substring(2, propertyValue.length() - 1);
        }

        return propertyValue;
    }


}
