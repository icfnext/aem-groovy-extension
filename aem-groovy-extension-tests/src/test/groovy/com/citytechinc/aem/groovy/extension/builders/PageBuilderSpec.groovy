package com.citytechinc.aem.groovy.extension.builders

import com.citytechinc.aem.groovy.extension.GroovyExtensionSpec

class PageBuilderSpec extends GroovyExtensionSpec {

    def cleanup() {
        removeAllNodes()
    }

    def "build page"() {
        setup:
        pageBuilder.foo()

        expect:
        assertNodeExists("/foo", "cq:Page")
        assertNodeExists("/foo/jcr:content", "cq:PageContent")
    }

    def "build page with properties"() {
        setup:
        def pageProperties = ["sling:resourceType": "foundation/components/page"]

        pageBuilder.content {
            citytechinc("CITYTECH, Inc.", pageProperties)
        }

        expect:
        assertPageExists("/content/citytechinc", pageProperties + ["jcr:title": "CITYTECH, Inc."])
    }

    def "build page with content"() {
        setup:
        def pageProperties = ["sling:resourceType": "foundation/components/page"]
        def parProperties = ["sling:resourceType": "foundation/components/parsys"]

        pageBuilder.content {
            citytechinc("CITYTECH, Inc.") {
                "jcr:content"(pageProperties) {
                    mainpar(parProperties)
                }
            }
        }

        expect:
        assertPageExists("/content/citytechinc", pageProperties + ["jcr:title": "CITYTECH, Inc."])
        assertNodeExists("/content/citytechinc/jcr:content/mainpar", parProperties)
    }

    def "build page with descendant node of given type"() {
        setup:
        pageBuilder.content {
            citytechinc("CITYTECH, Inc.") {
                "jcr:content" {
                    derp("sling:Folder")
                }
            }
        }

        expect:
        assertNodeExists("/content/citytechinc/jcr:content/derp", "sling:Folder")
    }

    def "build pages with content"() {
        setup:
        def page1Properties = ["sling:resourceType": "foundation/components/page"]
        def page2Properties = ["sling:resourceType": "foundation/components/page"]

        pageBuilder.content {
            citytechinc("CITYTECH, Inc.") {
                "jcr:content"(page1Properties)
            }
            ctmsp("CTMSP") {
                "jcr:content"(page2Properties)
            }
        }

        expect:
        assertPageExists("/content/citytechinc", page1Properties + ["jcr:title": "CITYTECH, Inc."])
        assertPageExists("/content/ctmsp", page2Properties + ["jcr:title": "CTMSP"])
    }

    def "build page with root page"() {
        setup:
        pageBuilder.foo()

        new PageBuilder(session, getPage("/foo")).bar()

        expect:
        assertPageExists("/foo/bar")
    }

    def "build page with root path"() {
        setup:
        pageBuilder.foo()

        new PageBuilder(session, "/foo").bar()

        expect:
        assertPageExists("/foo/bar")
    }
}
