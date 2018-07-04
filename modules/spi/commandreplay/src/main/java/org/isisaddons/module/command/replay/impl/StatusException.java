package org.isisaddons.module.command.replay.impl;


public class StatusException extends Exception {
    public final SlaveStatus slaveStatus;

    StatusException(SlaveStatus slaveStatus) {
        this(slaveStatus, null);
    }
    StatusException(SlaveStatus slaveStatus, final Exception ex) {
        super(ex);
        this.slaveStatus = slaveStatus;
    }
}
