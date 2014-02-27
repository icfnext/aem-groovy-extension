package com.citytechinc.aem.groovy.extension.metaclass

import com.day.cq.wcm.api.Page
import org.codehaus.groovy.runtime.InvokerHelper

import javax.jcr.Binary
import javax.jcr.Node
import javax.jcr.PropertyType
import javax.jcr.Value

class GroovyExtensionMetaClassRegistry {

    static void registerMetaClasses() {
        registerBinaryMetaClass()
        registerNodeMetaClass()
        registerPageMetaClass()
    }

    static void removeMetaClasses() {
        def metaRegistry = InvokerHelper.metaRegistry

        metaRegistry.removeMetaClass(Binary)
        metaRegistry.removeMetaClass(Node)
        metaRegistry.removeMetaClass(Page)
    }

    private static void registerBinaryMetaClass() {
        Binary.metaClass {
            withBinary { Closure c ->
                def result

                try {
                    result = c(delegate)
                } finally {
                    delegate.dispose()
                }

                result
            }
        }
    }

    private static void registerNodeMetaClass() {
        Node.metaClass {
            iterator {
                delegate.nodes
            }

            recurse { Closure c ->
                c(delegate)

                delegate.nodes.each { node ->
                    node.recurse(c)
                }
            }

            recurse { String primaryNodeTypeName, c ->
                if (delegate.primaryNodeType.name == primaryNodeTypeName) {
                    c(delegate)
                }

                delegate.nodes.findAll { it.primaryNodeType.name == primaryNodeTypeName }.each { node ->
                    node.recurse(primaryNodeTypeName, c)
                }
            }

            recurse { Collection<String> primaryNodeTypeNames, c ->
                if (primaryNodeTypeNames.contains(delegate.primaryNodeType.name)) {
                    c(delegate)
                }

                delegate.nodes.findAll { primaryNodeTypeNames.contains(it.primaryNodeType.name) }.each { node ->
                    node.recurse(primaryNodeTypeNames, c)
                }
            }

            get { String propertyName ->
                def result = null

                if (delegate.hasProperty(propertyName)) {
                    def property = delegate.getProperty(propertyName)

                    if (property.multiple) {
                        result = property.values.collect { getResult(it) }
                    } else {
                        result = getResult(property.value)
                    }
                }

                result
            }

            set { String propertyName, value ->
                if (value == null) {
                    if (delegate.hasProperty(propertyName)) {
                        delegate.getProperty(propertyName).remove()
                    }
                } else {
                    def valueFactory = delegate.session.valueFactory

                    if (value instanceof Object[] || value instanceof Collection) {
                        def values = value.collect { valueFactory.createValue(it) }.toArray(new Value[0])

                        delegate.setProperty(propertyName, values)
                    } else {
                        def jcrValue = valueFactory.createValue(value)

                        delegate.setProperty(propertyName, jcrValue)
                    }
                }
            }

            getOrAddNode { String name ->
                delegate.hasNode(name) ? delegate.getNode(name) : delegate.addNode(name)
            }

            getOrAddNode { String name, String primaryNodeTypeName ->
                delegate.hasNode(name) ? delegate.getNode(name) : delegate.addNode(name, primaryNodeTypeName)
            }

            removeNode { String name ->
                boolean removed = false

                if (delegate.hasNode(name)) {
                    delegate.getNode(name).remove()
                    removed = true
                }

                removed
            }
        }
    }

    private static def getResult(value) {
        def result = null

        switch (value.type) {
            case PropertyType.BINARY:
                result = value.binary
                break
            case PropertyType.BOOLEAN:
                result = value.boolean
                break
            case PropertyType.DATE:
                result = value.date
                break
            case PropertyType.DECIMAL:
                result = value.decimal
                break
            case PropertyType.DOUBLE:
                result = value.double
                break
            case PropertyType.LONG:
                result = value.long
                break
            case PropertyType.STRING:
                result = value.string
        }

        result
    }

    private static void registerPageMetaClass() {
        Page.metaClass {
            iterator {
                delegate.listChildren()
            }

            recurse { Closure c ->
                c(delegate)

                delegate.listChildren().each { child ->
                    child.recurse(c)
                }
            }

            getNode {
                delegate.contentResource?.adaptTo(Node)
            }

            get { String name ->
                def node = delegate.contentResource?.adaptTo(Node)

                node?.get(name)
            }

            set { String name, value ->
                def node = delegate.contentResource?.adaptTo(Node)

                if (node) {
                    node.set(name, value)
                }
            }
        }
    }
}
