package org.incode.example.alias.demo.shared.dom;

import java.util.List;

import org.apache.isis.applib.annotation.DomainService;
import org.apache.isis.applib.annotation.NatureOfService;
import org.apache.isis.applib.query.QueryDefault;
import org.apache.isis.applib.services.registry.ServiceRegistry2;
import org.apache.isis.applib.services.repository.RepositoryService;

@DomainService(
        nature = NatureOfService.DOMAIN,
        repositoryFor = AliasedObject.class
)
public class AliasedObjectRepository {

    public List<AliasedObject> listAll() {
        return repositoryService.allInstances(AliasedObject.class);
    }

    public List<AliasedObject> findByName(final String name) {
        return repositoryService.allMatches(
                new QueryDefault<>(
                        AliasedObject.class,
                        "findByName",
                        "name", name));
    }

    public AliasedObject create(final String name) {
        final AliasedObject object = new AliasedObject(name);
        serviceRegistry.injectServicesInto(object);
        repositoryService.persist(object);
        return object;
    }

    @javax.inject.Inject
    RepositoryService repositoryService;
    @javax.inject.Inject
    ServiceRegistry2 serviceRegistry;
}
