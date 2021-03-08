// Copyright (c) Microsoft Corporation. All rights reserved.
// Licensed under the MIT License.
// Code generated by Microsoft (R) AutoRest Code Generator.

package com.azure.containers.containerregistry.implementation;

import com.azure.containers.containerregistry.implementation.models.AcrErrorsException;
import com.azure.containers.containerregistry.implementation.models.ContainerRegistriesGetRepositoriesResponse;

import com.azure.containers.containerregistry.implementation.models.Repositories;
import com.azure.core.annotation.ExpectedResponses;
import com.azure.core.annotation.Get;
import com.azure.core.annotation.HeaderParam;
import com.azure.core.annotation.Host;
import com.azure.core.annotation.HostParam;
import com.azure.core.annotation.QueryParam;
import com.azure.core.annotation.ReturnType;
import com.azure.core.annotation.ServiceInterface;
import com.azure.core.annotation.ServiceMethod;
import com.azure.core.annotation.UnexpectedResponseExceptionType;
import com.azure.core.http.rest.Response;
import com.azure.core.http.rest.RestProxy;
import com.azure.core.util.Context;
import com.azure.core.util.FluxUtil;
import reactor.core.publisher.Mono;

/** An instance of this class provides access to all the operations defined in ContainerRegistries. */
public final class ContainerRegistriesImpl {
    /** The proxy service used to perform REST calls. */
    private final ContainerRegistriesService service;

    /** The service client containing this operation class. */
    private final ContainerRegistryImpl client;

    /**
     * Initializes an instance of ContainerRegistriesImpl.
     *
     * @param client the instance of the service client containing this operation class.
     */
    ContainerRegistriesImpl(ContainerRegistryImpl client) {
        this.service =
                RestProxy.create(
                        ContainerRegistriesService.class, client.getHttpPipeline(), client.getSerializerAdapter());
        this.client = client;
    }

    /**
     * The interface defining all the services for ContainerRegistryContainerRegistries to be used by the proxy service
     * to perform REST calls.
     */
    @Host("{url}")
    @ServiceInterface(name = "ContainerRegistryCon")
    private interface ContainerRegistriesService {
//        @Get("/v2/")
//        @ExpectedResponses({200})
//        @UnexpectedResponseExceptionType(AcrErrorsException.class)
//        Mono<Response<Void>> checkDockerV2Support(
//                @HostParam("url") String url, @HeaderParam("Accept") String accept, Context context);

        @Get("/acr/v1/_catalog")
        @ExpectedResponses({200})
        @UnexpectedResponseExceptionType(AcrErrorsException.class)
        Mono<ContainerRegistriesGetRepositoriesResponse> getRepositories(
                @HostParam("url") String url,
                @QueryParam("last") String last,
                @QueryParam("n") Integer n,
                @HeaderParam("Accept") String accept,
                Context context);

//        @Get("/acr/v1/{name}")
//        @ExpectedResponses({200})
//        @UnexpectedResponseExceptionType(AcrErrorsException.class)
//        Mono<Response<RepositoryProperties>> getRepositoryAttributes(
//                @HostParam("url") String url,
//                @PathParam("name") String name,
//                @HeaderParam("Accept") String accept,
//                Context context);
//
//        @Delete("/acr/v1/{name}")
//        @ExpectedResponses({202})
//        @UnexpectedResponseExceptionType(AcrErrorsException.class)
//        Mono<Response<DeletedRepository>> deleteRepository(
//                @HostParam("url") String url,
//                @PathParam("name") String name,
//                @HeaderParam("Accept") String accept,
//                Context context);
//
//        @Patch("/acr/v1/{name}")
//        @ExpectedResponses({200})
//        @UnexpectedResponseExceptionType(AcrErrorsException.class)
//        Mono<Response<Void>> updateRepositoryAttributes(
//                @HostParam("url") String url,
//                @PathParam("name") String name,
//                @BodyParam("application/json") ContentPermissions value,
//                @HeaderParam("Accept") String accept,
//                Context context);
    }

//    /**
//     * Tells whether this Docker Registry instance supports Docker Registry HTTP API v2.
//     *
//     * @throws AcrErrorsException thrown if the request is rejected by server.
//     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent.
//     * @return the completion.
//     */
//    @ServiceMethod(returns = ReturnType.SINGLE)
//    public Mono<Response<Void>> checkDockerV2SupportWithResponseAsync() {
//        final String accept = "application/json";
//        return FluxUtil.withContext(context -> service.checkDockerV2Support(this.client.getUrl(), accept, context));
//    }
//
//    /**
//     * Tells whether this Docker Registry instance supports Docker Registry HTTP API v2.
//     *
//     * @param context The context to associate with this operation.
//     * @throws IllegalArgumentException thrown if parameters fail the validation.
//     * @throws AcrErrorsException thrown if the request is rejected by server.
//     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent.
//     * @return the completion.
//     */
//    @ServiceMethod(returns = ReturnType.SINGLE)
//    public Mono<Response<Void>> checkDockerV2SupportWithResponseAsync(Context context) {
//        final String accept = "application/json";
//        return service.checkDockerV2Support(this.client.getUrl(), accept, context);
//    }
//
//    /**
//     * Tells whether this Docker Registry instance supports Docker Registry HTTP API v2.
//     *
//     * @throws AcrErrorsException thrown if the request is rejected by server.
//     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent.
//     * @return the completion.
//     */
//    @ServiceMethod(returns = ReturnType.SINGLE)
//    public Mono<Void> checkDockerV2SupportAsync() {
//        return checkDockerV2SupportWithResponseAsync().flatMap((Response<Void> res) -> Mono.empty());
//    }
//
//    /**
//     * Tells whether this Docker Registry instance supports Docker Registry HTTP API v2.
//     *
//     * @param context The context to associate with this operation.
//     * @throws IllegalArgumentException thrown if parameters fail the validation.
//     * @throws AcrErrorsException thrown if the request is rejected by server.
//     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent.
//     * @return the completion.
//     */
//    @ServiceMethod(returns = ReturnType.SINGLE)
//    public Mono<Void> checkDockerV2SupportAsync(Context context) {
//        return checkDockerV2SupportWithResponseAsync(context).flatMap((Response<Void> res) -> Mono.empty());
//    }
//
//    /**
//     * Tells whether this Docker Registry instance supports Docker Registry HTTP API v2.
//     *
//     * @throws AcrErrorsException thrown if the request is rejected by server.
//     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent.
//     */
//    @ServiceMethod(returns = ReturnType.SINGLE)
//    public void checkDockerV2Support() {
//        checkDockerV2SupportAsync().block();
//    }
//
//    /**
//     * Tells whether this Docker Registry instance supports Docker Registry HTTP API v2.
//     *
//     * @param context The context to associate with this operation.
//     * @throws IllegalArgumentException thrown if parameters fail the validation.
//     * @throws AcrErrorsException thrown if the request is rejected by server.
//     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent.
//     * @return the response.
//     */
//    @ServiceMethod(returns = ReturnType.SINGLE)
//    public Response<Void> checkDockerV2SupportWithResponse(Context context) {
//        return checkDockerV2SupportWithResponseAsync(context).block();
//    }

    /**
     * List repositories.
     *
     * @param last Query parameter for the last item in previous query. Result set will include values lexically after
     *     last.
     * @param n query parameter for max number of items.
     * @throws IllegalArgumentException thrown if parameters fail the validation.
     * @throws AcrErrorsException thrown if the request is rejected by server.
     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent.
     * @return list of repositories.
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    public Mono<ContainerRegistriesGetRepositoriesResponse> getRepositoriesWithResponseAsync(String last, Integer n) {
        final String accept = "application/json";
        return FluxUtil.withContext(context -> service.getRepositories(this.client.getUrl(), last, n, accept, context));
    }

    /**
     * List repositories.
     *
     * @param last Query parameter for the last item in previous query. Result set will include values lexically after
     *     last.
     * @param n query parameter for max number of items.
     * @param context The context to associate with this operation.
     * @throws IllegalArgumentException thrown if parameters fail the validation.
     * @throws AcrErrorsException thrown if the request is rejected by server.
     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent.
     * @return list of repositories.
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    public Mono<ContainerRegistriesGetRepositoriesResponse> getRepositoriesWithResponseAsync(
            String last, Integer n, Context context) {
        final String accept = "application/json";
        return service.getRepositories(this.client.getUrl(), last, n, accept, context);
    }

   /* *//**
     * List repositories.
     *
     * @param last Query parameter for the last item in previous query. Result set will include values lexically after
     *     last.
     * @param n query parameter for max number of items.
     * @throws IllegalArgumentException thrown if parameters fail the validation.
     * @throws AcrErrorsException thrown if the request is rejected by server.
     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent.
     * @return list of repositories.
     *//*
    @ServiceMethod(returns = ReturnType.SINGLE)
    public Mono<Repositories> getRepositoriesAsync(String last, Integer n) {
        return getRepositoriesWithResponseAsync(last, n)
                .flatMap(
                        (ContainerRegistriesGetRepositoriesResponse res) -> {
                            if (res.getValue() != null) {
                                return Mono.just(res.getValue());
                            } else {
                                return Mono.empty();
                            }
                        });
    }

    *//**
     * List repositories.
     *
     * @param last Query parameter for the last item in previous query. Result set will include values lexically after
     *     last.
     * @param n query parameter for max number of items.
     * @param context The context to associate with this operation.
     * @throws IllegalArgumentException thrown if parameters fail the validation.
     * @throws AcrErrorsException thrown if the request is rejected by server.
     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent.
     * @return list of repositories.
     *//*
    @ServiceMethod(returns = ReturnType.SINGLE)
    public Mono<Repositories> getRepositoriesAsync(String last, Integer n, Context context) {
        return getRepositoriesWithResponseAsync(last, n, context)
                .flatMap(
                        (ContainerRegistriesGetRepositoriesResponse res) -> {
                            if (res.getValue() != null) {
                                return Mono.just(res.getValue());
                            } else {
                                return Mono.empty();
                            }
                        });
    }*/

//    /**
//     * List repositories.
//     *
//     * @param last Query parameter for the last item in previous query. Result set will include values lexically after
//     *     last.
//     * @param n query parameter for max number of items.
//     * @throws IllegalArgumentException thrown if parameters fail the validation.
//     * @throws AcrErrorsException thrown if the request is rejected by server.
//     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent.
//     * @return list of repositories.
//     */
//    @ServiceMethod(returns = ReturnType.SINGLE)
//    public Repositories getRepositories(String last, Integer n) {
//        return getRepositoriesAsync(last, n).block();
//    }

    /**
     * List repositories.
     *
     * @param last Query parameter for the last item in previous query. Result set will include values lexically after
     *     last.
     * @param n query parameter for max number of items.
     * @param context The context to associate with this operation.
     * @throws IllegalArgumentException thrown if parameters fail the validation.
     * @throws AcrErrorsException thrown if the request is rejected by server.
     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent.
     * @return list of repositories.
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    public Response<Repositories> getRepositoriesWithResponse(String last, Integer n, Context context) {
        return getRepositoriesWithResponseAsync(last, n, context).block();
    }

//    /**
//     * Get repository attributes.
//     *
//     * @param name Name of the image (including the namespace).
//     * @throws IllegalArgumentException thrown if parameters fail the validation.
//     * @throws AcrErrorsException thrown if the request is rejected by server.
//     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent.
//     * @return repository attributes.
//     */
//    @ServiceMethod(returns = ReturnType.SINGLE)
//    public Mono<Response<RepositoryProperties>> getRepositoryAttributesWithResponseAsync(String name) {
//        final String accept = "application/json";
//        return FluxUtil.withContext(
//                context -> service.getRepositoryAttributes(this.client.getUrl(), name, accept, context));
//    }
//
//    /**
//     * Get repository attributes.
//     *
//     * @param name Name of the image (including the namespace).
//     * @param context The context to associate with this operation.
//     * @throws IllegalArgumentException thrown if parameters fail the validation.
//     * @throws AcrErrorsException thrown if the request is rejected by server.
//     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent.
//     * @return repository attributes.
//     */
//    @ServiceMethod(returns = ReturnType.SINGLE)
//    public Mono<Response<RepositoryProperties>> getRepositoryAttributesWithResponseAsync(String name, Context context) {
//        final String accept = "application/json";
//        return service.getRepositoryAttributes(this.client.getUrl(), name, accept, context);
//    }
//
//    /**
//     * Get repository attributes.
//     *
//     * @param name Name of the image (including the namespace).
//     * @throws IllegalArgumentException thrown if parameters fail the validation.
//     * @throws AcrErrorsException thrown if the request is rejected by server.
//     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent.
//     * @return repository attributes.
//     */
//    @ServiceMethod(returns = ReturnType.SINGLE)
//    public Mono<RepositoryProperties> getRepositoryAttributesAsync(String name) {
//        return getRepositoryAttributesWithResponseAsync(name)
//                .flatMap(
//                        (Response<RepositoryProperties> res) -> {
//                            if (res.getValue() != null) {
//                                return Mono.just(res.getValue());
//                            } else {
//                                return Mono.empty();
//                            }
//                        });
//    }
//
//    /**
//     * Get repository attributes.
//     *
//     * @param name Name of the image (including the namespace).
//     * @param context The context to associate with this operation.
//     * @throws IllegalArgumentException thrown if parameters fail the validation.
//     * @throws AcrErrorsException thrown if the request is rejected by server.
//     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent.
//     * @return repository attributes.
//     */
//    @ServiceMethod(returns = ReturnType.SINGLE)
//    public Mono<RepositoryProperties> getRepositoryAttributesAsync(String name, Context context) {
//        return getRepositoryAttributesWithResponseAsync(name, context)
//                .flatMap(
//                        (Response<RepositoryProperties> res) -> {
//                            if (res.getValue() != null) {
//                                return Mono.just(res.getValue());
//                            } else {
//                                return Mono.empty();
//                            }
//                        });
//    }
//
//    /**
//     * Get repository attributes.
//     *
//     * @param name Name of the image (including the namespace).
//     * @throws IllegalArgumentException thrown if parameters fail the validation.
//     * @throws AcrErrorsException thrown if the request is rejected by server.
//     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent.
//     * @return repository attributes.
//     */
//    @ServiceMethod(returns = ReturnType.SINGLE)
//    public RepositoryProperties getRepositoryAttributes(String name) {
//        return getRepositoryAttributesAsync(name).block();
//    }
//
//    /**
//     * Get repository attributes.
//     *
//     * @param name Name of the image (including the namespace).
//     * @param context The context to associate with this operation.
//     * @throws IllegalArgumentException thrown if parameters fail the validation.
//     * @throws AcrErrorsException thrown if the request is rejected by server.
//     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent.
//     * @return repository attributes.
//     */
//    @ServiceMethod(returns = ReturnType.SINGLE)
//    public Response<RepositoryProperties> getRepositoryAttributesWithResponse(String name, Context context) {
//        return getRepositoryAttributesWithResponseAsync(name, context).block();
//    }
//
//    /**
//     * Delete the repository identified by `name`.
//     *
//     * @param name Name of the image (including the namespace).
//     * @throws IllegalArgumentException thrown if parameters fail the validation.
//     * @throws AcrErrorsException thrown if the request is rejected by server.
//     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent.
//     * @return deleted repository.
//     */
//    @ServiceMethod(returns = ReturnType.SINGLE)
//    public Mono<Response<DeletedRepository>> deleteRepositoryWithResponseAsync(String name) {
//        final String accept = "application/json";
//        return FluxUtil.withContext(context -> service.deleteRepository(this.client.getUrl(), name, accept, context));
//    }
//
//    /**
//     * Delete the repository identified by `name`.
//     *
//     * @param name Name of the image (including the namespace).
//     * @param context The context to associate with this operation.
//     * @throws IllegalArgumentException thrown if parameters fail the validation.
//     * @throws AcrErrorsException thrown if the request is rejected by server.
//     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent.
//     * @return deleted repository.
//     */
//    @ServiceMethod(returns = ReturnType.SINGLE)
//    public Mono<Response<DeletedRepository>> deleteRepositoryWithResponseAsync(String name, Context context) {
//        final String accept = "application/json";
//        return service.deleteRepository(this.client.getUrl(), name, accept, context);
//    }
//
//    /**
//     * Delete the repository identified by `name`.
//     *
//     * @param name Name of the image (including the namespace).
//     * @throws IllegalArgumentException thrown if parameters fail the validation.
//     * @throws AcrErrorsException thrown if the request is rejected by server.
//     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent.
//     * @return deleted repository.
//     */
//    @ServiceMethod(returns = ReturnType.SINGLE)
//    public Mono<DeletedRepository> deleteRepositoryAsync(String name) {
//        return deleteRepositoryWithResponseAsync(name)
//                .flatMap(
//                        (Response<DeletedRepository> res) -> {
//                            if (res.getValue() != null) {
//                                return Mono.just(res.getValue());
//                            } else {
//                                return Mono.empty();
//                            }
//                        });
//    }
//
//    /**
//     * Delete the repository identified by `name`.
//     *
//     * @param name Name of the image (including the namespace).
//     * @param context The context to associate with this operation.
//     * @throws IllegalArgumentException thrown if parameters fail the validation.
//     * @throws AcrErrorsException thrown if the request is rejected by server.
//     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent.
//     * @return deleted repository.
//     */
//    @ServiceMethod(returns = ReturnType.SINGLE)
//    public Mono<DeletedRepository> deleteRepositoryAsync(String name, Context context) {
//        return deleteRepositoryWithResponseAsync(name, context)
//                .flatMap(
//                        (Response<DeletedRepository> res) -> {
//                            if (res.getValue() != null) {
//                                return Mono.just(res.getValue());
//                            } else {
//                                return Mono.empty();
//                            }
//                        });
//    }
//
//    /**
//     * Delete the repository identified by `name`.
//     *
//     * @param name Name of the image (including the namespace).
//     * @throws IllegalArgumentException thrown if parameters fail the validation.
//     * @throws AcrErrorsException thrown if the request is rejected by server.
//     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent.
//     * @return deleted repository.
//     */
//    @ServiceMethod(returns = ReturnType.SINGLE)
//    public DeletedRepository deleteRepository(String name) {
//        return deleteRepositoryAsync(name).block();
//    }
//
//    /**
//     * Delete the repository identified by `name`.
//     *
//     * @param name Name of the image (including the namespace).
//     * @param context The context to associate with this operation.
//     * @throws IllegalArgumentException thrown if parameters fail the validation.
//     * @throws AcrErrorsException thrown if the request is rejected by server.
//     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent.
//     * @return deleted repository.
//     */
//    @ServiceMethod(returns = ReturnType.SINGLE)
//    public Response<DeletedRepository> deleteRepositoryWithResponse(String name, Context context) {
//        return deleteRepositoryWithResponseAsync(name, context).block();
//    }
//
//    /**
//     * Update the attribute identified by `name` where `reference` is the name of the repository.
//     *
//     * @param name Name of the image (including the namespace).
//     * @param value Repository attribute value.
//     * @throws IllegalArgumentException thrown if parameters fail the validation.
//     * @throws AcrErrorsException thrown if the request is rejected by server.
//     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent.
//     * @return the completion.
//     */
//    @ServiceMethod(returns = ReturnType.SINGLE)
//    public Mono<Response<Void>> updateRepositoryAttributesWithResponseAsync(String name, ContentPermissions value) {
//        final String accept = "application/json";
//        return FluxUtil.withContext(
//                context -> service.updateRepositoryAttributes(this.client.getUrl(), name, value, accept, context));
//    }
//
//    /**
//     * Update the attribute identified by `name` where `reference` is the name of the repository.
//     *
//     * @param name Name of the image (including the namespace).
//     * @param value Repository attribute value.
//     * @param context The context to associate with this operation.
//     * @throws IllegalArgumentException thrown if parameters fail the validation.
//     * @throws AcrErrorsException thrown if the request is rejected by server.
//     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent.
//     * @return the completion.
//     */
//    @ServiceMethod(returns = ReturnType.SINGLE)
//    public Mono<Response<Void>> updateRepositoryAttributesWithResponseAsync(
//            String name, ContentPermissions value, Context context) {
//        final String accept = "application/json";
//        return service.updateRepositoryAttributes(this.client.getUrl(), name, value, accept, context);
//    }
//
//    /**
//     * Update the attribute identified by `name` where `reference` is the name of the repository.
//     *
//     * @param name Name of the image (including the namespace).
//     * @param value Repository attribute value.
//     * @throws IllegalArgumentException thrown if parameters fail the validation.
//     * @throws AcrErrorsException thrown if the request is rejected by server.
//     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent.
//     * @return the completion.
//     */
//    @ServiceMethod(returns = ReturnType.SINGLE)
//    public Mono<Void> updateRepositoryAttributesAsync(String name, ContentPermissions value) {
//        return updateRepositoryAttributesWithResponseAsync(name, value).flatMap((Response<Void> res) -> Mono.empty());
//    }
//
//    /**
//     * Update the attribute identified by `name` where `reference` is the name of the repository.
//     *
//     * @param name Name of the image (including the namespace).
//     * @param value Repository attribute value.
//     * @param context The context to associate with this operation.
//     * @throws IllegalArgumentException thrown if parameters fail the validation.
//     * @throws AcrErrorsException thrown if the request is rejected by server.
//     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent.
//     * @return the completion.
//     */
//    @ServiceMethod(returns = ReturnType.SINGLE)
//    public Mono<Void> updateRepositoryAttributesAsync(String name, ContentPermissions value, Context context) {
//        return updateRepositoryAttributesWithResponseAsync(name, value, context)
//                .flatMap((Response<Void> res) -> Mono.empty());
//    }
//
//    /**
//     * Update the attribute identified by `name` where `reference` is the name of the repository.
//     *
//     * @param name Name of the image (including the namespace).
//     * @param value Repository attribute value.
//     * @throws IllegalArgumentException thrown if parameters fail the validation.
//     * @throws AcrErrorsException thrown if the request is rejected by server.
//     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent.
//     */
//    @ServiceMethod(returns = ReturnType.SINGLE)
//    public void updateRepositoryAttributes(String name, ContentPermissions value) {
//        updateRepositoryAttributesAsync(name, value).block();
//    }
//
//    /**
//     * Update the attribute identified by `name` where `reference` is the name of the repository.
//     *
//     * @param name Name of the image (including the namespace).
//     * @param value Repository attribute value.
//     * @param context The context to associate with this operation.
//     * @throws IllegalArgumentException thrown if parameters fail the validation.
//     * @throws AcrErrorsException thrown if the request is rejected by server.
//     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent.
//     * @return the response.
//     */
//    @ServiceMethod(returns = ReturnType.SINGLE)
//    public Response<Void> updateRepositoryAttributesWithResponse(
//            String name, ContentPermissions value, Context context) {
//        return updateRepositoryAttributesWithResponseAsync(name, value, context).block();
//    }
}
