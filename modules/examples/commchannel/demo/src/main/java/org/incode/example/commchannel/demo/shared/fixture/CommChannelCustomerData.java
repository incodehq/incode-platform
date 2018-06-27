package org.incode.example.commchannel.demo.shared.fixture;

import org.apache.isis.applib.annotation.Programmatic;
import org.apache.isis.applib.services.registry.ServiceRegistry2;

import org.incode.example.commchannel.demo.shared.dom.CommChannelCustomer;
import org.incode.module.fixturesupport.dom.data.DemoData;
import org.incode.module.fixturesupport.dom.data.DemoDataPersistAbstract;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.Accessors;

@AllArgsConstructor
@Getter
@Accessors(chain = true)
public enum CommChannelCustomerData implements DemoData<CommChannelCustomerData, CommChannelCustomer> {

    Foo("Foo"),
    Bar("Bar"),
    Baz("Baz"),
    Foz("Foz"),
    ;

    private final String name;

    @Programmatic
    public CommChannelCustomer asDomainObject() {
        return CommChannelCustomer.builder()
                .name(name)
                .build();
    }

    @Programmatic
    public CommChannelCustomer persistUsing(final ServiceRegistry2 serviceRegistry) {
        return Util.persist(this, serviceRegistry);
    }

    @Programmatic
    public CommChannelCustomer findUsing(final ServiceRegistry2 serviceRegistry) {
        return Util.firstMatch(this, serviceRegistry);
    }

    public static class PersistScript extends DemoDataPersistAbstract<PersistScript, CommChannelCustomerData, CommChannelCustomer> {
        public PersistScript() {
            super(CommChannelCustomerData.class);
        }
    }

}
