package org.incode.example.document.demo.usage.dom.spiimpl;

import org.apache.isis.applib.annotation.DomainService;
import org.apache.isis.applib.annotation.NatureOfService;

import org.incode.example.document.dom.spi.ApplicationTenancyService;

@DomainService(
    nature = NatureOfService.DOMAIN
)
public class ApplicationTenancyServiceForDemo implements ApplicationTenancyService {

    @Override
    public String atPathFor(final Object domainObject) {
        return "/";
    }
}
