package org.isisaddons.module.settings.integtests;

import java.util.List;
import org.isisaddons.module.settings.dom.UserSetting;
import org.isisaddons.module.settings.dom.UserSettingsServiceRW;
import domainapp.modules.exampledom.module.settings.fixture.SettingsModuleAppSetUpFixture;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class UserSettingsServiceTest extends SettingsModuleIntegTest {

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