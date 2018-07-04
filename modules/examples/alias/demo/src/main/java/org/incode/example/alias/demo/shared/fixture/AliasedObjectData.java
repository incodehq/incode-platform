package org.incode.example.alias.demo.shared.fixture;

import org.apache.isis.applib.annotation.Programmatic;
import org.apache.isis.applib.fixturescripts.PersonaWithBuilderScript;
import org.apache.isis.applib.services.registry.ServiceRegistry2;

import org.incode.example.alias.demo.shared.dom.AliasedObject;
import org.incode.module.fixturesupport.dom.data.DemoData;
import org.incode.module.fixturesupport.dom.data.DemoDataPersistAbstract;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.Accessors;

/**
 * TODO: rework to use {@link PersonaWithBuilderScript}.
 */
@AllArgsConstructor
@Getter
@Accessors(chain = true)
public enum AliasedObjectData implements DemoData<AliasedObjectData, AliasedObject> {

    Foo("Foo"),
    Bar("Bar"),
    Baz("Baz"),
    Foz("Foz"),
    ;

    private final String name;

    @Programmatic
    public AliasedObject asDomainObject() {
        return AliasedObject.builder()
                .name(name)
                .build();
    }

    @Programmatic
    public AliasedObject persistUsing(final ServiceRegistry2 serviceRegistry) {
        return Util.persist(this, serviceRegistry);
    }

    @Programmatic
    public AliasedObject findUsing(final ServiceRegistry2 serviceRegistry) {
        return Util.firstMatch(this, serviceRegistry);
    }

    public static class PersistScript extends DemoDataPersistAbstract<PersistScript, AliasedObjectData, AliasedObject> {
        public PersistScript() {
            super(AliasedObjectData.class);
        }
    }

}
