package org.isisaddons.module.command.dom;

public enum ReplayState {
    PENDING(false),
    OK(false),
    RESULT_DIFFERS(true),
    EXCEPTION_DIFFERS(true),
    NUM_CHILDREN_DIFFER(true),
    UNABLE_TO_CHECK_CHILDREN(true),
    EXCLUDED(false),
    ;

    private final boolean representsError;

    ReplayState(final boolean representsError) {
        this.representsError = representsError;
    }
    public boolean representsError() { return representsError;}
}
