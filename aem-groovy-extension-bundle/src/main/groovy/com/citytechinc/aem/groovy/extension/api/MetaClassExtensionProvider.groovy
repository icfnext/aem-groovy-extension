package com.citytechinc.aem.groovy.extension.api

/**
 *
 */
interface MetaClassExtensionProvider {

    /**
     * @return map of classes and their associated metaclass closure
     */
    Map<Class, Closure> getMetaClasses()
}
