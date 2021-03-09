// Copyright (c) Microsoft Corporation. All rights reserved.
// Licensed under the MIT License.

package com.azure.containers.containerregistry;

import com.azure.core.credential.TokenRequestContext;
import com.azure.core.http.HttpClient;
import com.azure.core.http.ProxyOptions;
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

import java.net.InetSocketAddress;
import java.util.Arrays;

public class HelloWorld
{
    public static void main(String[] args)
    {
        var defaultCredential = new AzureCliCredentialBuilder().build();

        ProxyOptions proxyOptions = new ProxyOptions(ProxyOptions.Type.HTTP, new InetSocketAddress("localhost", 8888));

        HttpClient nettyHttpClient = new NettyAsyncHttpClientBuilder()
            .proxy(proxyOptions)
            .build();

/*        // Configure proxy to Fiddler using port 8888
        Configuration configuration = new Configuration()
            .put("java.net.useSystemProxies", "true")
            .put("https.proxyHost", "localhost")
            .put("https.proxyPort", "8888")
           .put("https.proxyUser", "1")
            .put("https.proxyPassword", "1");
// Create the Netty HTTP client
        HttpClient nettyHttpClient = new NettyAsyncHttpClientBuilder()
            .configuration(configuration)
            .build();*/

        ContainerRegistryClient client = new ContainerRegistryBuilder()
            .tokenCredential(defaultCredential)
            .httpClient(nettyHttpClient)
            .httpLogOptions(new HttpLogOptions().setLogLevel(HttpLogDetailLevel.BODY_AND_HEADERS))
            .url("https://pallavitacr.azurecr.io")
            .buildContainerRegistryClient();

        var repositories = client.getRepositoriesWithResponse(null, 0, Context.NONE);
    }
}

