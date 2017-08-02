package org.incode.platform.lib.excel.integtests.app;

import org.apache.isis.applib.AppManifestAbstract;

import org.isisaddons.module.excel.ExcelModule;

import org.incode.domainapp.example.dom.lib.excel.ExampleDomLibExcelModule;

public class ExcelLibAppManifest extends AppManifestAbstract {

    public static final Builder BUILDER = Builder.forModules(
            ExcelModule.class,
            ExampleDomLibExcelModule.class,
            ExcelAppModule.class
    );

    public ExcelLibAppManifest() {
        super(BUILDER);
    }

}
