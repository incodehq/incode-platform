package org.incode.platform.lib.stringinterpolator.integtests.tests;

import java.net.URL;

import javax.inject.Inject;

import org.junit.Before;
import org.junit.Test;

import org.apache.isis.core.commons.config.IsisConfiguration;

import org.incode.platform.lib.stringinterpolator.integtests.StringInterpolatorDemoIntegTestAbstract;

import domainapp.modules.exampledom.lib.stringinterpolator.dom.demo.StringInterpolatorDemoToDoItem;
import domainapp.modules.exampledom.lib.stringinterpolator.dom.demo.StringInterpolatorDemoToDoItemReportingContributions;
import domainapp.modules.exampledom.lib.stringinterpolator.dom.demo.StringInterpolatorDemoToDoItems;
import domainapp.modules.exampledom.lib.stringinterpolator.fixture.StringInterpolatorDemoToDoItemsFixture;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class StringInterpolatorDemoToDoItemReportingContributions_IntegTest extends StringInterpolatorDemoIntegTestAbstract {

    @Before
    public void setUpData() throws Exception {
        scenarioExecution().install(new StringInterpolatorDemoToDoItemsFixture());
    }

    @Inject
    StringInterpolatorDemoToDoItems toDoItems;

    @Inject
    IsisConfiguration configuration;

    @Inject
    StringInterpolatorDemoToDoItemReportingContributions toDoItemReportingContributions;

    public static class Open extends StringInterpolatorDemoToDoItemReportingContributions_IntegTest {

        @Test
        public void happyCase() throws Exception {

            // given
            assertThat(configuration.getString("isis.website"), is("http://isis.apache.org"));
            assertThat(toDoItemReportingContributions.TEMPLATE, is("${properties['isis.website']}/${this.documentationPage}"));

            final StringInterpolatorDemoToDoItem toDoItem = toDoItems.allToDos().get(0);
            assertThat(toDoItem.getDocumentationPage(), is("documentation.html"));

            // when
            final URL url = toDoItemReportingContributions.open(toDoItem);

            // then
            assertThat(url.toExternalForm(), is("http://isis.apache.org/documentation.html"));
        }
    }
}