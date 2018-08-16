package org.incode.example.commchannel.demo.shared.dom;

import java.util.List;

import org.apache.isis.applib.annotation.DomainService;
import org.apache.isis.applib.annotation.NatureOfService;
import org.apache.isis.applib.query.QueryDefault;
import org.apache.isis.applib.services.registry.ServiceRegistry2;
import org.apache.isis.applib.services.repository.RepositoryService;

@DomainService(
        nature = NatureOfService.DOMAIN,
        repositoryFor = CommChannelCustomer.class
)
public class CommChannelCustomerRepository {

    public List<CommChannelCustomer> listAll() {
        return repositoryService.allInstances(CommChannelCustomer.class);
    }

    public List<CommChannelCustomer> findByName(final String name) {
        return repositoryService.allMatches(
                new QueryDefault<>(
                        CommChannelCustomer.class,
                        "findByName",
                        "name", name));
    }

    public CommChannelCustomer create(final String name) {
        final CommChannelCustomer object = new CommChannelCustomer(name);
        serviceRegistry.injectServicesInto(object);
        repositoryService.persist(object);
        return object;
    }

    @javax.inject.Inject
    RepositoryService repositoryService;
    @javax.inject.Inject
    ServiceRegistry2 serviceRegistry;
}
