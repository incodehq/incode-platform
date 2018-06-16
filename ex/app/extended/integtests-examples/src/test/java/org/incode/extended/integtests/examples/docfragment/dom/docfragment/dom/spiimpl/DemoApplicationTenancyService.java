package org.incode.extended.integtests.examples.docfragment.dom.docfragment.dom.spiimpl;

import org.apache.isis.applib.annotation.DomainService;
import org.apache.isis.applib.annotation.NatureOfService;

import org.incode.example.docfragment.dom.spi.ApplicationTenancyService;
import org.incode.extended.integtests.examples.docfragment.demo.dom.customer.DemoCustomer;
import org.incode.extended.integtests.examples.docfragment.demo.dom.invoicewithatpath.DemoInvoiceWithAtPath;

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