package com.icfolson.aem.groovy.extension

import com.icfolson.aem.groovy.extension.api.MetaClassExtensionProvider
import com.icfolson.aem.groovy.extension.services.impl.DefaultMetaClassExtensionProvider
import com.icfolson.aem.prosper.specs.ProsperSpec

abstract class GroovyExtensionSpec extends ProsperSpec {

    def setupSpec() {
        slingContext.registerService(MetaClassExtensionProvider, new DefaultMetaClassExtensionProvider())

        // GroovyExtensionMetaClassRegistry.registerMetaClasses()
    }
}
