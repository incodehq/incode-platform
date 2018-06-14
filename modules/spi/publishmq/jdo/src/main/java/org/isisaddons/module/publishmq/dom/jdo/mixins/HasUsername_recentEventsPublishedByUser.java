package org.isisaddons.module.publishmq.dom.jdo.mixins;

import java.util.Collections;
import java.util.List;

import org.apache.isis.applib.annotation.Action;
import org.apache.isis.applib.annotation.ActionLayout;
import org.apache.isis.applib.annotation.CollectionLayout;
import org.apache.isis.applib.annotation.Contributed;
import org.apache.isis.applib.annotation.MemberOrder;
import org.apache.isis.applib.annotation.Mixin;
import org.apache.isis.applib.annotation.SemanticsOf;
import org.apache.isis.applib.services.HasUsername;

import org.isisaddons.module.publishmq.PublishMqModule;
import org.isisaddons.module.publishmq.dom.jdo.events.PublishedEvent;
import org.isisaddons.module.publishmq.dom.jdo.events.PublishedEventRepository;

@Mixin
public class HasUsername_recentEventsPublishedByUser {


    public static class ActionDomainEvent
            extends PublishMqModule.ActionDomainEvent<HasUsername_recentEventsPublishedByUser> { }

    private final HasUsername hasUsername;
    public HasUsername_recentEventsPublishedByUser(final HasUsername hasUsername) {
        this.hasUsername = hasUsername;
    }

    @Action(
            semantics = SemanticsOf.SAFE,
            domainEvent = ActionDomainEvent.class
    )
    @ActionLayout(
            contributed = Contributed.AS_ACTION
    )
    @CollectionLayout(
            defaultView = "table"
    )
    @MemberOrder(name="user", sequence = "4")
    public List<PublishedEvent> $$() {
        if(hasUsername.getUsername() == null) {
            return Collections.emptyList();
        }
        return publishedEventRepository.findRecentByUser(hasUsername.getUsername());
    }
    public boolean hide$$() {
        return hasUsername.getUsername() == null;
    }


    @javax.inject.Inject
    private PublishedEventRepository publishedEventRepository;


}
