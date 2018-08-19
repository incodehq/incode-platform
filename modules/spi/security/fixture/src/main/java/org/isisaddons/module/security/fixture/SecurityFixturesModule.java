package org.isisaddons.module.security.fixture;

import java.util.Set;

import javax.xml.bind.annotation.XmlRootElement;

import com.google.common.collect.Sets;

import org.apache.isis.applib.Module;
import org.apache.isis.applib.ModuleAbstract;
import org.apache.isis.applib.fixturescripts.FixtureScript;
import org.apache.isis.applib.fixturescripts.teardown.TeardownFixtureAbstract2;

import org.isisaddons.module.security.SecurityModule;
import org.isisaddons.module.security.fixture.demoapp.demonontenantedmodule.dom.NonTenantedEntity;
import org.isisaddons.module.security.fixture.demoapp.demotenantedmodule.dom.TenantedEntity;

@XmlRootElement(name = "module")
public class SecurityFixturesModule extends ModuleAbstract {

    @Override public Set<Module> getDependencies() {
        return Sets.newHashSet(new SecurityModule());
    }

    @Override public FixtureScript getTeardownFixture() {
        return new TeardownFixtureAbstract2() {
            @Override protected void execute(final ExecutionContext executionContext) {
                deleteFrom(NonTenantedEntity.class);
                deleteFrom(TenantedEntity.class);
            }
        };
    }
}
