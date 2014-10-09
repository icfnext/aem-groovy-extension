package com.citytechinc.aem.groovy.extension.services.impl

import com.citytechinc.aem.groovy.extension.GroovyExtensionSpec
import org.springframework.mock.web.MockHttpServletRequest

class ServletRequestMetaClassSpec extends GroovyExtensionSpec {

    def "get parameter"() {
        setup:
        def request = new MockHttpServletRequest()

        request.setParameter("firstName", "Mark")

        expect:
        request["firstName"] == "Mark"
    }

    def "get parameter array"() {
        setup:
        def request = new MockHttpServletRequest()

        request.setParameter("languages", "Java", "Groovy")

        expect:
        request["languages"] == ["Java", "Groovy"]
    }

    def "non-existent parameter returns null"() {
        setup:
        def request = new MockHttpServletRequest()

        expect:
        request["nonExistent"] == null
    }
}
