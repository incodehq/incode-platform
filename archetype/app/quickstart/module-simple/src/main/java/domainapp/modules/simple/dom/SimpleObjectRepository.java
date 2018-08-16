package domainapp.modules.simple.dom;

import java.util.List;

import org.apache.isis.applib.annotation.DomainService;
import org.apache.isis.applib.annotation.NatureOfService;
import org.apache.isis.applib.query.QueryDefault;
import org.apache.isis.applib.services.registry.ServiceRegistry2;
import org.apache.isis.applib.services.repository.RepositoryService;

@DomainService(
        nature = NatureOfService.DOMAIN,
        repositoryFor = SimpleObject.class
)
public class SimpleObjectRepository {

    public List<SimpleObject> listAll() {
        return repositoryService.allInstances(SimpleObject.class);
    }

    public List<SimpleObject> findByName(final String name) {
        return repositoryService.allMatches(
                new QueryDefault<>(
                        SimpleObject.class,
                        "findByName",
                        "name", name));
    }

    public SimpleObject create(final String name) {
        final SimpleObject object = new SimpleObject(name);
        serviceRegistry.injectServicesInto(object);
        repositoryService.persist(object);
        return object;
    }

    @javax.inject.Inject
    RepositoryService repositoryService;
    @javax.inject.Inject
    ServiceRegistry2 serviceRegistry;
}
