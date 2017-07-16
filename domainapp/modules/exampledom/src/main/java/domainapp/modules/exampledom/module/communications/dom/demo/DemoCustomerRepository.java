package domainapp.modules.exampledom.module.communications.dom.demo;

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

    public List<DemoCustomer> findByName(final String name) {
        return repositoryService.allMatches(
                new QueryDefault<DemoCustomer>(
                        DemoCustomer.class,
                        "findByName",
                        "name", name));
    }

    public DemoCustomer create(final String name) {
        final DemoCustomer object = new DemoCustomer(name);
        serviceRegistry.injectServicesInto(object);
        repositoryService.persist(object);
        return object;
    }

    @javax.inject.Inject
    RepositoryService repositoryService;
    @javax.inject.Inject
    ServiceRegistry2 serviceRegistry;
}
