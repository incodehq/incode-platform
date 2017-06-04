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
package org.isisaddons.module.publishmq.dom.jdo.status;

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
import org.apache.isis.applib.services.repository.RepositoryService;

/**
 * Provides supporting functionality for querying
 * {@link StatusMessage status message} entities.
 */
@DomainService(
        nature = NatureOfService.DOMAIN
)
public class StatusMessageRepository {


    //region > findByTransactionId

    @Programmatic
    public List<StatusMessage> findByTransactionId(final UUID transactionId) {
        return repositoryService.allMatches(
                new QueryDefault<>(StatusMessage.class,
                        "findByTransactionId",
                        "transactionId", transactionId));
    }

    @Programmatic
    public List<StatusMessage> findByTransactionIds(final List<UUID> transactionIds) {
        return repositoryService.allMatches(
                new QueryDefault<>(StatusMessage.class,
                        "findByTransactionIds",
                        "transactionIds", transactionIds));
    }

    //endregion

    //region > findByFromAndTo

    @Programmatic
    public List<StatusMessage> findByFromAndTo(
            final LocalDate from,
            final LocalDate to) {
        final Timestamp fromTs = toTimestampStartOfDayWithOffset(from, 0);
        final Timestamp toTs = toTimestampStartOfDayWithOffset(to, 1);

        final Query<StatusMessage> query;
        if(from != null) {
            if(to != null) {
                query = new QueryDefault<>(StatusMessage.class,
                        "findByTimestampBetween",
                        "from", fromTs,
                        "to", toTs);
            } else {
                query = new QueryDefault<>(StatusMessage.class,
                        "findByTimestampAfter",
                        "from", fromTs);
            }
        } else {
            if(to != null) {
                query = new QueryDefault<>(StatusMessage.class,
                        "findByTimestampBefore",
                        "to", toTs);
            } else {
                query = new QueryDefault<>(StatusMessage.class,
                        "find");
            }
        }
        return repositoryService.allMatches(query);
    }

    //endregion

    //region > helpers

    private static Timestamp toTimestampStartOfDayWithOffset(final LocalDate dt, int daysOffset) {
        return dt!=null
                ? new Timestamp(dt.toDateTimeAtStartOfDay().plusDays(daysOffset).getMillis())
                : null;
    }

    //endregion

    @Inject
    RepositoryService repositoryService;

}
