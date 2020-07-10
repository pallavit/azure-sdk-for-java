// Copyright (c) Microsoft Corporation. All rights reserved.
// Licensed under the MIT License.
// Code generated by Microsoft (R) AutoRest Code Generator.

package com.azure.resourcemanager.appplatform.models;

import com.azure.core.annotation.Fluent;
import com.azure.core.util.logging.ClientLogger;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

/** The RegenerateTestKeyRequestPayload model. */
@Fluent
public final class RegenerateTestKeyRequestPayload {
    @JsonIgnore private final ClientLogger logger = new ClientLogger(RegenerateTestKeyRequestPayload.class);

    /*
     * Type of the test key
     */
    @JsonProperty(value = "keyType", required = true)
    private TestKeyType keyType;

    /**
     * Get the keyType property: Type of the test key.
     *
     * @return the keyType value.
     */
    public TestKeyType keyType() {
        return this.keyType;
    }

    /**
     * Set the keyType property: Type of the test key.
     *
     * @param keyType the keyType value to set.
     * @return the RegenerateTestKeyRequestPayload object itself.
     */
    public RegenerateTestKeyRequestPayload withKeyType(TestKeyType keyType) {
        this.keyType = keyType;
        return this;
    }

    /**
     * Validates the instance.
     *
     * @throws IllegalArgumentException thrown if the instance is not valid.
     */
    public void validate() {
        if (keyType() == null) {
            throw logger
                .logExceptionAsError(
                    new IllegalArgumentException(
                        "Missing required property keyType in model RegenerateTestKeyRequestPayload"));
        }
    }
}
