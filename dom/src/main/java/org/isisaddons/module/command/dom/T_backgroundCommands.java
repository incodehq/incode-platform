/*
 *  Copyright 2016 Dan Haywood
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
