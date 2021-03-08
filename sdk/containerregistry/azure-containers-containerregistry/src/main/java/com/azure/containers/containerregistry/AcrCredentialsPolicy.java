package com.azure.containers.containerregistry;

import com.azure.containers.containerregistry.implementation.authentication.AccessTokenCache;
import com.azure.containers.containerregistry.implementation.authentication.AcrTokenCredentials;
import com.azure.core.credential.AccessToken;
import com.azure.core.credential.TokenCredential;
import com.azure.core.credential.TokenRequestContext;
import com.azure.core.http.HttpPipeline;
import com.azure.core.http.HttpPipelineCallContext;
import com.azure.core.http.HttpPipelineNextPolicy;
import com.azure.core.http.HttpResponse;
import com.azure.core.http.policy.HttpPipelinePolicy;
import com.azure.core.util.serializer.SerializerAdapter;
import com.fasterxml.jackson.annotation.JsonProperty;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AcrCredentialsPolicy implements HttpPipelinePolicy {
    private static final String AUTHORIZATION_HEADER = "Authorization";
    private static final String BEARER = "Bearer";
    public static final Pattern AUTHENTICATION_CHALLENGE_PATTERN =
        Pattern.compile("(\\w+) ((?:\\w+=\".*?\"(?:, )?)+)(?:, )?");
    public static final Pattern AUTHENTICATION_CHALLENGE_PARAMS_PATTERN =
        Pattern.compile("(?:(\\w+)=\"([^\"\"]*)\")+");
    public static final String WWW_AUTHENTICATE = "WWW-Authenticate";

    public static final String SCOPES_PARAMETER = "scope";

//
    private final AcrTokenCredentials credential;
    private final Map<String,AccessTokenCache> cache;

    private String scope;

    private String getScope() {
        return scope;
    }

    private void setScope(String scope)
    {
        this.scope = scope;
    }

    /**
     * Creates BearerTokenAuthenticationChallengePolicy.
     *
     * @param credential the token credential to authenticate the request
     */
    public AcrCredentialsPolicy(TokenCredential tokenCredential, String url, HttpPipeline httpPipeline, SerializerAdapter serializerAdapter) {
        Objects.requireNonNull(tokenCredential);
        this.credential  = new AcrTokenCredentials(tokenCredential, url, httpPipeline, serializerAdapter);
        this.cache = new HashMap<>();
    }


    private Mono<AccessToken> getAccessToken()
    {
        var scope = this.getScope();
        return credential.getToken(new TokenRequestContext().addScopes(scope));
    }

    /**
     *
     * Executed before sending the initial request and authenticates the request.
     *
     * @param context The request context.
     * @return A {@link Mono} containing {@link Void}
     */
    public Mono<Void> onBeforeRequest(HttpPipelineCallContext context) {
        return authenticateRequest(context, false);
    }

    /**
     * Handles the authentication challenge in the event a 401 response with a WWW-Authenticate authentication
     * challenge header is received after the initial request.
     *
     * @param context The request context.
     * @param response The Http Response containing the authentication challenge header.
     * @return A {@link Mono} containing the status, whether the challenge was successfully extracted and handled.
     *  if true then a follow up request needs to be sent authorized with the challenge based bearer token.
     */
    public Mono<Boolean> onChallenge(HttpPipelineCallContext context, HttpResponse response) {
        String authHeader = response.getHeaderValue(WWW_AUTHENTICATE);
        if (response.getStatusCode() == 401 && authHeader != null) {
          /*  List<AuthenticationChallenge> challenges = parseChallengeParams(authHeader);*/
                Map<String, String> extractedChallengeParams = parseChallengeParams(authHeader);
                if (extractedChallengeParams.containsKey(SCOPES_PARAMETER)) {
                    /*String claims = new String(Base64.getUrlDecoder()
                        .decode(extractedChallengeParams.get(CLAIMS_PARAMETER)), StandardCharsets.UTF_8);*/
                    String scope = extractedChallengeParams.get(SCOPES_PARAMETER);
                    this.setScope(scope);
                    return authenticateRequest(context, true)
                        .flatMap(b -> Mono.just(true));
                }
        }
        return Mono.just(false);
    }

    @Override
    public Mono<HttpResponse> process(HttpPipelineCallContext context, HttpPipelineNextPolicy next) {
        if ("http".equals(context.getHttpRequest().getUrl().getProtocol())) {
            return Mono.error(new RuntimeException("token credentials require a URL using the HTTPS protocol scheme"));
        }
        HttpPipelineNextPolicy nextPolicy = next.clone();

        return onBeforeRequest(context)
            .then(next.process())
            .flatMap(httpResponse -> {
                String authHeader = httpResponse.getHeaderValue(WWW_AUTHENTICATE);
                if (httpResponse.getStatusCode() == 401 && authHeader != null) {
                    return onChallenge(context, httpResponse).flatMap(retry -> {
                        if (retry) {
                            return nextPolicy.process();
                        } else {
                            return Mono.just(httpResponse);
                        }
                    });
                }
                return Mono.just(httpResponse);
            });
    }

    private Mono<Void> authenticateRequest(HttpPipelineCallContext context , boolean forceTokenRefresh)
    {
        if(scope != null) {
            if(!cache.containsKey(scope))
            {
                cache.put(scope, new AccessTokenCache(this::getAccessToken));
            }

            return cache.get(scope).getToken(this::getAccessToken, forceTokenRefresh)
                .flatMap(token -> {
                    context.getHttpRequest().getHeaders().set(AUTHORIZATION_HEADER, BEARER + " " + token.getToken());
                    return Mono.empty();
                });
        }

        return Mono.empty();
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
