package org.isisaddons.module.command.dom;

import java.util.List;

import org.apache.isis.applib.annotation.Action;
import org.apache.isis.applib.annotation.ActionLayout;
import org.apache.isis.applib.annotation.Contributed;
import org.apache.isis.applib.annotation.MemberOrder;
import org.apache.isis.applib.annotation.Mixin;
import org.apache.isis.applib.annotation.SemanticsOf;
import org.apache.isis.applib.services.HasTransactionId;
import org.apache.isis.applib.services.bookmark.Bookmark;
import org.apache.isis.applib.services.bookmark.BookmarkService2;

import org.isisaddons.module.command.CommandModule;

/**
 * This mixin contributes a <tt>recentCommands</tt> collection to any domain object (unless also a {@link HasTransactionId}).
 */
@Mixin(method = "act")
public class Object_recentCommands {

    public static class ActionDomainEvent
            extends CommandModule.ActionDomainEvent<Object_recentCommands> { }


    private final Object domainObject;
    public Object_recentCommands(final Object domainObject) {
        this.domainObject = domainObject;
    }


    @Action(
            semantics = SemanticsOf.SAFE,
            domainEvent = ActionDomainEvent.class
    )
    @ActionLayout(
            contributed = Contributed.AS_ACTION,
            cssClassFa = "fa-bolt",
            position = ActionLayout.Position.PANEL_DROPDOWN
    )
    @MemberOrder(name = "datanucleusIdLong", sequence = "900.1")
    public List<CommandJdo> act() {
        final Bookmark bookmark = bookmarkService.bookmarkFor(domainObject);
        return commandServiceRepository.findRecentByTarget(bookmark);
    }
    /**
     * Hide if the contributee is a {@link HasTransactionId}.
     */
    public boolean hideAct() {
        return (domainObject instanceof HasTransactionId);
    }

    @javax.inject.Inject
    CommandServiceJdoRepository commandServiceRepository;

    @javax.inject.Inject
    BookmarkService2 bookmarkService;


}
