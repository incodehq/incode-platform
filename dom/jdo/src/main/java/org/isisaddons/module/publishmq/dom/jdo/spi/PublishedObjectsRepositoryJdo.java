/*
 *  Copyright 2013~2014 Dan Haywood
 *
 *  Licensed under the Apache License, Version 2.0 (the
 *  "License"); you may not use this file except in compliance
 *  with the License.  You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing,
 *  software distributed under the License is distributed on an
 *  "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 *  KIND, either express or implied.  See the License for the
 *  specific language governing permissions and limitations
 *  under the License.
 */
package org.isisaddons.module.publishmq.dom.jdo.spi;

import javax.inject.Inject;

import org.apache.isis.applib.annotation.DomainService;
import org.apache.isis.applib.annotation.NatureOfService;
import org.apache.isis.applib.annotation.Programmatic;
import org.apache.isis.applib.services.publish.PublishedObjects;
import org.apache.isis.applib.services.repository.RepositoryService;
import org.apache.isis.applib.services.title.TitleService;
import org.apache.isis.schema.chg.v1.ChangesDto;
import org.apache.isis.schema.utils.ChangesDtoUtils;

import org.isisaddons.module.publishmq.dom.jdo.PublishedEvent;
import org.isisaddons.module.publishmq.dom.jdo.PublishedEventType;
import org.isisaddons.module.publishmq.dom.servicespi.PublishedObjectsRepository;

@DomainService(
        nature = NatureOfService.DOMAIN
)
public class PublishedObjectsRepositoryJdo implements PublishedObjectsRepository {

    @Override
    @Programmatic
    public void persist(final PublishedObjects publishedObjects) {

        final PublishedEvent publishedEvent = new PublishedEvent();

        publishedEvent.setEventType(PublishedEventType.CHANGED_OBJECTS);
        publishedEvent.setTransactionId(publishedObjects.getTransactionId());
        publishedEvent.setTimestamp(publishedObjects.getCompletedAt());
        publishedEvent.setSequence(-1); // interactions start at 0
        publishedEvent.setUser(publishedObjects.getUser());

        publishedEvent.setTarget(null);
        publishedEvent.setTargetClass(null);
        publishedEvent.setMemberIdentifier(null);
        publishedEvent.setTargetAction(null);

        final ChangesDto dto = publishedObjects.getDto();
        final String xml = ChangesDtoUtils.toXml(dto);

        publishedEvent.setSerializedForm(xml);

        final String title = buildTitle(publishedObjects);
        publishedEvent.setTitle(title);

        repositoryService.persist(publishedEvent);
    }

    private String buildTitle(final PublishedObjects publishedObjects) {
        final StringBuilder buf = new StringBuilder();
        buf.append(titleService.titleOf(PublishedEventType.CHANGED_OBJECTS))
                .append("(")
                .append(publishedObjects.numberCreated()).append( " created, ")
                .append(publishedObjects.numberUpdated()).append( " updated, ")
                .append(publishedObjects.numberDeleted()).append( " deleted")
                .append(")");
        return buf.toString();
    }

    @Inject
    RepositoryService repositoryService;

    @Inject
    TitleService titleService;

}
