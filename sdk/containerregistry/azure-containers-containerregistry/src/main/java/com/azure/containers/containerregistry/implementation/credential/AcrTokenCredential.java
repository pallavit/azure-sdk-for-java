package com.azure.containers.containerregistry.implementation.credential;

import com.azure.core.credential.AccessToken;
import reactor.core.publisher.Mono;

    @FunctionalInterface
    public interface AcrTokenCredential {
        Mono<AccessToken> getToken(AcrTokenRequestContext var1);
    }
