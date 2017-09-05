package org.incode.domainapp.example.app.modules;

import org.isisaddons.wicket.excel.cpt.ui.ExcelUiModule;

public class ExampleDomWktExcelAppManifest extends DemoToDoItemAppManifestAbstract {

    public static final Builder BUILDER = DemoToDoItemAppManifestAbstract.BUILDER.withAdditionalModules(
                ExcelUiModule.class
            );

    public ExampleDomWktExcelAppManifest() {
        super(BUILDER);
    }

}
