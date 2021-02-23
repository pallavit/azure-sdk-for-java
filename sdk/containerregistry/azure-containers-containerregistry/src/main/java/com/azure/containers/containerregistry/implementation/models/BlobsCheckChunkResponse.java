// Copyright (c) Microsoft Corporation. All rights reserved.
// Licensed under the MIT License.
// Code generated by Microsoft (R) AutoRest Code Generator.

package com.azure.containers.containerregistry.implementation.models;

import com.azure.core.http.HttpHeaders;
import com.azure.core.http.HttpRequest;
import com.azure.core.http.rest.ResponseBase;

/** Contains all response data for the checkChunk operation. */
public final class BlobsCheckChunkResponse extends ResponseBase<BlobsCheckChunkHeaders, Void> {
    /**
     * Creates an instance of BlobsCheckChunkResponse.
     *
     * @param request the request which resulted in this BlobsCheckChunkResponse.
     * @param statusCode the status code of the HTTP response.
     * @param rawHeaders the raw headers of the HTTP response.
     * @param value the deserialized value of the HTTP response.
     * @param headers the deserialized headers of the HTTP response.
     */
    public BlobsCheckChunkResponse(
            HttpRequest request, int statusCode, HttpHeaders rawHeaders, Void value, BlobsCheckChunkHeaders headers) {
        super(request, statusCode, rawHeaders, value, headers);
    }
}
