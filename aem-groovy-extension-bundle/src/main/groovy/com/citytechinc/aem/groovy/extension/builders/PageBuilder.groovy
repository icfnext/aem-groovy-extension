package com.citytechinc.aem.groovy.extension.builders

import com.day.cq.commons.jcr.JcrConstants
import com.day.cq.wcm.api.NameConstants

import javax.jcr.Session

class PageBuilder extends BuilderSupport {

    def session

    def currentNode

    PageBuilder(Session session) {
        this.session = session

        currentNode = session.rootNode
    }

    @Override
    def createNode(name) {
        if (isContentNode(name)) {
            currentNode = currentNode.getOrAddNode(name)
        } else {
            currentNode = getOrAddPage(name: name)
        }

        currentNode
    }

    @Override
    def createNode(name, value) {
        if (isContentNode(name)) {
            currentNode = currentNode.getOrAddNode(name, value)
        } else {
            currentNode = getOrAddPage(name: name, title: value)
        }

        currentNode
    }

    @Override
    def createNode(name, Map attributes) {
        if (isContentNode(name)) {
            currentNode = currentNode.getOrAddNode(name)

            setAttributes(currentNode, attributes)
        } else {
            currentNode = getOrAddPage(name: name, attributes: attributes)
        }

        currentNode
    }

    @Override
    def createNode(name, Map attributes, value) {
        if (isContentNode(name)) {
            currentNode = currentNode.getOrAddNode(name, value)

            setAttributes(currentNode, attributes)
        } else {
            currentNode = getOrAddPage(name: name, title: value, attributes: attributes)
        }

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

    def getOrAddPage(map) {
        def pageNode = currentNode.getOrAddNode(map.name, NameConstants.NT_PAGE)
        def contentNode = pageNode.getOrAddNode(JcrConstants.JCR_CONTENT)

        if (map.title) {
            contentNode.set(JcrConstants.JCR_TITLE, map.title)
        }

        if (map.attributes) {
            setAttributes(contentNode, map.attributes)
        }

        pageNode
    }

    boolean isContentNode(name) {
        name == JcrConstants.JCR_CONTENT || currentNode.path.contains(JcrConstants.JCR_CONTENT)
    }

    void setAttributes(node, attributes) {
        attributes.each { k, v ->
            node.set(k, v)
        }
    }
}