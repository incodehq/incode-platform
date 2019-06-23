package org.incode.module.slack.main;

import java.util.HashSet;
import java.util.Set;

import org.apache.log4j.BasicConfigurator;

import org.apache.isis.applib.services.confview.ConfigurationProperty;
import org.apache.isis.applib.services.confview.ConfigurationViewService;

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

        final ConfigurationViewService configuration = newConfiguration();

        final SlackService slackService = new SlackService(configuration);

        slackService.init();

        final String channelName = argsElse(args, 0, "ecp-estatio-error-tst");
        final String message = argsElse(args, 1, "Hello, world");

        slackService.sendMessage(channelName, message);

        slackService.destroy();
    }

    private static ConfigurationViewService newConfiguration() {
        return new ConfigurationViewService() {

            private Set<ConfigurationProperty> allProps = new HashSet<>();
            {
                String key = SlackService.CONFIG_KEY_PREFIX + "authToken";
                final String value = System.getProperty(key);
                if(value == null) {
                    throw new RuntimeException("Missing system property\n\n-D" + key + "=\n");
                }
                allProps.add(new ConfigurationProperty(key, value));
            }

            @Override public Set<ConfigurationProperty> allProperties() {
                return allProps;
            }
        };
    }

    private static String argsElse(final String[] args, final int i, final String fallback) {
        return args.length > i ? args[i] : fallback;
    }


}
