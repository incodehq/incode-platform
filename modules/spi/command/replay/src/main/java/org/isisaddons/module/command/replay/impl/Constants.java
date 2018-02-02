package org.isisaddons.module.command.replay.impl;

import lombok.experimental.UtilityClass;

@UtilityClass
class Constants {

    static final String SLAVE_USER_QUARTZ_KEY  = "user";
    static final String SLAVE_USER_DEFAULT     = "replay_user";
    static final String SLAVE_ROLES_QUARTZ_KEY = "roles";
    static final String SLAVE_ROLES_DEFAULT    = "replay_role";

    static final String URL_SUFFIX =
            "services/isiscommand.CommandReplayOnMasterService/actions/findCommandsOnMasterSince/invoke";

    static final String ISIS_KEY_PREFIX = "isis.command.replay.";


    // eg "http://localhost:8080/restful/"
    static final String MASTER_BASE_URL_ISIS_KEY                = ISIS_KEY_PREFIX + "master.baseUrl";
    static final String MASTER_PASSWORD_ISIS_KEY                = ISIS_KEY_PREFIX + "master.password";
    static final String MASTER_USER_ISIS_KEY                    = ISIS_KEY_PREFIX + "master.user";
    static final String MASTER_BASE_URL_END_USER_URL_ISIS_KEY   = ISIS_KEY_PREFIX + "master.baseUrlEndUser";
}
