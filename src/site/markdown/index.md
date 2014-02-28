## Overview

The AEM Groovy Extension is an OSGi bundle containing Groovy builders and metaclasses for Adobe AEM (CQ).

## Usage

1. Add Maven dependency to project POM.

        <dependency>
            <groupId>com.citytechinc.aem.groovy.extension</groupId>
            <artifactId>aem-groovy-extension-bundle</artifactId>
            <version>0.6.0</version>
            <scope>provided</scope>
        </dependency>

2. Install Groovy and extension OSGi bundles.

        mvn install -P install-bundles

    To install to a non-localhost AEM environment, connection parameters can be passed as Maven properties on the command line.

        mvn install -P install-bundles -Daem.host=hostname -Daem.port=7402