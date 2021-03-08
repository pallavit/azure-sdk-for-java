package com.azure.containers.containerregistry.implementation.authentication;

import com.azure.containers.containerregistry.implementation.models.AcrAccessToken;
import com.azure.containers.containerregistry.implementation.models.AcrErrorsException;
import com.azure.containers.containerregistry.implementation.models.Oauth2TokenPostRequestbody;
import com.azure.core.annotation.*;
import com.azure.core.http.HttpPipeline;
import com.azure.core.http.rest.Response;
import com.azure.core.http.rest.RestProxy;
import com.azure.core.util.Context;
import com.azure.core.util.FluxUtil;
import com.azure.core.util.serializer.SerializerAdapter;
import reactor.core.publisher.Mono;

/** An instance of this class provides access to all the operations defined in AccessTokens. */
final class AccessTokensImpl {

    /** The proxy service used to perform REST calls. */
    private final AccessTokensService service;

    /** Registry login URL. */
    private final String url;

    /**
     * Gets Registry login URL.
     *
     * @return the url value.
     */
    public String getUrl() {
        return this.url;
    }



    AccessTokensImpl(String url, HttpPipeline httpPipeline, SerializerAdapter serializerAdapter) {
        this.service =
            RestProxy.create(AccessTokensService.class, httpPipeline, serializerAdapter);
        this.url = url;
    }

    /**
     * The interface defining all the services for ContainerRegistryAccessTokens to be used by the proxy service to
     * perform REST calls.
     */
    @Host("{url}")
    @ServiceInterface(name = "ContainerRegistryAcc")
    private interface AccessTokensService {
        // @Multipart not supported by RestProxy
        @Post("/oauth2/token")
        @ExpectedResponses({200})
        @UnexpectedResponseExceptionType(AcrErrorsException.class)
        Mono<Response<AcrAccessToken>> getAccessToken(
            @HostParam("url") String url,
            @BodyParam("application/x-www-form-urlencoded")
                Oauth2TokenPostRequestbody refreshToken,
            @HeaderParam("Accept") String accept,
            Context context);
    }

    /**
     * Exchange ACR Refresh token for an ACR Access Token.
     *
     * @param refreshToken The refreshToken parameter.
     * @throws IllegalArgumentException thrown if parameters fail the validation.
     * @throws AcrErrorsException thrown if the request is rejected by server.
     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent.
     * @return the response.
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    public Mono<Response<AcrAccessToken>> getAccessTokenWithResponseAsync(
        Oauth2TokenPostRequestbody refreshToken) {
        final String accept = "application/json";
        return FluxUtil.withContext(
            context -> service.getAccessToken(getUrl(), refreshToken, accept, context));
    }

    /**
     * Exchange ACR Refresh token for an ACR Access Token.
     *
     * @param refreshToken The refreshToken parameter.
     * @param context The context to associate with this operation.
     * @throws IllegalArgumentException thrown if parameters fail the validation.
     * @throws AcrErrorsException thrown if the request is rejected by server.
     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent.
     * @return the response.
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    public Mono<Response<AcrAccessToken>> getAccessTokenWithResponseAsync(
        Oauth2TokenPostRequestbody refreshToken,
        Context context) {
        final String accept = "application/json";
        return service.getAccessToken(getUrl(), refreshToken, accept, context);
    }

    /**
     * Exchange ACR Refresh token for an ACR Access Token.
     *
     * @param refreshToken The refreshToken parameter.
     * @throws IllegalArgumentException thrown if parameters fail the validation.
     * @throws AcrErrorsException thrown if the request is rejected by server.
     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent.
     * @return the response.
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    public Mono<AcrAccessToken> getAccessTokenAsync(
        Oauth2TokenPostRequestbody refreshToken) {
        return getAccessTokenWithResponseAsync(refreshToken)
            .flatMap(
                (Response<AcrAccessToken> res) -> {
                    if (res.getValue() != null) {
                        return Mono.just(res.getValue());
                    } else {
                        return Mono.empty();
                    }
                });
    }

    /**
     * Exchange ACR Refresh token for an ACR Access Token.
     *
     * @param refreshToken The refreshToken parameter.
     * @param context The context to associate with this operation.
     * @throws IllegalArgumentException thrown if parameters fail the validation.
     * @throws AcrErrorsException thrown if the request is rejected by server.
     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent.
     * @return the response.
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    public Mono<AcrAccessToken> getAccessTokenAsync(
        Oauth2TokenPostRequestbody refreshToken,
        Context context) {
        return getAccessTokenWithResponseAsync(refreshToken, context)
            .flatMap(
                (Response<AcrAccessToken> res) -> {
                    if (res.getValue() != null) {
                        return Mono.just(res.getValue());
                    } else {
                        return Mono.empty();
                    }
                });
    }

    /**
     * Exchange ACR Refresh token for an ACR Access Token.
     *
     * @param refreshToken The refreshToken parameter.
     * @throws IllegalArgumentException thrown if parameters fail the validation.
     * @throws AcrErrorsException thrown if the request is rejected by server.
     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent.
     * @return the response.
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    public AcrAccessToken getAccessToken(
        Oauth2TokenPostRequestbody refreshToken) {
        return getAccessTokenAsync(refreshToken).block();
    }

    /**
     * Exchange ACR Refresh token for an ACR Access Token.
     *
     * @param refreshToken The refreshToken parameter.
     * @param context The context to associate with this operation.
     * @throws IllegalArgumentException thrown if parameters fail the validation.
     * @throws AcrErrorsException thrown if the request is rejected by server.
     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent.
     * @return the response.
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    public Response<AcrAccessToken> getAccessTokenWithResponse(
        Oauth2TokenPostRequestbody refreshToken,
        Context context) {
        return getAccessTokenWithResponseAsync(refreshToken, context).block();
    }
}
