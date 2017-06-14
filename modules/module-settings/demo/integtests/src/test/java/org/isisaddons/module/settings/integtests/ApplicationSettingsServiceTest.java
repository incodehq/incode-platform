package org.isisaddons.module.settings.integtests;

import java.util.List;
import org.isisaddons.module.settings.dom.ApplicationSetting;
import org.isisaddons.module.settings.dom.ApplicationSettingsServiceRW;
import org.isisaddons.module.settings.fixture.scripts.SettingsModuleAppSetUpFixture;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class ApplicationSettingsServiceTest extends SettingsModuleIntegTest {

    @Before
    public void setUpData() throws Exception {
        scenarioExecution().install(new SettingsModuleAppSetUpFixture());
    }

    @javax.inject.Inject
    private ApplicationSettingsServiceRW applicationSettingsServiceRW;

    @Test
    public void listAll() throws Exception {

        final List<ApplicationSetting> all = wrap(applicationSettingsServiceRW).listAll();
        assertThat(all.size(), is(5));
    }
    

}