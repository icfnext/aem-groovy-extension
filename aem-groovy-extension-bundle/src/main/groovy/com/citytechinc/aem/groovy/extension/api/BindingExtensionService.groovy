package com.citytechinc.aem.groovy.extension.api

import org.apache.sling.api.SlingHttpServletRequest

interface BindingExtensionService {

	Map<String, ?> getBindings(SlingHttpServletRequest request)
}