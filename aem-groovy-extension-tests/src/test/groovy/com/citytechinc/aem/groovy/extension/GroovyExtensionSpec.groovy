package com.citytechinc.aem.groovy.extension

import com.citytechinc.aem.spock.specs.AemSpec
import com.day.cq.wcm.api.PageManager

abstract class GroovyExtensionSpec extends AemSpec {

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
