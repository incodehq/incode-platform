package org.incode.example.communications.demo.shared.demowithnotes.dom;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import com.google.common.collect.Lists;

import org.apache.isis.applib.annotation.DomainService;
import org.apache.isis.applib.annotation.NatureOfService;
import org.apache.isis.applib.annotation.Programmatic;
import org.apache.isis.applib.services.repository.RepositoryService;

@DomainService(nature = NatureOfService.DOMAIN )
public class CommsInvoiceRepository {


    @Programmatic
    public List<CommsInvoice> listAll() {
        return repositoryService.allInstances(CommsInvoice.class);
    }

    @Programmatic
    public List<CommsInvoice> findByCustomer(final CommsCustomer demoCustomer) {
        return Lists.newArrayList(
                listAll().stream()
                        .filter(x -> Objects.equals(x.getCustomer(), demoCustomer))
                        .collect(Collectors.toList()));
    }

    @Programmatic
    public CommsInvoice create(
            final String num,
            final CommsCustomer customer) {
        final CommsInvoice obj = new CommsInvoice(num, customer);
        repositoryService.persist(obj);
        return obj;
    }

    @javax.inject.Inject
    RepositoryService repositoryService;

}
