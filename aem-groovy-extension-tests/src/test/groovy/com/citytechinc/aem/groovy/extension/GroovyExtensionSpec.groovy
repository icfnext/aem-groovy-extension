package com.citytechinc.aem.groovy.extension

import com.citytechinc.aem.prosper.specs.ProsperSpec
import com.day.cq.wcm.api.PageManager
import spock.lang.Shared

abstract class GroovyExtensionSpec extends ProsperSpec {

    @Shared featurePageManager

    def setup() {
        featurePageManager = resourceResolver.adaptTo(PageManager)
    }

    def cleanup() {
        removeAllNodes()
    }

    def getNode(String path) {
        session.getNode(path)
    }

    def getPage(String path) {
        featurePageManager.getPage(path)
    }
}
