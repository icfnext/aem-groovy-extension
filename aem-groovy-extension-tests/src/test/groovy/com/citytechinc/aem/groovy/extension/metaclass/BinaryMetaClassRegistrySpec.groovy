package com.citytechinc.aem.groovy.extension.metaclass

import com.citytechinc.aem.groovy.extension.AbstractGroovyExtensionSpec

import javax.jcr.Binary

class BinaryMetaClassRegistrySpec extends AbstractGroovyExtensionSpec {

    def "execute closure and dispose binary"() {
        setup:
        def binary = Mock(Binary)

        when:
        binary.withBinary {

        }

        then:
        1 * binary.dispose()
    }
}
