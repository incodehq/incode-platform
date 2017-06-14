package org.isisaddons.module.sessionlogger.dom;

import java.util.List;

import org.joda.time.LocalDate;

import org.apache.isis.applib.annotation.Action;
import org.apache.isis.applib.annotation.ActionLayout;
import org.apache.isis.applib.annotation.BookmarkPolicy;
import org.apache.isis.applib.annotation.DomainService;
import org.apache.isis.applib.annotation.DomainServiceLayout;
import org.apache.isis.applib.annotation.MemberOrder;
import org.apache.isis.applib.annotation.NatureOfService;
import org.apache.isis.applib.annotation.Optionality;
import org.apache.isis.applib.annotation.Parameter;
import org.apache.isis.applib.annotation.ParameterLayout;
import org.apache.isis.applib.annotation.SemanticsOf;

import org.isisaddons.module.sessionlogger.SessionLoggerModule;

/**
 * This service exposes a &lt;Sessions&gt; menu to the secondary menu bar for searching for sessions.
 *
 * <p>
 * Because this service influences the UI, it must be explicitly registered as a service
 * (eg using <tt>isis.properties</tt>).
 */
@DomainService(
        nature = NatureOfService.VIEW_MENU_ONLY,
        objectType = "isissessionlogger.SessionLoggingServiceMenu"
)
@DomainServiceLayout(
        menuBar = DomainServiceLayout.MenuBar.SECONDARY,
        named = "Activity",
        menuOrder = "10"
)
public class SessionLoggingServiceMenu {

    public static abstract class ActionDomainEvent
            extends SessionLoggerModule.ActionDomainEvent<SessionLoggingServiceMenu> { }

    //region > activeSessions

    public static class ListAllActiveDomainEvent extends ActionDomainEvent {
    }

    @Action(
            domainEvent = ListAllActiveDomainEvent.class,
            semantics = SemanticsOf.SAFE
    )
    @ActionLayout(
            bookmarking = BookmarkPolicy.AS_ROOT,
            cssClassFa = "fa-bolt"
    )
    @MemberOrder(sequence = "10")
    public List<SessionLogEntry> activeSessions() {
        return sessionLogEntryRepository.listAllActiveSessions();
    }

    //endregion

    //region > findSessions


    public static class FindSessionsDomainEvent extends ActionDomainEvent {
    }

    @Action(
            domainEvent = FindSessionsDomainEvent.class,
            semantics = SemanticsOf.SAFE
    )
    @ActionLayout(
            cssClassFa = "fa-search"
    )
    @MemberOrder(sequence = "20")
    public List<SessionLogEntry> findSessions(
            @Parameter(
                    optionality = Optionality.OPTIONAL
            )
            @ParameterLayout(named = "User")
            final String username,
            @Parameter(
                    optionality = Optionality.OPTIONAL
            )
            @ParameterLayout(named = "From")
            final LocalDate from,
            @Parameter(
                    optionality = Optionality.OPTIONAL
            )
            @ParameterLayout(named = "To")
            final LocalDate to) {

        if(username == null) {
            return sessionLogEntryRepository.findByFromAndTo(from, to);
        } else {
            return sessionLogEntryRepository.findByUserAndFromAndTo(username, from, to);
        }
    }

    //endregion

    //region > injected services

    @javax.inject.Inject
    private SessionLogEntryRepository sessionLogEntryRepository;

    //endregion

}
