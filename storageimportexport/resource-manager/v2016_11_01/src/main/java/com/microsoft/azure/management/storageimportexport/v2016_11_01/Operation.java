/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 *
 * Code generated by Microsoft (R) AutoRest Code Generator.
 */

package com.microsoft.azure.management.storageimportexport.v2016_11_01;

import com.microsoft.azure.arm.model.HasInner;
import com.microsoft.azure.arm.resources.models.HasManager;
import com.microsoft.azure.management.storageimportexport.v2016_11_01.implementation.StorageImportExportManager;
import com.microsoft.azure.management.storageimportexport.v2016_11_01.implementation.OperationInner;

/**
 * Type representing Operation.
 */
public interface Operation extends HasInner<OperationInner>, HasManager<StorageImportExportManager> {
    /**
     * @return the description value.
     */
    String description();

    /**
     * @return the name value.
     */
    String name();

    /**
     * @return the operation value.
     */
    String operation();

    /**
     * @return the provider value.
     */
    String provider();

    /**
     * @return the resource value.
     */
    String resource();

}
