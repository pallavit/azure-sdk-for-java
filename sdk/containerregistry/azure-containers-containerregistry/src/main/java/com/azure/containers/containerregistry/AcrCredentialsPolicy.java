package com.azure.containers.containerregistry;

import com.azure.containers.containerregistry.implementation.authentication.*;
import com.azure.containers.containerregistry.implementation.credential.AcrAccessTokenCredential;
import com.azure.containers.containerregistry.implementation.credential.AcrRefreshTokenCredential;
import com.azure.containers.containerregistry.implementation.credential.AcrTokenRequestContext;
import com.azure.core.credential.AccessToken;
import com.azure.core.credential.TokenCredential;
import com.azure.core.http.HttpPipelineCallContext;
import com.azure.core.http.HttpPipelineNextPolicy;
import com.azure.core.http.HttpResponse;
import com.azure.core.http.policy.HttpPipelinePolicy;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;


import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.HashMap;

public class AcrCredentialsPolicy implements HttpPipelinePolicy {

    private static final String AUTHORIZATION_HEADER = "Authorization";
    private static final String BEARER = "Bearer";
    public static final Pattern AUTHENTICATION_CHALLENGE_PATTERN =
        Pattern.compile("(\\w+) ((?:\\w+=\".*?\"(?:, )?)+)(?:, )?");
    public static final Pattern AUTHENTICATION_CHALLENGE_PARAMS_PATTERN =
        Pattern.compile("(?:(\\w+)=\"([^\"\"]*)\")+");
    public static final String WWW_AUTHENTICATE = "WWW-Authenticate";

    public static final String SCOPES_PARAMETER = "scope";
    public static final String SERVICE_PARAMETER = "service";
    AccessTokenCache refreshTokenCache;

    private final AcrTokenService tokenService;
    private ConcurrentMap<String, AccessTokenCache> tokenCacheMap;

    public AcrCredentialsPolicy(TokenCredential credential, String url)
    {
        this.tokenService = new AcrTokenService(url);
        this.tokenCacheMap = new ConcurrentHashMap<>();
        this.refreshTokenCache = new AccessTokenCache(new AcrRefreshTokenCredential(tokenService, credential));
    }

    public Mono<Void> onBeforeRequest(HttpPipelineCallContext context) {
        // TODO: Add scopes on the fly based on the request.
        return Mono.empty();
    }

    public Mono<HttpResponse> process(HttpPipelineCallContext context, HttpPipelineNextPolicy next) {
        if ("http".equals(context.getHttpRequest().getUrl().getProtocol())) {
            return Mono.error(new RuntimeException("token credentials require a URL using the HTTPS protocol scheme"));
        } else {
            HttpPipelineNextPolicy nextPolicy = next.clone();
            return this.onBeforeRequest(context).then(Mono.defer(() -> {
                return next.process();
            })).flatMap((httpResponse) -> {
                String authHeader = httpResponse.getHeaderValue("WWW-Authenticate");
                return httpResponse.getStatusCode() == 401 && authHeader != null ? this.onChallenge(context, httpResponse).flatMap((retry) -> {
                    return retry ? nextPolicy.process() : Mono.just(httpResponse);
                }) : Mono.just(httpResponse);
            });
        }
    }

    private Mono<AccessToken> getOperationToken(AcrTokenRequestContext tokenRequestContext)
    {
        String scope = tokenRequestContext.getScope();
        return Mono.defer(() -> this.refreshTokenCache.getToken(tokenRequestContext)
            .flatMap(refreshToken -> this.tokenCacheMap.get(scope).getToken(new AcrTokenRequestContext(tokenRequestContext.getServiceName(), tokenRequestContext.getScope(), tokenRequestContext.getRefreshToken()))))
            .repeatWhenEmpty((Flux<Long> longFlux) -> longFlux.concatMap(ignored -> Flux.just(true)));
    }

    public Mono<Void> authorizeRequest(HttpPipelineCallContext context, AcrTokenRequestContext tokenRequestContext) {
        String scope = tokenRequestContext.getScope();
        this.tokenCacheMap.putIfAbsent(scope, new AccessTokenCache(new AcrAccessTokenCredential(this.tokenService)));

        return getOperationToken(tokenRequestContext)
            .flatMap((token) -> { context.getHttpRequest().getHeaders().set("Authorization", "Bearer " + token.getToken());
                return Mono.empty();
            });
        }

    public Mono<Boolean> onChallenge(HttpPipelineCallContext context, HttpResponse response) {
        return Mono.defer(() -> {
            String authHeader = response.getHeaderValue(WWW_AUTHENTICATE);
            if (!(response.getStatusCode() == 401 && authHeader != null)) {
                return Mono.just(false);
            } else {

                    Map<String, String> extractedChallengeParams = parseChallengeParams(authHeader);
                    if (extractedChallengeParams.containsKey(SCOPES_PARAMETER)) {
                        String scope = extractedChallengeParams.get(SCOPES_PARAMETER);
                        String serviceName = extractedChallengeParams.get(SERVICE_PARAMETER);
                        return authorizeRequest(context, new AcrTokenRequestContext(serviceName, scope))
                            .flatMap(b -> Mono.just(true));
                    }
                return Mono.just(false);
            }
        });
    }

    private Map<String, String> parseBearerChallenge(String header) {
        Matcher matcher = AUTHENTICATION_CHALLENGE_PATTERN.matcher(header);

        while (matcher.find()) {
            var challengeName = matcher.group(1);
            if(challengeName.equalsIgnoreCase(BEARER))
            {
             return parseChallengeParams(matcher.group(2));
            }
        }

        return null;
    }

    private Map<String, String> parseChallengeParams(String challengeParams) {
        Matcher matcher = AUTHENTICATION_CHALLENGE_PARAMS_PATTERN.matcher(challengeParams);

        Map<String, String> challengeParameters = new HashMap<>();
        while (matcher.find()) {
            challengeParameters.put(matcher.group(1), matcher.group(2));
        }
        return challengeParameters;
    }
}
