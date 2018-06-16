package org.incode.domainapp.extended.integtests.examples.alias;

import java.util.Set;

import javax.inject.Inject;
import javax.xml.bind.annotation.XmlRootElement;

import com.google.common.collect.Sets;

import org.apache.isis.applib.ModuleAbstract;
import org.apache.isis.core.integtestsupport.IntegrationTestAbstract3;

import org.isisaddons.module.fakedata.FakeDataModule;
import org.isisaddons.module.fakedata.dom.FakeDataService;

import org.incode.domainapp.extended.integtests.examples.alias.dom.alias.AliasModuleIntegrationSubmodule;
import org.incode.domainapp.extended.integtests.examples.alias.dom.alias.dom.AliasForDemoObject;
import org.incode.domainapp.extended.module.fixtures.shared.demo.FixturesModuleSharedDemoSubmodule;

public abstract class AliasModuleIntegTestAbstract extends IntegrationTestAbstract3 {

    @XmlRootElement(name = "module")
    public static class MyModule extends AliasModuleIntegrationSubmodule {
        @Override
        public Set<org.apache.isis.applib.Module> getDependencies() {
            final Set<org.apache.isis.applib.Module> dependencies = super.getDependencies();
            dependencies.addAll(Sets.newHashSet(
                    new FixturesModuleSharedDemoSubmodule(),
                    new FakeDataModule()
            ));
            return dependencies;
        }
    }

    public static ModuleAbstract module() {
        return new MyModule();
    }

    protected AliasModuleIntegTestAbstract() {
        super(module());
    }

    @Inject
    protected FakeDataService fakeData;

    protected AliasForDemoObject._addAlias mixinAddAlias(final Object aliased) {
        return mixin(AliasForDemoObject._addAlias.class, aliased);
    }
    protected AliasForDemoObject._removeAlias mixinRemoveAlias(final Object aliased) {
        return mixin(AliasForDemoObject._removeAlias.class, aliased);
    }

    protected AliasForDemoObject._aliases mixinAliases(final Object aliased) {
        return mixin(AliasForDemoObject._aliases.class, aliased);
    }

}
