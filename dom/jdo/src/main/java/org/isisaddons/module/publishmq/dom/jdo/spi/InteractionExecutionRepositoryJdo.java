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
import org.apache.isis.applib.services.bookmark.BookmarkService2;
import org.apache.isis.applib.services.iactn.Interaction;
import org.apache.isis.applib.services.repository.RepositoryService;
import org.apache.isis.applib.services.title.TitleService;
import org.apache.isis.schema.common.v1.InteractionType;
import org.apache.isis.schema.ixn.v1.InteractionDto;
import org.apache.isis.schema.utils.InteractionDtoUtils;

import org.isisaddons.module.publishmq.dom.jdo.events.PublishedEvent;
import org.isisaddons.module.publishmq.dom.jdo.events.PublishedEventType;
import org.isisaddons.module.publishmq.dom.servicespi.InteractionExecutionRepository;

@DomainService(
        nature = NatureOfService.DOMAIN
)
public class InteractionExecutionRepositoryJdo implements InteractionExecutionRepository {

    @Override
    @Programmatic
    public void persist(final Interaction.Execution<?, ?> execution) {

        final PublishedEvent publishedEvent = new PublishedEvent();

        publishedEvent.setEventType(eventTypeFor(execution.getInteractionType()));
        publishedEvent.setTransactionId(execution.getInteraction().getTransactionId());
        publishedEvent.setTimestamp(execution.getStartedAt());
        publishedEvent.setSequence(execution.getDto().getSequence());
        publishedEvent.setUser(execution.getDto().getUser());

        publishedEvent.setTarget(bookmarkService2.bookmarkFor(execution.getTarget()));
        publishedEvent.setMemberIdentifier(execution.getMemberIdentifier());

        publishedEvent.setTargetClass(execution.getTargetClass());
        publishedEvent.setTargetAction(execution.getTargetMember());

        final InteractionDto interactionDto = InteractionDtoUtils.newInteractionDto(execution);
        final String xml = InteractionDtoUtils.toXml(interactionDto);

        publishedEvent.setSerializedForm(xml);

        final String title = buildTitle(publishedEvent);
        publishedEvent.setTitle(title);

        repositoryService.persist(publishedEvent);
    }

    private String buildTitle(final PublishedEvent publishedEvent) {
        final StringBuilder buf = new StringBuilder();

        buf.append(titleService.titleOf(publishedEvent.getEventType()))
            .append(" of '").append(publishedEvent.getTargetAction())
            .append("' on ").append(publishedEvent.getTargetStr());
        return buf.toString();
    }

    private PublishedEventType eventTypeFor(final InteractionType interactionType) {
        switch (interactionType) {

        case ACTION_INVOCATION:
            return PublishedEventType.ACTION_INVOCATION;
        case PROPERTY_EDIT:
            return PublishedEventType.PROPERTY_EDIT;
        default:
            // should never occur
            throw new IllegalArgumentException(String.format(
                    "InteractionType '%s' not recognized", interactionType));
        }
    }

    @Inject
    RepositoryService repositoryService;

    @Inject
    BookmarkService2 bookmarkService2;

    @Inject
    TitleService titleService;


}
