package org.isisaddons.module.command.replay.impl;

import lombok.experimental.UtilityClass;

@UtilityClass
public class ConfigurationKeys {

    static final String SLAVE_USER_QUARTZ_KEY  = "user";
    static final String SLAVE_USER_DEFAULT     = "replay_user";
    static final String SLAVE_ROLES_QUARTZ_KEY = "roles";
    static final String SLAVE_ROLES_DEFAULT    = "replay_role";

    static final String ISIS_KEY_PREFIX = "isis.command.replay.";

    // eg "http://localhost:8080/restful/"
    public static final String MASTER_BASE_URL_ISIS_KEY              = ISIS_KEY_PREFIX + "master.baseUrl";
    public static final String MASTER_PASSWORD_ISIS_KEY              = ISIS_KEY_PREFIX + "master.password";
    public static final String MASTER_USER_ISIS_KEY                  = ISIS_KEY_PREFIX + "master.user";
    public static final String MASTER_BASE_URL_END_USER_URL_ISIS_KEY = ISIS_KEY_PREFIX + "master.baseUrlEndUser";
    public static final String MASTER_BATCH_SIZE_ISIS_KEY = ISIS_KEY_PREFIX + "master.batchSize";
    public static final int MASTER_BATCH_SIZE_ISIS_DEFAULT = 10;
}
