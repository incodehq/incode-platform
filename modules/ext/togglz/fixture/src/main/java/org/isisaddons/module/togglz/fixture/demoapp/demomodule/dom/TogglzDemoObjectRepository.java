package org.isisaddons.module.togglz.fixture.demoapp.demomodule.dom;

import java.util.List;

import org.apache.isis.applib.annotation.DomainService;
import org.apache.isis.applib.annotation.NatureOfService;
import org.apache.isis.applib.query.QueryDefault;
import org.apache.isis.applib.services.registry.ServiceRegistry2;
import org.apache.isis.applib.services.repository.RepositoryService;

@DomainService(
        nature = NatureOfService.DOMAIN,
        repositoryFor = TogglzDemoObject.class
)
public class TogglzDemoObjectRepository {

    public List<TogglzDemoObject> listAll() {
        return repositoryService.allInstances(TogglzDemoObject.class);
    }

    public List<TogglzDemoObject> findByName(final String name) {
        return repositoryService.allMatches(
                new QueryDefault<>(
                        TogglzDemoObject.class,
                        "findByName",
                        "name", name));
    }

    public TogglzDemoObject create(final String name) {
        final TogglzDemoObject object = new TogglzDemoObject(name);
        serviceRegistry.injectServicesInto(object);
        repositoryService.persist(object);
        return object;
    }

    @javax.inject.Inject
    RepositoryService repositoryService;
    @javax.inject.Inject
    ServiceRegistry2 serviceRegistry;
}
