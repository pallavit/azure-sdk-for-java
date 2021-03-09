package com.azure.containers.containerregistry.implementation.authentication;

import com.azure.core.credential.AccessToken;
import com.azure.core.credential.SimpleTokenCache;
import com.azure.core.credential.TokenCredential;
import com.azure.core.credential.TokenRequestContext;
import com.azure.core.http.HttpPipeline;
import com.azure.core.util.serializer.SerializerAdapter;
import reactor.core.publisher.Mono;

public class AcrTokenCredentials implements TokenCredential {

    private final AcrTokenService tokenService;
    private final TokenCredential aadTokenCredentials;

    public static final String AAD_DEFAULTSCOPE = "https://management.core.windows.net/.default";

    public AcrTokenCredentials(TokenCredential aadTokenCredentials, String url, HttpPipeline httpPipeline, SerializerAdapter adapter)
    {
        this.aadTokenCredentials = aadTokenCredentials;
        this.tokenService = new AcrTokenService(url, httpPipeline, adapter);
    }

    @Override
    public Mono<AccessToken> getToken(TokenRequestContext request) {
        var scopes = request.getScopes();
        var scope = scopes.isEmpty() ? null : scopes.get(0);
        return aadTokenCredentials.getToken(new TokenRequestContext().addScopes(AAD_DEFAULTSCOPE)).flatMap(token -> this.tokenService.getAccessTokenFromAadToken(null, null, token.getToken(), scope));
    }
}
