// Copyright (c) Microsoft Corporation. All rights reserved.
// Licensed under the MIT License.
// Code generated by Microsoft (R) AutoRest Code Generator.

package com.azure.containers.containerregistry;

import com.azure.containers.containerregistry.models.ListRegistryArtifactOptions;
import com.azure.containers.containerregistry.models.ListTagsOptions;
import com.azure.containers.containerregistry.models.RegistryArtifactOrderBy;
import com.azure.containers.containerregistry.models.RegistryArtifactProperties;
import com.azure.containers.containerregistry.models.TagOrderBy;
import com.azure.containers.containerregistry.models.TagProperties;
import com.azure.core.exception.ResourceNotFoundException;
import com.azure.core.http.HttpClient;
import com.azure.core.http.rest.PagedIterable;
import com.azure.core.http.rest.Response;
import com.azure.core.test.implementation.ImplUtils;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.time.Duration;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static com.azure.containers.containerregistry.TestUtils.ALPINE_REPOSITORY_NAME;
import static com.azure.containers.containerregistry.TestUtils.DIGEST_UNKNOWN;
import static com.azure.containers.containerregistry.TestUtils.DISPLAY_NAME_WITH_ARGUMENTS;
import static com.azure.containers.containerregistry.TestUtils.HELLO_WORLD_REPOSITORY_NAME;
import static com.azure.containers.containerregistry.TestUtils.LATEST_TAG_NAME;
import static com.azure.containers.containerregistry.TestUtils.PAGESIZE_2;
import static com.azure.containers.containerregistry.TestUtils.TAG_UNKNOWN;
import static com.azure.containers.containerregistry.TestUtils.isSorted;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class ContainerRepositoryClientAsyncIntegrationTests extends ContainerRegistryClientsTestBase {

    private ContainerRepositoryAsyncClient client;

    @BeforeAll
    static void beforeAll() {
        StepVerifier.setDefaultTimeout(Duration.ofMinutes(30));
        TestUtils.importImage(ImplUtils.getTestMode(), HELLO_WORLD_REPOSITORY_NAME, Arrays.asList("latest", "v1", "v2", "v3", "v4"));
        TestUtils.importImage(ImplUtils.getTestMode(), ALPINE_REPOSITORY_NAME, Arrays.asList("latest"));
    }

    @AfterAll
    static void afterAll() {
        StepVerifier.resetDefaultTimeout();
    }

    private ContainerRepositoryAsyncClient getContainerRepositoryAsyncClient(HttpClient httpClient) {
        return getContainerRepositoryBuilder(HELLO_WORLD_REPOSITORY_NAME, httpClient).buildAsyncClient();
    }

    private ContainerRepositoryAsyncClient getUnknownContainerRepositoryAsyncClient(HttpClient httpClient) {
        return getContainerRepositoryBuilder("Unknown", httpClient).buildAsyncClient();
    }

    @ParameterizedTest(name = DISPLAY_NAME_WITH_ARGUMENTS)
    @MethodSource("getHttpClients")
    public void getRepositoryPropertiesWithResponse(HttpClient httpClient) {
        client = getContainerRepositoryAsyncClient(httpClient);

        StepVerifier.create(client.getPropertiesWithResponse())
            .assertNext(res -> {
                assertNotNull(res);
                assertEquals(HELLO_WORLD_REPOSITORY_NAME, res.getValue().getName());
            })
            .verifyComplete();
    }

    @ParameterizedTest(name = DISPLAY_NAME_WITH_ARGUMENTS)
    @MethodSource("getHttpClients")
    public void getUnknownRepositoryPropertiesWithResponse(HttpClient httpClient) {
        ContainerRepositoryAsyncClient client = getUnknownContainerRepositoryAsyncClient(httpClient);

        StepVerifier.create(client.getPropertiesWithResponse())
            .expectErrorMatches(res -> res instanceof ResourceNotFoundException)
            .verify();
    }

    @ParameterizedTest(name = DISPLAY_NAME_WITH_ARGUMENTS)
    @MethodSource("getHttpClients")
    public void getMultiArchitectureImagePropertiesWithResponse(HttpClient httpClient) {
        ContainerRepositoryAsyncClient client = getContainerRepositoryAsyncClient(httpClient);

        Mono<Response<RegistryArtifactProperties>> safeTestRegistyArtifacts = client.getRegistryArtifactPropertiesWithResponse(LATEST_TAG_NAME)
            .flatMap(res -> {
                validateArtifactProperties(res, true, false);
                return Mono.just(res);
            }).flatMap(res -> client.getRegistryArtifactPropertiesWithResponse(res.getValue().getDigest()));

        StepVerifier.create(safeTestRegistyArtifacts)
            .assertNext(res -> validateArtifactProperties(res, true, false))
            .verifyComplete();

        PagedIterable<RegistryArtifactProperties> props = new PagedIterable<>(client.listRegistryArtifacts());
        List<RegistryArtifactProperties> repositories = props.stream().collect(Collectors.toList());
        String childDigest = getChildArtifactDigest(repositories);

        StepVerifier.create(client.getRegistryArtifactPropertiesWithResponse(childDigest))
            .assertNext(res -> validateArtifactProperties(res, false, true))
            .verifyComplete();
    }

    @ParameterizedTest(name = DISPLAY_NAME_WITH_ARGUMENTS)
    @MethodSource("getHttpClients")
    public void getMultiArchitectureImagePropertiesWithResponseThrows(HttpClient httpClient) {
        ContainerRepositoryAsyncClient client = getContainerRepositoryAsyncClient(httpClient);

        StepVerifier.create(client.getRegistryArtifactPropertiesWithResponse(DIGEST_UNKNOWN))
            .expectError(ResourceNotFoundException.class)
            .verify();
    }

    @ParameterizedTest(name = DISPLAY_NAME_WITH_ARGUMENTS)
    @MethodSource("getHttpClients")
    public void listArtifacts(HttpClient httpClient) {
        ContainerRepositoryAsyncClient client = getContainerRepositoryAsyncClient(httpClient);

        StepVerifier.create(client.listRegistryArtifacts())
            .recordWith(ArrayList::new)
            .thenConsumeWhile(x -> true)
            .expectRecordedMatches(artifacts -> {
                validateListArtifacts(artifacts);
                return true;
            })
            .verifyComplete();
    }

    @ParameterizedTest(name = DISPLAY_NAME_WITH_ARGUMENTS)
    @MethodSource("getHttpClients")
    public void listArtifactsWithPageSize(HttpClient httpClient) {
        ContainerRepositoryAsyncClient client = getContainerRepositoryAsyncClient(httpClient);

        StepVerifier.create(client.listRegistryArtifacts().byPage(PAGESIZE_2))
            .recordWith(ArrayList::new)
            .thenConsumeWhile(x -> true)
            .expectRecordedMatches(pagedResList -> {

                List<RegistryArtifactProperties> props = new ArrayList<>();
                pagedResList.forEach(res -> res.getValue().forEach(prop -> props.add(prop)));

                validateListArtifacts(props);
                return pagedResList.stream().allMatch(res -> res.getValue().size() <= PAGESIZE_2);
            }).verifyComplete();
    }

    @ParameterizedTest(name = DISPLAY_NAME_WITH_ARGUMENTS)
    @MethodSource("getHttpClients")
    public void listArtifactsWithInvalidPageSize(HttpClient httpClient) {
        ContainerRepositoryAsyncClient client = getContainerRepositoryAsyncClient(httpClient);
        StepVerifier.create(client.listRegistryArtifacts().byPage(-1)).expectError(IllegalArgumentException.class).verify();
    }

    @ParameterizedTest(name = DISPLAY_NAME_WITH_ARGUMENTS)
    @MethodSource("getHttpClients")
    public void listArtifactsWithPageSizeAndOrderBy(HttpClient httpClient) {
        ContainerRepositoryAsyncClient client = getContainerRepositoryAsyncClient(httpClient);
        ListRegistryArtifactOptions options = new ListRegistryArtifactOptions().setRegistryArtifactOrderBy(RegistryArtifactOrderBy.LAST_UPDATED_ON_ASCENDING);

        StepVerifier.create(client.listRegistryArtifacts(options).byPage(PAGESIZE_2))
            .recordWith(ArrayList::new)
            .thenConsumeWhile(x -> true)
            .expectRecordedMatches(pagedResList -> {
                List<RegistryArtifactProperties> props = new ArrayList<>();
                pagedResList.forEach(res -> res.getValue().forEach(prop -> props.add(prop)));
                List<OffsetDateTime> lastUpdatedOn = props.stream().map(artifact -> artifact.getLastUpdatedOn()).collect(Collectors.toList());


                validateListArtifacts(props);
                return pagedResList.stream().allMatch(res -> res.getValue().size() <= PAGESIZE_2)
                    && isSorted(lastUpdatedOn);
            }).verifyComplete();
    }

    @ParameterizedTest(name = DISPLAY_NAME_WITH_ARGUMENTS)
    @MethodSource("getHttpClients")
    public void listArtifactsWithPageSizeNoOrderBy(HttpClient httpClient) {
        ContainerRepositoryAsyncClient client = getContainerRepositoryAsyncClient(httpClient);
        ListRegistryArtifactOptions options = new ListRegistryArtifactOptions();

        StepVerifier.create(client.listRegistryArtifacts(options).byPage(PAGESIZE_2))
            .recordWith(ArrayList::new)
            .thenConsumeWhile(x -> true)
            .expectRecordedMatches(pagedResList -> {
                List<RegistryArtifactProperties> props = new ArrayList<>();
                pagedResList.forEach(res -> res.getValue().forEach(prop -> props.add(prop)));
                List<OffsetDateTime> lastUpdatedOn = props.stream().map(artifact -> artifact.getLastUpdatedOn()).collect(Collectors.toList());
                validateListArtifacts(props);
                return pagedResList.stream().allMatch(res -> res.getValue().size() <= PAGESIZE_2);
            }).verifyComplete();
    }

    @ParameterizedTest(name = DISPLAY_NAME_WITH_ARGUMENTS)
    @MethodSource("getHttpClients")
    public void listTags(HttpClient httpClient) {
        ContainerRepositoryAsyncClient client = getContainerRepositoryAsyncClient(httpClient);

        StepVerifier.create(client.listTags())
            .recordWith(ArrayList::new)
            .thenConsumeWhile(x -> true)
            .expectRecordedMatches(tags -> {
                validateListTags(tags);
                return true;
            })
            .verifyComplete();
    }

    @ParameterizedTest(name = DISPLAY_NAME_WITH_ARGUMENTS)
    @MethodSource("getHttpClients")
    public void listTagsWithPageSize(HttpClient httpClient) {
        ContainerRepositoryAsyncClient client = getContainerRepositoryAsyncClient(httpClient);

        StepVerifier.create(client.listTags().byPage(PAGESIZE_2))
            .recordWith(ArrayList::new)
            .thenConsumeWhile(x -> true)
            .expectRecordedMatches(pagedResList -> {
                List<TagProperties> props = new ArrayList<>();
                pagedResList.forEach(res -> res.getValue().forEach(prop -> props.add(prop)));

                validateListTags(props);
                return pagedResList.stream().allMatch(res -> res.getValue().size() <= PAGESIZE_2);
            }).verifyComplete();
    }


    @ParameterizedTest(name = DISPLAY_NAME_WITH_ARGUMENTS)
    @MethodSource("getHttpClients")
    public void listTagsWithInvalidPageSize(HttpClient httpClient) {
        ContainerRepositoryAsyncClient client = getContainerRepositoryAsyncClient(httpClient);
        StepVerifier.create(client.listTags().byPage(-1)).expectError(IllegalArgumentException.class).verify();
    }

    @ParameterizedTest(name = DISPLAY_NAME_WITH_ARGUMENTS)
    @MethodSource("getHttpClients")
    public void listTagsWithPageSizeAndOrderBy(HttpClient httpClient) {
        ContainerRepositoryAsyncClient client = getContainerRepositoryAsyncClient(httpClient);
        ListTagsOptions options = new ListTagsOptions().setTagOrderBy(TagOrderBy.LAST_UPDATED_ON_ASCENDING);

        StepVerifier.create(client.listTags(options).byPage(PAGESIZE_2))
            .recordWith(ArrayList::new)
            .thenConsumeWhile(x -> true)
            .expectRecordedMatches(pagedResList -> {

                List<TagProperties> props = new ArrayList<>();
                pagedResList.forEach(res -> res.getValue().forEach(prop -> props.add(prop)));
                List<OffsetDateTime> lastUpdatedOn = props.stream().map(artifact -> artifact.getLastUpdatedOn()).collect(Collectors.toList());

                validateListTags(props);
                return pagedResList.stream().allMatch(res -> res.getValue().size() <= PAGESIZE_2)
                    && isSorted(lastUpdatedOn);

            }).verifyComplete();
    }

    @ParameterizedTest(name = DISPLAY_NAME_WITH_ARGUMENTS)
    @MethodSource("getHttpClients")
    public void listTagsWithPageSizeNoOrderBy(HttpClient httpClient) {
        ContainerRepositoryAsyncClient client = getContainerRepositoryAsyncClient(httpClient);
        ListTagsOptions options = new ListTagsOptions();

        StepVerifier.create(client.listTags(options).byPage(PAGESIZE_2))
            .recordWith(ArrayList::new)
            .thenConsumeWhile(x -> true)
            .expectRecordedMatches(pagedResList -> {

                List<TagProperties> props = new ArrayList<>();
                pagedResList.forEach(res -> res.getValue().forEach(prop -> props.add(prop)));

                validateListTags(props);
                return pagedResList.stream().allMatch(res -> res.getValue().size() <= PAGESIZE_2);

            }).verifyComplete();
    }

    @ParameterizedTest(name = DISPLAY_NAME_WITH_ARGUMENTS)
    @MethodSource("getHttpClients")
    public void getTagPropertiesWithResponse(HttpClient httpClient) {
        ContainerRepositoryAsyncClient client = getContainerRepositoryAsyncClient(httpClient);

        StepVerifier.create(client.getTagPropertiesWithResponse(LATEST_TAG_NAME))
            .assertNext(res -> validateTagProperties(res, LATEST_TAG_NAME))
            .verifyComplete();
    }

    @ParameterizedTest(name = DISPLAY_NAME_WITH_ARGUMENTS)
    @MethodSource("getHttpClients")
    public void getTagPropertiesWithResponseThrows(HttpClient httpClient) {
        ContainerRepositoryAsyncClient client = getContainerRepositoryAsyncClient(httpClient);

        StepVerifier.create(client.getTagPropertiesWithResponse(TAG_UNKNOWN))
            .expectError(ResourceNotFoundException.class)
            .verify();
    }
}

