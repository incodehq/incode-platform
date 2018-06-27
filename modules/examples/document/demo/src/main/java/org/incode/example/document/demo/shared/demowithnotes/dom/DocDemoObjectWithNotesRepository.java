package org.incode.example.document.demo.shared.demowithnotes.dom;

import java.util.List;

import org.apache.isis.applib.annotation.DomainService;
import org.apache.isis.applib.annotation.NatureOfService;
import org.apache.isis.applib.query.QueryDefault;
import org.apache.isis.applib.services.registry.ServiceRegistry2;
import org.apache.isis.applib.services.repository.RepositoryService;

@DomainService(
        nature = NatureOfService.DOMAIN,
        repositoryFor = DocDemoObjectWithNotes.class
)
public class DocDemoObjectWithNotesRepository {

    public List<DocDemoObjectWithNotes> listAll() {
        return repositoryService.allInstances(DocDemoObjectWithNotes.class);
    }

    public List<DocDemoObjectWithNotes> findByName(final String name) {
        return repositoryService.allMatches(
                new QueryDefault<DocDemoObjectWithNotes>(
                        DocDemoObjectWithNotes.class,
                        "findByName",
                        "name", name));
    }

    public DocDemoObjectWithNotes create(final String name) {
        final DocDemoObjectWithNotes object = new DocDemoObjectWithNotes(name, null);
        serviceRegistry.injectServicesInto(object);
        repositoryService.persist(object);
        return object;
    }

    @javax.inject.Inject
    RepositoryService repositoryService;
    @javax.inject.Inject
    ServiceRegistry2 serviceRegistry;
}
