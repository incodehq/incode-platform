package org.incode.example.settings;

import javax.xml.bind.annotation.XmlRootElement;

import org.apache.isis.applib.ModuleAbstract;
import org.apache.isis.applib.fixturescripts.FixtureScript;
import org.apache.isis.applib.fixturescripts.teardown.TeardownFixtureAbstract2;

import org.incode.example.settings.dom.ApplicationSetting;
import org.incode.example.settings.dom.UserSetting;

@XmlRootElement(name = "module")
public class SettingsModule extends ModuleAbstract {

    @Override public FixtureScript getTeardownFixture() {
        return new TeardownFixtureAbstract2() {
            @Override protected void execute(final ExecutionContext executionContext) {
                deleteFrom(UserSetting.class);
                deleteFrom(ApplicationSetting.class);
            }
        };
    }

    public abstract static class ActionDomainEvent<S>
            extends org.apache.isis.applib.services.eventbus.ActionDomainEvent<S> {
    }

    public abstract static class CollectionDomainEvent<S,T>
            extends org.apache.isis.applib.services.eventbus.CollectionDomainEvent<S,T> {
    }

    public abstract static class PropertyDomainEvent<S,T>
            extends org.apache.isis.applib.services.eventbus.PropertyDomainEvent<S,T> {
    }
}
