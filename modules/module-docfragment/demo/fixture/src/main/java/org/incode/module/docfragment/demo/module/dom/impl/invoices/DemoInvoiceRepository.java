package org.incode.module.docfragment.demo.module.dom.impl.invoices;

import java.util.List;

import org.apache.isis.applib.annotation.DomainService;
import org.apache.isis.applib.annotation.NatureOfService;
import org.apache.isis.applib.services.repository.RepositoryService;

@DomainService(
        nature = NatureOfService.DOMAIN,
        repositoryFor = DemoInvoice.class
)
public class DemoInvoiceRepository {

    public List<DemoInvoice> listAll() {
        return repositoryService.allInstances(DemoInvoice.class);
    }

    @javax.inject.Inject
    RepositoryService repositoryService;
}
