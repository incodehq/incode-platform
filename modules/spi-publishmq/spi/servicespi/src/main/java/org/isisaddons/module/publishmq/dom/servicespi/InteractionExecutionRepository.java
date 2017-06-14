package org.isisaddons.module.publishmq.dom.servicespi;

import org.apache.isis.applib.annotation.Programmatic;
import org.apache.isis.applib.services.iactn.Interaction;

public interface InteractionExecutionRepository {

    /**
     * @param execution - the execution an action invocation or property edit to be persisted.
     */
    @Programmatic
    void persist(Interaction.Execution<?, ?> execution);

}
