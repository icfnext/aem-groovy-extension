package com.citytechinc.aem.groovy.extension

import com.citytechinc.aem.spock.specs.AbstractSlingRepositorySpec
import com.citytechinc.aem.groovy.extension.builders.NodeBuilder
import com.citytechinc.aem.groovy.extension.builders.PageBuilder
import com.citytechinc.aem.groovy.extension.metaclass.GroovyExtensionMetaClassRegistry
import com.day.cq.wcm.api.PageManager
import spock.lang.Shared

abstract class AbstractGroovyExtensionSpec extends AbstractSlingRepositorySpec {

    @Shared nodeBuilder

    @Shared pageBuilder

    def setupSpec() {
        GroovyExtensionMetaClassRegistry.registerMetaClasses()

        nodeBuilder = new NodeBuilder(session)
        pageBuilder = new PageBuilder(session)
    }

    def cleanupSpec() {
        GroovyExtensionMetaClassRegistry.removeMetaClasses()
    }

    def cleanup() {
        removeAllNodes()
    }

    def getNode(path) {
        session.getNode(path)
    }

    def getPage(path) {
        resourceResolver.adaptTo(PageManager).getPage(path)
    }
}
