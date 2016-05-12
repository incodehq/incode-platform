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
package org.isisaddons.module.publishmq.dom.jdo;

import java.sql.Timestamp;
import java.util.List;
import java.util.UUID;

import javax.inject.Inject;

import org.joda.time.LocalDate;

import org.apache.isis.applib.annotation.DomainService;
import org.apache.isis.applib.annotation.NatureOfService;
import org.apache.isis.applib.annotation.Programmatic;
import org.apache.isis.applib.query.Query;
import org.apache.isis.applib.query.QueryDefault;
import org.apache.isis.applib.services.bookmark.Bookmark;
import org.apache.isis.applib.services.bookmark.BookmarkService;
import org.apache.isis.applib.services.repository.RepositoryService;

/**
 * Provides supporting functionality for querying and persisting
 * {@link PublishedEvent published event} entities.
 *
 * <p>
 * This supporting service with no UI and no side-effects, and is there are no other implementations of the service,
 * thus has been annotated with {@link org.apache.isis.applib.annotation.DomainService}.  This means that there is no
 * need to explicitly register it as a service (eg in <tt>isis.properties</tt>).
 */
@DomainService(
        nature = NatureOfService.DOMAIN
)
public class PublishedEventRepository {


    //region > findByTransactionId

    @Programmatic
    public List<PublishedEvent> findByTransactionId(final UUID transactionId) {
        return repositoryService.allMatches(
                new QueryDefault<>(PublishedEvent.class,
                        "findByTransactionId", 
                        "transactionId", transactionId));
    }

    //endregion

    //region > findByTargetAndFromAndTo

    @Programmatic
    public List<PublishedEvent> findByTargetAndFromAndTo(
            final Object publishedObject,
            final LocalDate from,
            final LocalDate to) {

        final Bookmark bookmark = bookmarkService.bookmarkFor(publishedObject);
        return findByTargetAndFromAndTo(bookmark, from, to);
    }


    @Programmatic
    public List<PublishedEvent> findByTargetAndFromAndTo(
            final Bookmark target, 
            final LocalDate from, 
            final LocalDate to) {
        final String targetStr = target.toString();
        final Timestamp fromTs = toTimestampStartOfDayWithOffset(from, 0);
        final Timestamp toTs = toTimestampStartOfDayWithOffset(to, 1);
        
        final Query<PublishedEvent> query;
        if(from != null) {
            if(to != null) {
                query = new QueryDefault<>(PublishedEvent.class,
                        "findByTargetAndTimestampBetween",
                        "targetStr", targetStr,
                        "from", fromTs,
                        "to", toTs);
            } else {
                query = new QueryDefault<>(PublishedEvent.class,
                        "findByTargetAndTimestampAfter",
                        "targetStr", targetStr,
                        "from", fromTs);
            }
        } else {
            if(to != null) {
                query = new QueryDefault<>(PublishedEvent.class,
                        "findByTargetAndTimestampBefore",
                        "targetStr", targetStr,
                        "to", toTs);
            } else {
                query = new QueryDefault<>(PublishedEvent.class,
                        "findByTarget",
                        "targetStr", targetStr);
            }
        }
        return repositoryService.allMatches(query);
    }

    //endregion

    //region > findByFromAndTo

    @Programmatic
    public List<PublishedEvent> findByFromAndTo(
            final LocalDate from, 
            final LocalDate to) {
        final Timestamp fromTs = toTimestampStartOfDayWithOffset(from, 0);
        final Timestamp toTs = toTimestampStartOfDayWithOffset(to, 1);
        
        final Query<PublishedEvent> query;
        if(from != null) {
            if(to != null) {
                query = new QueryDefault<>(PublishedEvent.class,
                        "findByTimestampBetween",
                        "from", fromTs,
                        "to", toTs);
            } else {
                query = new QueryDefault<>(PublishedEvent.class,
                        "findByTimestampAfter",
                        "from", fromTs);
            }
        } else {
            if(to != null) {
                query = new QueryDefault<>(PublishedEvent.class,
                        "findByTimestampBefore",
                        "to", toTs);
            } else {
                query = new QueryDefault<>(PublishedEvent.class,
                        "find");
            }
        }
        return repositoryService.allMatches(query);
    }

    //endregion

    //region > findRecentByUser

    @Programmatic
    public List<PublishedEvent> findRecentByUser(final String user) {
        return repositoryService.allMatches(
                new QueryDefault<>(PublishedEvent.class, "findRecentByUser", "user", user));

    }
    //endregion

    //region > helpers


    private static Timestamp toTimestampStartOfDayWithOffset(final LocalDate dt, int daysOffset) {
        return dt!=null
                ?new java.sql.Timestamp(dt.toDateTimeAtStartOfDay().plusDays(daysOffset).getMillis())
                :null;
    }

    //endregion

    @Inject
    RepositoryService repositoryService;
    @Inject
    BookmarkService bookmarkService;

}
