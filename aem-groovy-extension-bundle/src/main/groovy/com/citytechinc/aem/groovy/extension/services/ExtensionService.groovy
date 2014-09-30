package com.citytechinc.aem.groovy.extension.services

/**
 * The extension service is responsible for aggregrating all metaclass extension providers and handling the dynamic
 * registration and removal of metaclass closures.
 */
interface ExtensionService {

    /**
     * Get the set of all classes that have associated metaclasses.  This value may change as metaclass extension
     * providers are activated or disabled in the OSGi container.
     *
     * @return set of classes with registered metaclasses
     */
    Set<Class> getMetaClasses()
}
