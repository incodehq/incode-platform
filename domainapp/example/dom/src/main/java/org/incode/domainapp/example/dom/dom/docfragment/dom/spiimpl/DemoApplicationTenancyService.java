package org.incode.domainapp.example.dom.dom.docfragment.dom.spiimpl;

import org.apache.isis.applib.annotation.DomainService;
import org.apache.isis.applib.annotation.NatureOfService;

import org.incode.domainapp.example.dom.demo.dom.invoicewithatpath.DemoInvoiceWithAtPath;
import org.incode.module.docfragment.dom.spi.ApplicationTenancyService;

import org.incode.domainapp.example.dom.demo.dom.customer.DemoCustomer;

@DomainService(nature = NatureOfService.DOMAIN)
public class DemoApplicationTenancyService implements ApplicationTenancyService {

    @Override
    public String atPathFor(final Object domainObjectToRender) {
        if (domainObjectToRender instanceof DemoInvoiceWithAtPath) {
            final DemoInvoiceWithAtPath demoInvoice = (DemoInvoiceWithAtPath) domainObjectToRender;
            return demoInvoice.getAtPath();
        }
        if (domainObjectToRender instanceof DemoCustomer) {
            final DemoCustomer demoCustomer = (DemoCustomer) domainObjectToRender;
            return demoCustomer.getAtPath();
        }
        return null;
    }

}