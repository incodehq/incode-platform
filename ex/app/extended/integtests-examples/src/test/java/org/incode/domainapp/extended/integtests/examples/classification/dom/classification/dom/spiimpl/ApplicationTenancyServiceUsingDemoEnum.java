package org.incode.domainapp.extended.integtests.examples.classification.dom.classification.dom.spiimpl;

import org.apache.isis.applib.annotation.DomainService;
import org.apache.isis.applib.annotation.NatureOfService;

import org.incode.example.classification.dom.spi.ApplicationTenancyService;
import org.incode.domainapp.extended.integtests.examples.classification.demo.dom.demowithatpath.DemoObjectWithAtPath;
import org.incode.domainapp.extended.integtests.examples.classification.demo.dom.otherwithatpath.OtherObjectWithAtPath;

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