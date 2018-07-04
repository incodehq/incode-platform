package org.incode.domainapp.extended.module.fixtures.per_cpt.spi.security;

import java.util.Set;

import javax.xml.bind.annotation.XmlRootElement;

import com.google.common.collect.Sets;

import org.apache.isis.applib.Module;
import org.apache.isis.applib.ModuleAbstract;
import org.apache.isis.applib.fixturescripts.FixtureScript;
import org.apache.isis.applib.fixturescripts.teardown.TeardownFixtureAbstract2;

import org.isisaddons.module.security.SecurityModule;

import org.incode.domainapp.extended.module.fixtures.per_cpt.spi.security.dom.demo.nontenanted.NonTenantedEntity;
import org.incode.domainapp.extended.module.fixtures.per_cpt.spi.security.dom.demo.tenanted.TenantedEntity;

@XmlRootElement(name = "module")
public class FixturesModuleSpiSecuritySubmodule extends ModuleAbstract {

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
