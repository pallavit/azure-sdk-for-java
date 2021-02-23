// Copyright (c) Microsoft Corporation. All rights reserved.
// Licensed under the MIT License.
// Code generated by Microsoft (R) AutoRest Code Generator.

package com.azure.containers.containerregistry.implementation.models;

import com.azure.core.annotation.Fluent;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

/** Signature of a signed manifest. */
@JacksonXmlRootElement(localName = "ImageSignature")
@Fluent
public final class ImageSignature {
    /*
     * A JSON web signature
     */
    @JsonProperty(value = "header")
    private JWK headerProperty;

    /*
     * A signature for the image manifest, signed by a libtrust private key
     */
    @JsonProperty(value = "signature")
    private String signature;

    /*
     * The signed protected header
     */
    @JsonProperty(value = "protected")
    private String protectedProperty;

    /**
     * Get the headerProperty property: A JSON web signature.
     *
     * @return the headerProperty value.
     */
    public JWK getHeaderProperty() {
        return this.headerProperty;
    }

    /**
     * Set the headerProperty property: A JSON web signature.
     *
     * @param headerProperty the headerProperty value to set.
     * @return the ImageSignature object itself.
     */
    public ImageSignature setHeaderProperty(JWK headerProperty) {
        this.headerProperty = headerProperty;
        return this;
    }

    /**
     * Get the signature property: A signature for the image manifest, signed by a libtrust private key.
     *
     * @return the signature value.
     */
    public String getSignature() {
        return this.signature;
    }

    /**
     * Set the signature property: A signature for the image manifest, signed by a libtrust private key.
     *
     * @param signature the signature value to set.
     * @return the ImageSignature object itself.
     */
    public ImageSignature setSignature(String signature) {
        this.signature = signature;
        return this;
    }

    /**
     * Get the protectedProperty property: The signed protected header.
     *
     * @return the protectedProperty value.
     */
    public String getProtectedProperty() {
        return this.protectedProperty;
    }

    /**
     * Set the protectedProperty property: The signed protected header.
     *
     * @param protectedProperty the protectedProperty value to set.
     * @return the ImageSignature object itself.
     */
    public ImageSignature setProtectedProperty(String protectedProperty) {
        this.protectedProperty = protectedProperty;
        return this;
    }
}
