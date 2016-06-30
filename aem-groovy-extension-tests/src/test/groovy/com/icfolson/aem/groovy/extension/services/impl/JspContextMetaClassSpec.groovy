package com.icfolson.aem.groovy.extension.services.impl

import com.icfolson.aem.groovy.extension.GroovyExtensionSpec
import org.springframework.mock.web.MockPageContext

class JspContextMetaClassSpec extends GroovyExtensionSpec {

    def "get attribute"() {
        setup:
        def jspContext = new MockPageContext()

        def name = "name"
        def value = "value"

        when:
        jspContext.setAttribute(name, value)

        then:
        jspContext[name] == value
    }

    def "set attribute"() {
        setup:
        def jspContext = new MockPageContext()

        def name = "name"
        def value = "value"

        when:
        jspContext[name] = value

        then:
        jspContext[name] == value
    }
}
