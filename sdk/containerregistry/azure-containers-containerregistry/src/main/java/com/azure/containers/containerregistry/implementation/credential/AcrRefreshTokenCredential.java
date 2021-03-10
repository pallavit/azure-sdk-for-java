package com.azure.containers.containerregistry.implementation.credential;

import com.azure.containers.containerregistry.implementation.authentication.AcrTokenService;
import com.azure.core.credential.AccessToken;
import com.azure.core.credential.TokenCredential;
import com.azure.core.credential.TokenRequestContext;
import reactor.core.publisher.Mono;

public class AcrRefreshTokenCredential implements AcrTokenCredential {

    private final TokenCredential aadTokenCredential;
    private final AcrTokenService acrTokenService;

    public static final String AAD_DEFAULTSCOPE = "https://management.core.windows.net/.default";

    public AcrRefreshTokenCredential(AcrTokenService acrTokenService, TokenCredential aadTokenCredential)
    {
        this.acrTokenService = acrTokenService;
        this.aadTokenCredential = aadTokenCredential;
    }

    @Override
    public Mono<AccessToken> getToken(AcrTokenRequestContext request) {
        String serviceName = request.getServiceName();
        return Mono.defer(() -> aadTokenCredential.getToken(new TokenRequestContext().addScopes(AAD_DEFAULTSCOPE))
                .flatMap(token -> this.acrTokenService.getAcrRefreshTokenAsync(token.getToken(), serviceName)));
    }

}
