package org.incode.platform.dom.settings.integtests.tests;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

import org.isisaddons.module.settings.dom.UserSetting;
import org.isisaddons.module.settings.dom.UserSettingsServiceRW;
import org.incode.platform.dom.settings.integtests.SettingsModuleIntegTestAbstract;

import org.incode.domainapp.example.dom.dom.settings.fixture.SettingsModuleAppSetUpFixture;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class UserSettingsService_IntegTest extends SettingsModuleIntegTestAbstract {

    @Before
    public void setUpData() throws Exception {
        scenarioExecution().install(new SettingsModuleAppSetUpFixture());
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