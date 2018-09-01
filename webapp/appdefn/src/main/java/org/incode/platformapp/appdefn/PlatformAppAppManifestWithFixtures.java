package org.incode.platformapp.appdefn;

import java.util.List;

import org.apache.isis.applib.fixturescripts.FixtureScript;

import org.incode.platformapp.appdefn.fixture.RecreateDemoFixtures;
import org.incode.platformapp.appdefn.seed.security.SeedSuperAdministratorRoleAndSvenSuperUser;

public class PlatformAppAppManifestWithFixtures extends PlatformAppAppManifest {

    @Override
    protected void overrideFixtures(
            final List<Class<? extends FixtureScript>> fixtureScriptClasses) {
        super.overrideFixtures(fixtureScriptClasses);
        fixtureScriptClasses.add(RecreateDemoFixtures.class);
    }
}
