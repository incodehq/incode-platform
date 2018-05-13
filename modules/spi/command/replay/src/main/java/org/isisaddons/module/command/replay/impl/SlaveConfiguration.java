package org.isisaddons.module.command.replay.impl;

import java.util.Map;

import static org.isisaddons.module.command.replay.impl.ConfigurationKeys.MASTER_BASE_URL_ISIS_KEY;
import static org.isisaddons.module.command.replay.impl.ConfigurationKeys.MASTER_BATCH_SIZE_ISIS_DEFAULT;
import static org.isisaddons.module.command.replay.impl.ConfigurationKeys.MASTER_BATCH_SIZE_ISIS_KEY;
import static org.isisaddons.module.command.replay.impl.ConfigurationKeys.MASTER_PASSWORD_ISIS_KEY;
import static org.isisaddons.module.command.replay.impl.ConfigurationKeys.MASTER_USER_ISIS_KEY;

public class SlaveConfiguration {

    final String masterUser;
    final String masterPassword;
    final String masterBaseUrl;
    final int masterBatchSize;

    public SlaveConfiguration(final Map<String, String> map) {
        masterUser = map.get(MASTER_USER_ISIS_KEY);
        masterPassword = map.get(MASTER_PASSWORD_ISIS_KEY);
        String masterBaseUrl = map.get(MASTER_BASE_URL_ISIS_KEY);
        if(masterBaseUrl != null && !masterBaseUrl.endsWith("/")) {
            masterBaseUrl = masterBaseUrl + "/";
        }
        this.masterBaseUrl= masterBaseUrl;
        this.masterBatchSize = batchSizeFrom(map);
    }

    private static int batchSizeFrom(final Map<String, String> map) {
        try {
            return Integer.parseInt(map.get(MASTER_BATCH_SIZE_ISIS_KEY));
        } catch (NumberFormatException e) {
            return MASTER_BATCH_SIZE_ISIS_DEFAULT;
        }
    }

    public boolean isConfigured() {
        return masterUser != null &&
               masterPassword != null &&
               masterBaseUrl != null;
    }
}
