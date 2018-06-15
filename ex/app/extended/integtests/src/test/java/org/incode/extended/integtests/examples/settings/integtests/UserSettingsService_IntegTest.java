package org.incode.extended.integtests.examples.settings.integtests;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

import org.incode.example.settings.dom.UserSetting;
import org.incode.example.settings.dom.UserSettingsServiceRW;
import org.incode.extended.integtests.examples.settings.SettingsModuleIntegTestAbstract;

import org.incode.domainapp.extended.module.fixtures.per_cpt.examples.settings.fixture.ApplicationSetting_and_UserSetting_recreate5;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class UserSettingsService_IntegTest extends SettingsModuleIntegTestAbstract {

    @Before
    public void setUpData() throws Exception {
        scenarioExecution().install(new ApplicationSetting_and_UserSetting_recreate5());
    }

    @javax.inject.Inject
    private UserSettingsServiceRW userSettingsServiceRW;

    @Test
    public void listAll() throws Exception {

        final List<UserSetting> all = wrap(userSettingsServiceRW).listAll();

        assertThat(all.size(), is(6));
    }

    @Test
    public void listAllFor() throws Exception {

        final List<UserSetting> all = wrap(userSettingsServiceRW).listAllFor("sven");

        assertThat(all.size(), is(5));
    }


}