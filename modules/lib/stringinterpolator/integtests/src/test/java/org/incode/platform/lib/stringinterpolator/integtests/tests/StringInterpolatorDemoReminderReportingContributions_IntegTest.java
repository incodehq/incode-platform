package org.incode.platform.lib.stringinterpolator.integtests.tests;

import java.net.URL;

import javax.inject.Inject;

import org.junit.Before;
import org.junit.Test;

import org.apache.isis.core.commons.config.IsisConfiguration;

import org.incode.domainapp.example.dom.demo.dom.reminder.DemoReminder;
import org.incode.platform.lib.stringinterpolator.integtests.StringInterpolatorDemoIntegTestAbstract;

import org.incode.domainapp.example.dom.lib.stringinterpolator.dom.DemoToDoItem2StringInterpolatorContributions;
import org.incode.domainapp.example.dom.demo.dom.reminder.DemoReminderMenu;
import org.incode.domainapp.example.dom.demo.fixture.reminders.DemoReminder_recreate4;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class StringInterpolatorDemoReminderReportingContributions_IntegTest extends StringInterpolatorDemoIntegTestAbstract {

    @Before
    public void setUpData() throws Exception {
        scenarioExecution().install(new DemoReminder_recreate4());
    }

    @Inject
    DemoReminderMenu reminderMenu;

    @Inject
    IsisConfiguration configuration;

    @Inject
    DemoToDoItem2StringInterpolatorContributions toDoItemReportingContributions;

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