package org.isisaddons.module.publishmq.dom.outboxclient.delete;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;

import lombok.Getter;

public class DeleteMessage {

    private static final ObjectWriter writer;
    static {
        final ObjectMapper mapper = new ObjectMapper();
        writer = mapper.writer().withDefaultPrettyPrinter();
    }

    @Getter
    private final StringValue transactionId;
    @Getter
    private final IntValue sequence;

    public DeleteMessage(final String transactionId, final int sequence) {
        this.transactionId = new StringValue(transactionId);
        this.sequence = new IntValue(sequence);
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
        return "[DELETE MESSAGE] \n" +
                "transactionId: " + transactionId + "\n" +
                "sequence     : " + sequence + "\n";
    }

}
