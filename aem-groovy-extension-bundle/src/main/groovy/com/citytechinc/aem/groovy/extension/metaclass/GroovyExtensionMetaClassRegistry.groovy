package com.citytechinc.aem.groovy.extension.metaclass

import com.citytechinc.aem.groovy.extension.services.impl.DefaultMetaClassExtensionProvider
import org.codehaus.groovy.runtime.InvokerHelper

/**
 * Provides access to AEM metaclasses outside of the OSGi container (e.g. for testing).
 */
class GroovyExtensionMetaClassRegistry {

    private static final Map<Class, Closure> EXTENSIONS = new DefaultMetaClassExtensionProvider().metaClasses

    /**
     * Register metaclasses.
     */
    static void registerMetaClasses() {
        // remove any existing metaclasses
        removeMetaClasses()

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
