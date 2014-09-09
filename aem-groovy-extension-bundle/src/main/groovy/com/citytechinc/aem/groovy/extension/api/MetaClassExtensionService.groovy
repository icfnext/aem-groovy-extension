package com.citytechinc.aem.groovy.extension.api

interface MetaClassExtensionService {

	Map<Class, Closure[]> getMetaClassExtensions()
}
