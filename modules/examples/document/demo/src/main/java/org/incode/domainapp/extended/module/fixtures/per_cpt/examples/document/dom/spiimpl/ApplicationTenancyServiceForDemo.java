package org.incode.example.alias.demo.examples.document.dom.spiimpl;

import org.apache.isis.applib.annotation.DomainService;
import org.apache.isis.applib.annotation.NatureOfService;

import org.incode.example.classification.dom.spi.ApplicationTenancyService;

@DomainService(
    nature = NatureOfService.DOMAIN
)
public class ApplicationTenancyServiceForDemo implements ApplicationTenancyService {

    @Override
    public String atPathFor(final Object domainObject) {
        return "/";
    }
}
