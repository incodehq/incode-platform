package org.incode.example.alias.integtests;

import javax.inject.Inject;

import org.apache.isis.applib.ModuleAbstract;
import org.apache.isis.core.integtestsupport.IntegrationTestAbstract3;

import org.isisaddons.module.fakedata.dom.FakeDataService;

import org.incode.example.alias.demo.usage.dom.AliasForAliasedObject;

public abstract class AliasModuleIntegTestAbstract extends IntegrationTestAbstract3 {

    public static ModuleAbstract module() {
        return new AliasModuleIntegTestModule();
    }

    protected AliasModuleIntegTestAbstract() {
        super(module());
    }

    @Inject
    protected FakeDataService fakeData;

    protected AliasForAliasedObject._addAlias mixinAddAlias(final Object aliased) {
        return mixin(AliasForAliasedObject._addAlias.class, aliased);
    }
    protected AliasForAliasedObject._removeAlias mixinRemoveAlias(final Object aliased) {
        return mixin(AliasForAliasedObject._removeAlias.class, aliased);
    }

    protected AliasForAliasedObject._aliases mixinAliases(final Object aliased) {
        return mixin(AliasForAliasedObject._aliases.class, aliased);
    }

}
