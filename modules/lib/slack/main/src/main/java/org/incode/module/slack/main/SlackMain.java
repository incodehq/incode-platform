package org.incode.module.slack.main;

import org.apache.log4j.BasicConfigurator;

import org.apache.isis.core.commons.config.IsisConfigurationDefault;

import org.incode.module.slack.impl.SlackService;

/**
 * compile using:

   mvn clean install -Dmain

 * run using:

   java -Disis.service.slack.authToken=xxx \
        -Dhttp.proxyHost=10.1.0.4 \
        -Dhttp.proxyPort=3128 \
        -jar target/incode-module-errorrpt-slack-1.16.1-SNAPSHOT-shaded.jar \
        "ecp-estatio-error-tst" \
        "Hi there !!!"

 */
public class SlackMain {

    public static void main(String[] args) {

        BasicConfigurator.configure();

        final IsisConfigurationDefault configuration = new IsisConfigurationDefault();
        putConfig(configuration, "authToken");

        final SlackService slackService = new SlackService(configuration);

        slackService.init();

        final String channelName = argsElse(args, 0, "ecp-estatio-error-tst");
        final String message = argsElse(args, 1, "Hello, world");

        slackService.sendMessage(channelName, message);

        slackService.destroy();
    }

    private static String argsElse(final String[] args, final int i, final String fallback) {
        return args.length > i ? args[i] : fallback;
    }

    private static void putConfig(final IsisConfigurationDefault configuration, final String suffix) {
        final String key = SlackService.CONFIG_KEY_PREFIX + suffix;
        final String value = System.getProperty(key);
        if(value == null) {
            throw new RuntimeException("Missing system property\n\n-D" + key + "=\n");
        }
        configuration.put(key, value);
    }

}
