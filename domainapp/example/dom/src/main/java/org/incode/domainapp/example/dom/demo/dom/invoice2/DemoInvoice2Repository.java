package org.incode.domainapp.example.dom.demo.dom.invoice2;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import com.google.common.collect.Lists;

import org.apache.isis.applib.annotation.DomainService;
import org.apache.isis.applib.annotation.NatureOfService;
import org.apache.isis.applib.annotation.Programmatic;
import org.apache.isis.applib.services.repository.RepositoryService;

import org.incode.domainapp.example.dom.demo.dom.demowithnotes.DemoObjectWithNotes;

@DomainService(nature = NatureOfService.DOMAIN )
public class DemoInvoice2Repository {


    @Programmatic
    public List<DemoInvoice2> listAll() {
        return repositoryService.allInstances(DemoInvoice2.class);
    }

    @Programmatic
    public List<DemoInvoice2> findByCustomer(final DemoObjectWithNotes demoCustomer) {
        return Lists.newArrayList(
                listAll().stream()
                        .filter(x -> Objects.equals(x.getCustomer(), demoCustomer))
                        .collect(Collectors.toList()));
    }

    @Programmatic
    public DemoInvoice2 create(
            final String num,
            final DemoObjectWithNotes customer) {
        final DemoInvoice2 obj = new DemoInvoice2(num, customer);
        repositoryService.persist(obj);
        return obj;
    }

    @javax.inject.Inject
    RepositoryService repositoryService;

}
