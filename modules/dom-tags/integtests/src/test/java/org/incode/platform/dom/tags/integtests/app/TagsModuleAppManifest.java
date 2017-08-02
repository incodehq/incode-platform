package org.incode.platform.dom.tags.integtests.app;

import org.apache.isis.applib.AppManifestAbstract;

import org.isisaddons.module.tags.TagsModule;

import domainapp.modules.exampledom.module.tags.ExampleDomModuleTagsModule;

public class TagsModuleAppManifest extends AppManifestAbstract {

    public static final Builder BUILDER = Builder.forModules(TagsModule.class,
            ExampleDomModuleTagsModule.class,
            ExampleDomModuleTagsModule.class
    );

    public TagsModuleAppManifest() {
        super(BUILDER);
    }

}
