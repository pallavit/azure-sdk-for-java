package com.azure.tools.bomgenerator.models;

import org.jboss.shrinkwrap.resolver.api.maven.coordinate.MavenCoordinate;

public class BomDependency extends BomDependencyNoVersion {
    private String version;

    public BomDependency(String groupId, String artifactId, String version) {
        super(groupId, artifactId);
        this.version = version;
    }

    public BomDependency(MavenCoordinate coordinate) {
        super(coordinate);
        this.version = coordinate.getVersion();
    }

    @Override
    public String getVersion() {
        return this.version;
    }

//
//    @Override
//    public boolean equals(Object otherObject) {
//        if (!(otherObject instanceof BomDependency)) {
//            return false;
//        }
//
//        BomDependency otherDependency = (BomDependency) otherObject;
//        return otherDependency.getGroupId().equals(this.getGroupId())
//            && otherDependency.getArtifactId().equals(this.getArtifactId())
//            && otherDependency.getVersion().equals(this.getVersion());
//    }

    @Override
    public String toString() {
        return this.getGroupId() + ":" + this.getArtifactId() + ":" + this.getVersion();
    }
}
