package org.incode.domainapp.example.dom.dom.classification.dom.classification.demowithatpath;

import org.apache.isis.applib.annotation.DomainService;
import org.apache.isis.applib.annotation.NatureOfService;

import org.incode.domainapp.example.dom.demo.dom.demowithatpath.DemoObjectWithAtPath;
import org.incode.module.classification.dom.spi.ApplicationTenancyService;

@DomainService(nature = NatureOfService.DOMAIN)
public class AppTenancyServiceForDemoObjectWithAtPath implements ApplicationTenancyService {
    @Override
    public String atPathFor(final Object domainObjectToClassify) {
        if (domainObjectToClassify instanceof DemoObjectWithAtPath) {
            return ((DemoObjectWithAtPath) domainObjectToClassify).getAtPath();
        }
        return null;
    }
}
