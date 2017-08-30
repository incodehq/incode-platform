package org.incode.domainapp.example.dom.dom.classification.dom.classification.otherwithatpath;

import org.apache.isis.applib.annotation.DomainService;
import org.apache.isis.applib.annotation.NatureOfService;

import org.incode.domainapp.example.dom.demo.dom.otherwithatpath.OtherObjectWithAtPath;
import org.incode.module.classification.dom.impl.classification.ClassificationRepository;

@DomainService(nature = NatureOfService.DOMAIN)
public class ClassificationForOtherObjectWithAtPathSubtypeProvider
        extends ClassificationRepository.SubtypeProviderAbstract {
    public ClassificationForOtherObjectWithAtPathSubtypeProvider() {
        super(OtherObjectWithAtPath.class, ClassificationForOtherObjectWithAtPath.class);
    }
}
