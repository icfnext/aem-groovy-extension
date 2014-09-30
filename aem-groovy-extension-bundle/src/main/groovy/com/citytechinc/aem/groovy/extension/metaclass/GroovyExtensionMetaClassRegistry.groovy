package com.citytechinc.aem.groovy.extension.metaclass

import com.citytechinc.aem.groovy.extension.services.impl.DefaultMetaClassExtensionService
import org.codehaus.groovy.runtime.InvokerHelper

class GroovyExtensionMetaClassRegistry {

    static final Map<Class, Closure> EXTENSIONS = new DefaultMetaClassExtensionService().metaClasses

    /**
     * Register metaclasses.
     */
    static void registerMetaClasses() {
        EXTENSIONS.each { clazz, closure ->
            clazz.metaClass(closure)
        }
    }

    /**
     * Remove all metaclasses from the registry.
     */
    static void removeMetaClasses() {
        def metaRegistry = InvokerHelper.metaRegistry

        EXTENSIONS.keySet().each { clazz ->
            metaRegistry.removeMetaClass(clazz)
        }
    }
}
