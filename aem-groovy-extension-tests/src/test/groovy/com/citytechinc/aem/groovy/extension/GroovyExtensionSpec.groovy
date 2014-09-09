package com.citytechinc.aem.groovy.extension

import org.codehaus.groovy.runtime.InvokerHelper

import com.citytechinc.aem.groovy.extension.services.DefaultMetaClassExtensionService
import com.citytechinc.aem.prosper.specs.ProsperSpec

abstract class GroovyExtensionSpec extends ProsperSpec {

	def setupSpec() {
		DefaultMetaClassExtensionService service = new DefaultMetaClassExtensionService()
		service.getMetaClassExtensions()?.each { entry ->
			entry.value?.each {entry.key.metaClass it}
		}
	}

	def cleanupSpec() {
		DefaultMetaClassExtensionService service = new DefaultMetaClassExtensionService()
		service.getMetaClassExtensions()?.each { entry ->
			InvokerHelper.metaRegistry.removeMetaClass(entry.key)
		}
	}

	def cleanup() {
		removeAllNodes()
	}
}
