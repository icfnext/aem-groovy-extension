# AEM Groovy Extension

[CITYTECH, Inc.](http://www.citytechinc.com)

## Overview

OSGi bundle containing Groovy builders and metaclasses for AEM (Adobe CQ).

```groovy
new NodeBuilder(session).content {
    satirists("sling:Folder") {
        bierce(firstName: "Ambrose", lastName: "Bierce", birthDate: Calendar.instance.updated(year: 1842, month: 5, date: 24))
        mencken(firstName: "H.L.", lastName: "Mencken", birthDate: Calendar.instance.updated(year: 1880, month: 8, date: 12))
        other("sling:Folder", "jcr:title": "Other")
    }
}
```

## Usage

1. Add dependency to your project's Maven `pom.xml`.

        <dependency>
            <groupId>com.citytechinc.aem.groovy.extension</groupId>
            <artifactId>aem-groovy-extension-bundle</artifactId>
            <version>0.6.2</version>
            <scope>provided</scope>
        </dependency>

2. Install Groovy and extension OSGi bundles from the command line.

        mvn install -P install-bundles

    To install to a non-localhost AEM environment, connection parameters can be passed as Maven properties on the command line (see bundle `pom.xml` for additional environment property names).

        mvn install -P install-bundles -Daem.host=hostname -Daem.port=7402

## Versioning

Follows [Semantic Versioning](http://semver.org/) guidelines.