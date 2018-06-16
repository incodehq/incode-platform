package org.incode.domainapp.extended.integtests.examples.tags.app;

import org.apache.isis.applib.AppManifestAbstract;

import org.incode.example.tags.TagsModule;

import org.incode.domainapp.extended.module.fixtures.per_cpt.examples.tags.FixturesModuleExamplesTagsSubmodule;

public class TagsModuleAppManifest extends AppManifestAbstract {

    public static final Builder BUILDER = Builder.forModules(TagsModule.class,
            FixturesModuleExamplesTagsSubmodule.class,
            FixturesModuleExamplesTagsSubmodule.class
    );

    public TagsModuleAppManifest() {
        super(BUILDER);
    }

}
