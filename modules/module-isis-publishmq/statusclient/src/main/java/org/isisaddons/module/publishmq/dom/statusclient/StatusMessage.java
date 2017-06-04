package org.isisaddons.module.publishmq.dom.statusclient;

import java.net.URI;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;

/**
 * The order of the fields must correspond to that of <code>StatusMessageService#log(...)</code>.
 */
public class StatusMessage {

    public static class Builder {
        private final StatusMessage statusMessage = new StatusMessage();
        Builder(final String transactionId, final String message) {
            statusMessage.transactionId = new StringValue(transactionId);
            statusMessage.message = new StringValue(message);
        }
        public Builder withOid(final String objectType, final String identifier) {
            statusMessage.oid = new StringValue(objectType + ":" + identifier);
            return this;
        }
        public Builder withStatus(final Integer status) {
            statusMessage.status = new IntValue(status);
            return this;
        }
        public Builder withUri(final String uri) {
            statusMessage.uri = new StringValue(uri);
            return this;
        }
        public Builder withUri(final URI uri) {
            return withUri(uri.toString());
        }
        public Builder withDetail(final String detail) {
            statusMessage.detail = new StringValue(detail);
            return this;
        }
        public StatusMessage build() {
            return statusMessage;
        }
    }

    public static Builder builder(String transactionId, String message) {
        return new Builder(transactionId, message);
    }

    private static final ObjectWriter writer;

    static {
        final ObjectMapper mapper = new ObjectMapper();
        writer = mapper.writer().withDefaultPrettyPrinter();
    }

    StringValue transactionId;
    StringValue message;
    StringValue oid = new StringValue(null);
    StringValue uri = new StringValue(null);
    IntValue status = new IntValue(null);
    StringValue detail = new StringValue(null);

    public StringValue getTransactionId() {
        return transactionId;
    }

    public StringValue getMessage() {
        return message;
    }

    public StringValue getOid() {
        return oid;
    }

    public StringValue getUri() {
        return uri;
    }

    public IntValue getStatus() {
        return status;
    }

    public StringValue getDetail() {
        return detail;
    }

    public String asJson() {
        try {
            return writer.writeValueAsString(this);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public String toString() {
        return "[STATUS MESSAGE] \n" +
                "transactionId: " + transactionId + "\n" +
                "message      : " + message + "\n" +
                "oid          : " + oid + "\n" +
                "uri          : " + uri + "\n" +
                "status       : " + status + "\n" +
                "detail       : " + detail;
    }

}
