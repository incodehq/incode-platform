package org.isisaddons.module.command.replay.impl;

enum SlaveStatus {
    TICKING_CLOCK_STATUS_UNKNOWN,
    TICKING_CLOCK_NOT_YET_INITIALIZED,
    OK,
    REST_CALL_FAILING,
    FAILED_TO_UNMARSHALL_RESPONSE,
    UNKNOWN_STATE,
}
