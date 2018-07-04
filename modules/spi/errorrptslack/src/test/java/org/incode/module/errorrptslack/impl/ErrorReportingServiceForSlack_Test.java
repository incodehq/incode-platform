package org.incode.module.errorrptslack.impl;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.jmock.Expectations;
import org.jmock.auto.Mock;
import org.junit.Assert;
import org.junit.Assume;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import org.apache.isis.applib.services.error.ErrorDetails;
import org.apache.isis.applib.services.error.Ticket;
import org.apache.isis.core.commons.config.IsisConfiguration;
import org.apache.isis.core.unittestsupport.jmocking.JUnitRuleMockery2;
import org.apache.isis.viewer.wicket.ui.errors.ExceptionModel;

import org.incode.module.slack.impl.SlackService;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.nullValue;

/**
 * run using:

 -Disis.service.slack.authToken=XXXXXX
 -Disis.service.errorReporting.slack.channel=ecp-estatio-error-tst

 */
public class ErrorReportingServiceForSlack_Test {

    ErrorReportingServiceForSlack errorReportingService;

    @Rule
    public JUnitRuleMockery2 context = JUnitRuleMockery2.createFor(JUnitRuleMockery2.Mode.INTERFACES_AND_CLASSES);

    @Mock
    IsisConfiguration mockIsisConfiguration;

    @Before
    public void setUp() throws Exception {
        errorReportingService = new ErrorReportingServiceForSlack();
        errorReportingService.configuration = mockIsisConfiguration;
        errorReportingService.slackService = new SlackService();

        context.checking(new Expectations() {{
            allowingGetStringReturnSystemProperty("isis.service.slack.authToken");
            allowingGetStringReturnSystemProperty("isis.service.errorReporting.slack.channel");
            allowing(mockIsisConfiguration);
        }

        private void allowingGetStringReturnSystemProperty(final String property) {
                allowing(mockIsisConfiguration).getString(property);
                will(returnValue(System.getProperty(property)));
            }
        });

        errorReportingService.init();
        final boolean initialized = errorReportingService.isInitialized();

        Assume.assumeTrue(initialized);
    }

    public static class reportError extends ErrorReportingServiceForSlack_Test {

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
                    .map(org.apache.isis.viewer.wicket.ui.errors.StackTraceDetail::getLine)
                    .collect(Collectors.toList());
        }
    }

}

