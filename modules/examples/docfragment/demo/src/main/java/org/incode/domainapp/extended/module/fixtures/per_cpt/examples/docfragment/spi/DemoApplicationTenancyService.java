package org.incode.example.alias.demo.examples.docfragment.spi;

import org.apache.isis.applib.annotation.DomainService;
import org.apache.isis.applib.annotation.NatureOfService;

import org.incode.examples.commchannel.demo.shared.invoicewithatpath.dom.DemoInvoiceWithAtPath;
import org.incode.example.docfragment.dom.spi.ApplicationTenancyService;

import org.incode.examples.commchannel.demo.shared.customer.dom.DemoCustomer;

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