package org.incode.module.slack.impl;

import java.io.IOException;
import java.net.Proxy;
import java.util.Objects;

import javax.annotation.Nullable;
import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.inject.Inject;

import com.ullink.slack.simpleslackapi.SlackChannel;
import com.ullink.slack.simpleslackapi.SlackMessageHandle;
import com.ullink.slack.simpleslackapi.SlackPreparedMessage;
import com.ullink.slack.simpleslackapi.SlackSession;
import com.ullink.slack.simpleslackapi.impl.SlackSessionFactory;
import com.ullink.slack.simpleslackapi.replies.SlackMessageReply;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.apache.isis.applib.annotation.DomainService;
import org.apache.isis.applib.annotation.NatureOfService;
import org.apache.isis.applib.annotation.Programmatic;
import org.apache.isis.applib.services.confview.ConfigurationProperty;
import org.apache.isis.applib.services.confview.ConfigurationViewService;

import lombok.Setter;

@DomainService(nature = NatureOfService.DOMAIN)
public class SlackService {

    private static final Logger LOG = LoggerFactory.getLogger(SlackService.class);

    public static final String CONFIG_KEY_PREFIX = "isis.service.slack.";
    private static final String HTTP_PROXY_PORT = "http.proxyPort";
    private static final String HTTP_PROXY_HOST = "http.proxyHost";

    /** Mandatory ... either set or specify as configuration property. */
    @Setter
    private String authToken;

    private boolean configured;

    /**
     * Populated only if {@link #isConfigured()}.
     */
    private SlackSession slackSession;

    /**
     * Populated only if {@link #isConfigured()}.
     */
    private ChannelCache channelCache;

    public SlackService() {
    }

    /**
     * For testing purposes only.
     */
    public SlackService(final ConfigurationViewService configurationViewService) {
        this.configurationViewService = configurationViewService;
    }

    @PostConstruct
    @Programmatic
    public void init() {
        this.authToken = asSetElseConfigured(this.authToken, "authToken");
        final boolean requiredConfiguration = !isNullOrEmpty(authToken);
        if(!requiredConfiguration) {
            return;
        }

        final SlackSessionFactory.SlackSessionFactoryBuilder builder =
                SlackSessionFactory.getSlackSessionBuilder(authToken);

        if (!configureHttpProxyIfSet(builder)) {
            return;
        }

        final SlackSession slackSession = builder.build();
        try {
            slackSession.connect();
        } catch (IOException e) {
            LOG.warn("Failed to connect to Slack", e);
            return;
        }

        this.slackSession = slackSession;
        this.channelCache = new ChannelCache(slackSession);
        this.configured = true;
    }

    static boolean configureHttpProxyIfSet(
            final SlackSessionFactory.SlackSessionFactoryBuilder builder) {
        final String httpProxyHost = System.getProperty(HTTP_PROXY_HOST);
        if (httpProxyHost == null) {
            return true;
        }
        final Integer proxyPort = parseProxyPort();
        if (proxyPort == null) {
            return false;
        }
        builder.withProxy(Proxy.Type.HTTP,httpProxyHost, proxyPort);
        return true;
    }

    static Integer parseProxyPort() {
        final String proxyPortStr = System.getProperty(HTTP_PROXY_PORT);
        if(proxyPortStr == null) {
            LOG.warn(String.format(
                    "System property '%s' was set, but '%s' was not; not initialized",
                    HTTP_PROXY_HOST, HTTP_PROXY_PORT));
            return null;
        }

        final Integer proxyPort;
        try {
            proxyPort = Integer.parseInt(proxyPortStr);
        } catch (NumberFormatException e) {
            LOG.warn("Failed to parse system property 'http.proxyPort' as an integer (was " + proxyPortStr + "); not initialized");
            return null;
        }
        return proxyPort;
    }

    @Programmatic
    public boolean isConfigured() {
        return configured;
    }

    @Programmatic
    public boolean channelExists(final String channelName) {
        return isConfigured() &&
               channelName != null &&
               channelCache.findChannel(channelName) != null;
    }

    @Programmatic
    public void sendMessage(
            final String channelName,
            final String message) {

        final SlackPreparedMessage preparedMessage =
                new SlackPreparedMessage.Builder()
                        .withMessage(message)
                        .withUnfurl(false)
                        .withLinkNames(true)
                        .build();

        sendMessage(channelName, preparedMessage);
    }

    @Programmatic
    public SlackMessageHandle<SlackMessageReply> sendMessage(
            final String channelName,
            final SlackPreparedMessage preparedMessage) {
        final SlackChannel slackChannel = findChannel(channelName);

        return slackSession.sendMessage(slackChannel, preparedMessage);
    }

    private SlackChannel findChannel(final String channelName) {
        final SlackChannel slackChannel = channelCache.findChannel(channelName);
        if(slackChannel == null) {
            throw new IllegalArgumentException(String.format("Could not find channel '%s'", channelName));
        }
        return slackChannel;
    }

    @PreDestroy
    public void destroy() {
        if(!isConfigured()) {
            return;
        }
        disconnect(slackSession);
    }

    private void disconnect(final SlackSession slackSession) {
        if(slackSession == null) {
            return;
        }
        try {
            slackSession.disconnect();
        } catch (IOException e) {
            LOG.warn("Failed to disconnect, ignoring");
        }
    }

    private String asSetElseConfigured(final String field, final String configKeySuffix) {
        return Util.coalesce(field, configValue(configKeySuffix));
    }

    private String configValue(final String configKeySuffix) {
        return configurationViewService.allProperties().stream()
                .filter(x -> Objects.equals(x.getKey(), CONFIG_KEY_PREFIX + configKeySuffix))
                .map(ConfigurationProperty::getValue)
                .findFirst().orElse(null);
    }

    private static boolean isNullOrEmpty(@Nullable CharSequence x) {
        return x == null || x.length() == 0;
    }

    @Inject
    ConfigurationViewService configurationViewService;

}
