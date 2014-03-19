package com.citytechinc.aem.groovy.extension.builders

import com.day.cq.commons.jcr.JcrConstants
import com.day.cq.wcm.api.NameConstants
import com.day.cq.wcm.api.Page

import javax.jcr.Node
import javax.jcr.Session

class PageBuilder extends AbstractContentBuilder {

    PageBuilder(Session session) {
        super(session, session.rootNode)
    }

    PageBuilder(Session session, Page rootPage) {
        super(session, session.getNode(rootPage.path))
    }

    PageBuilder(Session session, String rootPath) {
        super(session, session.getNode(rootPath))
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
    def createNode(name, title) {
        if (isContentNode(name)) {
            currentNode = currentNode.getOrAddNode(name, title)
        } else {
            currentNode = getOrAddPage(name: name, title: title)
        }

        currentNode
    }

    @Override
    def createNode(name, Map properties) {
        if (isContentNode(name)) {
            currentNode = currentNode.getOrAddNode(name)

            setProperties(currentNode, properties)
        } else {
            currentNode = getOrAddPage(name: name, properties: properties)
        }

        currentNode
    }

    @Override
    def createNode(name, Map properties, value) {
        if (isContentNode(name)) {
            currentNode = currentNode.getOrAddNode(name, value)

            setProperties(currentNode, properties)
        } else {
            currentNode = getOrAddPage(name: name, title: value, properties: properties)
        }

        currentNode
    }

    private Node getOrAddPage(map) {
        def pageNode = currentNode.getOrAddNode(map.name, NameConstants.NT_PAGE)
        def contentNode = pageNode.getOrAddNode(JcrConstants.JCR_CONTENT)

        if (map.title) {
            contentNode.set(JcrConstants.JCR_TITLE, map.title)
        }

        if (map.properties) {
            setProperties(contentNode, map.properties)
        }

        pageNode
    }

    private boolean isContentNode(name) {
        name == JcrConstants.JCR_CONTENT || currentNode.path.contains(JcrConstants.JCR_CONTENT)
    }
}