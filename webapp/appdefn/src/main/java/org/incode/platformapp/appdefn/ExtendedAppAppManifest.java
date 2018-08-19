package org.incode.platformapp.appdefn;

import java.util.List;

import org.apache.isis.applib.AppManifestAbstract2;
import org.apache.isis.applib.fixturescripts.FixtureScript;

import org.incode.platformapp.appdefn.seed.security.SeedSuperAdministratorRoleAndSvenSuperUser;

public class ExtendedAppAppManifest extends AppManifestAbstract2 {

    public ExtendedAppAppManifest() {
        super(Builder.forModule(new ExtendedAppAppDefnModule()));
    }

    @Override
    protected void overrideFixtures(
            final List<Class<? extends FixtureScript>> fixtureScriptClasses) {
        fixtureScriptClasses.add(SeedSuperAdministratorRoleAndSvenSuperUser.class);
    }
}
