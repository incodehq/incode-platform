package org.isisaddons.module.command.dom;

import org.apache.isis.applib.annotation.Action;
import org.apache.isis.applib.annotation.ActionLayout;
import org.apache.isis.applib.annotation.Contributed;
import org.apache.isis.applib.annotation.SemanticsOf;
import org.apache.isis.applib.services.bookmark.Bookmark;
import org.apache.isis.applib.services.bookmark.BookmarkService2;
import org.apache.isis.applib.services.queryresultscache.QueryResultsCache;
import org.isisaddons.module.command.CommandModule;

import javax.inject.Inject;
import java.util.List;
import java.util.concurrent.Callable;

public abstract class T_backgroundCommands<T> {

    //region > constructor
    private final T domainObject;

    public T_backgroundCommands(final T domainObject) {
        this.domainObject = domainObject;
    }
    //endregion


    public static class ActionDomainEvent extends CommandModule.ActionDomainEvent<T_backgroundCommands> { }
    @Action(
            semantics = SemanticsOf.SAFE,
            domainEvent = ActionDomainEvent.class
    )
    @ActionLayout(
            contributed = Contributed.AS_ASSOCIATION
    )
    public List<CommandJdo> $$() {
        return findRecentBackground();
    }

    private List<CommandJdo> findRecentBackground() {
        final Bookmark bookmark = bookmarkService2.bookmarkFor(domainObject);
        return queryResultsCache.execute(new Callable<List<CommandJdo>>() {
            @Override
            public List<CommandJdo> call() throws Exception {
                return commandServiceJdoRepository.findRecentBackgroundByTarget(bookmark);
            }
        }, T_backgroundCommands.class, "findRecentBackground", domainObject);
    }


    @Inject
    CommandServiceJdoRepository commandServiceJdoRepository;
    @Inject
    BookmarkService2 bookmarkService2;
    @Inject
    QueryResultsCache queryResultsCache;

}
