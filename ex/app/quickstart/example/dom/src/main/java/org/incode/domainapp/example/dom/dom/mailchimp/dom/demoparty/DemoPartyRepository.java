package org.incode.domainapp.example.dom.dom.mailchimp.dom.demoparty;

import java.util.List;

import javax.inject.Inject;

import org.apache.isis.applib.annotation.DomainService;
import org.apache.isis.applib.annotation.NatureOfService;
import org.apache.isis.applib.annotation.Programmatic;
import org.apache.isis.applib.services.registry.ServiceRegistry2;
import org.apache.isis.applib.services.repository.RepositoryService;

@DomainService(
        nature = NatureOfService.DOMAIN,
        repositoryFor = DemoParty.class
)
public class DemoPartyRepository {

    @Programmatic
    public List<DemoParty> listAll() {
        return repositoryService.allInstances(DemoParty.class);
    }

    @Programmatic
    public DemoParty findByEmail(
            final String email
    ) {
        return repositoryService.uniqueMatch(
                new org.apache.isis.applib.query.QueryDefault<>(
                        DemoParty.class,
                        "findByEmail",
                        "email", email));
    }

    @Programmatic
    public DemoParty create(final String name, final String email, final boolean sendMail) {
        final DemoParty demoParty = new DemoParty(name, email, sendMail);
        serviceRegistry2.injectServicesInto(demoParty);
        repositoryService.persist(demoParty);
        return demoParty;
    }

    @Programmatic
    public DemoParty findOrCreate(
            final String name,
            final String email,
            final boolean sendMail
    ) {
        DemoParty demoParty = findByEmail(email);
        if (demoParty == null) {
            demoParty = create(name, email, sendMail);
        }
        return demoParty;
    }

    @Inject
    RepositoryService repositoryService;

    @Inject
    ServiceRegistry2 serviceRegistry2;
}
