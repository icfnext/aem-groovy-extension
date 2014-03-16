package com.citytechinc.aem.groovy.extension.builders

import com.citytechinc.aem.groovy.extension.GroovyExtensionSpec

class NodeBuilderSpec extends GroovyExtensionSpec {

    def cleanup() {
        removeAllNodes()
    }

    def "build unstructured node"() {
        setup:
        nodeBuilder.foo()

        expect:
        assertNodeExists("/foo", "nt:unstructured")
    }

    def "build node with type"() {
        setup:
        nodeBuilder.foo("sling:Folder")

        expect:
        assertNodeExists("/foo", "sling:Folder")
    }

    def "build node with properties"() {
        setup:
        def properties = ["jcr:title": "Foo", "sling:resourceType": "foo/bar"]

        nodeBuilder.foo(properties)

        expect:
        assertNodeExists("/foo", properties)
    }

    def "build node with non-string properties"() {
        setup:
        def properties = ["date": Calendar.instance, "number": 1L, "array": ["one", "two", "three"].toArray(new String[0])]

        nodeBuilder.foo(properties)

        expect:
        assertNodeExists("/foo", properties)
    }

    def "build node with type and properties"() {
        setup:
        def properties = ["jcr:title": "Foo"]

        nodeBuilder.foo("sling:Folder", properties)

        expect:
        assertNodeExists("/foo", "sling:Folder", properties)
    }

    def "build node hierarchy"() {
        setup:
        nodeBuilder.foo {
            bar()
        }

        expect:
        assertNodeExists("/foo/bar")
    }

    def "build node hierarchy with type"() {
        setup:
        nodeBuilder.foo("sling:Folder") {
            bar("sling:Folder")
        }

        expect:
        assertNodeExists("/foo", "sling:Folder")
        assertNodeExists("/foo/bar", "sling:Folder")
    }

    def "build node hierarchy with type and properties"() {
        setup:
        def fooProperties = ["jcr:title": "Foo"]
        def barProperties = ["jcr:title": "Bar"]

        nodeBuilder.foo("sling:Folder", fooProperties) {
            bar("sling:Folder", barProperties)
        }

        expect:
        assertNodeExists("/foo", "sling:Folder", fooProperties)
        assertNodeExists("/foo/bar", "sling:Folder", barProperties)
    }
}
