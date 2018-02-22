package org.incode.module.jira.impl;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;

import com.google.common.base.Joiner;
import com.google.common.base.Strings;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.apache.isis.applib.annotation.DomainService;
import org.apache.isis.applib.annotation.NatureOfService;
import org.apache.isis.applib.annotation.Programmatic;
import org.apache.isis.applib.services.error.ErrorDetails;
import org.apache.isis.applib.services.error.ErrorReportingService;
import org.apache.isis.applib.services.error.Ticket;
import org.apache.isis.core.commons.config.IsisConfiguration;

import lombok.Setter;

@DomainService(nature = NatureOfService.DOMAIN)
public class ErrorReportingServiceForJira implements ErrorReportingService {

    private static final Logger LOG = LoggerFactory.getLogger(ErrorReportingServiceForJira.class);

    public static final String CONFIG_KEY_PREFIX = "isis.service.errorReporting.jira.";
    public static final String JIRA_REST_RESOURCE = "rest/api/latest/issue/";
    public static final String JIRA_HTML_PREFIX = "browse/";
    public static final String USER_MESSAGE_DEFAULT = "Our apologies, an error has occurred.  Support has been notified.";
    public static final String DETAILS_FORMAT_DEFAULT = "For more details, see JIRA issue: %s";

    /** Mandatory ... either set or specify as configuration property. */
    @Setter
    private String base;

    /** Mandatory ... either set or specify as configuration property. */
    @Setter
    private String username;

    /** Mandatory ... either set or specify as configuration property. */
    @Setter
    private String password;

    /** Mandatory ... either set or specify as configuration property. */
    @Setter
    private String projectKey;

    /** Mandatory ... either set or specify as configuration property. */
    @Setter
    private String issueType;

    /** Optional, will default */
    @Setter
    private String userMessage;

    /** Optional, will default */
    @Setter
    private String detailsFormat;

    @Setter
    private JsonMarshaller jsonMarshaller;

    @Setter
    private HttpPoster httpPoster;

    private JiraMarshaller jiraMarshaller;

    private boolean initialized;

    public ErrorReportingServiceForJira() {
    }

        /**
         * For testing only.
         */
    public ErrorReportingServiceForJira(final IsisConfiguration configuration) {
        this.configuration = configuration;
    }

    @PostConstruct
    @Programmatic
    public void init() {
        this.base = asSetElseConfigured(this.base, "base");
        this.username = asSetElseConfigured(this.username, "username");
        this.password = asSetElseConfigured(this.password, "password");

        this.projectKey = asSetElseConfigured(this.projectKey, "projectKey");
        this.issueType = asSetElseConfigured(this.issueType, "issueType");

        this.initialized =
                !Strings.isNullOrEmpty(username) &&
                !Strings.isNullOrEmpty(password) &&
                !Strings.isNullOrEmpty(base) &&
                !Strings.isNullOrEmpty(projectKey) &&
                !Strings.isNullOrEmpty(issueType);
        if(!initialized) {
            return;
        }

        userMessage = asConfiguredElseDefault("userMessage", USER_MESSAGE_DEFAULT);
        detailsFormat = asConfiguredElseDefault("detailsFormat", DETAILS_FORMAT_DEFAULT);

        this.jiraMarshaller = new JiraMarshaller(projectKey, issueType);
        this.jsonMarshaller = coalesce(this.jsonMarshaller, new JsonMarshaller());
        this.httpPoster = coalesce(this.httpPoster, new HttpPoster(base, username, password));
    }

    @Programmatic
    public boolean isInitialized() {
        return initialized;
    }

    @Programmatic
    @Override
    public Ticket reportError(final ErrorDetails errorDetails) {

        if(!initialized) {
            return null;
        }

        final String summary = errorDetails.getMainMessage();
        final List<String> stackTraceDetailList =
                errorDetails.getStackTraceDetailList()
                        .stream()
                        .map(String::trim)
                        .collect(Collectors.toList());
        final String description = Joiner.on("\n").join(stackTraceDetailList);

        return reportError(summary, description);
    }

    Ticket reportError(final String summary, final String description) {

        final Map<String, Object> body = jiraMarshaller.buildRequestBody(summary, description);

        final Optional<String> jiraNumberIfAny =
                jsonMarshaller.asJson(body)
                        .flatMap(httpPoster::post)
                        .map(jsonMarshaller::asJsonNode)
                        .map(jiraMarshaller::readResponseKey);

        if(jiraNumberIfAny.isPresent()) {
            final String jiraNumber = jiraNumberIfAny.get();
            final String jiraIssueUrl = base + JIRA_HTML_PREFIX + jiraNumber;
            final String details = String.format(detailsFormat, jiraIssueUrl);
            return new Ticket(jiraNumber, userMessage, details);
        }

        final String logMessageIfFail =
                String.format(
                        "Failed to report:\n"
                        + "summary: %s\n"
                        + "description (user's stack trace): \n"
                        + "%s",
                        summary, description);
        LOG.warn(logMessageIfFail);
        return null;
    }

    private String asConfiguredElseDefault(final String configKeySuffix, final String fallback) {
        return coalesce(configValue(configKeySuffix), fallback);
    }

    private String asSetElseConfigured(final String field, final String configKeySuffix) {
        return coalesce(field, configValue(configKeySuffix));
    }

    private String configValue(final String configKeySuffix) {
        return configuration.getString(CONFIG_KEY_PREFIX + configKeySuffix);
    }

    private static <T> T coalesce(T... values) {
        for (T value : values) {
            if(value != null) { return value; }
        }
        return null;
    }

    private static String coalesce(String... values) {
        for (String value : values) {
            if (!Strings.isNullOrEmpty(value)) {
                return value;
            }

        }
        return null;
    }

    @javax.inject.Inject
    IsisConfiguration configuration;

}
