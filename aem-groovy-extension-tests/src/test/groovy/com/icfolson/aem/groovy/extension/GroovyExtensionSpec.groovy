package com.icfolson.aem.groovy.extension

import com.icfolson.aem.groovy.extension.metaclass.GroovyExtensionMetaClassRegistry
import com.icfolson.aem.prosper.specs.ProsperSpec

abstract class GroovyExtensionSpec extends ProsperSpec {

    def setupSpec() {
        GroovyExtensionMetaClassRegistry.registerMetaClasses()
    }
}
