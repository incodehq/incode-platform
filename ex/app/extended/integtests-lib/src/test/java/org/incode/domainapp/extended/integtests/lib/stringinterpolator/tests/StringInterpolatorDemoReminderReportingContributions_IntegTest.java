package org.incode.domainapp.extended.integtests.lib.stringinterpolator.tests;

import java.net.URL;

import javax.inject.Inject;

import org.junit.Before;
import org.junit.Test;

import org.apache.isis.core.commons.config.IsisConfiguration;

import org.incode.domainapp.extended.integtests.lib.stringinterpolator.StringInterpolatorModuleIntegTestAbstract;
import org.incode.domainapp.extended.module.fixtures.per_cpt.lib.stringinterpolator.contributions.DemoReminderStringInterpolatorContributions;
import org.incode.domainapp.extended.module.fixtures.per_cpt.lib.stringinterpolator.dom.DemoReminder;
import org.incode.domainapp.extended.module.fixtures.per_cpt.lib.stringinterpolator.dom.DemoReminderMenu;
import org.incode.domainapp.extended.module.fixtures.per_cpt.lib.stringinterpolator.fixture.DemoReminder_create4;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class StringInterpolatorDemoReminderReportingContributions_IntegTest extends
        StringInterpolatorModuleIntegTestAbstract {

    @Before
    public void setUpData() throws Exception {
        runFixtureScript(new DemoReminder_create4());
    }

    @Inject
    DemoReminderMenu reminderMenu;

    @Inject
    IsisConfiguration configuration;

    @Inject
    DemoReminderStringInterpolatorContributions toDoItemReportingContributions;

    public static class Open extends StringInterpolatorDemoReminderReportingContributions_IntegTest {

        @Test
        public void happyCase() throws Exception {

            // given
            assertThat(configuration.getString("isis.website"), is("http://isis.apache.org"));
            assertThat(toDoItemReportingContributions.TEMPLATE, is("${properties['isis.website']}/${this.documentationPage}"));

            final DemoReminder reminder = reminderMenu.listAllReminders().get(0);
            assertThat(reminder.getDocumentationPage(), is("documentation.html"));

            // when
            final URL url = toDoItemReportingContributions.open(reminder);

            // then
            assertThat(url.toExternalForm(), is("http://isis.apache.org/documentation.html"));
        }
    }
}