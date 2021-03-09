// Copyright (c) Microsoft Corporation. All rights reserved.
// Licensed under the MIT License.
package com.azure.containers.containerregistry.implementation.authentication;

import com.azure.containers.containerregistry.implementation.models.AcrRefreshToken;
import com.azure.containers.containerregistry.implementation.models.Oauth2ExchangePostRequestbody;
import com.azure.containers.containerregistry.implementation.models.Oauth2TokenPostRequestbody;
import com.azure.containers.containerregistry.implementation.models.PostContentSchemaGrantType;
import com.azure.core.credential.AccessToken;
import com.azure.core.credential.SimpleTokenCache;
import com.azure.core.http.HttpPipeline;
import com.azure.core.util.serializer.SerializerAdapter;
import reactor.core.publisher.Mono;

import java.util.concurrent.ConcurrentHashMap;

public final class AcrTokenService {

    SimpleTokenCache refreshToken;
    SimpleTokenCache accessToken;

    private final AccessTokensImpl accessTokensImpl;
    private final RefreshTokensImpl refreshTokenImpl;

    AcrTokenService(String url, HttpPipeline pipeline, SerializerAdapter adapter) {
        this.endpoint = url;
        serviceName = getServiceNameFromEndpoint(url);
        this.accessTokensImpl = new AccessTokensImpl(url, pipeline, adapter);
        this.refreshTokenImpl = new RefreshTokensImpl(url, pipeline, adapter);
        refreshToken = new SimpleTokenCache()
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

    public Mono<AccessToken> getAccessTokenFromAadToken(String tenant, String aadRefreshToken, String aadAccessToken, String scope)
    {
        return this.getAcrRefreshTokenAsync(tenant, aadRefreshToken, aadAccessToken, scope)
            .flatMap(token -> this.getAccessTokenAsync(token.getRefreshToken(), scope));
    }

    private Mono<AcrRefreshToken> getAcrRefreshTokenAsync(String tenant, String refreshToken, String accessToken,String scope) {

        var service = "pallavitacr.azurecr.io"; // endpoint.substring("https:///".length() - 1);
        var grant_type = "access_token";
//        var refreshTokenCallContent = new Oauth2ExchangePostRequestbody()
//            .setService(endpoint)
//            .setTenant(tenant)
//            .setRefreshToken(refreshToken)
//            .setAccessToken(accessToken)
//            .setGrantType(accessToken != null
//                ? (refreshToken != null ? PostContentSchemaGrantType.ACCESS_TOKEN_REFRESH_TOKEN: PostContentSchemaGrantType.ACCESS_TOKEN)
//                : PostContentSchemaGrantType.REFRESH_TOKEN );

        return this.refreshTokenImpl.getRefreshTokenAsync(PostContentSchemaGrantType.ACCESS_TOKEN.toString(), accessToken, tenant, service);
    }
}
