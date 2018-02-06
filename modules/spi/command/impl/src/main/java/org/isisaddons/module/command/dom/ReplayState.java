package org.isisaddons.module.command.dom;

public enum ReplayState {
    PENDING,
    OK,
    FAILED,
    EXCLUDED,
    ;

    public boolean isFailed() { return this == FAILED;}
}
