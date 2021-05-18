// Copyright (c) Microsoft Corporation. All rights reserved.
// Licensed under the MIT License.
package com.azure.containers.containerregistry;

import com.azure.containers.containerregistry.models.ArtifactManifestProperties;
import com.azure.containers.containerregistry.models.ArtifactTagProperties;
import com.azure.containers.containerregistry.models.ManifestOrderBy;
import com.azure.core.credential.TokenCredential;
import com.azure.core.exception.HttpResponseException;
import com.azure.core.http.rest.PagedIterable;
import com.azure.core.util.Context;
import com.azure.identity.DefaultAzureCredential;
import com.azure.identity.DefaultAzureCredentialBuilder;

/**
 * WARNING: MODIFYING THIS FILE WILL REQUIRE CORRESPONDING UPDATES TO README.md FILE. LINE NUMBERS
 * ARE USED TO EXTRACT APPROPRIATE CODE SEGMENTS FROM THIS FILE. ADD NEW CODE AT THE BOTTOM TO AVOID CHANGING
 * LINE NUMBERS OF EXISTING CODE SAMPLES.
 * <p>
 * Code samples for the README.md
 *
 */
public class ReadmeSamples {

    private String endpoint = "endpoint";

    public void createClient() {
        DefaultAzureCredential credential = new DefaultAzureCredentialBuilder().build();
        ContainerRegistryClient client = new ContainerRegistryClientBuilder()
            .endpoint(endpoint)
            .credential(credential)
            .buildClient();
    }

    public void createAsyncClient() {
        DefaultAzureCredential credential = new DefaultAzureCredentialBuilder().build();
        ContainerRegistryAsyncClient client = new ContainerRegistryClientBuilder()
            .endpoint(endpoint)
            .credential(credential)
            .buildAsyncClient();
    }

    public void listRepositoryNamesSample() {
        DefaultAzureCredential credential = new DefaultAzureCredentialBuilder().build();
        ContainerRegistryClient client = new ContainerRegistryClientBuilder()
            .endpoint(endpoint)
            .credential(credential)
            .buildClient();

        client.listRepositoryNames().forEach(repository -> System.out.println(repository));
    }

    public void listRepositoryNamesAsyncSample() {
        DefaultAzureCredential credential = new DefaultAzureCredentialBuilder().build();
        ContainerRegistryAsyncClient client = new ContainerRegistryClientBuilder()
            .endpoint(endpoint)
            .credential(credential)
            .buildAsyncClient();

        client.listRepositoryNames().subscribe(repository -> System.out.println(repository));
    }

    private final String repositoryName = "repository";

    public void getPropertiesThrows() {
        DefaultAzureCredential credential = new DefaultAzureCredentialBuilder().build();
        ContainerRepository containerRepository = new ContainerRegistryClientBuilder()
            .endpoint(endpoint)
            .credential(credential)
            .buildClient()
            .getRepository(repositoryName);
        try {
            containerRepository.getProperties();
        } catch (HttpResponseException exception) {
            // Do something with the exception.
        }
    }

    public void createAnonymousAccessClient() {
        ContainerRegistryClient client = new ContainerRegistryClientBuilder()
            .endpoint(endpoint)
            .buildClient();
    }

    public void createAnonymousAccessAsyncClient() {
        ContainerRegistryAsyncClient client = new ContainerRegistryClientBuilder()
            .endpoint(endpoint)
            .buildAsyncClient();
    }

    public void deleteImages() {
        TokenCredential defaultCredential = new DefaultAzureCredentialBuilder().build();

        ContainerRegistryClient client = new ContainerRegistryClientBuilder()
            .endpoint(endpoint)
            .credential(defaultCredential)
            .buildClient();

        final int imagesCountToKeep = 3;
        for (String repositoryName : client.listRepositoryNames()) {
            final ContainerRepository repository = client.getRepository(repositoryName);

            // Obtain the images ordered from newest to oldest
            PagedIterable<ArtifactManifestProperties> imageManifests =
                repository.listManifests(
                    ManifestOrderBy.LAST_UPDATED_ON_DESCENDING,
                    Context.NONE);

            imageManifests.stream().skip(imagesCountToKeep)
                .forEach(imageManifest -> {
                    System.out.printf(String.format("Deleting image with digest %s.%n", imageManifest.getDigest()));
                    System.out.printf("    This image has the following tags: ");

                    for (String tagName : imageManifest.getTags()) {
                        System.out.printf("        %s:%s", imageManifest.getRepositoryName(), tagName);
                    }

                    repository.getArtifact(imageManifest.getDigest()).delete();
                });
        }
    }

    public void deleteImagesAsync() {
        TokenCredential defaultCredential = new DefaultAzureCredentialBuilder().build();

        ContainerRegistryAsyncClient client = new ContainerRegistryClientBuilder()
            .endpoint(endpoint)
            .credential(defaultCredential)
            .buildAsyncClient();

        final int imagesCountToKeep = 3;
        client.listRepositoryNames()
            .map(repositoryName -> client.getRepository(repositoryName))
            .flatMap(repository -> repository.listManifests(
                ManifestOrderBy.LAST_UPDATED_ON_DESCENDING))
            .skip(imagesCountToKeep).subscribe(imageManifest -> {
                System.out.printf(String.format("Deleting image with digest %s.%n", imageManifest.getDigest()));
                System.out.printf("    This image has the following tags: ");

                for (String tagName : imageManifest.getTags()) {
                    System.out.printf("        %s:%s", imageManifest.getRepositoryName(), tagName);
                }

                client.getArtifact(
                    imageManifest.getRepositoryName(),
                    imageManifest.getDigest()).delete().subscribe();
            }, error -> {
                System.out.println("Failed to delete older images.");
            });
    }

    private String tag = "tag";

    public void setArtifactProperties() {
        TokenCredential defaultCredential = new DefaultAzureCredentialBuilder().build();

        ContainerRegistryClient client = new ContainerRegistryClientBuilder()
            .endpoint(endpoint)
            .credential(defaultCredential)
            .buildClient();

        RegistryArtifact image = client.getArtifact(repositoryName, tagOrDigest);

        image.setTagProperties(
            tag,
            new ArtifactTagProperties()
                .setWriteEnabled(false)
                .setDeleteEnabled(false));
    }

    public void setArtifactPropertiesAsync() {
        TokenCredential defaultCredential = new DefaultAzureCredentialBuilder().build();

        ContainerRegistryAsyncClient client = new ContainerRegistryClientBuilder()
            .endpoint(endpoint)
            .credential(defaultCredential)
            .buildAsyncClient();

        RegistryArtifactAsync image = client.getArtifact(repositoryName, tagOrDigest);

        image.setTagProperties(tag, new ArtifactTagProperties()
            .setWriteEnabled(false)
            .setDeleteEnabled(false)).subscribe(artifactTagProperties -> {
                System.out.println("Tag properties are now read-only");
            }, error -> {
                System.out.println("Failed to make the tag properties read-only.");
            });
    }

    private final String architecture = "architecture";
    private final String os = "os";
    private final String tagOrDigest = "tagOrDigest";

    public void listTags() {
        ContainerRegistryClient anonymousClient = new ContainerRegistryClientBuilder()
            .endpoint(endpoint)
            .buildClient();

        RegistryArtifact image = anonymousClient.getArtifact(repositoryName, tagOrDigest);
        PagedIterable<ArtifactTagProperties> tags = image.listTags();

        System.out.printf(String.format("%s has the following aliases:", image.getFullyQualifiedName()));

        for (ArtifactTagProperties tag : tags) {
            System.out.printf(String.format("%s/%s:%s", anonymousClient.getName(), repositoryName, tag.getName()));
        }
    }

    public void listTagsAsync() {
        final String endpoint = getEndpoint();
        final String repositoryName = getRepositoryName();

        ContainerRegistryAsyncClient anonymousClient = new ContainerRegistryClientBuilder()
            .endpoint(endpoint)
            .buildAsyncClient();

        RegistryArtifactAsync image = anonymousClient.getArtifact(repositoryName, tagOrDigest);

        System.out.printf(String.format("%s has the following aliases:", image.getFullyQualifiedName()));

        image.listTags().subscribe(tag -> {
            System.out.printf(String.format("%s/%s:%s", anonymousClient.getName(), repositoryName, tag.getName()));
        }, error -> {
            System.out.println("There was an error while trying to list tags" + error);
        });
    }

    public void anonymousClientThrows() {
        final String endpoint = getEndpoint();
        final String repositoryName = getRepositoryName();

        ContainerRegistryClient anonymousClient = new ContainerRegistryClientBuilder()
            .endpoint(endpoint)
            .buildClient();

        try {
            anonymousClient.deleteRepository(repositoryName);
            System.out.println("Unexpected Success: Delete is not allowed on anonymous access");
        } catch (Exception ex) {
            System.out.println("Expected exception: Delete is not allowed on anonymous access");
        }
    }

    public void anonymousAsyncClientThrows() {
        final String endpoint = getEndpoint();
        final String repositoryName = getRepositoryName();

        ContainerRegistryAsyncClient anonymousClient = new ContainerRegistryClientBuilder()
            .endpoint(endpoint)
            .buildAsyncClient();

        anonymousClient.deleteRepository(repositoryName).subscribe(deleteRepositoryResult -> {
            System.out.println("Unexpected Success: Delete is not allowed on anonymous access");
        }, error -> {
            System.out.println("Expected exception: Delete is not allowed on anonymous access");
        });
    }

    private static String getEndpoint() {
        return null;
    }

    private static String getRepositoryName() {
        return null;
    }

    private static String getTagName() {
        return null;
    }

}

