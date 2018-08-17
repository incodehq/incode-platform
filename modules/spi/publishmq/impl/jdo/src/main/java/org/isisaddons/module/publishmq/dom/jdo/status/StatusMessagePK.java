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
    public int sequence;
    @Getter @Setter
    public Timestamp timestamp;

    //region > constructor, toString (reciprocals of each other)

    public StatusMessagePK() {
    }

    public StatusMessagePK(final String value) {
        final StringTokenizer token = new StringTokenizer (value, SEPARATOR);
        this.transactionId = UUID.fromString(token.nextToken());
        this.sequence = Integer.parseInt(token.nextToken());
        this.timestamp = new java.sql.Timestamp(Long.parseLong(token.nextToken()));
    }

    @Override
    public String toString() {
        return transactionId + SEPARATOR + sequence + SEPARATOR + timestamp.getTime();
    }
    //endregion

    //region > hashCode, equals
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + (transactionId != null ? transactionId.hashCode() : 0);
        result = prime * result + sequence;
        result = prime * result + (timestamp != null ? timestamp.hashCode() : 0);
        return result;
    }
    @Override
    public boolean equals(final Object o) {
        if (this == o)
            return true;
        if (o == null)
            return false;
        if (getClass() != o.getClass())
            return false;

        final StatusMessagePK other = (StatusMessagePK) o;
        if (transactionId == null) {
            if (other.transactionId != null) {
                return false;
            }
        } else {
            if (!transactionId.equals(other.transactionId)) {
                return false;
            }
        }
        if (sequence != other.sequence) {
            return false;
        }
        return timestamp != null ? timestamp.equals(other.timestamp) : other.timestamp == null;
    }

    //endregion

}
