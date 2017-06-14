package org.isisaddons.module.publishmq.dom.servicespi;

import org.apache.isis.applib.annotation.Programmatic;
import org.apache.isis.applib.services.publish.PublishedObjects;

public interface PublishedObjectsRepository {

    /**
     * @param publishedObjects - the identity of the objects created, updated or deleted within a transaction, to be persisted.
     */
    @Programmatic
    void persist(final PublishedObjects publishedObjects);

}
