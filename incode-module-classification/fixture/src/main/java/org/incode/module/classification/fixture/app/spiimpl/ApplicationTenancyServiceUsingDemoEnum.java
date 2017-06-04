package org.incode.module.classification.fixture.app.spiimpl;

import org.apache.isis.applib.annotation.DomainService;
import org.apache.isis.applib.annotation.NatureOfService;
import org.incode.module.classification.dom.spi.ApplicationTenancyService;
import org.incode.module.classification.fixture.dom.demo.first.DemoObject;
import org.incode.module.classification.fixture.dom.demo.other.OtherObject;

@DomainService(
    nature = NatureOfService.DOMAIN
)
public class ApplicationTenancyServiceUsingDemoEnum implements ApplicationTenancyService {

    @Override
    public String atPathFor(final Object domainObjectToClassify) {
        if(domainObjectToClassify instanceof DemoObject) {
            return ((DemoObject) domainObjectToClassify).getAtPath();
        }
        if(domainObjectToClassify instanceof OtherObject) {
            return ((OtherObject) domainObjectToClassify).getAtPath();
        }
        return null;
    }
}
