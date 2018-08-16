package org.incode.example.docfragment.demo.shared.customer.fixture;

import org.apache.isis.applib.annotation.Programmatic;
import org.apache.isis.applib.services.registry.ServiceRegistry2;

import org.incode.example.docfragment.demo.shared.customer.dom.DocFragCustomer;
import org.incode.module.fixturesupport.dom.data.DemoData;
import org.incode.module.fixturesupport.dom.data.DemoDataPersistAbstract;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.Accessors;

@AllArgsConstructor
@Getter
@Accessors(chain = true)
public enum DocFragCustomerData implements DemoData<DocFragCustomerData, DocFragCustomer> {

    Mr_Joe_Bloggs("Mr", "Joe", "Bloggs", "/"),
    Ms_Joanna_Smith("Ms", "Joanna", "Smith", "/ITA"),
    Mrs_Betty_Flintstone("Mrs", "Betty", "Flintstone", "/FRA"),
    ;

    private final String title;
    private final String firstName;
    private final String lastName;
    private final String atPath;

    @Programmatic
    public DocFragCustomer asDomainObject() {
        return DocFragCustomer.builder()
                .title(title)
                .firstName(firstName)
                .lastName(lastName)
                .atPath(atPath)
                .build();
    }

    @Programmatic
    public DocFragCustomer persistUsing(final ServiceRegistry2 serviceRegistry) {
        return Util.persist(this, serviceRegistry);
    }

    @Programmatic
    public DocFragCustomer findUsing(final ServiceRegistry2 serviceRegistry) {
        return Util.firstMatch(this, serviceRegistry);
    }

    public static class PersistScript extends DemoDataPersistAbstract<PersistScript, DocFragCustomerData, DocFragCustomer> {
        public PersistScript() {
            super(DocFragCustomerData.class);
        }
    }

}
