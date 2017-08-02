package org.incode.platform.lib.fakedata.integtests.app;

import org.apache.isis.applib.AppManifestAbstract;

import org.isisaddons.module.fakedata.FakeDataModule;

import org.incode.domainapp.example.dom.lib.fakedata.ExampleDomLibFakeDataModule;

public class FakedataLibAppManifest extends AppManifestAbstract {

    public static final Builder BUILDER = Builder.forModules(
            FakeDataModule.class,
            ExampleDomLibFakeDataModule.class
    );

    public FakedataLibAppManifest() {
        super(BUILDER);
    }


}
