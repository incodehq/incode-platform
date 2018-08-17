package org.isisaddons.module.sessionlogger.dom;

import java.util.Collections;
import java.util.List;

import org.apache.isis.applib.AbstractService;
import org.apache.isis.applib.annotation.Action;
import org.apache.isis.applib.annotation.ActionLayout;
import org.apache.isis.applib.annotation.CollectionLayout;
import org.apache.isis.applib.annotation.Contributed;
import org.apache.isis.applib.annotation.MemberOrder;
import org.apache.isis.applib.annotation.Mixin;
import org.apache.isis.applib.annotation.SemanticsOf;
import org.apache.isis.applib.services.HasUsername;

import org.isisaddons.module.sessionlogger.SessionLoggerModule;

@Mixin
public class HasUsername_recentSessionsForUser extends AbstractService {


    public static class ActionDomainEvent extends SessionLoggerModule.ActionDomainEvent<HasUsername_recentSessionsForUser> {
    }


    private final HasUsername hasUsername;
    public HasUsername_recentSessionsForUser(final HasUsername hasUsername) {
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
    @MemberOrder(name = "user", sequence = "2")
    public List<SessionLogEntry> $$() {
        if(hasUsername == null || hasUsername.getUsername() == null) {
            return Collections.emptyList();
        }
        return sessionLogEntryRepository.findRecentByUser(hasUsername.getUsername());
    }
    public boolean hide$$() {
        return hasUsername == null || hasUsername.getUsername() == null;
    }

    // //////////////////////////////////////

    @javax.inject.Inject
    private SessionLogEntryRepository sessionLogEntryRepository;

}
