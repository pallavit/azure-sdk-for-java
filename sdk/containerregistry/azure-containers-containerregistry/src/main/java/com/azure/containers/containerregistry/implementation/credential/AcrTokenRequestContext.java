package com.azure.containers.containerregistry.implementation.credential;


import com.azure.core.credential.AccessToken;
import reactor.core.publisher.Mono;

public class AcrTokenRequestContext {
    private final String scope;
    private final String tenant;
    private final String serviceName;
    private final Mono<AccessToken> refreshToken;

    public AcrTokenRequestContext(String serviceName, String scope) {
        this(serviceName, scope, null, null);
    }
    public AcrTokenRequestContext(String serviceName, String scope, Mono<AccessToken> refreshToken) {
        this(serviceName, scope, refreshToken, null);
    }

    public AcrTokenRequestContext(String serviceName, String scope, String tenant) {
        this(serviceName, scope, null, null);
    }

    private AcrTokenRequestContext(String serviceName, String scope, Mono<AccessToken> refreshToken, String tenant) {
        this.tenant = tenant;
        this.serviceName = serviceName;
        this.scope = scope;
        this.refreshToken = refreshToken;
    }

    public String getServiceName()
    {
        return this.serviceName;
    }

    public String getScope()
    {
        return this.scope;
    }

    public String getTenant()
    {
        return this.tenant;
    }

    public Mono<AccessToken> getRefreshToken()
    {
        return this.refreshToken;
    }

}

