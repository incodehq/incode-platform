package org.incode.example.alias.demo.shared.fixture;

import org.apache.isis.applib.annotation.Programmatic;
import org.apache.isis.applib.services.registry.ServiceRegistry2;

import org.incode.example.alias.demo.shared.dom.AliasDemoObject;
import org.incode.module.fixturesupport.dom.data.DemoData;
import org.incode.module.fixturesupport.dom.data.DemoDataPersistAbstract;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.Accessors;

@AllArgsConstructor
@Getter
@Accessors(chain = true)
public enum AliasDemoObjectData implements DemoData<AliasDemoObjectData, AliasDemoObject> {

    Foo("Foo"),
    Bar("Bar"),
    Baz("Baz"),
    Foz("Foz"),
    ;

    private final String name;

    @Programmatic
    public AliasDemoObject asDomainObject() {
        return AliasDemoObject.builder()
                .name(name)
                .build();
    }

    @Programmatic
    public AliasDemoObject persistUsing(final ServiceRegistry2 serviceRegistry) {
        return Util.persist(this, serviceRegistry);
    }

    @Programmatic
    public AliasDemoObject findUsing(final ServiceRegistry2 serviceRegistry) {
        return Util.firstMatch(this, serviceRegistry);
    }

    public static class PersistScript extends DemoDataPersistAbstract<PersistScript, AliasDemoObjectData, AliasDemoObject> {
        public PersistScript() {
            super(AliasDemoObjectData.class);
        }
    }

}
