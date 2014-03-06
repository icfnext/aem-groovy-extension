package com.citytechinc.aem.groovy.extension.metaclass

import com.citytechinc.aem.groovy.extension.GroovyExtensionSpec

import javax.jcr.Binary

class BinaryMetaClassRegistrySpec extends GroovyExtensionSpec {

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
