package domainapp.appdefn;

import java.util.List;

import org.apache.isis.applib.AppManifestAbstract2;
import org.apache.isis.applib.fixturescripts.FixtureScript;

import domainapp.appdefn.seed.security.SeedSuperAdministratorRoleAndSvenSuperUser;
public class DomainAppAppManifestWithFlywayEnabledForSqlServer extends AppManifestAbstract2 {

	public DomainAppAppManifestWithFlywayEnabledForSqlServer() {
		super(DomainAppAppManifest.BUILDER
		//.withFixtureScripts(SeedSuperAdministratorRoleAndSvenSuperUser.class)
		.withConfigurationPropertiesFile(
                    DomainAppAppManifestWithFlywayEnabledForSqlServer.class,
                    "persistor-sqlserver-rtrm_db.properties")
		);
	}

	@Override
    protected void overrideFixtures(final List<Class<? extends FixtureScript>> fixtureScriptClasses) {
        // using withFixtureScripts(...) is broken in 1.16.0
        fixtureScriptClasses.add(SeedSuperAdministratorRoleAndSvenSuperUser.class);
    }
}
