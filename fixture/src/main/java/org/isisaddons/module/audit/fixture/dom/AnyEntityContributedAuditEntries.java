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
package org.isisaddons.module.audit.fixture.dom;

import java.util.List;
import org.isisaddons.module.audit.dom.AuditEntry;
import org.isisaddons.module.audit.dom.AuditingServiceRepository;
import org.apache.isis.applib.annotation.*;
import org.apache.isis.applib.services.HasTransactionId;
import org.apache.isis.applib.services.bookmark.Bookmark;
import org.apache.isis.applib.services.bookmark.BookmarkService;

@DomainService(
        nature = NatureOfService.VIEW_CONTRIBUTIONS_ONLY
)
public class AnyEntityContributedAuditEntries {


    @Action(
            semantics = SemanticsOf.SAFE
    )
    @ActionLayout(
            contributed = Contributed.AS_ASSOCIATION
    )
    @CollectionLayout(
            render = RenderType.EAGERLY
    )
    public List<AuditEntry> auditEntries(Object entity) {
        final Bookmark bookmark = bookmarkService.bookmarkFor(entity);
        return auditingServiceRepository.findByTargetAndFromAndTo(bookmark, null, null);
    }

    public boolean hideAuditEntries(Object entity) {
        // because AuditServiceContributions already contributes to HasTransactionId
        return entity instanceof HasTransactionId;
    }

    @javax.inject.Inject
    private BookmarkService bookmarkService;

    @javax.inject.Inject
    private AuditingServiceRepository auditingServiceRepository;

}
