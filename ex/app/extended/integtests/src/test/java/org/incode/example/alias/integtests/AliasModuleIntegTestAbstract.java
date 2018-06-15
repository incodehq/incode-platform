package org.incode.example.alias.integtests;

import javax.inject.Inject;

import org.junit.BeforeClass;

import org.apache.isis.core.integtestsupport.IntegrationTestAbstract2;

import org.isisaddons.module.fakedata.FakeDataModule;
import org.isisaddons.module.fakedata.dom.FakeDataService;

import org.incode.domainapp.module.fixtures.per_cpt.examples.alias.dom.AliasForDemoObject;
import org.incode.domainapp.module.fixtures.shared.ExampleDomDemoDomSubmodule;
import org.incode.example.alias.integtests.app.AliasModuleAppManifest;

public abstract class AliasModuleIntegTestAbstract extends IntegrationTestAbstract2 {

    @BeforeClass
    public static void initClass() {
        bootstrapUsing(
                AliasModuleAppManifest.BUILDER.
                        withAdditionalModules(
                                ExampleDomDemoDomSubmodule.class,
                                AliasModuleIntegTestAbstract.class,
                                FakeDataModule.class
                        )
                        .build());
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
