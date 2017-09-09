package org.incode.domainapp.example.app.modules;

import org.isisaddons.wicket.summernote.cpt.ui.SummernoteUiModule;

public class ExampleDomWktSummerNoteAppManifest extends DemoToDoItemAppManifestAbstract {

    public static final Builder BUILDER = DemoToDoItemAppManifestAbstract.BUILDER.withAdditionalModules(
            SummernoteUiModule.class
    );

    public ExampleDomWktSummerNoteAppManifest() {
        super(BUILDER);
    }

}
