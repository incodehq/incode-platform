package org.incode.example.docfragment.demo.usage.spi;

import org.apache.isis.applib.annotation.DomainService;
import org.apache.isis.applib.annotation.NatureOfService;

import org.incode.example.docfragment.demo.shared.customer.dom.DocFragCustomer;
import org.incode.example.docfragment.demo.shared.invoicewithatpath.dom.DocFragInvoice;
import org.incode.example.docfragment.dom.spi.ApplicationTenancyService;

@DomainService(nature = NatureOfService.DOMAIN)
public class DemoApplicationTenancyService implements ApplicationTenancyService {

    @Override
    public String atPathFor(final Object domainObjectToRender) {
        if (domainObjectToRender instanceof DocFragInvoice) {
            final DocFragInvoice demoInvoice = (DocFragInvoice) domainObjectToRender;
            return demoInvoice.getAtPath();
        }
        if (domainObjectToRender instanceof DocFragCustomer) {
            final DocFragCustomer docFragCustomer = (DocFragCustomer) domainObjectToRender;
            return docFragCustomer.getAtPath();
        }
        return null;
    }

}