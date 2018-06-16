package org.incode.domainapp.extended.integtests.lib.fakedata.app;

import org.apache.isis.applib.AppManifestAbstract;

import org.isisaddons.module.fakedata.FakeDataModule;

import org.incode.domainapp.extended.module.fixtures.per_cpt.lib.fakedata.FixturesModuleLibFakeDataSubmodule;

public class FakedataLibAppManifest extends AppManifestAbstract {

    public static final Builder BUILDER = Builder.forModules(
            FakeDataModule.class,
            FixturesModuleLibFakeDataSubmodule.class
    );

    public FakedataLibAppManifest() {
        super(BUILDER);
    }


}
