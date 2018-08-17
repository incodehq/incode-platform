package org.isisaddons.module.publishmq.dom.jdo.events;

public enum PublishedEventType {
    ACTION_INVOCATION,
    PROPERTY_EDIT,
    CHANGED_OBJECTS;

    public boolean relatesToMember() {
        return this == ACTION_INVOCATION || this == PROPERTY_EDIT;
    }
}