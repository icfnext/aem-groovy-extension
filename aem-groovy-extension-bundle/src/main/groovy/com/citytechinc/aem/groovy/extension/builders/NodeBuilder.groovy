package com.citytechinc.aem.groovy.extension.builders

import javax.jcr.Session

class NodeBuilder extends BuilderSupport {

    def session

    def currentNode

    NodeBuilder(Session session) {
        this.session = session

        currentNode = session.rootNode
    }

    NodeBuilder(Session session, Node rootNode) {
        this.session = session

        currentNode = rootNode
    }

    NodeBuilder(Session session, String rootPath) {
        this.session = session

        currentNode = session.getNode(rootPath)
    }

    @Override
    def createNode(name) {
        currentNode = currentNode.getOrAddNode(name)

        currentNode
    }

    @Override
    def createNode(name, primaryNodeTypeName) {
        currentNode = currentNode.getOrAddNode(name, primaryNodeTypeName)

        currentNode
    }

    @Override
    def createNode(name, Map properties) {
        currentNode = currentNode.getOrAddNode(name)

        setProperties(currentNode, properties)

        currentNode
    }

    @Override
    def createNode(name, Map properties, primaryNodeTypeName) {
        currentNode = createNode(name, primaryNodeTypeName)

        setProperties(currentNode, properties)

        currentNode
    }

    @Override
    void setParent(parent, child) {

    }

    @Override
    void nodeCompleted(parent, node) {
        session.save()

        currentNode = currentNode.parent
    }

    void setProperties(node, properties) {
        properties.each { k, v ->
            node.set(k, v)
        }
    }
}