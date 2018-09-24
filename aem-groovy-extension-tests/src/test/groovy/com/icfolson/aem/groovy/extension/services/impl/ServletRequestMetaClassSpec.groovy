package com.icfolson.aem.groovy.extension.services.impl

import com.icfolson.aem.groovy.extension.GroovyExtensionSpec

class ServletRequestMetaClassSpec extends GroovyExtensionSpec {

    def "get parameter"() {
        setup:
        def request = requestBuilder.build {
            setParameterMap(["firstName": "Mark"])
        }

        expect:
        request["firstName"] == "Mark"
    }

    def "get parameter array"() {
        setup:
        def request = requestBuilder.build {
            setParameterMap(["languages": ["Java", "Groovy"]])
        }

        expect:
        request["languages"] == ["Java", "Groovy"]
    }

    def "non-existent parameter returns null"() {
        setup:
        def request = requestBuilder.build()

        expect:
        !request["nonExistent"]
    }
}
