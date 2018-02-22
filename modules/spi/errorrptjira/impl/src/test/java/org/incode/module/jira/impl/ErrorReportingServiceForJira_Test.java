package org.estatio.webapp.services.jira;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.jmock.Expectations;
import org.jmock.auto.Mock;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import org.apache.isis.applib.services.error.ErrorDetails;
import org.apache.isis.applib.services.error.Ticket;
import org.apache.isis.core.commons.config.IsisConfiguration;
import org.apache.isis.core.unittestsupport.jmocking.JUnitRuleMockery2;
import org.apache.isis.viewer.wicket.ui.errors.ExceptionModel;
import org.apache.isis.viewer.wicket.ui.errors.StackTraceDetail;

import org.incode.module.jira.impl.ErrorReportingServiceForJira;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assume.assumeTrue;

/**
 * run using:

 -Disis.service.errorReporting.jira.username=XXXXXX
 -Disis.service.errorReporting.jira.password=XXXXXX
 -Disis.service.errorReporting.jira.base=https://incodehq.atlassian.net/
 -Disis.service.errorReporting.jira.projectKey=SUP
 -Disis.service.errorReporting.jira.issueType="IT Help"

 */
public class ErrorReportingServiceForJira_Test {

    ErrorReportingServiceForJira errorReportingService;

    @Rule
    public JUnitRuleMockery2 context = JUnitRuleMockery2.createFor(JUnitRuleMockery2.Mode.INTERFACES_AND_CLASSES);

    @Mock
    IsisConfiguration mockIsisConfiguration;

    @Before
    public void setUp() throws Exception {
        errorReportingService = new ErrorReportingServiceForJira(mockIsisConfiguration);

        context.checking(new Expectations() {{
            allowingGetStringReturnSystemProperty("base");
            allowingGetStringReturnSystemProperty("username");
            allowingGetStringReturnSystemProperty("password");
            allowingGetStringReturnSystemProperty("projectKey");
            allowingGetStringReturnSystemProperty("issueType");
            allowing(mockIsisConfiguration);
        }

        private void allowingGetStringReturnSystemProperty(final String suffix) {
                allowing(mockIsisConfiguration).getString("isis.service.errorReporting.jira." + suffix);
                will(returnValue(System.getProperty("isis.service.errorReporting.jira." + suffix)));
            }
        });

        errorReportingService.init();
        final boolean initialized = errorReportingService.isInitialized();

        assumeTrue(initialized);
    }

    public static class reportError extends ErrorReportingServiceForJira_Test {

        @Test
        public void happy_case() throws Exception {

            final String message = "Some recognised message";
            final List<String> lines = asExceptionStackTraceLines(message);

            final ErrorDetails errorDetails = new ErrorDetails(message, true, false, lines, Collections.singletonList(lines));
            final Ticket ticket = errorReportingService.reportError(errorDetails);


            Assert.assertThat(ticket, is(not(nullValue())));
        }

        private static List<String> asExceptionStackTraceLines(final String message) {
            final Exception dummy = new Exception("underlying exception reason");
            dummy.fillInStackTrace();

            final ExceptionModel model = ExceptionModel.create(message, dummy);
            return model.getStackTrace()
                    .stream()
                    .map(StackTraceDetail::getLine)
                    .collect(Collectors.toList());
        }
    }
}