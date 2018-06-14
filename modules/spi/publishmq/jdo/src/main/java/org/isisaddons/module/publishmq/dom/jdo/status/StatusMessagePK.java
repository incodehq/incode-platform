package org.isisaddons.module.publishmq.dom.jdo.status;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.StringTokenizer;
import java.util.UUID;

import lombok.Getter;
import lombok.Setter;

public class StatusMessagePK implements Serializable {

    private static final long serialVersionUID = 1L;

    private static final String SEPARATOR = "_";

    @Getter @Setter
    public UUID transactionId;
    @Getter @Setter
    public Timestamp timestamp;

    //region > constructor, toString (reciprocals of each other)

    public StatusMessagePK() {
    }

    public StatusMessagePK(final String value) {
        final StringTokenizer token = new StringTokenizer (value, SEPARATOR);
        this.transactionId = UUID.fromString(token.nextToken());
        this.timestamp = new java.sql.Timestamp(Long.parseLong(token.nextToken()));
    }

    @Override
    public String toString() {
        return transactionId + SEPARATOR + timestamp.getTime();
    }
    //endregion

    //region > hashCode, equals

    @Override public boolean equals(final Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;

        final StatusMessagePK that = (StatusMessagePK) o;

        if (transactionId != null ? !transactionId.equals(that.transactionId) : that.transactionId != null)
            return false;
        return timestamp != null ? timestamp.equals(that.timestamp) : that.timestamp == null;

    }

    @Override public int hashCode() {
        int result = transactionId != null ? transactionId.hashCode() : 0;
        result = 31 * result + (timestamp != null ? timestamp.hashCode() : 0);
        return result;
    }

    //endregion

}
