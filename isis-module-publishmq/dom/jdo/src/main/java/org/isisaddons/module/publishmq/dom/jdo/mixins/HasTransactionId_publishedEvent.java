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
package org.isisaddons.module.publishmq.dom.jdo.mixins;

import org.apache.isis.applib.annotation.*;
import org.apache.isis.applib.services.HasTransactionId;
import org.isisaddons.module.publishmq.PublishMqModule;
import org.isisaddons.module.publishmq.dom.jdo.events.PublishedEvent;
import org.isisaddons.module.publishmq.dom.jdo.events.PublishedEventRepository;

import java.util.UUID;


/**
 * This mixin contributes a <tt>publishedEvent</tt> action to any other implementations of
 * {@link HasTransactionId}; that is: audit entries, and commands.  If there is more than
 * one {@link PublishedEvent} then only the first (ie top-most) is returned.
 */
@Mixin
public class HasTransactionId_publishedEvent {

    public static class ActionDomainEvent
            extends PublishMqModule.ActionDomainEvent<HasTransactionId_publishedEvent> { }


    private final HasTransactionId hasTransactionId;
    public HasTransactionId_publishedEvent(final HasTransactionId hasTransactionId) {
        this.hasTransactionId = hasTransactionId;
    }


    @Action(
            semantics = SemanticsOf.SAFE,
            domainEvent = ActionDomainEvent.class
    )
    @ActionLayout(
            contributed = Contributed.AS_ACTION
    )
    @MemberOrder(name="transactionId", sequence="1")
    public PublishedEvent $$() {
        return findFirstEvent();
    }
    /**
     * Hide if the contributee is a {@link PublishedEvent}
     */
    public boolean hide$$() {
        return (hasTransactionId instanceof PublishedEvent);
    }
    public String disable$$() {
        return findFirstEvent() == null ? "No command found for transaction Id": null;
    }

    private PublishedEvent findFirstEvent() {
        final UUID transactionId = hasTransactionId.getTransactionId();
        final int sequenceForFirstEvent = 0;
        return publishedEventRepository.findByTransactionIdAndSequence(transactionId, sequenceForFirstEvent);
    }


    @javax.inject.Inject
    private PublishedEventRepository publishedEventRepository;


}
