// Copyright (c) Microsoft Corporation. All rights reserved.
// Licensed under the MIT License.

package com.azure.containers.containerregistry;

import com.azure.core.credential.TokenRequestContext;
import com.azure.core.http.HttpClient;
import com.azure.core.http.netty.NettyAsyncHttpClientBuilder;
import com.azure.core.http.policy.HttpLogDetailLevel;
import com.azure.core.http.policy.HttpLogOptions;
import com.azure.core.http.policy.HttpLoggingPolicy;
import com.azure.core.util.Configuration;
import com.azure.core.util.Context;
import com.azure.identity.AzureCliCredentialBuilder;
import com.azure.identity.DefaultAzureCredential;
import com.azure.identity.DefaultAzureCredentialBuilder;
import reactor.core.publisher.Mono;

import java.util.Arrays;

public class HelloWorld
{
    public static void main(String[] args)
    {
        var x = Mono.empty().then(Mono.defer(() -> { System.out.println("Hello"); return Mono.just(1);}));

        var y = x.block();
        var defaultCredential = new AzureCliCredentialBuilder().build();

/*        // Configure proxy to Fiddler using port 8888
        Configuration configuration = new Configuration()
            .put("java.net.useSystemProxies", "true")
            .put("http.proxyHost", "localhost")
            .put("http.proxyPort", "8888")
            .put("http.proxyUser", "1")
            .put("http.proxyPassword", "1");
// Create the Netty HTTP client
        HttpClient nettyHttpClient = new NettyAsyncHttpClientBuilder()
            .configuration(configuration)
            .build();*/

        ContainerRegistryClient client = new ContainerRegistryBuilder()
            .tokenCredential(defaultCredential)
            /*.httpClient(nettyHttpClient)*/
            .httpLogOptions(new HttpLogOptions().setLogLevel(HttpLogDetailLevel.BODY_AND_HEADERS))
            .url("https://pallavitacr.azurecr.io")
            .buildContainerRegistryClient();

        var repositories = client.getRepositoriesWithResponse(null, 0, Context.NONE);
    }
}

