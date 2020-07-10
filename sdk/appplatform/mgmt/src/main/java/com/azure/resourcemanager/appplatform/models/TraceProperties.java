// Copyright (c) Microsoft Corporation. All rights reserved.
// Licensed under the MIT License.
// Code generated by Microsoft (R) AutoRest Code Generator.

package com.azure.resourcemanager.appplatform.models;

import com.azure.core.annotation.Fluent;
import com.azure.core.util.logging.ClientLogger;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

/** The TraceProperties model. */
@Fluent
public final class TraceProperties {
    @JsonIgnore private final ClientLogger logger = new ClientLogger(TraceProperties.class);

    /*
     * State of the trace proxy.
     */
    @JsonProperty(value = "state", access = JsonProperty.Access.WRITE_ONLY)
    private TraceProxyState state;

    /*
     * Error when apply trace proxy changes.
     */
    @JsonProperty(value = "error")
    private Error error;

    /*
     * Indicates whether enable the tracing functionality
     */
    @JsonProperty(value = "enabled")
    private Boolean enabled;

    /*
     * Target application insight instrumentation key
     */
    @JsonProperty(value = "appInsightInstrumentationKey")
    private String appInsightInstrumentationKey;

    /**
     * Get the state property: State of the trace proxy.
     *
     * @return the state value.
     */
    public TraceProxyState state() {
        return this.state;
    }

    /**
     * Get the error property: Error when apply trace proxy changes.
     *
     * @return the error value.
     */
    public Error error() {
        return this.error;
    }

    /**
     * Set the error property: Error when apply trace proxy changes.
     *
     * @param error the error value to set.
     * @return the TraceProperties object itself.
     */
    public TraceProperties withError(Error error) {
        this.error = error;
        return this;
    }

    /**
     * Get the enabled property: Indicates whether enable the tracing functionality.
     *
     * @return the enabled value.
     */
    public Boolean enabled() {
        return this.enabled;
    }

    /**
     * Set the enabled property: Indicates whether enable the tracing functionality.
     *
     * @param enabled the enabled value to set.
     * @return the TraceProperties object itself.
     */
    public TraceProperties withEnabled(Boolean enabled) {
        this.enabled = enabled;
        return this;
    }

    /**
     * Get the appInsightInstrumentationKey property: Target application insight instrumentation key.
     *
     * @return the appInsightInstrumentationKey value.
     */
    public String appInsightInstrumentationKey() {
        return this.appInsightInstrumentationKey;
    }

    /**
     * Set the appInsightInstrumentationKey property: Target application insight instrumentation key.
     *
     * @param appInsightInstrumentationKey the appInsightInstrumentationKey value to set.
     * @return the TraceProperties object itself.
     */
    public TraceProperties withAppInsightInstrumentationKey(String appInsightInstrumentationKey) {
        this.appInsightInstrumentationKey = appInsightInstrumentationKey;
        return this;
    }

    /**
     * Validates the instance.
     *
     * @throws IllegalArgumentException thrown if the instance is not valid.
     */
    public void validate() {
        if (error() != null) {
            error().validate();
        }
    }
}
