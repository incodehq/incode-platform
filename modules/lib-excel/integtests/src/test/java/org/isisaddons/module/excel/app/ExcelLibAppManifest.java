package org.isisaddons.module.excel.app;

import org.apache.isis.applib.AppManifestAbstract;

import org.isisaddons.module.excel.ExcelModule;

import domainapp.modules.exampledom.lib.excel.ExampleDomLibExcelModule;

public class ExcelLibAppManifest extends AppManifestAbstract {

    public static final Builder BUILDER = Builder.withModules(
            ExcelModule.class,
            ExampleDomLibExcelModule.class,
            ExcelAppModule.class
    );

    public ExcelLibAppManifest() {
        super(BUILDER);
    }

}
