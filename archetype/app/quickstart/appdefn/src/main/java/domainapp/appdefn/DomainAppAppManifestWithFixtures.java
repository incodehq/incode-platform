package domainapp.appdefn;

import java.util.List;

import org.apache.isis.applib.fixturescripts.FixtureScript;

import domainapp.appdefn.seed.security.SeedSuperAdministratorRoleAndSvenSuperUser;
import domainapp.modules.simple.fixture.scenario.data.SimpleObject_data;

public class DomainAppAppManifestWithFixtures extends DomainAppAppManifest {

    @Override
    protected void overrideFixtures(final List<Class<? extends FixtureScript>> fixtureScripts) {
        super.overrideFixtures(fixtureScripts);
        fixtureScripts.add(SimpleObject_data.PersistScript.class);
    }

}
