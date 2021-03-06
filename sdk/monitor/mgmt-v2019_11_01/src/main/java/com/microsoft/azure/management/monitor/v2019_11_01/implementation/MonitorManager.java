/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 *
 * Code generated by Microsoft (R) AutoRest Code Generator.
 */

package com.microsoft.azure.management.monitor.v2019_11_01.implementation;

import com.microsoft.azure.AzureEnvironment;
import com.microsoft.azure.AzureResponseBuilder;
import com.microsoft.azure.credentials.AzureTokenCredentials;
import com.microsoft.azure.management.apigeneration.Beta;
import com.microsoft.azure.management.apigeneration.Beta.SinceVersion;
import com.microsoft.azure.arm.resources.AzureConfigurable;
import com.microsoft.azure.serializer.AzureJacksonAdapter;
import com.microsoft.rest.RestClient;
import com.microsoft.azure.management.monitor.v2019_11_01.AutoscaleSettings;
import com.microsoft.azure.management.monitor.v2019_11_01.Operations;
import com.microsoft.azure.management.monitor.v2019_11_01.AlertRuleIncidents;
import com.microsoft.azure.management.monitor.v2019_11_01.AlertRules;
import com.microsoft.azure.management.monitor.v2019_11_01.LogProfiles;
import com.microsoft.azure.management.monitor.v2019_11_01.DiagnosticSettings;
import com.microsoft.azure.management.monitor.v2019_11_01.SubscriptionDiagnosticSettings;
import com.microsoft.azure.management.monitor.v2019_11_01.DiagnosticSettingsCategorys;
import com.microsoft.azure.management.monitor.v2019_11_01.ActionGroups;
import com.microsoft.azure.management.monitor.v2019_11_01.ActivityLogAlerts;
import com.microsoft.azure.management.monitor.v2019_11_01.ActivityLogs;
import com.microsoft.azure.management.monitor.v2019_11_01.EventCategories;
import com.microsoft.azure.management.monitor.v2019_11_01.TenantActivityLogs;
import com.microsoft.azure.management.monitor.v2019_11_01.MetricDefinitions;
import com.microsoft.azure.management.monitor.v2019_11_01.Metrics;
import com.microsoft.azure.management.monitor.v2019_11_01.MetricBaselines;
import com.microsoft.azure.management.monitor.v2019_11_01.Baselines;
import com.microsoft.azure.management.monitor.v2019_11_01.MetricAlerts;
import com.microsoft.azure.management.monitor.v2019_11_01.MetricAlertsStatus;
import com.microsoft.azure.management.monitor.v2019_11_01.ScheduledQueryRules;
import com.microsoft.azure.management.monitor.v2019_11_01.MetricNamespaces;
import com.microsoft.azure.management.monitor.v2019_11_01.VMInsights;
import com.microsoft.azure.management.monitor.v2019_11_01.PrivateLinkScopes;
import com.microsoft.azure.management.monitor.v2019_11_01.PrivateLinkScopeOperationStatus;
import com.microsoft.azure.management.monitor.v2019_11_01.PrivateLinkResources;
import com.microsoft.azure.management.monitor.v2019_11_01.PrivateEndpointConnections;
import com.microsoft.azure.management.monitor.v2019_11_01.PrivateLinkScopedResources;
import com.microsoft.azure.arm.resources.implementation.AzureConfigurableCoreImpl;
import com.microsoft.azure.arm.resources.implementation.ManagerCore;

/**
 * Entry point to Azure Insights resource management.
 */
public final class MonitorManager extends ManagerCore<MonitorManager, MonitorManagementClientImpl> {
    private AutoscaleSettings autoscaleSettings;
    private Operations operations;
    private AlertRuleIncidents alertRuleIncidents;
    private AlertRules alertRules;
    private LogProfiles logProfiles;
    private DiagnosticSettings diagnosticSettings;
    private SubscriptionDiagnosticSettings subscriptionDiagnosticSettings;
    private DiagnosticSettingsCategorys diagnosticSettingsCategorys;
    private ActionGroups actionGroups;
    private ActivityLogAlerts activityLogAlerts;
    private ActivityLogs activityLogs;
    private EventCategories eventCategories;
    private TenantActivityLogs tenantActivityLogs;
    private MetricDefinitions metricDefinitions;
    private Metrics metrics;
    private MetricBaselines metricBaselines;
    private Baselines baselines;
    private MetricAlerts metricAlerts;
    private MetricAlertsStatus metricAlertsStatus;
    private ScheduledQueryRules scheduledQueryRules;
    private MetricNamespaces metricNamespaces;
    private VMInsights vMInsights;
    private PrivateLinkScopes privateLinkScopes;
    private PrivateLinkScopeOperationStatus privateLinkScopeOperationStatus;
    private PrivateLinkResources privateLinkResources;
    private PrivateEndpointConnections privateEndpointConnections;
    private PrivateLinkScopedResources privateLinkScopedResources;
    /**
    * Get a Configurable instance that can be used to create MonitorManager with optional configuration.
    *
    * @return the instance allowing configurations
    */
    public static Configurable configure() {
        return new MonitorManager.ConfigurableImpl();
    }
    /**
    * Creates an instance of MonitorManager that exposes Insights resource management API entry points.
    *
    * @param credentials the credentials to use
    * @param subscriptionId the subscription UUID
    * @return the MonitorManager
    */
    public static MonitorManager authenticate(AzureTokenCredentials credentials, String subscriptionId) {
        return new MonitorManager(new RestClient.Builder()
            .withBaseUrl(credentials.environment(), AzureEnvironment.Endpoint.RESOURCE_MANAGER)
            .withCredentials(credentials)
            .withSerializerAdapter(new AzureJacksonAdapter())
            .withResponseBuilderFactory(new AzureResponseBuilder.Factory())
            .build(), subscriptionId);
    }
    /**
    * Creates an instance of MonitorManager that exposes Insights resource management API entry points.
    *
    * @param restClient the RestClient to be used for API calls.
    * @param subscriptionId the subscription UUID
    * @return the MonitorManager
    */
    public static MonitorManager authenticate(RestClient restClient, String subscriptionId) {
        return new MonitorManager(restClient, subscriptionId);
    }
    /**
    * The interface allowing configurations to be set.
    */
    public interface Configurable extends AzureConfigurable<Configurable> {
        /**
        * Creates an instance of MonitorManager that exposes Insights management API entry points.
        *
        * @param credentials the credentials to use
        * @param subscriptionId the subscription UUID
        * @return the interface exposing Insights management API entry points that work across subscriptions
        */
        MonitorManager authenticate(AzureTokenCredentials credentials, String subscriptionId);
    }

    /**
     * @return Entry point to manage AutoscaleSettings.
     */
    public AutoscaleSettings autoscaleSettings() {
        if (this.autoscaleSettings == null) {
            this.autoscaleSettings = new AutoscaleSettingsImpl(this);
        }
        return this.autoscaleSettings;
    }

    /**
     * @return Entry point to manage Operations.
     */
    public Operations operations() {
        if (this.operations == null) {
            this.operations = new OperationsImpl(this);
        }
        return this.operations;
    }

    /**
     * @return Entry point to manage AlertRuleIncidents.
     */
    public AlertRuleIncidents alertRuleIncidents() {
        if (this.alertRuleIncidents == null) {
            this.alertRuleIncidents = new AlertRuleIncidentsImpl(this);
        }
        return this.alertRuleIncidents;
    }

    /**
     * @return Entry point to manage AlertRules.
     */
    public AlertRules alertRules() {
        if (this.alertRules == null) {
            this.alertRules = new AlertRulesImpl(this);
        }
        return this.alertRules;
    }

    /**
     * @return Entry point to manage LogProfiles.
     */
    public LogProfiles logProfiles() {
        if (this.logProfiles == null) {
            this.logProfiles = new LogProfilesImpl(this);
        }
        return this.logProfiles;
    }

    /**
     * @return Entry point to manage DiagnosticSettings.
     */
    public DiagnosticSettings diagnosticSettings() {
        if (this.diagnosticSettings == null) {
            this.diagnosticSettings = new DiagnosticSettingsImpl(this);
        }
        return this.diagnosticSettings;
    }

    /**
     * @return Entry point to manage SubscriptionDiagnosticSettings.
     */
    public SubscriptionDiagnosticSettings subscriptionDiagnosticSettings() {
        if (this.subscriptionDiagnosticSettings == null) {
            this.subscriptionDiagnosticSettings = new SubscriptionDiagnosticSettingsImpl(this);
        }
        return this.subscriptionDiagnosticSettings;
    }

    /**
     * @return Entry point to manage DiagnosticSettingsCategorys.
     */
    public DiagnosticSettingsCategorys diagnosticSettingsCategorys() {
        if (this.diagnosticSettingsCategorys == null) {
            this.diagnosticSettingsCategorys = new DiagnosticSettingsCategorysImpl(this);
        }
        return this.diagnosticSettingsCategorys;
    }

    /**
     * @return Entry point to manage ActionGroups.
     */
    public ActionGroups actionGroups() {
        if (this.actionGroups == null) {
            this.actionGroups = new ActionGroupsImpl(this);
        }
        return this.actionGroups;
    }

    /**
     * @return Entry point to manage ActivityLogAlerts.
     */
    public ActivityLogAlerts activityLogAlerts() {
        if (this.activityLogAlerts == null) {
            this.activityLogAlerts = new ActivityLogAlertsImpl(this);
        }
        return this.activityLogAlerts;
    }

    /**
     * @return Entry point to manage ActivityLogs.
     */
    public ActivityLogs activityLogs() {
        if (this.activityLogs == null) {
            this.activityLogs = new ActivityLogsImpl(this);
        }
        return this.activityLogs;
    }

    /**
     * @return Entry point to manage EventCategories.
     */
    public EventCategories eventCategories() {
        if (this.eventCategories == null) {
            this.eventCategories = new EventCategoriesImpl(this);
        }
        return this.eventCategories;
    }

    /**
     * @return Entry point to manage TenantActivityLogs.
     */
    public TenantActivityLogs tenantActivityLogs() {
        if (this.tenantActivityLogs == null) {
            this.tenantActivityLogs = new TenantActivityLogsImpl(this);
        }
        return this.tenantActivityLogs;
    }

    /**
     * @return Entry point to manage MetricDefinitions.
     */
    public MetricDefinitions metricDefinitions() {
        if (this.metricDefinitions == null) {
            this.metricDefinitions = new MetricDefinitionsImpl(this);
        }
        return this.metricDefinitions;
    }

    /**
     * @return Entry point to manage Metrics.
     */
    public Metrics metrics() {
        if (this.metrics == null) {
            this.metrics = new MetricsImpl(this);
        }
        return this.metrics;
    }

    /**
     * @return Entry point to manage MetricBaselines.
     */
    public MetricBaselines metricBaselines() {
        if (this.metricBaselines == null) {
            this.metricBaselines = new MetricBaselinesImpl(this);
        }
        return this.metricBaselines;
    }

    /**
     * @return Entry point to manage Baselines.
     */
    public Baselines baselines() {
        if (this.baselines == null) {
            this.baselines = new BaselinesImpl(this);
        }
        return this.baselines;
    }

    /**
     * @return Entry point to manage MetricAlerts.
     */
    public MetricAlerts metricAlerts() {
        if (this.metricAlerts == null) {
            this.metricAlerts = new MetricAlertsImpl(this);
        }
        return this.metricAlerts;
    }

    /**
     * @return Entry point to manage MetricAlertsStatus.
     */
    public MetricAlertsStatus metricAlertsStatus() {
        if (this.metricAlertsStatus == null) {
            this.metricAlertsStatus = new MetricAlertsStatusImpl(this);
        }
        return this.metricAlertsStatus;
    }

    /**
     * @return Entry point to manage ScheduledQueryRules.
     */
    public ScheduledQueryRules scheduledQueryRules() {
        if (this.scheduledQueryRules == null) {
            this.scheduledQueryRules = new ScheduledQueryRulesImpl(this);
        }
        return this.scheduledQueryRules;
    }

    /**
     * @return Entry point to manage MetricNamespaces.
     */
    public MetricNamespaces metricNamespaces() {
        if (this.metricNamespaces == null) {
            this.metricNamespaces = new MetricNamespacesImpl(this);
        }
        return this.metricNamespaces;
    }

    /**
     * @return Entry point to manage VMInsights.
     */
    public VMInsights vMInsights() {
        if (this.vMInsights == null) {
            this.vMInsights = new VMInsightsImpl(this);
        }
        return this.vMInsights;
    }

    /**
     * @return Entry point to manage PrivateLinkScopes.
     */
    public PrivateLinkScopes privateLinkScopes() {
        if (this.privateLinkScopes == null) {
            this.privateLinkScopes = new PrivateLinkScopesImpl(this);
        }
        return this.privateLinkScopes;
    }

    /**
     * @return Entry point to manage PrivateLinkScopeOperationStatus.
     */
    public PrivateLinkScopeOperationStatus privateLinkScopeOperationStatus() {
        if (this.privateLinkScopeOperationStatus == null) {
            this.privateLinkScopeOperationStatus = new PrivateLinkScopeOperationStatusImpl(this);
        }
        return this.privateLinkScopeOperationStatus;
    }

    /**
     * @return Entry point to manage PrivateLinkResources.
     */
    public PrivateLinkResources privateLinkResources() {
        if (this.privateLinkResources == null) {
            this.privateLinkResources = new PrivateLinkResourcesImpl(this);
        }
        return this.privateLinkResources;
    }

    /**
     * @return Entry point to manage PrivateEndpointConnections.
     */
    public PrivateEndpointConnections privateEndpointConnections() {
        if (this.privateEndpointConnections == null) {
            this.privateEndpointConnections = new PrivateEndpointConnectionsImpl(this);
        }
        return this.privateEndpointConnections;
    }

    /**
     * @return Entry point to manage PrivateLinkScopedResources.
     */
    public PrivateLinkScopedResources privateLinkScopedResources() {
        if (this.privateLinkScopedResources == null) {
            this.privateLinkScopedResources = new PrivateLinkScopedResourcesImpl(this);
        }
        return this.privateLinkScopedResources;
    }

    /**
    * The implementation for Configurable interface.
    */
    private static final class ConfigurableImpl extends AzureConfigurableCoreImpl<Configurable> implements Configurable {
        public MonitorManager authenticate(AzureTokenCredentials credentials, String subscriptionId) {
           return MonitorManager.authenticate(buildRestClient(credentials), subscriptionId);
        }
     }
    private MonitorManager(RestClient restClient, String subscriptionId) {
        super(
            restClient,
            subscriptionId,
            new MonitorManagementClientImpl(restClient).withSubscriptionId(subscriptionId));
    }
}
