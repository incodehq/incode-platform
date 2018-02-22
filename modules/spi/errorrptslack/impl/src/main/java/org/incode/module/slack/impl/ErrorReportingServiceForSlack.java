package org.incode.module.slack.impl;

import java.util.List;
import java.util.Objects;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import com.google.common.base.Joiner;
import com.google.common.base.Strings;
import com.google.common.collect.Lists;
import com.ullink.slack.simpleslackapi.SlackAttachment;
import com.ullink.slack.simpleslackapi.SlackMessageHandle;
import com.ullink.slack.simpleslackapi.SlackPreparedMessage;
import com.ullink.slack.simpleslackapi.replies.SlackMessageReply;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.apache.isis.applib.annotation.DomainService;
import org.apache.isis.applib.annotation.NatureOfService;
import org.apache.isis.applib.annotation.Programmatic;
import org.apache.isis.applib.services.command.Command;
import org.apache.isis.applib.services.command.CommandContext;
import org.apache.isis.applib.services.error.ErrorDetails;
import org.apache.isis.applib.services.error.ErrorReportingService;
import org.apache.isis.applib.services.error.Ticket;
import org.apache.isis.applib.services.user.UserService;
import org.apache.isis.core.commons.config.IsisConfiguration;

import lombok.Setter;

@DomainService(nature = NatureOfService.DOMAIN, menuOrder = "100")
public class ErrorReportingServiceForSlack implements ErrorReportingService {

    private static final Logger LOG = LoggerFactory.getLogger(ErrorReportingServiceForSlack.class);

    private static final String CONFIG_KEY_PREFIX = "isis.service.errorReporting.slack.";
    private static final String USER_MESSAGE_DEFAULT = "Our apologies, an error has occurred.  The development team has been notified (via the Slack messaging system).";
    private static final String DETAILS_DEFAULT =
                      "The reference identifier below gives us more "
                    + "detailed information about the problem.\n"
                    + "\n"
                    + "In the meantime, and by way of an apology, \n"
                    + "here's a picture of a kitten for you to look at.";

    private static final String KITTEN_URL_DEFAULT = "http://www.randomkittengenerator.com/cats/rotator.php";
    private static final long TIMEOUT_IN_MS_DEFAULT = 5000;

    /** Optional, will default */
    @Setter
    private String channel;

    /** Optional, will default */
    @Setter
    private String userMessage;

    /** Optional, will default */
    @Setter
    private String details;

    /** Optional, will default */
    @Setter
    private String kittenUrl;

    /** Optional, will default */
    @Setter
    private long timeoutMs;

    @PostConstruct
    @Programmatic
    public void init() {
        channel = asConfiguredElseDefault("channel", null);
        userMessage = asConfiguredElseDefault("userMessage", USER_MESSAGE_DEFAULT);
        details = asConfiguredElseDefault("details", DETAILS_DEFAULT);
        kittenUrl = asConfiguredElseDefault("kittenUrl", KITTEN_URL_DEFAULT);
        timeoutMs = asConfiguredElseDefault("timeoutMs", TIMEOUT_IN_MS_DEFAULT);
    }

    @Programmatic
    public boolean isInitialized() {
        return slackService.isConfigured() && slackService.channelExists(channel);
    }

    @Programmatic
    @Override
    public Ticket reportError(final ErrorDetails errorDetails) {

        if(!isInitialized()) {
            return null;
        }

        final String summary = errorDetails.getMainMessage();

        String combined = toDescription(errorDetails.getStackTraceDetailCombined());

        final String transactionId = determineTransactionId();
        final String user = determineUser();
        final String memberIdentifier = determineMemberIdentifier();

        final String ticketSummaryMember = String.format("%s %s (%s)", transactionId, summary, memberIdentifier);

        final List<SlackAttachment> slackAttachments = Lists.newArrayList();

        final String[] colors = new String[] {"#fff9ac", "#acffcf", "#ffcfac", "#abcbff", "#acb1ff"};
        int color = 0;
        final SlackAttachment header = new SlackAttachment();
        header.addField("Transaction Id", transactionId, false);
        header.addField("User", user, false);
        header.addField("Action", memberIdentifier, false);
        header.setColor(colors[color++ % colors.length]);

        slackAttachments.add(header);

        for (List<String> stackTracePerCause : errorDetails.getStackTraceDetailPerCause()) {
            final String tracePerCause = toDescription(stackTracePerCause);
            final SlackAttachment slackAttachment = new SlackAttachment(stackTracePerCause.get(0), tracePerCause, tracePerCause, null);
            slackAttachment.setColor(colors[color++ % colors.length]);
            slackAttachments.add(slackAttachment);
        }

        final SlackPreparedMessage preparedMessage =
                new SlackPreparedMessage.Builder()
                        .withMessage(summary)
                        .withUnfurl(false)
                        .withLinkNames(true)
                        .withAttachments(slackAttachments).build();
        final SlackMessageHandle<SlackMessageReply> handle = slackService.sendMessage(channel, preparedMessage);

        if(handle == null) {
            logFailure("handle: SlackMessageHandle<SlackMessageReply> == null", ticketSummaryMember, combined);
            return null;
        }

        handle.waitForReply(timeoutMs, TimeUnit.MILLISECONDS);

        final SlackMessageReply reply = handle.getReply();
        if(reply == null) {
            logFailure("handle.getReply() == null", ticketSummaryMember, combined);
            return null;
        }

        return new Ticket(transactionId, userMessage, details, kittenUrl);
    }

    private String determineTransactionId() {
        try {
            final Command command = commandContext.getCommand();
            return command.getTransactionId().toString();
        } catch(Exception ex) {
            return "<unknown>";
        }
    }
    private String determineUser() {
        try {
            return userService.getUser().getName();
        } catch(Exception ex) {
            return "<unknown>";
        }
    }
    private String determineMemberIdentifier() {
        try {
            final Command command = commandContext.getCommand();
            return command.getMemberIdentifier();
        } catch(Exception ex) {
            return "<unknown>";
        }
    }

    private static String toDescription(final List<String> list) {

        final List<String> stackTraceDetailList =
                list.stream()
                        .filter(Objects::nonNull)
                        .map(String::trim)
                        .filter(x -> !x.startsWith("org.apache.wicket")
                                  && !x.startsWith("org.eclipse.jetty")
                                  && !x.startsWith("org.apache.shiro")
                                  && !x.startsWith("org.togglz.servlet.TogglzFilter")
                                  && !x.startsWith("org.apache.isis.core.webapp.diagnostics.IsisLogOnExceptionFilter")
                                  && !x.startsWith("sun.reflect.DelegatingMethodAccessorImpl")
                                  && !x.startsWith("sun.reflect.NativeMethodAccessorImpl")
                                  && !x.startsWith("java.lang.Thread#run")
                                  && !x.startsWith("java.lang.reflect.Method#invoke")
                        )
                        .collect(Collectors.toList());
        return Joiner.on("\n").join(stackTraceDetailList);
    }

    private static void logFailure(final String reason, final String summary, final String description) {
        final String logMessageIfFail =
                String.format(
                        "Failed to report: (%s)\n"
                        + "summary: %s\n"
                        + "description (user's stack trace): \n"
                        + "%s",
                        reason, summary, description);
        LOG.warn(logMessageIfFail);
    }

    private String asConfiguredElseDefault(final String configKeySuffix, final String fallback) {
        return coalesce(configValue(configKeySuffix), fallback);
    }

    private long asConfiguredElseDefault(final String configKeySuffix, final long fallback) {
        try {
            return Integer.parseInt(configValue(configKeySuffix));
        } catch (Exception ex) {
            return fallback;
        }
    }

    private String configValue(final String configKeySuffix) {
        return configuration.getString(CONFIG_KEY_PREFIX + configKeySuffix);
    }

    private static String coalesce(String... values) {
        for (String value : values) {
            if (!Strings.isNullOrEmpty(value)) {
                return value;
            }

        }
        return null;
    }


    @Inject
    SlackService slackService;

    @javax.inject.Inject
    IsisConfiguration configuration;

    @javax.inject.Inject
    CommandContext commandContext;

    @javax.inject.Inject
    UserService userService;


}
