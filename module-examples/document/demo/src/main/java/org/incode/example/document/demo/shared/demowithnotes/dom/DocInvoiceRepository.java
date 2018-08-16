package org.incode.example.document.demo.shared.demowithnotes.dom;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import com.google.common.collect.Lists;

import org.apache.isis.applib.annotation.DomainService;
import org.apache.isis.applib.annotation.NatureOfService;
import org.apache.isis.applib.annotation.Programmatic;
import org.apache.isis.applib.services.repository.RepositoryService;

@DomainService(nature = NatureOfService.DOMAIN )
public class DocInvoiceRepository {


    @Programmatic
    public List<DocInvoice> listAll() {
        return repositoryService.allInstances(DocInvoice.class);
    }

    @Programmatic
    public List<DocInvoice> findByCustomer(final DocDemoObjectWithNotes demoCustomer) {
        return Lists.newArrayList(
                listAll().stream()
                        .filter(x -> Objects.equals(x.getCustomer(), demoCustomer))
                        .collect(Collectors.toList()));
    }

    @Programmatic
    public DocInvoice create(
            final String num,
            final DocDemoObjectWithNotes customer) {
        final DocInvoice obj = new DocInvoice(num, customer);
        repositoryService.persist(obj);
        return obj;
    }

    @javax.inject.Inject
    RepositoryService repositoryService;

}
