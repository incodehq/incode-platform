package org.incode.module.docfragment.demo.module.dom.impl.customers;

import java.util.List;

import org.apache.isis.applib.annotation.DomainService;
import org.apache.isis.applib.annotation.NatureOfService;
import org.apache.isis.applib.query.QueryDefault;
import org.apache.isis.applib.services.registry.ServiceRegistry2;
import org.apache.isis.applib.services.repository.RepositoryService;

@DomainService(
        nature = NatureOfService.DOMAIN,
        repositoryFor = DemoCustomer.class
)
public class DemoCustomerRepository {

    public List<DemoCustomer> listAll() {
        return repositoryService.allInstances(DemoCustomer.class);
    }

    @javax.inject.Inject
    RepositoryService repositoryService;
}
