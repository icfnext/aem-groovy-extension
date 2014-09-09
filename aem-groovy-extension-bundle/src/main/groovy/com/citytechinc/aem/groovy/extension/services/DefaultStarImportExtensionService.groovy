package com.citytechinc.aem.groovy.extension.services

import java.util.List;

import groovy.util.logging.Slf4j

import org.apache.felix.scr.annotations.Component
import org.apache.felix.scr.annotations.Service

import com.citytechinc.aem.groovy.extension.api.StarImportExtensionService;

@Service
@Component(immediate = true)
@Slf4j("LOG")
class DefaultStarImportExtensionService implements StarImportExtensionService {

	static final List<String> STAR_IMPORTS = ["javax.jcr", "org.apache.sling.api", "org.apache.sling.api.resource",
		"com.day.cq.search", "com.day.cq.tagging", "com.day.cq.wcm.api"]
	
	@Override
	String[] getStarImports() {
		STAR_IMPORTS
	}
	
}