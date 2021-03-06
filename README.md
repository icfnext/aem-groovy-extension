# AEM Groovy Extension

[ICF Next](http://www.icfnext.com/)

## Overview

OSGi bundle containing Groovy builders and metaclasses for Adobe Experience Manager.  The bundle exposes an API to implement extension "provider" services to register additional Groovy metaclasses in the container.

```groovy
new NodeBuilder(session).content {
    satirists("sling:Folder") {
        bierce(firstName: "Ambrose", lastName: "Bierce", birthDate: Calendar.instance.updated(year: 1842, month: 5, date: 24))
        mencken(firstName: "H.L.", lastName: "Mencken", birthDate: Calendar.instance.updated(year: 1880, month: 8, date: 12))
        other("sling:Folder", "jcr:title": "Other")
    }
}
```

See [Groovydocs](http://icfnext.github.io/aem-groovy-extension/groovydocs/index.html) for complete API documentation.

## Requirements

* AEM 6.x running on localhost:4502
* [Maven](http://maven.apache.org/) 3.x

## Compatibility

Groovy Extension Version(s) | AEM Version(s)
------------ | -------------
7.x.x | 6.3, 6.4, 6.5
6.x.x, 5.x.x | 6.4
4.x.x | 6.3
3.x.x, 2.x.x | 6.2
1.x.x | 6.1
0.9.x, 0.8.x | 6.0
<= 0.7.x | 5.6 (CQ5)

## Usage and Installation

1. Add dependency to your project's Maven `pom.xml`.

        <dependency>
            <groupId>com.icfolson.aem.groovy.extension</groupId>
            <artifactId>aem-groovy-extension-bundle</artifactId>
            <version>7.0.0</version>
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