package org.isisaddons.module.command.replay.impl;

import java.util.Map;

import static org.isisaddons.module.command.replay.impl.ConfigurationKeys.MASTER_BASE_URL_ISIS_KEY;
import static org.isisaddons.module.command.replay.impl.ConfigurationKeys.MASTER_PASSWORD_ISIS_KEY;
import static org.isisaddons.module.command.replay.impl.ConfigurationKeys.MASTER_USER_ISIS_KEY;

public class SlaveConfiguration {

    final String masterUser;
    final String masterPassword;
    final String masterBaseUrl;

    public SlaveConfiguration(final Map<String, String> map) {
        masterUser = map.get(MASTER_USER_ISIS_KEY);
        masterPassword = map.get(MASTER_PASSWORD_ISIS_KEY);
        String masterBaseUrl = map.get(MASTER_BASE_URL_ISIS_KEY);
        if(masterBaseUrl != null && !masterBaseUrl.endsWith("/")) {
            masterBaseUrl = masterBaseUrl + "/";
        }
        this.masterBaseUrl= masterBaseUrl;
    }

    boolean isConfigured() {
        return masterUser != null &&
               masterPassword != null &&
               masterBaseUrl != null;
    }
}
