package org.incode.example.classification.demo.usage.dom.classification.otherwithatpath;

import org.apache.isis.applib.annotation.DomainService;
import org.apache.isis.applib.annotation.NatureOfService;

import org.incode.example.classification.dom.spi.ApplicationTenancyService;
import org.incode.example.classification.demo.shared.otherwithatpath.dom.OtherObjectWithAtPath;

@DomainService(nature = NatureOfService.DOMAIN)
public class AppTenancyServiceForOtherObjectWithAtPath implements ApplicationTenancyService {

    @Override
    public String atPathFor(final Object domainObjectToClassify) {
        if (domainObjectToClassify instanceof OtherObjectWithAtPath) {
            return ((OtherObjectWithAtPath) domainObjectToClassify).getAtPath();
        }
        return null;
    }
}