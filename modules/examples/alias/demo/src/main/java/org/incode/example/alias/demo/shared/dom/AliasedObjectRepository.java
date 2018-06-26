package org.incode.example.alias.demo.shared.dom;

import java.util.List;

import org.apache.isis.applib.annotation.DomainService;
import org.apache.isis.applib.annotation.NatureOfService;
import org.apache.isis.applib.query.QueryDefault;
import org.apache.isis.applib.services.registry.ServiceRegistry2;
import org.apache.isis.applib.services.repository.RepositoryService;

@DomainService(
        nature = NatureOfService.DOMAIN,
        repositoryFor = AliasDemoObject.class
)
public class AliasDemoObjectRepository {

    public List<AliasDemoObject> listAll() {
        return repositoryService.allInstances(AliasDemoObject.class);
    }

    public List<AliasDemoObject> findByName(final String name) {
        return repositoryService.allMatches(
                new QueryDefault<>(
                        AliasDemoObject.class,
                        "findByName",
                        "name", name));
    }

    public AliasDemoObject create(final String name) {
        final AliasDemoObject object = new AliasDemoObject(name);
        serviceRegistry.injectServicesInto(object);
        repositoryService.persist(object);
        return object;
    }

    @javax.inject.Inject
    RepositoryService repositoryService;
    @javax.inject.Inject
    ServiceRegistry2 serviceRegistry;
}
