# Azure Containers ContainerRegistry APIs for Java

> see https://aka.ms/autorest

## Getting Started

To build the SDK for ContainerRegistryManagementClient simply [Install AutoRest](https://github.com/Azure/autorest/blob/master/docs/install/readme.md) and in this folder, run:

### Setup
```ps
You need to have the following installed on your machine:

Node.JS v10.x - v13.x
Java 8+
Maven 3.x
You need to have autorest-beta installed through NPM:

npm i -g autorest
```

### Generation

There is one swagger for Service Bus management APIs.

```ps
cd <swagger-folder>
autorest --java --use=C:/work/autorest.java
```

### Code generation settings
``` yaml
input-file: https://github.com/Azure/azure-rest-api-specs/blob/255757f41275e8ec474361690ea8886cae8a503b/specification/containerregistry/data-plane/Microsoft.ContainerRegistry/preview/2019-08-15/containerregistry.json
java: true
output-folder: ..\
generate-client-as-impl: true
namespace: com.azure.containers.containerregistry
generate-client-interfaces: false
sync-methods: none
license-header: MICROSOFT_MIT_SMALL
add-context-parameter: true
models-subpackage: implementation.models
# custom-types: AccessRights,AuthorizationRule,EntityStatus,NamespaceProperties,MessagingSku
# custom-types-subpackage: models
context-client-method-parameter: true
enable-xml: false
```
