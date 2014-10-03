package com.citytechinc.aem.groovy.extension.services.impl

import com.citytechinc.aem.groovy.extension.GroovyExtensionSpec

import javax.jcr.Binary

class BinaryMetaClassSpec extends GroovyExtensionSpec {

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
