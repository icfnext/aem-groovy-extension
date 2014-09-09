package com.citytechinc.aem.groovy.extension.metaclass

import javax.jcr.Binary
import javax.jcr.Node

import org.codehaus.groovy.runtime.InvokerHelper

import com.citytechinc.aem.groovy.extension.services.DefaultMetaClassExtensionService
import com.day.cq.wcm.api.Page

class GroovyExtensionMetaClassRegistry {

	static Map<Class, Closure> extensions = new DefaultMetaClassExtensionService().getMetaClassExtensions()

	/**
	 * Register metaclasses.
	 */
	static void registerMetaClasses() {
		registerBinaryMetaClass()
		registerNodeMetaClass()
		registerPageMetaClass()
	}

	/**
	 * Remove all metaclasses from the registry.
	 */
	static void removeMetaClasses() {
		def metaRegistry = InvokerHelper.metaRegistry

		metaRegistry.removeMetaClass(Binary)
		metaRegistry.removeMetaClass(Node)
		metaRegistry.removeMetaClass(Page)
	}

	private static void registerBinaryMetaClass() {
		extensions[Binary.class].each {Binary.metaClass it}
	}

	private static void registerNodeMetaClass() {
		extensions[Node.class].each {Node.metaClass it}
	}

	private static void registerPageMetaClass() {
		extensions[Page.class].each {Page.metaClass it}
	}
}
