package org.isisaddons.module.publishmq.dom.mq.spi;

import org.apache.isis.applib.annotation.DomainService;
import org.apache.isis.applib.annotation.NatureOfService;
import org.apache.isis.applib.annotation.Programmatic;
import org.apache.isis.applib.services.publish.PublishedObjects;
import org.apache.isis.schema.chg.v1.ChangesDto;
import org.isisaddons.module.publishmq.dom.servicespi.PublishedObjectsRepository;

@DomainService(
        nature = NatureOfService.DOMAIN
)
public class PublishedObjectsRepositoryMq implements PublishedObjectsRepository {

    @Override
    @Programmatic
    public void persist(final PublishedObjects publishedObjects) {
        final ChangesDto changesDto = publishedObjects.getDto();
        // no-op; we currently do not published the ChangesDto onto JMS.
    }


}
