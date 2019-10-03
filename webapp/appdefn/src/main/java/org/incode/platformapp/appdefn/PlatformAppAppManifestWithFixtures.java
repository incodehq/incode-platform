package org.incode.platformapp.appdefn;

import java.util.List;

import org.apache.isis.applib.AppManifestAbstract2;
import org.apache.isis.applib.fixturescripts.FixtureScript;
import org.isisaddons.module.fakedata.FakeDataModule;
import org.incode.platformapp.appdefn.fixture.RecreateDemoFixtures;

public class PlatformAppAppManifestWithFixtures  extends AppManifestAbstract2 {

    public PlatformAppAppManifestWithFixtures() {
        super(Builder.forModule(new PlatformAppAppDefnModule())
                    .withAdditionalDependencies(new FakeDataModule()));
    }


    @Override
    protected void overrideFixtures(
            final List<Class<? extends FixtureScript>> fixtureScriptClasses) {
        super.overrideFixtures(fixtureScriptClasses);
        fixtureScriptClasses.add(RecreateDemoFixtures.class);
    }
}
