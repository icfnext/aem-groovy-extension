package com.citytechinc.aem.groovy.extension.services.impl

import com.citytechinc.aem.groovy.extension.api.MetaClassExtensionProvider
import com.citytechinc.aem.prosper.specs.ProsperSpec
import com.day.cq.wcm.api.Page
import spock.lang.Unroll

import javax.jcr.Binary
import javax.jcr.Node

@Unroll
class DefaultExtensionServiceSpec extends ProsperSpec {

    class TestMetaClassExtensionProvider implements MetaClassExtensionProvider {

        @Override
        Map<Class, Closure> getMetaClasses() {
            [(String): {}, (Integer): {}]
        }
    }

    def "get metaclasses"() {
        setup:
        def extensionService = new DefaultExtensionService()

        extensions.each {
            extensionService.bindMetaClassExtensions(it)
        }

        expect:
        extensionService.metaClasses == classes as LinkedHashSet

        where:
        extensions                                                                      | classes
        []                                                                              | []
        [new DefaultMetaClassExtensionProvider()]                                       | [Binary, Node, Page]
        [new DefaultMetaClassExtensionProvider(), new TestMetaClassExtensionProvider()] | [Binary, Node, Page, String, Integer]
    }
}
