package domainapp.modules.exampledom.ext.flywaydb.dom;

import java.util.List;

import org.apache.isis.applib.annotation.DomainService;
import org.apache.isis.applib.annotation.NatureOfService;
import org.apache.isis.applib.query.QueryDefault;
import org.apache.isis.applib.services.registry.ServiceRegistry2;
import org.apache.isis.applib.services.repository.RepositoryService;

@DomainService(
        nature = NatureOfService.DOMAIN,
        repositoryFor = FlywayDemoObject.class
)
public class FlywayDemoObjectRepository {

    public List<FlywayDemoObject> listAll() {
        return repositoryService.allInstances(FlywayDemoObject.class);
    }

    public List<FlywayDemoObject> findByName(final String name) {
        return repositoryService.allMatches(
                new QueryDefault<>(
                        FlywayDemoObject.class,
                        "findByName",
                        "name", name));
    }

    public FlywayDemoObject create(final String name) {
        final FlywayDemoObject object = new FlywayDemoObject(name);
        serviceRegistry.injectServicesInto(object);
        repositoryService.persist(object);
        return object;
    }

    @javax.inject.Inject
    RepositoryService repositoryService;
    @javax.inject.Inject
    ServiceRegistry2 serviceRegistry;
}
