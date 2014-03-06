package com.citytechinc.aem.groovy.extension.metaclass

import com.citytechinc.aem.groovy.extension.GroovyExtensionSpec
import spock.lang.Unroll

class NodeMetaClassRegistrySpec extends GroovyExtensionSpec {

    def setup() {
        nodeBuilder.test {
            child1("nt:folder") {
                sub("nt:folder") {
                    subsub("nt:folder")
                }
            }
            child2("sling:Folder", ["singleValuedProperty": "1"])
            child3("sling:Folder", ["multiValuedProperty": ["1", "2"].toArray(new String[0])])
        }
    }

    def "iterator"() {
        setup:
        def node = getNode("/test")

        expect:
        node.iterator().size() == 3
        node*.name == ["child1", "child2", "child3"]
    }

    def "recurse"() {
        setup:
        def node = getNode("/test")
        def names = []

        node.recurse {
            names.add(it.name)
        }

        expect:
        names == ["test", "child1", "sub", "subsub", "child2", "child3"]
    }

    def "recurse with type"() {
        setup:
        def node = getNode("/test")
        def names = []

        node.recurse("nt:folder") {
            names.add(it.name)
        }

        expect:
        names == ["child1", "sub", "subsub"]
    }

    def "recurse with types"() {
        setup:
        def node = getNode("/test")
        def types = ["nt:folder", "sling:Folder"]
        def names = []

        node.recurse(types) {
            names.add(it.name)
        }

        expect:
        names == ["child1", "sub", "subsub", "child2", "child3"]
    }

    def "get"() {
        setup:
        def node = getNode("/test/child2")

        expect:
        node.get("singleValuedProperty") == "1"
    }

    def "get multiple"() {
        setup:
        def node = getNode("/test/child3")

        expect:
        node.get("multiValuedProperty") == ["1", "2"]
    }

    @Unroll
    def "set"() {
        setup:
        def node = getNode("/test/child3")

        when:
        node.set("testProperty", value)

        then:
        node.get("testProperty") == value

        where:
        value << [true, Calendar.instance, BigDecimal.ZERO, Double.valueOf(0.0), Long.valueOf(0), "foo"]
    }

    def "set null"() {
        setup:
        def node = getNode("/test/child3")

        when:
        node.set("testProperty", null)

        then:
        !node.hasProperty("testProperty")
    }

    def "set binary"() {
        setup:
        def binary

        this.class.getResourceAsStream("/file").withStream { stream ->
            binary = session.valueFactory.createBinary(stream)
        }

        def node = getNode("/test/child3")

        when:
        node.set("testProperty", binary)

        then:
        node.get("testProperty").stream.text == this.class.getResourceAsStream("/file").text
    }

    @Unroll
    def "set multiple"() {
        setup:
        def node = getNode("/test/child3")

        when:
        node.set("testProperty", value)

        then:
        node.get("testProperty") == value

        where:
        value << [["one", "two"], ["one", "two", "three"].toArray()]
    }

    @Unroll
    def "get or add node"() {
        setup:
        def node = getNode("/test")

        when:
        node.getOrAddNode(relativePath)

        then:
        assertNodeExists(absolutePath)

        where:
        relativePath | absolutePath
        "child1"     | "/test/child1"
        "child4"     | "/test/child4"
    }

    @Unroll
    def "get or add node with type"() {
        setup:
        def node = getNode("/test")

        when:
        node.getOrAddNode(relativePath, type)

        then:
        assertNodeExists(absolutePath, type)

        where:
        relativePath | type           | absolutePath
        "child1"     | "nt:folder"    | "/test/child1"
        "child4"     | "sling:Folder" | "/test/child4"
    }

    def "remove existing node"() {
        setup:
        def node = getNode("/test")

        expect:
        node.removeNode("child1")
        !session.nodeExists("/test/child1")
    }

    def "remove non-existent node"() {
        setup:
        def node = getNode("/test")

        expect:
        !node.removeNode("child4")
    }
}
