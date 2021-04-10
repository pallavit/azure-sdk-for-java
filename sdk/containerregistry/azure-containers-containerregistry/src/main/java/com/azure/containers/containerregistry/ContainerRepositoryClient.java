// Copyright (c) Microsoft Corporation. All rights reserved.
// Licensed under the MIT License.
// Code generated by Microsoft (R) AutoRest Code Generator.

package com.azure.containers.containerregistry;

import com.azure.containers.containerregistry.models.ContentProperties;
import com.azure.containers.containerregistry.models.DeleteRepositoryResult;
import com.azure.containers.containerregistry.models.ListRegistryArtifactOptions;
import com.azure.containers.containerregistry.models.ListTagsOptions;
import com.azure.containers.containerregistry.models.RegistryArtifactProperties;
import com.azure.containers.containerregistry.models.RepositoryProperties;
import com.azure.containers.containerregistry.models.TagProperties;
import com.azure.core.annotation.ReturnType;
import com.azure.core.annotation.ServiceClient;
import com.azure.core.annotation.ServiceMethod;
import com.azure.core.exception.ClientAuthenticationException;
import com.azure.core.exception.ResourceNotFoundException;
import com.azure.core.http.rest.PagedIterable;
import com.azure.core.http.rest.Response;
import com.azure.core.util.Context;

/** Initializes a new instance of the synchronous container repository client.
 * This client is used for interacting with the repository.
 * */
@ServiceClient(builder = ContainerRepositoryClientBuilder.class)
public final class ContainerRepositoryClient {
    private final ContainerRepositoryAsyncClient asyncClient;

    ContainerRepositoryClient(ContainerRepositoryAsyncClient asyncClient) {
        this.asyncClient = asyncClient;
    }

    /**
     * Get endpoint associated with the class.
     * @return the endpoint associated with the client.
     * */
    public String getEndpoint() {
        return this.asyncClient.getEndpoint();
    }

    /**
     * Get registry associated with the class.
     * @return registry associated with the client.
     * */
    public String getRegistry() {
        return this.asyncClient.getRegistry();
    }

    /**
     * Get repository associated with the class.
     * @return repository associated with the client.
     * */
    public String getRepository() {
        return this.asyncClient.getRepository();
    }

    /**
     * Delete the repository.
     * @param context Context associated with the operation.
     *
     * @throws ClientAuthenticationException thrown if the client's credentials do not have access to modify the namespace.
     * @throws ResourceNotFoundException thrown if the given name was not found.
     * @return deleted repository properties.
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    public Response<DeleteRepositoryResult> deleteWithResponse(Context context) {
        return this.asyncClient.deleteWithResponse(context).block();
    }

    /**
     * Delete repository.
     * @throws ClientAuthenticationException thrown if the client's credentials do not have access to modify the namespace.
     * @throws ResourceNotFoundException thrown if the given name was not found.
     * @return deleted repository properties.
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    public DeleteRepositoryResult delete() {
        return this.deleteWithResponse(Context.NONE).getValue();
    }

    /**
     * Delete registry artifact.
     *
     * @param digest digest to delete.
     * @param context Context associated with the operation.
     * @throws ClientAuthenticationException thrown if the client's credentials do not have access to modify the namespace.
     * @throws ResourceNotFoundException thrown if the given digest was not found.
     * @throws NullPointerException thrown if digest is null.
     * @return the completion.
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    public Response<Void> deleteRegistryArtifactWithResponse(String digest, Context context) {
        return this.asyncClient.deleteRegistryArtifactWithResponse(digest, context).block();
    }

    /**
     * Delete registry artifact.
     *
     * @param digest digest to delete.
     * @throws ClientAuthenticationException thrown if the client's credentials do not have access to modify the namespace.
     * @throws ResourceNotFoundException thrown if the given digest was not found.
     * @throws NullPointerException thrown if digest is null.
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    public void deleteRegistryArtifact(String digest) {
        this.deleteRegistryArtifactWithResponse(digest, Context.NONE).getValue();
    }

    /**
     * Delete tag.
     *
     * @param tag tag to delete.
     * @param context associated with the operation.
     * @throws ClientAuthenticationException thrown if the client's credentials do not have access to modify the namespace.
     * @throws ResourceNotFoundException thrown if the given digest was not found.
     * @throws NullPointerException thrown if tag is null.
     * @return response for completion.
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    public Response<Void> deleteTagWithResponse(String tag, Context context) {
        return this.asyncClient.deleteTagWithResponse(tag, context).block();
    }

    /**
     * Delete tag.
     *
     * @param tag tag to delete.
     * @throws ClientAuthenticationException thrown if the client's credentials do not have access to modify the namespace.
     * @throws ResourceNotFoundException all other wrapped checked exceptions if the request fails to be sent.
     * @throws NullPointerException thrown if tag is null.
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    public void deleteTag(String tag) {
        this.deleteTagWithResponse(tag, Context.NONE).getValue();
    }

    /**
     * Get repository properties.
     *
     * @param context Context associated with the operation.
     * @throws ClientAuthenticationException thrown if the client's credentials do not have access to modify the namespace.
     * @throws ResourceNotFoundException thrown if the given repository was not found.
     * @return repository attributes.
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    public Response<RepositoryProperties> getPropertiesWithResponse(Context context) {
        return this.asyncClient.getPropertiesWithResponse(context).block();
    }

    /**
     * Get repository properties.
     *
     * @throws ClientAuthenticationException thrown if the client's credentials do not have access to modify the namespace.
     * @throws ResourceNotFoundException thrown if the given repository was not found.
     * @return repository properties.
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    public RepositoryProperties getProperties() {
        return this.getPropertiesWithResponse(Context.NONE).getValue();
    }

    /**
     * <p>Get registry artifact properties.</p>
     *
     * <p>This method can take in both a digest as well as a tag.<br>
     * In case a tag is provided it calls the service to get the digest associated with it.</p>
     *
     * @param digest digest of an artifact.
     * @param context Context associated with the operation.
     * @throws ClientAuthenticationException thrown if the client's credentials do not have access to modify the namespace.
     * @throws ResourceNotFoundException thrown if the given repository was not found.
     * @return registry artifact properties.
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    public Response<RegistryArtifactProperties> getRegistryArtifactPropertiesWithResponse(String digest, Context context) {
        return this.asyncClient.getRegistryArtifactPropertiesWithResponse(digest, context).block();
    }

    /**
     * <p>Get registry artifact properties.</p>
     *
     * <p>This method can take in both a digest as well as a tag.<br>
     * In case a tag is provided it calls the service to get the digest associated with it.</p>
     *
     * @param digest digest of an artifact.
     * @throws ClientAuthenticationException thrown if the client's credentials do not have access to modify the namespace.
     * @throws ResourceNotFoundException thrown if the given repository was not found.
     * @return registry artifact properties.
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    public RegistryArtifactProperties getRegistryArtifactProperties(String digest) {
        return this.getRegistryArtifactPropertiesWithResponse(digest, Context.NONE).getValue();
    }

    /**
     * List registry artifacts of a repository.
     *
     * @throws ClientAuthenticationException thrown if the client's credentials do not have access to modify the namespace.
     * @return registry artifact properties.
     */
    @ServiceMethod(returns = ReturnType.COLLECTION)
    public PagedIterable<RegistryArtifactProperties> listRegistryArtifacts() {
        return this.listRegistryArtifacts(null);
    }

    /**
     * List registry artifacts of a repository.
     *
     * @param options options associated with the list registry artifacts operation.
     * @throws ClientAuthenticationException thrown if the client's credentials do not have access to modify the namespace.
     * @return registry artifact properties.
     */
    @ServiceMethod(returns = ReturnType.COLLECTION)
    public PagedIterable<RegistryArtifactProperties> listRegistryArtifacts(ListRegistryArtifactOptions options) {
        return new PagedIterable<>(this.asyncClient.listRegistryArtifacts(options));
    }

    /**
     * Get tag properties.
     *
     * @param tag tag associated with the artifact.
     * @param context Context associated with the operation.
     * @throws ClientAuthenticationException thrown if the client's credentials do not have access to modify the namespace.
     * @throws ResourceNotFoundException thrown if the given tag was not found.
     * @return tag properties by tag.
     */
    @ServiceMethod(returns = ReturnType.SINGLE)

    public Response<TagProperties> getTagPropertiesWithResponse(String tag, Context context) {
        return this.asyncClient.getTagPropertiesWithResponse(tag, context).block();
    }

    /**
     * Get tag properties by tag.
     *
     * @param tag tag associated with the artifact.
     * @throws ClientAuthenticationException thrown if the client's credentials do not have access to modify the namespace.
     * @throws ResourceNotFoundException thrown if the given tag was not found.
     * @return tag properties by tag.
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    public TagProperties getTagProperties(String tag) {
        return getTagPropertiesWithResponse(tag, Context.NONE).getValue();
    }

    /**
     * List tags of a repository.
     *
     * @throws ClientAuthenticationException thrown if the client's credentials do not have access to modify the namespace.
     * @return list of tag details.
     */
    @ServiceMethod(returns = ReturnType.COLLECTION)
    public PagedIterable<TagProperties> listTags() {
        return listTags(null);
    }

    /**
     * List tags of a repository.
     *
     * @param options options associated with the operation.
     * @throws ClientAuthenticationException thrown if the client's credentials do not have access to modify the namespace.
     * @return list of tag details.
     */
    @ServiceMethod(returns = ReturnType.COLLECTION)
    public PagedIterable<TagProperties> listTags(ListTagsOptions options) {
        return new PagedIterable<TagProperties>(asyncClient.listTags(options));
    }

    /**
     * Update the content properties of the repository.
     *
     * @param value Content properties to be set.
     * @param context Context associated with the operation.
     * @throws ClientAuthenticationException thrown if the client's credentials do not have access to modify the namespace.
     * @return the completion.
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    Response<Void> setPropertiesWithResponse(ContentProperties value, Context context) {
        return this.asyncClient.setPropertiesWithResponse(value, context).block();
    }

    /**
     * Update the content properties of the repository.
     *
     * @param value Content properties to be set.
     * @throws ClientAuthenticationException thrown if the client's credentials do not have access to modify the namespace.
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    public void setProperties(ContentProperties value) {
        this.setPropertiesWithResponse(value, Context.NONE).getValue();
    }

    /**
     * Update tag properties.
     *
     * @param tag Name of the tag.
     * @param value Content properties to be set.
     * @param context Context associated with the operation.
     * @throws ClientAuthenticationException thrown if the client's credentials do not have access to modify the namespace.
     * @throws ResourceNotFoundException thrown if the given tag was not found.
     * @return the completion.
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    public Response<Void> setTagPropertiesWithResponse(String tag, ContentProperties value, Context context) {
        return this.asyncClient.setTagPropertiesWithResponse(tag, value, context).block();
    }

    /**
     * Update tag properties.
     *
     * @param tag Name of the tag.
     * @param value content properties to be set.
     * @throws ClientAuthenticationException thrown if the client's credentials do not have access to modify the namespace.
     * @throws ResourceNotFoundException thrown if the given tag was not found.
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    public void setTagProperties(String tag, ContentProperties value) {
        this.setTagPropertiesWithResponse(tag, value, Context.NONE).getValue();
    }

    /**
     * Update properties of a manifest.
     *
     * @param digest digest of an artifact.
     * @param value content properties to be set.
     * @param context Context associated with the operation.
     * @throws ClientAuthenticationException thrown if the client's credentials do not have access to modify the namespace.
     * @throws ResourceNotFoundException thrown if the given digest was not found.
     * @return the completion.
     */
    public Response<Void> setManifestPropertiesWithResponse(String digest, ContentProperties value, Context context) {
        return this.asyncClient.setManifestPropertiesWithResponse(digest, value, context).block();
    }

    /**
     * Update properties of a manifest.
     *
     * @param digest digest of an artifact.
     * @param value content properties to be set.
     * @throws ClientAuthenticationException thrown if the client's credentials do not have access to modify the namespace.
     * @throws ResourceNotFoundException thrown if the given digest was not found.
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    public void setManifestProperties(String digest, ContentProperties value) {
        this.setManifestPropertiesWithResponse(digest, value, Context.NONE).getValue();
    }
}
