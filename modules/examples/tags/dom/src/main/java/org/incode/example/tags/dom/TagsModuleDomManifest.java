package org.incode.example.tags.dom;

import org.apache.isis.applib.AppManifestAbstract;

import org.incode.example.tags.TagsModule;

/**
 * Provided for <tt>isis-maven-plugin</tt>.
 */
public class TagsModuleDomManifest extends AppManifestAbstract {

    public static final Builder BUILDER = Builder.forModules(
            TagsModule.class  // domain (entities and repositories)
    );

    public TagsModuleDomManifest() {
        super(BUILDER);
    }

}
