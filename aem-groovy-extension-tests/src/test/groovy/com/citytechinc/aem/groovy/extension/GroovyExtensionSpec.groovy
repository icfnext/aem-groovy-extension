package com.citytechinc.aem.groovy.extension

import com.citytechinc.aem.prosper.specs.ProsperSpec
import com.day.cq.wcm.api.PageManager

abstract class GroovyExtensionSpec extends ProsperSpec {

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
