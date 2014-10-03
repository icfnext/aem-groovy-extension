package com.citytechinc.aem.groovy.extension.services.impl

import com.citytechinc.aem.groovy.extension.GroovyExtensionSpec
import com.day.cq.wcm.api.NameConstants
import spock.lang.Unroll

import javax.jcr.Binary

@Unroll
class DefaultMetaClassExtensionProviderSpec extends GroovyExtensionSpec {

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

        pageBuilder.content {
            citytechinc("CITYTECH, Inc.") {
                "jcr:content"("sling:resourceType": "foundation/components/page") {
                    mainpar("sling:resourceType": "foundation/components/parsys")
                }
                news()
                company {
                    people()
                    places()
                }
            }
        }

        nodeBuilder.content {
            empty(NameConstants.NT_PAGE)
        }
    }

    def "binary: execute closure and dispose binary"() {
        setup:
        def binary = Mock(Binary)

        when:
        binary.withBinary {

        }

        then:
        1 * binary.dispose()
    }

    def "node: iterator"() {
        setup:
        def node = getNode("/test")

        expect:
        node.iterator().size() == 3
        node*.name == ["child1", "child2", "child3"]
    }

    def "node: recurse"() {
        setup:
        def node = getNode("/test")
        def names = []

        node.recurse {
            names.add(it.name)
        }

        expect:
        names == ["test", "child1", "sub", "subsub", "child2", "child3"]
    }

    def "node: recurse with type"() {
        setup:
        def node = getNode("/test")
        def names = []

        node.recurse("nt:folder") {
            names.add(it.name)
        }

        expect:
        names == ["child1", "sub", "subsub"]
    }

    def "node: recurse with types"() {
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

    def "node: get"() {
        setup:
        def node = getNode("/test/child2")

        expect:
        node.get("singleValuedProperty") == "1"
    }

    def "node: get multiple"() {
        setup:
        def node = getNode("/test/child3")

        expect:
        node.get("multiValuedProperty") == ["1", "2"]
    }

    def "node: set"() {
        setup:
        def node = getNode("/test/child3")

        when:
        node.set("testProperty", value)

        then:
        node.get("testProperty") == value

        where:
        value << [true, Calendar.instance, BigDecimal.ZERO, Double.valueOf(0.0), Long.valueOf(0), "foo"]
    }

    def "node: set null"() {
        setup:
        def node = getNode("/test/child3")

        when:
        node.set("testProperty", null)

        then:
        !node.hasProperty("testProperty")
    }

    def "node: set binary"() {
        setup:
        def binary = null

        this.class.getResourceAsStream("/file").withStream { stream ->
            binary = session.valueFactory.createBinary(stream)
        }

        def node = getNode("/test/child3")

        when:
        node.set("testProperty", binary)

        then:
        node.get("testProperty").stream.text == this.class.getResourceAsStream("/file").text
    }

    def "node: set multiple"() {
        setup:
        def node = getNode("/test/child3")

        when:
        node.set("testProperty", value)

        then:
        node.get("testProperty") == value

        where:
        value << [["one", "two"], ["one", "two", "three"].toArray()]
    }

    def "node: set map"() {
        setup:
        def map = [one: "a", two: "b", three: 1, four: Calendar.instance]
        def node = getNode("/test")

        when:
        node.set(map)

        then:
        assertNodeExists("/test", map)
    }

    def "node: get or add node"() {
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

    def "node: get or add node with type"() {
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

    def "node: move existing node"() {
        setup:
        def node = getNode("/test")

        expect:
        node.removeNode("child1")
        !session.nodeExists("/test/child1")
    }

    def "node: remove non-existent node"() {
        setup:
        def node = getNode("/test")

        expect:
        !node.removeNode("child4")
    }

    def "node: get next sibling"() {
        setup:
        def child = getNode(childPath)

        when:
        def nextSibling = child.nextSibling

        then:
        nextSibling.path == nextSiblingPath

        where:
        childPath      | nextSiblingPath
        "/test/child1" | "/test/child2"
        "/test/child2" | "/test/child3"
    }

    def "node: get next sibling with last sibling"() {
        setup:
        def child = getNode("/test/child3")

        expect:
        !child.nextSibling
    }

    def "node: get prev sibling"() {
        setup:
        def child = getNode(childPath)

        when:
        def prevSibling = child.prevSibling

        then:
        prevSibling.path == prevSiblingPath

        where:
        childPath      | prevSiblingPath
        "/test/child2" | "/test/child1"
        "/test/child3" | "/test/child2"
    }

    def "node: get prev sibling with first sibling"() {
        setup:
        def child = getNode("/test/child1")

        expect:
        !child.prevSibling
    }

    def "page: iterator"() {
        setup:
        def page = getPage("/content/citytechinc")

        expect:
        page.iterator().size() == 2
        page*.name == ["news", "company"]
    }

    def "page: recurse"() {
        setup:
        def page = getPage("/content/citytechinc")
        def names = []

        page.recurse {
            names.add(it.name)
        }

        expect:
        names == ["citytechinc", "news", "company", "people", "places"]
    }

    def "page: get node"() {
        setup:
        def page = getPage("/content/citytechinc")

        expect:
        page.node.path == "/content/citytechinc/jcr:content"
    }

    def "page: get node for page with no content"() {
        setup:
        def page = getPage("/content/empty")

        expect:
        !page.node
    }

    def "page: get"() {
        setup:
        def page = getPage("/content/citytechinc")

        expect:
        page.get(propertyName) == propertyValue

        where:
        propertyName          | propertyValue
        "sling:resourceType"  | "foundation/components/page"
        "nonExistentProperty" | null
    }

    def "page: set"() {
        setup:
        def page = getPage("/content/citytechinc")

        when:
        page.set(propertyName, propertyValue)

        then:
        page.get(propertyName) == propertyValue

        where:
        propertyName              | propertyValue
        "sling:resourceSuperType" | "foundation/components/parbase"
        "nonExistentProperty"     | null
    }

    def "page: set for page with no content"() {
        setup:
        def page = getPage("/content/empty")

        when:
        page.set("foo", "bar")

        then:
        notThrown(NullPointerException)
    }
}
