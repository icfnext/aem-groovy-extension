package com.citytechinc.aem.groovy.extension.services.impl

import com.citytechinc.aem.groovy.extension.api.MetaClassExtensionProvider
import com.citytechinc.aem.prosper.specs.ProsperSpec
import com.day.cq.wcm.api.Page
import org.codehaus.groovy.runtime.InvokerHelper
import spock.lang.Unroll

import javax.jcr.Binary
import javax.jcr.Node

@Unroll
class DefaultExtensionServiceSpec extends ProsperSpec {

    class StringMetaClassExtensionProvider implements MetaClassExtensionProvider {

        @Override
        Map<Class, Closure> getMetaClasses() {
            [(String): {
                upper {
                    delegate.toUpperCase()
                }
            }]
        }
    }

    class AnotherStringMetaClassExtensionProvider implements MetaClassExtensionProvider {

        @Override
        Map<Class, Closure> getMetaClasses() {
            [(String): {
                lower {
                    delegate.toLowerCase()
                }
            }]
        }
    }

    def "get metaclasses"() {
        setup:
        def extensionService = new DefaultExtensionService()

        extensions.each {
            extensionService.bindMetaClassExtensionProvider(it)
        }

        expect:
        extensionService.metaClasses == classes as LinkedHashSet

        where:
        extensions                                                                        | classes
        []                                                                                | []
        [new DefaultMetaClassExtensionProvider()]                                         | [Binary, Node, Page]
        [new DefaultMetaClassExtensionProvider(), new StringMetaClassExtensionProvider()] | [Binary, Node, Page, String]
    }

    def "metaclass is registered after binding extension provider"() {
        setup:
        def extensionService = new DefaultExtensionService()
        def provider = new StringMetaClassExtensionProvider()

        when:
        extensionService.bindMetaClassExtensionProvider(provider)

        then:
        extensionService.metaClasses.every { InvokerHelper.metaRegistry.getMetaClass(it) }

        and:
        "hello".upper() == "HELLO"
    }

    def "metaclass is removed after unbinding extension provider"() {
        setup:
        def extensionService = new DefaultExtensionService()
        def provider = new StringMetaClassExtensionProvider()

        extensionService.bindMetaClassExtensionProvider(provider)

        when:
        extensionService.unbindMetaClassExtensionProvider(provider)

        then:
        extensionService.metaClasses.every { !InvokerHelper.metaRegistry.getMetaClass(it) }
    }

    def "additional metaclass for same class is preserved after unbinding extension provider"() {
        setup:
        def extensionService = new DefaultExtensionService()
        def stringProvider = new StringMetaClassExtensionProvider()
        def anotherStringProvider = new AnotherStringMetaClassExtensionProvider()

        extensionService.bindMetaClassExtensionProvider(stringProvider)
        extensionService.bindMetaClassExtensionProvider(anotherStringProvider)

        when:
        extensionService.unbindMetaClassExtensionProvider(stringProvider)

        then:
        "HELLO".lower() == "hello"
    }
}
