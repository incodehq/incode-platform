package domainapp.appdefn;

import java.util.List;

import org.apache.isis.applib.fixturescripts.FixtureScript;

import domainapp.appdefn.seed.security.SeedSuperAdministratorRoleAndSvenSuperUser;

public class DomainAppAppManifestWithSeedUsers extends DomainAppAppManifest {

    @Override
    protected void overrideFixtures(final List<Class<? extends FixtureScript>> fixtureScripts) {
        fixtureScripts.add(SeedSuperAdministratorRoleAndSvenSuperUser.class);
    }

}
