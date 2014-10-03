package com.citytechinc.aem.groovy.extension.services

/**
 * The extension service is responsible for aggregrating all metaclass extension providers and handling the
 * registration and removal of metaclass closures as these provider implementations are added or removed from the
 * OSGi container.
 */
interface ExtensionService {

    /**
     * Get the set of all classes that have associated metaclasses.  This value may change as metaclass extension
     * providers are added or removed from the OSGi container.
     *
     * @return set of classes that have associated metaclasses registered in the container
     */
    Set<Class> getMetaClasses()
}
