package com.citytechinc.aem.groovy.extension

import com.citytechinc.aem.groovy.extension.metaclass.GroovyExtensionMetaClassRegistry
import com.citytechinc.aem.prosper.specs.ProsperSpec

abstract class GroovyExtensionSpec extends ProsperSpec {

    def setupSpec() {
        GroovyExtensionMetaClassRegistry.registerMetaClasses()
    }

    def cleanupSpec() {
        GroovyExtensionMetaClassRegistry.removeMetaClasses()
    }

    def cleanup() {
        removeAllNodes()
    }
}
