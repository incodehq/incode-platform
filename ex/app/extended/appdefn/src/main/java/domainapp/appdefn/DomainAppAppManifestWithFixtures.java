package domainapp.appdefn;

import java.util.List;
import java.util.Map;

import org.apache.isis.applib.fixturescripts.FixtureScript;

import domainapp.appdefn.seed.security.SeedSuperAdministratorRoleAndSvenSuperUser;
import domainapp.modules.simple.fixture.scenario.data.SimpleObject_data;

public class DomainAppAppManifestWithFixtures extends DomainAppAppManifest {

    @Override
    protected void overrideFixtures(final List<Class<? extends FixtureScript>> fixtureScripts) {
        fixtureScripts.add(SimpleObject_data.PersistScript.class);
        fixtureScripts.add(SeedSuperAdministratorRoleAndSvenSuperUser.class);
    }

    @Override
    protected void overrideConfigurationProperties(final Map<String, String> configurationProperties) {
        disableAuditingAndCommandAndPublishGlobally(configurationProperties);
    }

    protected void disableAuditingAndCommandAndPublishGlobally(final Map<String, String> configurationProperties) {
        configurationProperties.put("isis.services.audit.objects","none");
        configurationProperties.put("isis.services.command.actions","none");
        configurationProperties.put("isis.services.command.properties","none");
        configurationProperties.put("isis.services.publish.objects","none");
        configurationProperties.put("isis.services.publish.actions","none");
        configurationProperties.put("isis.services.publish.properties","none");
    }
}
