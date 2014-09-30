package com.citytechinc.aem.groovy.extension.services.impl

import com.citytechinc.aem.groovy.extension.api.MetaClassExtensionService
import com.citytechinc.aem.prosper.specs.ProsperSpec
import com.day.cq.wcm.api.Page
import spock.lang.Unroll

import javax.jcr.Binary
import javax.jcr.Node

@Unroll
class DefaultExtensionServiceSpec extends ProsperSpec {

    class TestMetaClassExtensionService implements MetaClassExtensionService {

        @Override
        Map<Class, Closure> getMetaClasses() {
            [(String): {}, (Integer): {}]
        }
    }

    def "get metaclasses"() {
        setup:
        def extensionService = new DefaultExtensionService()

        extensions.each {
            extensionService.bindMetaClassExtensionService(it)
        }

        expect:
        extensionService.metaClasses == classes as LinkedHashSet

        where:
        extensions                                                                    | classes
        []                                                                            | []
        [new DefaultMetaClassExtensionService()]                                      | [Binary, Node, Page]
        [new DefaultMetaClassExtensionService(), new TestMetaClassExtensionService()] | [Binary, Node, Page, String, Integer]
    }
}
