// Copyright (c) Microsoft Corporation. All rights reserved.
// Licensed under the MIT License.

package com.azure.containers.containerregistry;

import com.azure.containers.containerregistry.models.ArtifactManifestProperties;
import com.azure.containers.containerregistry.models.ArtifactTagProperties;
import com.azure.containers.containerregistry.models.ArtifactTagOrderBy;
import com.azure.core.credential.TokenCredential;
import com.azure.core.http.HttpPipeline;
import com.azure.core.http.HttpPipelineBuilder;

public class RegistryArtifactAsyncJavaDocSnippets {
    public RegistryArtifactAsync createRegistryArtifact() {
        String endpoint = getEndpoint();
        String repository = getRepository();
        String digest = getDigest();
        TokenCredential credential = getTokenCredentials();
        // BEGIN: com.azure.containers.containerregistry.async.registryartifact.instantiation
        RegistryArtifactAsync registryArtifactAsync = new ContainerRegistryClientBuilder()
            .endpoint(endpoint)
            .credential(credential)
            .buildAsyncClient().getArtifact(repository, digest);
        // END: com.azure.containers.containerregistry.async.registryartifact.instantiation
        return registryArtifactAsync;
    }

    public RegistryArtifactAsync createContainerRepositoryWithPipeline() {
        String endpoint = getEndpoint();
        String repository = getRepository();
        String digest = getDigest();
        TokenCredential credential = getTokenCredentials();
        // BEGIN: com.azure.containers.containerregistry.async.registryartifact.pipeline.instantiation
        HttpPipeline pipeline = new HttpPipelineBuilder()
            .policies(/* add policies */)
            .build();

        RegistryArtifactAsync registryArtifactAsync = new ContainerRegistryClientBuilder()
            .pipeline(pipeline)
            .endpoint(endpoint)
            .credential(credential)
            .buildAsyncClient().getArtifact(repository, digest);
        // END: com.azure.containers.containerregistry.async.registryartifact.pipeline.instantiation
        return registryArtifactAsync;
    }

    public void deleteCodeSnippet() {
        RegistryArtifactAsync client = getAsyncClient();
        // BEGIN: com.azure.containers.containerregistry.async.registryartifact.delete
        client.delete().subscribe();
        // END: com.azure.containers.containerregistry.async.registryartifact.delete
    }

    public void deleteWithResponseCodeSnippet() {
        RegistryArtifactAsync client = getAsyncClient();
        // BEGIN: com.azure.containers.containerregistry.async.registryartifact.deleteWithResponse
        client.deleteWithResponse().subscribe();
        // END: com.azure.containers.containerregistry.async.registryartifact.deleteWithResponse
    }

    public void deleteTagCodeSnippet() {
        RegistryArtifactAsync client = getAsyncClient();
        // BEGIN: com.azure.containers.containerregistry.async.registryartifact.deleteTag
        String tag = getTag();
        client.deleteTag(tag).subscribe();
        // END: com.azure.containers.containerregistry.async.registryartifact.deleteTag
    }

    public void deleteTagWithResponseCodeSnippet() {
        RegistryArtifactAsync client = getAsyncClient();
        // BEGIN: com.azure.containers.containerregistry.async.registryartifact.deleteTagWithResponse
        String tag = getTag();
        client.deleteTagWithResponse(tag).subscribe();
        // END: com.azure.containers.containerregistry.async.registryartifact.deleteTagWithResponse
    }

    public void getManifestPropertiesCodeSnippet() {
        RegistryArtifactAsync client = getAsyncClient();
        // BEGIN: com.azure.containers.containerregistry.async.registryartifact.getManifestProperties
        client.getManifestProperties()
            .subscribe(properties -> {
                System.out.printf("Digest:%s,", properties.getDigest());
            });
        // END: com.azure.containers.containerregistry.async.registryartifact.getManifestProperties
    }

    public void getManifestPropertiesWithResponseCodeSnippet() {
        RegistryArtifactAsync client = getAsyncClient();
        // BEGIN: com.azure.containers.containerregistry.async.registryartifact.getManifestPropertiesWithResponse
        client.getManifestPropertiesWithResponse()
            .subscribe(response -> {
                final ArtifactManifestProperties properties = response.getValue();
                System.out.printf("Digest:%s,", properties.getDigest());
            });
        // END: com.azure.containers.containerregistry.async.registryartifact.getManifestPropertiesWithResponse
    }

    public void getTagPropertiesCodeSnippet() {
        RegistryArtifactAsync client = getAsyncClient();
        // BEGIN: com.azure.containers.containerregistry.async.registryartifact.getTagProperties
        String tag = getTag();
        client.getTagProperties(tag).subscribe(properties -> {
            System.out.printf("Digest:%s,", properties.getDigest());
        });
        // END: com.azure.containers.containerregistry.async.registryartifact.getTagProperties
    }

    public void getTagPropertiesWithResponseCodeSnippet() {
        RegistryArtifactAsync client = getAsyncClient();
        // BEGIN: com.azure.containers.containerregistry.async.registryartifact.getTagPropertiesWithResponse
        String tag = getTag();
        client.getTagPropertiesWithResponse(tag).subscribe(response -> {
            final ArtifactTagProperties properties = response.getValue();
            System.out.printf("Digest:%s,", properties.getDigest());
        });
        // END: com.azure.containers.containerregistry.async.registryartifact.getTagPropertiesWithResponse
    }

    public void listTagPropertiesCodeSnippet() {
        RegistryArtifactAsync client = getAsyncClient();
        // BEGIN: com.azure.containers.containerregistry.async.registryartifact.listTagProperties
        client.listTagProperties().byPage(10)
            .subscribe(tagPropertiesPagedResponse -> {
                tagPropertiesPagedResponse.getValue().stream().forEach(
                    tagProperties -> System.out.println(tagProperties.getDigest()));
            });
        // END: com.azure.containers.containerregistry.async.registryartifact.listTagProperties
    }

    public void listTagPropertiesWithOptionsCodeSnippet() {
        RegistryArtifactAsync client = getAsyncClient();
        // BEGIN: com.azure.containers.containerregistry.async.registryartifact.listTagPropertiesWithOptions
        client.listTagProperties(ArtifactTagOrderBy.LAST_UPDATED_ON_DESCENDING)
            .byPage(10)
            .subscribe(tagPropertiesPagedResponse -> {
                tagPropertiesPagedResponse.getValue()
                    .stream()
                    .forEach(tagProperties -> System.out.println(tagProperties.getDigest()));
            });
        // END: com.azure.containers.containerregistry.async.registryartifact.listTagPropertiesWithOptions
    }

    public void updateTagPropertiesCodeSnippet() {
        RegistryArtifactAsync client = getAsyncClient();
        // BEGIN: com.azure.containers.containerregistry.async.registryartifact.updateTagProperties
        ArtifactTagProperties properties = getTagProperties();
        String tag = getTag();
        client.updateTagProperties(tag, properties).subscribe();
        // END: com.azure.containers.containerregistry.async.registryartifact.updateTagProperties
    }

    public void updateTagPropertiesWithResponseCodeSnippet() {
        RegistryArtifactAsync client = getAsyncClient();
        // BEGIN: com.azure.containers.containerregistry.async.registryartifact.updateTagPropertiesWithResponse
        ArtifactTagProperties properties = getTagProperties();
        String tag = getTag();
        client.updateTagPropertiesWithResponse(tag, properties).subscribe();
        // END: com.azure.containers.containerregistry.async.registryartifact.updateTagPropertiesWithResponse
    }

    public void updateManifestPropertiesCodeSnippet() {
        RegistryArtifactAsync client = getAsyncClient();
        // BEGIN: com.azure.containers.containerregistry.async.registryartifact.updateManifestProperties
        ArtifactManifestProperties properties = getArtifactManifestProperties();
        client.updateManifestProperties(properties).subscribe();
        // END: com.azure.containers.containerregistry.async.registryartifact.updateManifestProperties
    }

    public void updateManifestPropertiesWithResponseCodeSnippet() {
        RegistryArtifactAsync client = getAsyncClient();
        // BEGIN: com.azure.containers.containerregistry.async.registryartifact.updateManifestPropertiesWithResponse
        ArtifactManifestProperties properties = getArtifactManifestProperties();
        client.updateManifestPropertiesWithResponse(properties).subscribe();
        // END: com.azure.containers.containerregistry.async.registryartifact.updateManifestPropertiesWithResponse
    }

    /**
     * Implementation not provided for this method.
     *
     * @return {@code null}
     */
    private ArtifactTagProperties getTagProperties() {
        return null;
    }

    /**
     * Implementation not provided for this method.
     *
     * @return {@code null}
     */
    private ArtifactManifestProperties getArtifactManifestProperties() {
        return null;
    }

    /**
     * Implementation not provided for this method.
     *
     * @return {@code null}
     */
    private String getDigest() {
        return null;
    }

    /**
     * Implementation not provided for this method.
     *
     * @return {@code null}
     */
    private String getTag() {
        return null;
    }

    /**
     * Implementation not provided for this method
     *
     * @return {@code null}
     */
    private RegistryArtifactAsync getAsyncClient() {
        return null;
    }

    /**
     * Implementation not provided for this method
     *
     * @return {@code null}
     */
    private String getEndpoint() {
        return null;
    }

    /**
     * Implementation not provided for this method
     *
     * @return {@code null}
     */
    private String getRepository() {
        return null;
    }

    /**
     * Implementation not provided for this method
     *
     * @return {@code null}
     */
    private TokenCredential getTokenCredentials() {
        return null;
    }

}
