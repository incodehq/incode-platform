package org.incode.domainapp.extended.module.fixtures.shared.simple;

import javax.xml.bind.annotation.XmlRootElement;

import org.apache.isis.applib.ModuleAbstract;
import org.apache.isis.applib.fixturescripts.FixtureScript;

import org.incode.domainapp.extended.module.fixtures.shared.simple.fixture.SimpleObject_tearDown;

@XmlRootElement(name = "module")
public class FixturesModuleSharedSimpleSubmodule extends ModuleAbstract {

    public static class PropertyDomainEvent<S,T>
            extends org.apache.isis.applib.services.eventbus.PropertyDomainEvent<S,T> {}
    public static class CollectionDomainEvent<S,T>
            extends org.apache.isis.applib.services.eventbus.CollectionDomainEvent<S,T> {}
    public static class ActionDomainEvent<S> extends
            org.apache.isis.applib.services.eventbus.ActionDomainEvent<S> {}

    @Override public FixtureScript getTeardownFixture() {
        return new SimpleObject_tearDown();
    }
}
