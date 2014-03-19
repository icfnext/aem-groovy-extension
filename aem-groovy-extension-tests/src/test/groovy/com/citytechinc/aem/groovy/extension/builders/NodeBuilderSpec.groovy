package com.citytechinc.aem.groovy.extension.builders

import com.citytechinc.aem.groovy.extension.GroovyExtensionSpec
import spock.lang.Shared

class NodeBuilderSpec extends GroovyExtensionSpec {

    @Shared builder

    def setupSpec() {
        builder = new NodeBuilder(session)
    }

    def "build unstructured node"() {
        setup:
        builder.foo()

        expect:
        assertNodeExists("/foo", "nt:unstructured")
    }

    def "build node with type"() {
        setup:
        builder.foo("sling:Folder")

        expect:
        assertNodeExists("/foo", "sling:Folder")
    }

    def "build node with properties"() {
        setup:
        def properties = ["jcr:title": "Foo", "sling:resourceType": "foo/bar"]

        builder.foo(properties)

        expect:
        assertNodeExists("/foo", properties)
    }

    def "build node with non-string properties"() {
        setup:
        def properties = ["date": Calendar.instance, "number": 1L, "array": ["one", "two", "three"].toArray(new String[0])]

        builder.foo(properties)

        expect:
        assertNodeExists("/foo", properties)
    }

    def "build node with type and properties"() {
        setup:
        def properties = ["jcr:title": "Foo"]

        builder.foo("sling:Folder", properties)

        expect:
        assertNodeExists("/foo", "sling:Folder", properties)
    }

    def "build node hierarchy"() {
        setup:
        builder.foo {
            bar()
        }

        expect:
        assertNodeExists("/foo/bar")
    }

    def "build node hierarchy with type"() {
        setup:
        builder.foo("sling:Folder") {
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

        builder.foo("sling:Folder", fooProperties) {
            bar("sling:Folder", barProperties)
        }

        expect:
        assertNodeExists("/foo", "sling:Folder", fooProperties)
        assertNodeExists("/foo/bar", "sling:Folder", barProperties)
    }

    def "build node with root node"() {
        setup:
        session.rootNode.addNode("foo")
        session.save()

        new NodeBuilder(session, getNode("/foo")).bar()

        expect:
        assertNodeExists("/foo/bar")
    }

    def "build node with root path"() {
        setup:
        session.rootNode.addNode("foo")
        session.save()

        new NodeBuilder(session, "/foo").bar()

        expect:
        assertNodeExists("/foo/bar")
    }
}
