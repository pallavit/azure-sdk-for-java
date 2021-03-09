// Copyright (c) Microsoft Corporation. All rights reserved.
// Licensed under the MIT License.
package com.azure.containers.containerregistry.implementation.authentication;

import com.azure.containers.containerregistry.implementation.models.AcrRefreshToken;
import com.azure.containers.containerregistry.implementation.models.Oauth2ExchangePostRequestbody;
import com.azure.containers.containerregistry.implementation.models.Oauth2TokenPostRequestbody;
import com.azure.containers.containerregistry.implementation.models.PostContentSchemaGrantType;
import com.azure.core.credential.AccessToken;
import com.azure.core.credential.SimpleTokenCache;
import com.azure.core.credential.TokenRequestContext;
import com.azure.core.http.HttpPipeline;
import com.azure.core.util.serializer.SerializerAdapter;
import reactor.core.publisher.Mono;

import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Supplier;

public final class AcrTokenService {

    SimpleTokenCache refreshToken;
    SimpleTokenCache accessToken;

    private final AccessTokensImpl accessTokensImpl;
    private final RefreshTokensImpl refreshTokenImpl;

    AcrTokenService(String url, HttpPipeline pipeline, SerializerAdapter adapter, Supplier<Mono<TokenRequestContext>> tokenRequestContext) {
        this.endpoint = url;
        this.serviceName = getServiceNameFromEndpoint(url);
        this.accessTokensImpl = new AccessTokensImpl(url, pipeline, adapter);
        this.refreshTokenImpl = new RefreshTokensImpl(url, pipeline, adapter);
    }

    private String endpoint;
    private String serviceName;

    private static String getServiceNameFromEndpoint(String endpoint)
    {
        return endpoint.substring("https://".length());
    }

    public Mono<AccessToken> getAccessTokenAsync(String acrRefreshToken, String scope)
    {
        final String grant_type = "refresh_token";

        return this.accessTokensImpl.getAccessTokenAsync(grant_type, serviceName, scope, acrRefreshToken)
            .map(token -> {
                var accessToken = token.getAccessToken();
                var expirationTime = JsonWebToken.retrieveExpiration(accessToken);
                return new AccessToken(accessToken, expirationTime);
            });
    }

    public Mono<AccessToken> getAcrRefreshTokenAsync(String accessToken, String scope) {
        return this.refreshTokenImpl.getRefreshTokenAsync(
            PostContentSchemaGrantType.ACCESS_TOKEN.toString(),
            accessToken,
            null,
            serviceName).map(token -> {
                var refreshToken = token.getRefreshToken();
            var expirationTime = JsonWebToken.retrieveExpiration(refreshToken);
            return new AccessToken(refreshToken, expirationTime);
        });
    }
}
