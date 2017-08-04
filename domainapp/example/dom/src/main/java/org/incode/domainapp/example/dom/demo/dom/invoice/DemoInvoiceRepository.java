package org.incode.domainapp.example.dom.demo.dom.invoice;

import java.util.List;

import org.apache.isis.applib.annotation.DomainService;
import org.apache.isis.applib.annotation.NatureOfService;
import org.apache.isis.applib.services.repository.RepositoryService;

@DomainService(nature = NatureOfService.DOMAIN )
public class DemoInvoiceRepository {

    public List<DemoInvoice> listAll() {
        return repositoryService.allInstances(DemoInvoice.class);
    }

    @javax.inject.Inject
    RepositoryService repositoryService;
}
