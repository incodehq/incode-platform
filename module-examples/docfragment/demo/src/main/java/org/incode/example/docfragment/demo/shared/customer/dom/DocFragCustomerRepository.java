package org.incode.example.docfragment.demo.shared.customer.dom;

import java.util.List;

import org.apache.isis.applib.annotation.DomainService;
import org.apache.isis.applib.annotation.NatureOfService;
import org.apache.isis.applib.services.repository.RepositoryService;

@DomainService(nature = NatureOfService.DOMAIN )
public class DocFragCustomerRepository {

    public List<DocFragCustomer> listAll() {
        return repositoryService.allInstances(DocFragCustomer.class);
    }

    @javax.inject.Inject
    RepositoryService repositoryService;
}
