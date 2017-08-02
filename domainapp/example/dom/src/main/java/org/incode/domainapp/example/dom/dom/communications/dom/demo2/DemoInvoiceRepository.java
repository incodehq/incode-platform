package org.incode.domainapp.example.dom.dom.communications.dom.demo2;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import com.google.common.collect.Lists;

import org.apache.isis.applib.annotation.DomainService;
import org.apache.isis.applib.annotation.NatureOfService;
import org.apache.isis.applib.annotation.Programmatic;
import org.apache.isis.applib.services.repository.RepositoryService;

import org.incode.domainapp.example.dom.dom.communications.dom.demo.DemoCustomer;

@DomainService(
        nature = NatureOfService.DOMAIN,
        repositoryFor = DemoInvoice.class
)
public class DemoInvoiceRepository {

    @Programmatic
    public List<DemoInvoice> listAll() {
        return repositoryService.allInstances(DemoInvoice.class);
    }

    @Programmatic
    public List<DemoInvoice> findByCustomer(final DemoCustomer demoCustomer) {
        return Lists.newArrayList(
                listAll().stream()
                        .filter(x -> Objects.equals(x.getCustomer(), demoCustomer))
                        .collect(Collectors.toList()));
    }

    @Programmatic
    public DemoInvoice create(
            final String num,
            final DemoCustomer customer) {
        final DemoInvoice obj = repositoryService.instantiate(DemoInvoice.class);
        obj.setNum(num);
        obj.setCustomer(customer);
        repositoryService.persist(obj);
        return obj;
    }

    @javax.inject.Inject
    RepositoryService repositoryService;

}
