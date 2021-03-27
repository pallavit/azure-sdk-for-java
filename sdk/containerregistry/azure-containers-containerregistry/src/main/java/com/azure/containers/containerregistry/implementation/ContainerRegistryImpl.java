// Copyright (c) Microsoft Corporation. All rights reserved.
// Licensed under the MIT License.
// Code generated by Microsoft (R) AutoRest Code Generator.

package com.azure.containers.containerregistry.implementation;

import com.azure.core.http.HttpPipeline;
import com.azure.core.http.HttpPipelineBuilder;
import com.azure.core.http.policy.CookiePolicy;
import com.azure.core.http.policy.RetryPolicy;
import com.azure.core.http.policy.UserAgentPolicy;
import com.azure.core.util.serializer.JacksonAdapter;
import com.azure.core.util.serializer.SerializerAdapter;

/** Initializes a new instance of the ContainerRegistry type. */
public final class ContainerRegistryImpl {
    /** Registry login URL. */
    private final String endpoint;

    /**
     * Gets Registry login URL.
     *
     * @return the endpoint value.
     */
    public String getEndpoint() {
        return this.endpoint;
    }

    /** The HTTP pipeline to send requests through. */
    private final HttpPipeline httpPipeline;

    /**
     * Gets The HTTP pipeline to send requests through.
     *
     * @return the httpPipeline value.
     */
    public HttpPipeline getHttpPipeline() {
        return this.httpPipeline;
    }

    /** The serializer to serialize an object into a string. */
    private final SerializerAdapter serializerAdapter;

    /**
     * Gets The serializer to serialize an object into a string.
     *
     * @return the serializerAdapter value.
     */
    public SerializerAdapter getSerializerAdapter() {
        return this.serializerAdapter;
    }

    /** The ContainerRegistriesImpl object to access its operations. */
    private final ContainerRegistriesImpl containerRegistries;

    /**
     * Gets the ContainerRegistriesImpl object to access its operations.
     *
     * @return the ContainerRegistriesImpl object.
     */
    public ContainerRegistriesImpl getContainerRegistries() {
        return this.containerRegistries;
    }

    /** The ContainerRegistryRepositoriesImpl object to access its operations. */
    private final ContainerRegistryRepositoriesImpl containerRegistryRepositories;

    /**
     * Gets the ContainerRegistryRepositoriesImpl object to access its operations.
     *
     * @return the ContainerRegistryRepositoriesImpl object.
     */
    public ContainerRegistryRepositoriesImpl getContainerRegistryRepositories() {
        return this.containerRegistryRepositories;
    }

    /** The ContainerRegistryBlobsImpl object to access its operations. */
    private final ContainerRegistryBlobsImpl containerRegistryBlobs;

    /**
     * Gets the ContainerRegistryBlobsImpl object to access its operations.
     *
     * @return the ContainerRegistryBlobsImpl object.
     */
    public ContainerRegistryBlobsImpl getContainerRegistryBlobs() {
        return this.containerRegistryBlobs;
    }

    /** The AuthenticationsImpl object to access its operations. */
    private final AuthenticationsImpl authentications;

    /**
     * Gets the AuthenticationsImpl object to access its operations.
     *
     * @return the AuthenticationsImpl object.
     */
    public AuthenticationsImpl getAuthentications() {
        return this.authentications;
    }

    /**
     * Initializes an instance of ContainerRegistry client.
     *
     * @param endpoint Registry login URL.
     */
    ContainerRegistryImpl(String endpoint) {
        this(
                new HttpPipelineBuilder()
                        .policies(new UserAgentPolicy(), new RetryPolicy(), new CookiePolicy())
                        .build(),
                JacksonAdapter.createDefaultSerializerAdapter(),
                endpoint);
    }

    /**
     * Initializes an instance of ContainerRegistry client.
     *
     * @param httpPipeline The HTTP pipeline to send requests through.
     * @param endpoint Registry login URL.
     */
    ContainerRegistryImpl(HttpPipeline httpPipeline, String endpoint) {
        this(httpPipeline, JacksonAdapter.createDefaultSerializerAdapter(), endpoint);
    }

    /**
     * Initializes an instance of ContainerRegistry client.
     *
     * @param httpPipeline The HTTP pipeline to send requests through.
     * @param serializerAdapter The serializer to serialize an object into a string.
     * @param endpoint Registry login URL.
     */
    ContainerRegistryImpl(HttpPipeline httpPipeline, SerializerAdapter serializerAdapter, String endpoint) {
        this.httpPipeline = httpPipeline;
        this.serializerAdapter = serializerAdapter;
        this.endpoint = endpoint;
        this.containerRegistries = new ContainerRegistriesImpl(this);
        this.containerRegistryRepositories = new ContainerRegistryRepositoriesImpl(this);
        this.containerRegistryBlobs = new ContainerRegistryBlobsImpl(this);
        this.authentications = new AuthenticationsImpl(this);
    }
}
