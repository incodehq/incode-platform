package org.isisaddons.module.stringinterpolator.integtests.demo;

import java.net.URL;
import javax.inject.Inject;

import org.apache.isis.core.commons.config.IsisConfiguration;
import org.isisaddons.module.stringinterpolator.fixture.dom.StringInterpolatorDemoToDoItem;
import org.isisaddons.module.stringinterpolator.fixture.dom.StringInterpolatorDemoToDoItemReportingContributions;
import org.isisaddons.module.stringinterpolator.fixture.dom.StringInterpolatorDemoToDoItems;
import org.isisaddons.module.stringinterpolator.fixture.scripts.StringInterpolatorDemoToDoItemsFixture;
import org.isisaddons.module.stringinterpolator.integtests.StringInterpolatorDemoIntegTest;
import org.junit.Before;
import org.junit.Test;
import org.apache.isis.core.runtime.system.context.IsisContext;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class StringInterpolatorDemoToDoItemReportingContributionsTest extends StringInterpolatorDemoIntegTest {

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

    public static class Open extends StringInterpolatorDemoToDoItemReportingContributionsTest {

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