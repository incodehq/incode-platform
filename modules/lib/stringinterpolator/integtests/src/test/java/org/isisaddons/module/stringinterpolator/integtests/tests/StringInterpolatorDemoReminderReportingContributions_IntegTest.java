package org.isisaddons.module.stringinterpolator.integtests.tests;

import java.net.URL;

import javax.inject.Inject;

import org.junit.Before;
import org.junit.Test;

import org.apache.isis.core.commons.config.IsisConfiguration;

import org.isisaddons.module.stringinterpolator.fixture.lib.stringinterpolator.demoapp.demomodule.contributions.OgnlDemoReminderStringInterpolatorContributions;
import org.isisaddons.module.stringinterpolator.fixture.lib.stringinterpolator.demoapp.demomodule.dom.OgnlDemoReminder;
import org.isisaddons.module.stringinterpolator.fixture.lib.stringinterpolator.demoapp.demomodule.dom.OgnlDemoReminderMenu;
import org.isisaddons.module.stringinterpolator.fixture.lib.stringinterpolator.scripts.OgnlDemoReminder_create4;
import org.isisaddons.module.stringinterpolator.integtests.StringInterpolatorModuleIntegTestAbstract;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class StringInterpolatorDemoReminderReportingContributions_IntegTest extends
        StringInterpolatorModuleIntegTestAbstract {

    @Before
    public void setUpData() throws Exception {
        runFixtureScript(new OgnlDemoReminder_create4());
    }

    @Inject
    OgnlDemoReminderMenu reminderMenu;

    @Inject
    IsisConfiguration configuration;

    @Inject
    OgnlDemoReminderStringInterpolatorContributions toDoItemReportingContributions;

    public static class Open extends StringInterpolatorDemoReminderReportingContributions_IntegTest {

        @Test
        public void happyCase() throws Exception {

            // given
            assertThat(configuration.getString("isis.website"), is("http://isis.apache.org"));
            assertThat(toDoItemReportingContributions.TEMPLATE, is("${properties['isis.website']}/${this.documentationPage}"));

            final OgnlDemoReminder reminder = reminderMenu.listAllReminders().get(0);
            assertThat(reminder.getDocumentationPage(), is("documentation.html"));

            // when
            final URL url = toDoItemReportingContributions.open(reminder);

            // then
            assertThat(url.toExternalForm(), is("http://isis.apache.org/documentation.html"));
        }
    }
}