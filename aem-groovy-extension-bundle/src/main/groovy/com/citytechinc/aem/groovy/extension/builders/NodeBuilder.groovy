package com.citytechinc.aem.groovy.extension.builders

import javax.jcr.Session

class NodeBuilder extends AbstractContentBuilder {

    NodeBuilder(Session session) {
        super(session, session.rootNode)
    }

    NodeBuilder(Session session, Node rootNode) {
        super(session, rootNode)
    }

    NodeBuilder(Session session, String rootPath) {
        super(session, session.getNode(rootPath))
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
}