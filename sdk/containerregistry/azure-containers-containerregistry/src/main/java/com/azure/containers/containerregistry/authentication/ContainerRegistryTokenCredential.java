// Copyright (c) Microsoft Corporation. All rights reserved.
// Licensed under the MIT License.

package com.azure.containers.containerregistry.authentication;

import com.azure.containers.containerregistry.implementation.authentication.TokenServiceImpl;
import com.azure.core.credential.AccessToken;
import com.azure.core.credential.TokenCredential;
import com.azure.core.credential.TokenRequestContext;
import reactor.core.publisher.Mono;

import java.awt.*;

/**
 * Token credentials representing the container registry refresh token.
 * This token is unique per registry operation.
 */
class ContainerRegistryTokenCredential implements TokenCredential {

    private final TokenCredential tokenCredential;
    private final TokenServiceImpl tokenService;
    public static final String AAD_DEFAULT_SCOPE = "https://management.core.windows.net/.default";

    /**
     * Creates an instance of RefreshTokenCredential with default scheme "Bearer".
     * @param tokenService the container registry token service that calls the token rest APIs.
     * @param aadTokenCredential the ARM access token.
     */
    ContainerRegistryTokenCredential(TokenServiceImpl tokenService, TokenCredential aadTokenCredential) {
        this.tokenService = tokenService;
        this.tokenCredential = aadTokenCredential;
    }

    /**
     * Creates the container registry refresh token for the given context.
     *
     * @param context the context for the token to be generated.
     */
    @Override
    public Mono<AccessToken> getToken(TokenRequestContext context) {

        ContainerRegistryTokenRequestContext containerRegistryTokenRequestContext = (ContainerRegistryTokenRequestContext)context;

        String serviceName = containerRegistryTokenRequestContext.getServiceName();

        return Mono.defer(() -> tokenCredential.getToken(new TokenRequestContext().addScopes(AAD_DEFAULT_SCOPE))
            .flatMap(token -> this.tokenService.getAcrRefreshTokenAsync(token.getToken(), serviceName)));
    }

}
