package org.incode.domainapp.module.fixtures.per_cpt.examples.classification.dom.spiimpl;

import org.apache.isis.applib.annotation.DomainService;
import org.apache.isis.applib.annotation.NatureOfService;

import org.incode.domainapp.module.fixtures.shared.otherwithatpath.dom.OtherObjectWithAtPath;
import org.incode.example.classification.dom.spi.ApplicationTenancyService;
import org.incode.domainapp.module.fixtures.shared.demowithatpath.dom.DemoObjectWithAtPath;

@DomainService(
    nature = NatureOfService.DOMAIN
)
public class ApplicationTenancyServiceUsingDemoEnum implements ApplicationTenancyService {

    @Override
    public String atPathFor(final Object domainObjectToClassify) {
        if(domainObjectToClassify instanceof DemoObjectWithAtPath) {
            return ((DemoObjectWithAtPath) domainObjectToClassify).getAtPath();
        }
        if(domainObjectToClassify instanceof OtherObjectWithAtPath) {
            return ((OtherObjectWithAtPath) domainObjectToClassify).getAtPath();
        }
        return null;
    }
}
