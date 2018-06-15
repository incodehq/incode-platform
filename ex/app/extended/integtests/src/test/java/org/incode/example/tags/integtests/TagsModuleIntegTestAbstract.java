package org.incode.example.tags.integtests;

import org.junit.BeforeClass;

import org.apache.isis.core.integtestsupport.IntegrationTestAbstract2;

import org.incode.domainapp.extended.module.fixtures.shared.ExampleDomDemoDomSubmodule;
import org.incode.example.tags.integtests.app.TagsModuleAppManifest;

public abstract class TagsModuleIntegTestAbstract extends IntegrationTestAbstract2 {

    @BeforeClass
    public static void initClass() {
        bootstrapUsing(TagsModuleAppManifest.BUILDER
                .withAdditionalModules(ExampleDomDemoDomSubmodule.class)
        );
    }

}
