package org.incode.example.classification.demo.usage.dom.spiimpl;

import org.apache.isis.applib.annotation.DomainService;
import org.apache.isis.applib.annotation.NatureOfService;

import org.incode.example.classification.dom.spi.ApplicationTenancyService;
import org.incode.example.classification.demo.shared.demowithatpath.dom.DemoObjectWithAtPath;
import org.incode.example.classification.demo.shared.otherwithatpath.dom.OtherObjectWithAtPath;

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
