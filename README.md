# CITYTECH, Inc. AEM Groovy Extension

[CITYTECH, Inc.](http://www.citytechinc.com)

## Overview

OSGi bundle containing Groovy builders and metaclasses for Adobe AEM (CQ).

See the [project site](http://code.citytechinc.com/aem-groovy-extension) for additional details.

## Usage

1. Add Maven dependency to project POM.

        &lt;dependency&gt;
            &lt;groupId&gt;com.citytechinc.aem.groovy.extension&lt;/groupId&gt;
            &lt;artifactId&gt;aem-groovy-extension-bundle&lt;/artifactId&gt;
            &lt;version&gt;0.6.0&lt;/version&gt;
            &lt;scope&gt;provided&lt;/scope&gt;
        &lt;/dependency&gt;

2. Install Groovy and extension OSGi bundles.

        mvn install -P install-bundles

    To install to a non-localhost AEM environment, connection parameters can be passed as Maven properties on the command line.

        mvn install -P install-bundles -Daem.host=hostname -Daem.port=7402

## Versioning

Follows [Semantic Versioning](http://semver.org/) guidelines.