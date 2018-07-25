package org.incode.module.minio.docclient.archive;

public class StringValue {
    final String value;
    public StringValue(final String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return String.valueOf(value);
    }
}
