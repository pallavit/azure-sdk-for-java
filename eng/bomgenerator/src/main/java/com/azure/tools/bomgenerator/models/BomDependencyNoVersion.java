package com.azure.tools.bomgenerator.models;

import org.jboss.shrinkwrap.resolver.api.maven.PackagingType;
import org.jboss.shrinkwrap.resolver.api.maven.ScopeType;
import org.jboss.shrinkwrap.resolver.api.maven.coordinate.MavenCoordinate;
import org.jboss.shrinkwrap.resolver.api.maven.coordinate.MavenDependency;
import org.jboss.shrinkwrap.resolver.api.maven.coordinate.MavenDependencyExclusion;

import java.util.HashSet;
import java.util.Set;

public class BomDependencyNoVersion implements MavenDependency {
    private String groupId;
    private String artifactId;
    private Boolean isDiscarded;

    public BomDependencyNoVersion(String groupId, String artifactId) {
        this.groupId = groupId;
        this.artifactId = artifactId;
    }

    public BomDependencyNoVersion(MavenCoordinate coordinate) {
        this.artifactId = coordinate.getArtifactId();
        this.groupId = coordinate.getGroupId();
    }

    @Override
    public Set<MavenDependencyExclusion> getExclusions() {
        return new HashSet<>();
    }

    @Override
    public ScopeType getScope() {
        return ScopeType.RUNTIME;
    }

    public void setIsDiscarded(Boolean isDiscarded) {
        this.isDiscarded = isDiscarded;
    }

    public Boolean getIsDiscarded() {
        return this.isDiscarded;
    }

    @Override
    public boolean isOptional() {
        return false;
    }

    @Override
    public PackagingType getPackaging() {
        return PackagingType.JAR;
    }

    @Override
    public PackagingType getType() {
        return PackagingType.JAR;
    }

    @Override
    public String getClassifier() {
        return null;
    }

    @Override
    public String getVersion() {
        return null;
    }

    @Override
    public String getGroupId() {
        return groupId;
    }

    @Override
    public String getArtifactId() {
        return artifactId;
    }

    @Override
    public String toCanonicalForm() {
        return null;
    }

    public String getFullyQualifiedNameDependencyName() {
        return this.getGroupId() + ":" + this.getArtifactId();
    }

    @Override
    public String toString() {
        return this.getGroupId() + ":" + this.getArtifactId();
    }
}
