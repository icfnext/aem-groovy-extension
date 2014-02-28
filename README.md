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

2. Install OSGi bundle.

    mvn install -P install-bundle

To install to a non-localhost AEM environment, connection parameters can be passed as Maven properties on the command line.

    mvn install -P install-bundle -Daem.host=hostname -Daem.port=7402

3.

## Versioning

Follows [Semantic Versioning](http://semver.org/) guidelines.