package com.citytechinc.aem.groovy.extension.services.impl

import com.citytechinc.aem.groovy.extension.api.ExtensionService
import com.citytechinc.aem.groovy.extension.api.MetaClassExtensionService
import groovy.util.logging.Slf4j
import org.apache.felix.scr.annotations.Component
import org.apache.felix.scr.annotations.Reference
import org.apache.felix.scr.annotations.ReferenceCardinality
import org.apache.felix.scr.annotations.ReferencePolicy
import org.apache.felix.scr.annotations.Service
import org.codehaus.groovy.runtime.InvokerHelper

@Service(ExtensionService)
@Component(immediate = true)
@Slf4j("LOG")
class DefaultExtensionService implements ExtensionService {

    @Reference(cardinality = ReferenceCardinality.OPTIONAL_MULTIPLE,
        referenceInterface = MetaClassExtensionService, policy = ReferencePolicy.DYNAMIC)
    List<MetaClassExtensionService> metaClassExtensions = []

    @Override
    Set<Class> getMetaClasses() {
        def metaClasses = [] as LinkedHashSet

        metaClassExtensions.each {
            metaClasses.addAll(it.metaClasses.keySet())
        }

        metaClasses
    }

    void bindMetaClassExtensionService(MetaClassExtensionService extension) {
        metaClassExtensions.add(extension)

        extension.metaClasses.each { clazz, metaClassClosure ->
            clazz.metaClass(metaClassClosure)
        }
    }

    void unbindMetaClassExtensionService(MetaClassExtensionService extension) {
        metaClassExtensions.remove(extension)

        // remove metaclass from registry for each mapped class
        extension.metaClasses.each { clazz, closure ->
            InvokerHelper.metaRegistry.removeMetaClass(clazz)

            LOG.info "removed metaclass for class = {}", clazz.name

            // ensure that valid metaclasses are still registered
            metaClassExtensions.each {
                def metaClassClosure = it.metaClasses[clazz]

                if (metaClassClosure) {
                    LOG.info "retaining metaclass for class = {} from service = {}", clazz.name, it.class.name

                    clazz.metaClass(metaClassClosure)
                }
            }
        }
    }
}
