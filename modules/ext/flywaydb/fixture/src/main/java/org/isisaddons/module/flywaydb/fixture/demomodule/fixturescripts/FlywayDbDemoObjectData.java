package org.isisaddons.module.flywaydb.fixture.demomodule.fixturescripts;

import org.apache.isis.applib.annotation.Programmatic;
import org.apache.isis.applib.services.registry.ServiceRegistry2;

import org.isisaddons.module.flywaydb.fixture.demomodule.dom.FlywayDbDemoObject;

import org.incode.module.fixturesupport.dom.data.DemoData;
import org.incode.module.fixturesupport.dom.data.DemoDataPersistAbstract;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.Accessors;

@AllArgsConstructor
@Getter
@Accessors(chain = true)
public enum FlywayDbDemoObjectData implements DemoData<FlywayDbDemoObjectData, FlywayDbDemoObject> {

    Foo("Foo"),
    Bar("Bar"),
    Baz("Baz"),
    Foz("Foz"),
    ;

    private final String name;

    @Programmatic
    public FlywayDbDemoObject asDomainObject() {
        return FlywayDbDemoObject.builder()
                .name(name)
                .build();
    }

    @Programmatic
    public FlywayDbDemoObject persistUsing(final ServiceRegistry2 serviceRegistry) {
        return Util.persist(this, serviceRegistry);
    }

    @Programmatic
    public FlywayDbDemoObject findUsing(final ServiceRegistry2 serviceRegistry) {
        return Util.firstMatch(this, serviceRegistry);
    }

    public static class PersistScript extends DemoDataPersistAbstract<PersistScript, FlywayDbDemoObjectData, FlywayDbDemoObject> {
        public PersistScript() {
            super(FlywayDbDemoObjectData.class);
        }
    }

}
