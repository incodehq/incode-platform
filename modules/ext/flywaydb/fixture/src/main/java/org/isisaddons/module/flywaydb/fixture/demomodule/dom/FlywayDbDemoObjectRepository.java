package org.isisaddons.module.flywaydb.fixture.demomodule.dom;

import java.util.List;

import org.apache.isis.applib.annotation.DomainService;
import org.apache.isis.applib.annotation.NatureOfService;
import org.apache.isis.applib.query.QueryDefault;
import org.apache.isis.applib.services.registry.ServiceRegistry2;
import org.apache.isis.applib.services.repository.RepositoryService;

@DomainService(
        nature = NatureOfService.DOMAIN,
        repositoryFor = FlywayDbDemoObject.class
)
public class FlywayDbDemoObjectRepository {

    public List<FlywayDbDemoObject> listAll() {
        return repositoryService.allInstances(FlywayDbDemoObject.class);
    }

    public List<FlywayDbDemoObject> findByName(final String name) {
        return repositoryService.allMatches(
                new QueryDefault<>(
                        FlywayDbDemoObject.class,
                        "findByName",
                        "name", name));
    }

    public FlywayDbDemoObject create(final String name) {
        final FlywayDbDemoObject object = new FlywayDbDemoObject(name);
        serviceRegistry.injectServicesInto(object);
        repositoryService.persist(object);
        return object;
    }

    @javax.inject.Inject
    RepositoryService repositoryService;
    @javax.inject.Inject
    ServiceRegistry2 serviceRegistry;
}
