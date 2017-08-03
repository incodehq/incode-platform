package domainapp.appdefn;

import java.util.List;

import org.apache.isis.applib.fixturescripts.FixtureScript;

import domainapp.appdefn.fixtures.security.SeedSuperAdministratorRoleAndSvenSuperUser;

public class DomainAppAppManifestWithFixtures extends DomainAppAppManifest {

    @Override protected void overrideFixtures(final List<Class<? extends FixtureScript>> fixtureScripts) {
        fixtureScripts.add(SeedSuperAdministratorRoleAndSvenSuperUser.class);
    }
}
