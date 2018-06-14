package org.incode.example.tags;

import org.apache.isis.applib.AppManifestAbstract;

/**
 * Provided for <tt>isis-maven-plugin</tt>.
 */
public class TagsManifest extends AppManifestAbstract {

    public static final Builder BUILDER = Builder.forModules(
            TagsModule.class  // domain (entities and repositories)
    );

    public TagsManifest() {
        super(BUILDER);
    }

}
