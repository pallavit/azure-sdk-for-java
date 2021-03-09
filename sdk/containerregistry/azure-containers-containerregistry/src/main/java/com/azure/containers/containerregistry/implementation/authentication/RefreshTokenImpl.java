package com.azure.containers.containerregistry.implementation.authentication;

import com.azure.containers.containerregistry.implementation.models.AcrErrorsException;
import com.azure.containers.containerregistry.implementation.models.AcrRefreshToken;
import com.azure.containers.containerregistry.implementation.models.Oauth2ExchangePostRequestbody;
import com.azure.core.annotation.*;
import com.azure.core.http.HttpPipeline;
import com.azure.core.http.rest.Response;
import com.azure.core.http.rest.RestProxy;
import com.azure.core.util.Context;
import com.azure.core.util.FluxUtil;
import com.azure.core.util.serializer.SerializerAdapter;
import reactor.core.publisher.Mono;

/** An instance of this class provides access to all the operations defined in ContainerRegistryRefreshTokens. */
final class RefreshTokensImpl {
    /** The proxy service used to perform REST calls. */
    private final RefreshTokenService service;

    /** Registry login URL. */
    private final String url;

    /**
     * Gets Registry login URL.
     *
     * @return the url value.
     */
    private String getUrl() {
        return this.url;
    }

    /**
     * Initializes an instance of ContainerRegistryRefreshTokensImpl.
     *
     * @param client the instance of the service client containing this operation class.
     */
    RefreshTokensImpl(String url, HttpPipeline httpPipeline, SerializerAdapter serializerAdapter) {
        this.service =
            RestProxy.create(
                RefreshTokenService.class,
                httpPipeline,
                serializerAdapter);
        this.url = url;
    }

    /**
     * The interface defining all the services for ContainerRegistryContainerRegistryRefreshTokens to be used by the
     * proxy service to perform REST calls.
     */
    @Host("{url}")
    @ServiceInterface(name = "ContainerRegistryCon")
    private interface RefreshTokenService {
        // @Multipart not supported by RestProxy
        @Post("/oauth2/exchange")
        @ExpectedResponses({200})
        @UnexpectedResponseExceptionType(AcrErrorsException.class)
        Mono<Response<AcrRefreshToken>> getRefreshToken(
            @HostParam("url") String url,
            @FormParam(value = "grant-type", encoded = true) String grantType,
            @FormParam(value = "service", encoded = true) String service,
            @FormParam(value = "access_token", encoded = true) String accessToken,
            @FormParam(value = "tenant", encoded = true) String tenant,
            @HeaderParam("Accept") String accept,
            Context context);
    }

    /**
     * Exchange AAD tokens for an ACR refresh Token.
     *
     * @param accessToken The accessToken parameter.
     * @throws IllegalArgumentException thrown if parameters fail the validation.
     * @throws AcrErrorsException thrown if the request is rejected by server.
     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent.
     * @return the response.
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    public Mono<Response<AcrRefreshToken>> getRefreshTokenWithResponseAsync(
        String grantType,
        String accessToken,
        String tenant,
        String serviceValue) {
        final String accept = "application/json";
        return FluxUtil.withContext(
            context -> service.getRefreshToken(this.getUrl(), grantType, serviceValue, accessToken, tenant, accept, context));
    }

//    /**
//     * Exchange AAD tokens for an ACR refresh Token.
//     *
//     * @param accessToken The accessToken parameter.
//     * @param context The context to associate with this operation.
//     * @throws IllegalArgumentException thrown if parameters fail the validation.
//     * @throws AcrErrorsException thrown if the request is rejected by server.
//     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent.
//     * @return the response.
//     */
//    @ServiceMethod(returns = ReturnType.SINGLE)
//    public Mono<Response<AcrRefreshToken>> getRefreshTokenWithResponseAsync(
//        String grantType, String accessToken, String tenant, String serviceValue,
//        Context context) {
//        final String accept = "application/json";
//        return service.getRefreshToken(this.getUrl(),grantType, accessToken, tenant, serviceValue , accept, context);
//    }
//
    /**
     * Exchange AAD tokens for an ACR refresh Token.
     *
     * @param accessToken The accessToken parameter.
     * @throws IllegalArgumentException thrown if parameters fail the validation.
     * @throws AcrErrorsException thrown if the request is rejected by server.
     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent.
     * @return the response.
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    public Mono<AcrRefreshToken> getRefreshTokenAsync(
        String grantType, String accessToken, String tenant, String service) {
        return getRefreshTokenWithResponseAsync(grantType, accessToken, tenant, service)
            .flatMap(
                (Response<AcrRefreshToken> res) -> {
                    if (res.getValue() != null) {
                        return Mono.just(res.getValue());
                    } else {
                        return Mono.empty();
                    }
                });
    }

//    /**
//     * Exchange AAD tokens for an ACR refresh Token.
//     *
//     * @param accessToken The accessToken parameter.
//     * @param context The context to associate with this operation.
//     * @throws IllegalArgumentException thrown if parameters fail the validation.
//     * @throws AcrErrorsException thrown if the request is rejected by server.
//     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent.
//     * @return the response.
//     */
//    @ServiceMethod(returns = ReturnType.SINGLE)
//    public Mono<AcrRefreshToken> getRefreshTokenAsync(
//        String grantType, String accessToken, String tenant, String service,
//        Context context) {
//        return getRefreshTokenWithResponseAsync(grantType, accessToken, tenant, service, context)
//            .flatMap(
//                (Response<AcrRefreshToken> res) -> {
//                    if (res.getValue() != null) {
//                        return Mono.just(res.getValue());
//                    } else {
//                        return Mono.empty();
//                    }
//                });
//    }
//
//    /**
//     * Exchange AAD tokens for an ACR refresh Token.
//     *
//     * @param accessToken The accessToken parameter.
//     * @throws IllegalArgumentException thrown if parameters fail the validation.
//     * @throws AcrErrorsException thrown if the request is rejected by server.
//     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent.
//     * @return the response.
//     */
//    @ServiceMethod(returns = ReturnType.SINGLE)
//    public AcrRefreshToken getRefreshToken(
//        String grantType, String accessToken, String tenant, String service) {
//        return getRefreshTokenAsync(grantType, accessToken, tenant, service).block();
//    }
//
//    /**
//     * Exchange AAD tokens for an ACR refresh Token.
//     *
//     * @param accessToken The accessToken parameter.
//     * @param context The context to associate with this operation.
//     * @throws IllegalArgumentException thrown if parameters fail the validation.
//     * @throws AcrErrorsException thrown if the request is rejected by server.
//     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent.
//     * @return the response.
//     */
//    @ServiceMethod(returns = ReturnType.SINGLE)
//    public Response<AcrRefreshToken> getRefreshTokenWithResponse(
//        String grantType, String accessToken, String tenant, String service,
//        Context context) {
//        return getRefreshTokenWithResponseAsync(grantType, accessToken, tenant, service, context).block();
//    }
}
