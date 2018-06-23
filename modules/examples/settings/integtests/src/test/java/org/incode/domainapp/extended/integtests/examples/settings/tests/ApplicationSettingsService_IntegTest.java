package org.incode.domainapp.extended.integtests.examples.settings.tests;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

import org.incode.domainapp.extended.integtests.examples.settings.SettingsModuleIntegTestAbstract;
import org.incode.example.settings.dom.ApplicationSetting;
import org.incode.example.settings.dom.ApplicationSettingsServiceRW;
import org.incode.example.settings.fixture.ApplicationSetting_and_UserSetting_create5;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class ApplicationSettingsService_IntegTest extends SettingsModuleIntegTestAbstract {

    @Before
    public void setUpData() throws Exception {
        runFixtureScript(new ApplicationSetting_and_UserSetting_create5());
    }

    @javax.inject.Inject
    private ApplicationSettingsServiceRW applicationSettingsServiceRW;

    @Test
    public void listAll() throws Exception {

        final List<ApplicationSetting> all = wrap(applicationSettingsServiceRW).listAll();
        assertThat(all.size(), is(5));
    }
    

}