package domainapp.modules.simple.fixture.scenario.data;

import org.apache.isis.applib.services.registry.ServiceRegistry2;

import org.incode.module.fixturesupport.dom.data.DemoData;
import org.incode.module.fixturesupport.dom.data.DemoDataPersistAbstract;

import domainapp.modules.simple.dom.SimpleObject;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum SimpleObject_data implements DemoData<SimpleObject_data, SimpleObject> {

    FOO("Foo"),
    BAR("Bar"),
    BAZ("Baz"),
    FRODO("Frodo"),
    FROYO("Froyo"),
    FIZZ("Fizz"),
    BIP("Bip"),
    BOP("Bop"),
    BANG("Bang"),
    BOO("Boo");

    private final String name;

    @Override
    public SimpleObject asDomainObject() {
        return SimpleObject.builder().name(name).build();
    }

    @Override
    public SimpleObject persistUsing(final ServiceRegistry2 serviceRegistry2) {
        return Util.persist(this, serviceRegistry2);
    }

    @Override
    public SimpleObject findUsing(final ServiceRegistry2 serviceRegistry) {
        return Util.findMatch(this, serviceRegistry);
    }

    public static class PersistScript
            extends DemoDataPersistAbstract<PersistScript, SimpleObject_data, SimpleObject> {
        public PersistScript() {
            super(SimpleObject_data.class);
        }
    }

}
