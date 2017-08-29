package org.incode.domainapp.example.dom.dom.classification.dom.classification.demowithatpath;

import org.apache.isis.applib.annotation.DomainService;
import org.apache.isis.applib.annotation.NatureOfService;

import org.incode.domainapp.example.dom.demo.dom.demowithatpath.DemoObjectWithAtPath;
import org.incode.module.classification.dom.impl.classification.ClassificationRepository;

@DomainService(nature = NatureOfService.DOMAIN)
public class ClassificationForDemoObjectWithAtPathSubtypeProvider
        extends ClassificationRepository.SubtypeProviderAbstract {
    public ClassificationForDemoObjectWithAtPathSubtypeProvider() {
        super(DemoObjectWithAtPath.class, ClassificationForDemoObjectWithAtPath.class);
    }
}
