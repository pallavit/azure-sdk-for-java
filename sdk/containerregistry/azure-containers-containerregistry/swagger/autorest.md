# Azure Containers ContainerRegistry APIs for Java

> see https://aka.ms/autorest

## Getting Started

To build the client SDK for ContainerRegistry simply [Install AutoRest](https://github.com/Azure/autorest/blob/master/docs/install/readme.md) and in this folder, run:

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

There is one swagger for Container Registry APIs.

```ps
cd <swagger-folder>
autorest --java --use:@autorest/java@4.0.x
```

### Code generation settings
``` yaml
input-file: https://github.com/Azure/azure-sdk-for-js/blob/1998b841dcfa3fd17f0d8e0a4973ea61a25d2ecb/sdk/containerregistry/container-registry/swagger/containerregistry.json
java: true
output-folder: ./../check2
generate-client-as-impl: true
namespace: com.azure.containers.containerregistry
generate-client-interfaces: false
license-header: MICROSOFT_MIT_SMALL
add-context-parameter: true
context-client-method-parameter: true
sync-methods: all
models-subpackage: implementation.models
custom-types: ContentProperties,RegistryArtifactOrderBy,TagOrderBy,RepositoryAttributes,ManifestAttributes
custom-types-subpackage: models
```

directive:
- from: swagger-document
  where: $.definitions.DeletedRepository
  transform: >
  $["properties"]["manifestsDeleted"].readOnly = true;
  $["properties"]["tagsDeleted"].readOnly = true;

directive:
- from: swagger-document
  where: $.definitions.RepositoryAttributes
  transform: >
  $["properties"]["imageName"].readOnly = true;
  $["properties"]["createdTime"].readOnly = true;
  $["properties"]["lastUpdateTime"].readOnly = true;
  $["properties"]["manifestCount"].readOnly = true;
  $["properties"]["tagCount"].readOnly = true;
  $["properties"]["changeableAttributes"].readOnly = true;
  
directive:
- from: swagger-document
  where: $.definitions.ManifestAttributes
  transform: >
  $["properties"]["imageName"].readOnly = true;
  $["properties"]["manifest"].readOnly = true;
