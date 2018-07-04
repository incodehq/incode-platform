package org.incode.example.communications.demo.shared.demowithnotes.dom;

import java.util.List;

import org.apache.isis.applib.annotation.DomainService;
import org.apache.isis.applib.annotation.NatureOfService;
import org.apache.isis.applib.query.QueryDefault;
import org.apache.isis.applib.services.registry.ServiceRegistry2;
import org.apache.isis.applib.services.repository.RepositoryService;

@DomainService(
        nature = NatureOfService.DOMAIN,
        repositoryFor = CommsCustomer.class
)
public class CommsCustomerRepository {

    public List<CommsCustomer> listAll() {
        return repositoryService.allInstances(CommsCustomer.class);
    }

    public List<CommsCustomer> findByName(final String name) {
        return repositoryService.allMatches(
                new QueryDefault<CommsCustomer>(
                        CommsCustomer.class,
                        "findByName",
                        "name", name));
    }

    public CommsCustomer create(final String name) {
        final CommsCustomer object = new CommsCustomer(name, null);
        serviceRegistry.injectServicesInto(object);
        repositoryService.persist(object);
        return object;
    }

    @javax.inject.Inject
    RepositoryService repositoryService;
    @javax.inject.Inject
    ServiceRegistry2 serviceRegistry;
}
