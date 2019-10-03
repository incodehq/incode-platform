package org.incode.platform.publish.outbox.schema;

import org.apache.isis.schema.ixn.v1.InteractionDto;
import org.incode.platform.publish.outbox.schema.ixl.v1.InteractionType;

public class InteractionsDtoUtil {

    private InteractionsDtoUtil(){}

    public static InteractionDto toDto(InteractionType interactionType) {
        final InteractionDto interactionDto = new InteractionDto();
        interactionDto.setTransactionId(interactionType.getTransactionId());
        interactionDto.setExecution(interactionType.getExecution());
        interactionDto.setMajorVersion(interactionType.getMajorVersion());
        interactionDto.setMinorVersion(interactionType.getMinorVersion());
        return interactionDto;
    }
    public static InteractionType toType(InteractionDto interactionDto) {
        final InteractionType interactionType = new InteractionType();
        interactionType.setTransactionId(interactionDto.getTransactionId());
        interactionType.setExecution(interactionDto.getExecution());
        interactionType.setMajorVersion(interactionDto.getMajorVersion());
        interactionType.setMinorVersion(interactionDto.getMinorVersion());
        return interactionType;
    }
}
