package com.citytechinc.aem.groovy.extension

import com.citytechinc.aem.prosper.specs.ProsperSpec

abstract class GroovyExtensionSpec extends ProsperSpec {

    def cleanup() {
        removeAllNodes()
    }
}
