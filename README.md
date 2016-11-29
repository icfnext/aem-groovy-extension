# AEM Groovy Extension

[Olson Digital](http://www.digitalatolson.com/)

## Overview

OSGi bundle containing Groovy builders and metaclasses for AEM (Adobe CQ).  The bundle exposes an API to implement extension "provider" services to register additional Groovy metaclasses in the container.

```groovy
new NodeBuilder(session).content {
    satirists("sling:Folder") {
        bierce(firstName: "Ambrose", lastName: "Bierce", birthDate: Calendar.instance.updated(year: 1842, month: 5, date: 24))
        mencken(firstName: "H.L.", lastName: "Mencken", birthDate: Calendar.instance.updated(year: 1880, month: 8, date: 12))
        other("sling:Folder", "jcr:title": "Other")
    }
}
```

## Requirements

* AEM 6.2 running on localhost:4502 (versions 1.x and above)
* Versions 1.x.x are compatible with AEM 6.1.
* Versions 0.8.x - 0.9.x are compatible with AEM 6.0.
* Versions 0.7.x and below are compatible with CQ 5.6
* [Maven](http://maven.apache.org/) 3.x

## Usage and Installation

1. Add dependency to your project's Maven `pom.xml`.

        <dependency>
            <groupId>com.icfolson.aem.groovy.extension</groupId>
            <artifactId>aem-groovy-extension-bundle</artifactId>
            <version>2.0.0</version>
            <scope>provided</scope>
        </dependency>

2. Install the AEM Groovy Extension package.

        mvn install -P local

    or

        mvn install -P local,replicate

    The optional `replicate` profile activates the deployed package to the local publish instance.

    To install to a non-localhost AEM environment, connection parameters can be passed as Maven properties on the command line (see the parent POM file for additional environment property names).

        mvn install -P local -Daem.host=hostname -Daem.port.author=7402

## Context Path Support

If you are running AEM with a context path, set the Maven property `aem.context.path` during installation.

    mvn install -P local -Daem.context.path=/context

## Registering Additional Metaclasses

Services implementing the `com.icfolson.aem.groovy.extension.api.MetaClassExtensionProvider` will be automatically discovered and bound by the OSGi container.  These services can be implemented in any deployed bundle.  The AEM Groovy Extension bundle will handle the registration and removal of supplied metaclasses as these services are activated/deactivated in the container.  See the `DefaultMetaClassExtensionProvider` service for the proper closure syntax for registering metaclasses.

## Versioning

Follows [Semantic Versioning](http://semver.org/) guidelines.