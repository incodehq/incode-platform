package org.incode.module.docfragment.demo.module.dom.impl;

import java.util.List;

import com.google.common.collect.Lists;

import org.apache.isis.applib.annotation.DomainService;
import org.apache.isis.applib.annotation.NatureOfService;

import org.incode.module.docfragment.demo.module.dom.impl.customers.DemoCustomer;
import org.incode.module.docfragment.demo.module.dom.impl.invoices.DemoInvoice;
import org.incode.module.docfragment.dom.spi.ApplicationTenancyService;

@DomainService(nature = NatureOfService.DOMAIN)
public class DemoApplicationTenancyService implements ApplicationTenancyService {

    @Override
    public String atPathFor(final Object domainObjectToRender) {
        if (domainObjectToRender instanceof DemoInvoice) {
            final DemoInvoice demoInvoice = (DemoInvoice) domainObjectToRender;
            return demoInvoice.getAtPath();
        }
        if (domainObjectToRender instanceof DemoCustomer) {
            final DemoCustomer demoCustomer = (DemoCustomer) domainObjectToRender;
            return demoCustomer.getAtPath();
        }
        return null;
    }

}