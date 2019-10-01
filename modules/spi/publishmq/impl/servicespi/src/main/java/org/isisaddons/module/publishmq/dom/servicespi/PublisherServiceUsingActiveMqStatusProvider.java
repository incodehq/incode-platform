package org.isisaddons.module.publishmq.dom.servicespi;

import org.apache.isis.applib.annotation.Programmatic;

/**
 * Implemented by {@link PublisherServiceUsingActiveMq} itself, simply so can be injected into other SPI services
 * (implementations of {@link InteractionExecutionRepository} and {@link PublishedObjectsRepository}) so that they
 * enquire as to whether they should be enabled or not.
 */
public interface PublisherServiceUsingActiveMqStatusProvider {

    @Programmatic
    boolean isEnabled();
}
