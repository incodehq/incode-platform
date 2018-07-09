package org.isisaddons.module.publishmq.dom.jdo.events;

import java.io.Serializable;
import java.util.StringTokenizer;
import java.util.UUID;

import lombok.Getter;
import lombok.Setter;

public class PublishedEventPK implements Serializable {

    private static final long serialVersionUID = 1L;

    private static final String SEPARATOR = "_";

    @Getter @Setter
    public UUID transactionId;
    @Getter @Setter
    public int sequence;

    //region > constructor, toString (reciprocals of each other)
    public PublishedEventPK() {
    }
    
    public PublishedEventPK(final String value) {
        final StringTokenizer token = new StringTokenizer (value, SEPARATOR);
        this.transactionId = UUID.fromString(token.nextToken());
        this.sequence = Integer.parseInt(token.nextToken());
    }

    @Override
    public String toString() {
        return transactionId + SEPARATOR + sequence;
    }

    //endregion

    //region > hashCode, equals
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((transactionId == null) ? 0 : transactionId.hashCode());
        result = prime * result + sequence;
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

        PublishedEventPK other = (PublishedEventPK) o;
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
        return true;
    }
    //endregion

}
