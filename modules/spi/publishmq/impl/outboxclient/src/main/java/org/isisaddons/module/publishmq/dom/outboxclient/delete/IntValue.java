package org.isisaddons.module.publishmq.dom.outboxclient.delete;

public class IntValue {
    public final Integer value;
    public IntValue(final Integer value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return String.valueOf(value);
    }
}
