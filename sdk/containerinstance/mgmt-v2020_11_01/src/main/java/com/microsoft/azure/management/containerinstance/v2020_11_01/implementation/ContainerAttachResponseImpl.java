/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 *
 * Code generated by Microsoft (R) AutoRest Code Generator.
 */

package com.microsoft.azure.management.containerinstance.v2020_11_01.implementation;

import com.microsoft.azure.management.containerinstance.v2020_11_01.ContainerAttachResponse;
import com.microsoft.azure.arm.model.implementation.WrapperImpl;

class ContainerAttachResponseImpl extends WrapperImpl<ContainerAttachResponseInner> implements ContainerAttachResponse {
    private final ContainerInstanceManager manager;
    ContainerAttachResponseImpl(ContainerAttachResponseInner inner, ContainerInstanceManager manager) {
        super(inner);
        this.manager = manager;
    }

    @Override
    public ContainerInstanceManager manager() {
        return this.manager;
    }

    @Override
    public String password() {
        return this.inner().password();
    }

    @Override
    public String webSocketUri() {
        return this.inner().webSocketUri();
    }

}
