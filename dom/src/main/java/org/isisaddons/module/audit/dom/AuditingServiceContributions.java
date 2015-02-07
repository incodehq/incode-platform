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

import java.util.List;
import org.isisaddons.module.audit.AuditModule;
import org.apache.isis.applib.AbstractService;
import org.apache.isis.applib.Identifier;
import org.apache.isis.applib.annotation.Action;
import org.apache.isis.applib.annotation.ActionLayout;
import org.apache.isis.applib.annotation.Collection;
import org.apache.isis.applib.annotation.CollectionLayout;
import org.apache.isis.applib.annotation.Contributed;
import org.apache.isis.applib.annotation.DomainService;
import org.apache.isis.applib.annotation.NatureOfService;
import org.apache.isis.applib.annotation.RenderType;
import org.apache.isis.applib.annotation.SemanticsOf;
import org.apache.isis.applib.services.HasTransactionId;

/**
 * This service contributes an <tt>auditEntries</tt> collection to any implementation of
 * {@link org.apache.isis.applib.services.HasTransactionId}, in other words commands, audit entries and published
 * events.  This allows the user to navigate to other audited effects of the given command.
 *
 * <p>
 * Note that this service influences the UI.  If not required, use security or a vetoing subscriber to hide.
 */
@DomainService(
        nature = NatureOfService.VIEW_CONTRIBUTIONS_ONLY
)
public class AuditingServiceContributions extends AbstractService {

    public static abstract class CollectionDomainEvent<T> extends AuditModule.CollectionDomainEvent<AuditingServiceContributions,T> {
        public CollectionDomainEvent(final AuditingServiceContributions source, final Identifier identifier, final Of of) {
            super(source, identifier, of);
        }
        public CollectionDomainEvent(final AuditingServiceContributions source, final Identifier identifier, final Of of, final T value) {
            super(source, identifier, of, value);
        }
    }

    // //////////////////////////////////////

    public static class AuditEntriesDomainEvent extends AuditModule.CollectionDomainEvent<AuditingServiceContributions, AuditEntry> {
        public AuditEntriesDomainEvent(final AuditingServiceContributions source, final Identifier identifier, final Of of) {
            super(source, identifier, of);
        }
        public AuditEntriesDomainEvent(final AuditingServiceContributions source, final Identifier identifier, final Of of, final AuditEntry value) {
            super(source, identifier, of, value);
        }
    }

    @Action(
            semantics = SemanticsOf.SAFE
    )
    @ActionLayout(
            contributed = Contributed.AS_ASSOCIATION
    )
    @Collection(
            domainEvent = AuditEntriesDomainEvent.class
    )
    @CollectionLayout(
            render = RenderType.EAGERLY
    )
    public List<AuditEntry> auditEntries(final HasTransactionId hasTransactionId) {
        return auditEntryRepository.findByTransactionId(hasTransactionId.getTransactionId());
    }
    
    // //////////////////////////////////////

    @javax.inject.Inject
    private AuditingServiceRepository auditEntryRepository;

}
