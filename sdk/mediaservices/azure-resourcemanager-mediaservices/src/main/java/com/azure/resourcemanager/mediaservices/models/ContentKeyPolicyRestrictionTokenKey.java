// Copyright (c) Microsoft Corporation. All rights reserved.
// Licensed under the MIT License.
// Code generated by Microsoft (R) AutoRest Code Generator.

package com.azure.resourcemanager.mediaservices.models;

import com.azure.core.annotation.Immutable;
import com.azure.core.annotation.JsonFlatten;
import com.azure.core.util.logging.ClientLogger;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeName;

/** Base class for Content Key Policy key for token validation. A derived class must be used to create a token key. */
@JsonTypeInfo(
    use = JsonTypeInfo.Id.NAME,
    include = JsonTypeInfo.As.PROPERTY,
    property = "@odata\\.type",
    defaultImpl = ContentKeyPolicyRestrictionTokenKey.class)
@JsonTypeName("ContentKeyPolicyRestrictionTokenKey")
@JsonSubTypes({
    @JsonSubTypes.Type(
        name = "#Microsoft.Media.ContentKeyPolicySymmetricTokenKey",
        value = ContentKeyPolicySymmetricTokenKey.class),
    @JsonSubTypes.Type(
        name = "#Microsoft.Media.ContentKeyPolicyRsaTokenKey",
        value = ContentKeyPolicyRsaTokenKey.class),
    @JsonSubTypes.Type(
        name = "#Microsoft.Media.ContentKeyPolicyX509CertificateTokenKey",
        value = ContentKeyPolicyX509CertificateTokenKey.class)
})
@JsonFlatten
@Immutable
public class ContentKeyPolicyRestrictionTokenKey {
    @JsonIgnore private final ClientLogger logger = new ClientLogger(ContentKeyPolicyRestrictionTokenKey.class);

    /**
     * Validates the instance.
     *
     * @throws IllegalArgumentException thrown if the instance is not valid.
     */
    public void validate() {
    }
}
