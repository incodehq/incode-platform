package org.incode.examples.note.demo.shared.demo.dom;

import java.util.List;

import org.apache.isis.applib.annotation.DomainService;
import org.apache.isis.applib.annotation.NatureOfService;
import org.apache.isis.applib.query.QueryDefault;
import org.apache.isis.applib.services.registry.ServiceRegistry2;
import org.apache.isis.applib.services.repository.RepositoryService;

@DomainService(
        nature = NatureOfService.DOMAIN,
        repositoryFor = NotableObject.class
)
public class NotableObjectRepository {

    public List<NotableObject> listAll() {
        return repositoryService.allInstances(NotableObject.class);
    }

    public List<NotableObject> findByName(final String name) {
        return repositoryService.allMatches(
                new QueryDefault<>(
                        NotableObject.class,
                        "findByName",
                        "name", name));
    }

    public NotableObject create(final String name) {
        final NotableObject object = new NotableObject(name);
        serviceRegistry.injectServicesInto(object);
        repositoryService.persist(object);
        return object;
    }

    @javax.inject.Inject
    RepositoryService repositoryService;
    @javax.inject.Inject
    ServiceRegistry2 serviceRegistry;
}
