package org.incode.domainapp.extended.appdefn;

import java.util.List;

import org.apache.isis.applib.AppManifestAbstract2;
import org.apache.isis.applib.fixturescripts.FixtureScript;

import org.incode.domainapp.extended.appdefn.seed.security.SeedSuperAdministratorRoleAndSvenSuperUser;
import org.incode.domainapp.extended.module.fixtures.per_cpt.lib.excel.FixturesModuleLibExcelSubmodule;

public class ExtendedAppAppManifest extends AppManifestAbstract2 {

    public ExtendedAppAppManifest() {
        super(Builder.forModule(new FixturesModuleLibExcelSubmodule()));
//        super(Builder.forModule(new ExtendedAppAppDefnModule()));
    }

    @Override
    protected void overrideFixtures(
            final List<Class<? extends FixtureScript>> fixtureScriptClasses) {
        fixtureScriptClasses.add(SeedSuperAdministratorRoleAndSvenSuperUser.class);
    }
}
