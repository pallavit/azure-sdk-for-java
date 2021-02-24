// Copyright (c) Microsoft Corporation. All rights reserved.
// Licensed under the MIT License.
// Code generated by Microsoft (R) AutoRest Code Generator.

package com.azure.containers.containerregistry.implementation.models;

import com.azure.core.annotation.Fluent;
import com.fasterxml.jackson.annotation.JsonProperty;

/** The BlobsCheckHeaders model. */
@Fluent
public final class BlobsCheckHeaders {
    /*
     * The Content-Length property.
     */
    @JsonProperty(value = "Content-Length")
    private Long contentLength;

    /*
     * The Docker-Content-Digest property.
     */
    @JsonProperty(value = "Docker-Content-Digest")
    private String dockerContentDigest;

    /*
     * The Location property.
     */
    @JsonProperty(value = "Location")
    private String location;

    /**
     * Get the contentLength property: The Content-Length property.
     *
     * @return the contentLength value.
     */
    public Long getContentLength() {
        return this.contentLength;
    }

    /**
     * Set the contentLength property: The Content-Length property.
     *
     * @param contentLength the contentLength value to set.
     * @return the BlobsCheckHeaders object itself.
     */
    public BlobsCheckHeaders setContentLength(Long contentLength) {
        this.contentLength = contentLength;
        return this;
    }

    /**
     * Get the dockerContentDigest property: The Docker-Content-Digest property.
     *
     * @return the dockerContentDigest value.
     */
    public String getDockerContentDigest() {
        return this.dockerContentDigest;
    }

    /**
     * Set the dockerContentDigest property: The Docker-Content-Digest property.
     *
     * @param dockerContentDigest the dockerContentDigest value to set.
     * @return the BlobsCheckHeaders object itself.
     */
    public BlobsCheckHeaders setDockerContentDigest(String dockerContentDigest) {
        this.dockerContentDigest = dockerContentDigest;
        return this;
    }

    /**
     * Get the location property: The Location property.
     *
     * @return the location value.
     */
    public String getLocation() {
        return this.location;
    }

    /**
     * Set the location property: The Location property.
     *
     * @param location the location value to set.
     * @return the BlobsCheckHeaders object itself.
     */
    public BlobsCheckHeaders setLocation(String location) {
        this.location = location;
        return this;
    }
}
