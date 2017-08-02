package org.isisaddons.module.fakedata.app;

import org.apache.isis.applib.AppManifestAbstract;

import org.isisaddons.module.fakedata.FakeDataModule;

import domainapp.modules.exampledom.lib.fakedata.ExampleDomLibFakeDataModule;

public class FakedataLibAppManifest extends AppManifestAbstract {

    public static final Builder BUILDER = Builder.withModules(
            FakeDataModule.class,
            ExampleDomLibFakeDataModule.class
    );

    public FakedataLibAppManifest() {
        super(BUILDER);
    }


}
