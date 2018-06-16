package org.incode.domainapp.extended.integtests.examples.tags;

import org.junit.BeforeClass;

import org.apache.isis.core.integtestsupport.IntegrationTestAbstract2;

import org.incode.domainapp.extended.module.fixtures.shared.FixturesModuleSharedSubmodule;
import org.incode.domainapp.extended.integtests.examples.tags.app.TagsModuleAppManifest;

public abstract class TagsModuleIntegTestAbstract extends IntegrationTestAbstract2 {

    @BeforeClass
    public static void initClass() {
        bootstrapUsing(TagsModuleAppManifest.BUILDER
                .withAdditionalModules(FixturesModuleSharedSubmodule.class)
        );
    }

}
