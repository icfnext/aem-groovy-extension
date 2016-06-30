package com.icfolson.aem.groovy.extension.services.impl

import com.icfolson.aem.groovy.extension.api.MetaClassExtensionProvider
import com.icfolson.aem.prosper.specs.ProsperSpec
import org.codehaus.groovy.runtime.InvokerHelper
import spock.lang.Unroll

@Unroll
class DefaultExtensionServiceSpec extends ProsperSpec {

    static final def DEFAULT_METACLASSES = new DefaultMetaClassExtensionProvider().metaClasses.keySet() as List

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
        [new DefaultMetaClassExtensionProvider()]                                         | DEFAULT_METACLASSES
        [new DefaultMetaClassExtensionProvider(), new StringMetaClassExtensionProvider()] | DEFAULT_METACLASSES + (String)
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
