package com.azure.containers.containerregistry.implementation.credential;

import com.azure.containers.containerregistry.implementation.authentication.AcrTokenService;
import com.azure.core.credential.AccessToken;
import reactor.core.publisher.Mono;

public class AcrAccessTokenCredential implements AcrTokenCredential {

    private final AcrTokenService acrTokenService;

    public AcrAccessTokenCredential(AcrTokenService acrTokenService)
    {
        this.acrTokenService = acrTokenService;
    }

    @Override
    public Mono<AccessToken> getToken(AcrTokenRequestContext context) {

        String serviceName = context.getServiceName();
        Mono<AccessToken> refreshToken = context.getRefreshToken();
        String scope = context.getScope();
        return Mono.defer(() -> refreshToken.flatMap(token -> this.acrTokenService.getAcrAccessTokenAsync(token.getToken(), scope, serviceName)));
    }
}
