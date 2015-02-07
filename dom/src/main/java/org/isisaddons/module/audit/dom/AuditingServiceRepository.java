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
package org.isisaddons.module.audit.dom;

import java.sql.Timestamp;
import java.util.List;
import java.util.UUID;

import org.joda.time.LocalDate;

import org.apache.isis.applib.AbstractFactoryAndRepository;
import org.apache.isis.applib.annotation.DomainService;
import org.apache.isis.applib.annotation.NatureOfService;
import org.apache.isis.applib.annotation.Programmatic;
import org.apache.isis.applib.query.Query;
import org.apache.isis.applib.query.QueryDefault;
import org.apache.isis.applib.services.bookmark.Bookmark;

/**
 * Provides supporting functionality for querying
 * {@link AuditEntry audit entry} entities.
 *
 * <p>
 * This supporting service with no UI and no side-effects, and there are no other implementations of the service,
 * thus has been annotated with {@link org.apache.isis.applib.annotation.DomainService}.  This means that there is no
 * need to explicitly register it as a service (eg in <tt>isis.properties</tt>).
 */
@DomainService(
        nature = NatureOfService.DOMAIN
)
public class AuditingServiceRepository extends AbstractFactoryAndRepository {
    
    @Programmatic
    public List<AuditEntry> findByTransactionId(final UUID transactionId) {
        return allMatches(
                new QueryDefault<>(AuditEntry.class,
                        "findByTransactionId", 
                        "transactionId", transactionId));
    }

    @Programmatic
    public List<AuditEntry> findByTargetAndFromAndTo(
            final Bookmark target, 
            final LocalDate from, 
            final LocalDate to) {
        final String targetStr = target.toString();
        final Timestamp fromTs = toTimestampStartOfDayWithOffset(from, 0);
        final Timestamp toTs = toTimestampStartOfDayWithOffset(to, 1);
        
        final Query<AuditEntry> query;
        if(from != null) {
            if(to != null) {
                query = new QueryDefault<>(AuditEntry.class,
                        "findByTargetAndTimestampBetween", 
                        "targetStr", targetStr,
                        "from", fromTs,
                        "to", toTs);
            } else {
                query = new QueryDefault<>(AuditEntry.class,
                        "findByTargetAndTimestampAfter", 
                        "targetStr", targetStr,
                        "from", fromTs);
            }
        } else {
            if(to != null) {
                query = new QueryDefault<>(AuditEntry.class,
                        "findByTargetAndTimestampBefore", 
                        "targetStr", targetStr,
                        "to", toTs);
            } else {
                query = new QueryDefault<>(AuditEntry.class,
                        "findByTarget", 
                        "targetStr", targetStr);
            }
        }
        return allMatches(query);
    }

    @Programmatic
    public List<AuditEntry> findByFromAndTo(
            final LocalDate from, 
            final LocalDate to) {
        final Timestamp fromTs = toTimestampStartOfDayWithOffset(from, 0);
        final Timestamp toTs = toTimestampStartOfDayWithOffset(to, 1);
        
        final Query<AuditEntry> query;
        if(from != null) {
            if(to != null) {
                query = new QueryDefault<>(AuditEntry.class,
                        "findByTimestampBetween", 
                        "from", fromTs,
                        "to", toTs);
            } else {
                query = new QueryDefault<>(AuditEntry.class,
                        "findByTimestampAfter", 
                        "from", fromTs);
            }
        } else {
            if(to != null) {
                query = new QueryDefault<>(AuditEntry.class,
                        "findByTimestampBefore", 
                        "to", toTs);
            } else {
                query = new QueryDefault<>(AuditEntry.class,
                        "find");
            }
        }
        return allMatches(query);
    }

    private static Timestamp toTimestampStartOfDayWithOffset(final LocalDate dt, final int daysOffset) {
        return dt!=null
                ?new java.sql.Timestamp(dt.toDateTimeAtStartOfDay().plusDays(daysOffset).getMillis())
                :null;
    }
}
