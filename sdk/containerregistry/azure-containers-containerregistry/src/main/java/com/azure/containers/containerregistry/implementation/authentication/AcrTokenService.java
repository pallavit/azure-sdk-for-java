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
import com.azure.core.http.HttpPipelineBuilder;
import com.azure.core.http.policy.*;
import com.azure.core.util.Configuration;
import com.azure.core.util.serializer.JacksonAdapter;
import com.azure.core.util.serializer.SerializerAdapter;
import reactor.core.publisher.Mono;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Supplier;

public final class AcrTokenService {

    private final AccessTokensImpl accessTokensImpl;
    private final RefreshTokensImpl refreshTokenImpl;

    private static final String SDK_NAME = "name";
    private static final String SDK_VERSION = "version";
    private final Map<String, String> properties = new HashMap<>();

    public AcrTokenService(String url) {
        this.endpoint = url;
//        this.serviceName = getServiceNameFromEndpoint(url);

        HttpPipeline pipeline = createHttpPipeline();
        this.accessTokensImpl = new AccessTokensImpl(url, pipeline, JacksonAdapter.createDefaultSerializerAdapter());
        this.refreshTokenImpl = new RefreshTokensImpl(url, pipeline, JacksonAdapter.createDefaultSerializerAdapter());
    }

    private String endpoint;
//    private String serviceName;

    private HttpPipeline createHttpPipeline() {
        Configuration buildConfiguration =
            Configuration.getGlobalConfiguration();


        HttpLogOptions httpLogOptions = new HttpLogOptions();

        List<HttpPipelinePolicy> policies = new ArrayList<>();
        String clientName = properties.getOrDefault(SDK_NAME, "UnknownName");
        String clientVersion = properties.getOrDefault(SDK_VERSION, "UnknownVersion");
        policies.add(
            new UserAgentPolicy(httpLogOptions.getApplicationId(), clientName, clientVersion, buildConfiguration));
        HttpPolicyProviders.addBeforeRetryPolicies(policies);
        policies.add(new RetryPolicy());
        policies.add(new CookiePolicy());

        HttpPolicyProviders.addAfterRetryPolicies(policies);
        policies.add(new HttpLoggingPolicy(httpLogOptions));

        HttpPipeline httpPipeline =
            new HttpPipelineBuilder()
                .policies(policies.toArray(new HttpPipelinePolicy[0]))
                .build();
        return httpPipeline;
    }

    public Mono<AccessToken> getAcrAccessTokenAsync(String acrRefreshToken, String scope, String serviceName)
    {
        return this.accessTokensImpl.getAccessTokenAsync(PostContentSchemaGrantType.REFRESH_TOKEN.toString(), serviceName, scope, acrRefreshToken)
            .map(token -> {
                String accessToken = token.getAccessToken();
                OffsetDateTime expirationTime = JsonWebToken.retrieveExpiration(accessToken);
                return new AccessToken(accessToken, expirationTime);
            });
    }

    public Mono<AccessToken> getAcrRefreshTokenAsync(String aadAccessToken, String serviceName) {
        return this.refreshTokenImpl.getRefreshTokenAsync(
            PostContentSchemaGrantType.ACCESS_TOKEN.toString(),
            aadAccessToken,
            null,
            serviceName).map(token -> {
                String refreshToken = token.getRefreshToken();
            OffsetDateTime expirationTime = JsonWebToken.retrieveExpiration(refreshToken);
            return new AccessToken(refreshToken, expirationTime);
        });
    }
}
