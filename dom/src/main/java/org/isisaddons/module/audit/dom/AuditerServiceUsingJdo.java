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

import java.util.UUID;

import javax.inject.Inject;

import org.apache.isis.applib.annotation.DomainService;
import org.apache.isis.applib.annotation.NatureOfService;
import org.apache.isis.applib.annotation.Programmatic;
import org.apache.isis.applib.services.audit.AuditerService;
import org.apache.isis.applib.services.audit.AuditingService3;
import org.apache.isis.applib.services.bookmark.Bookmark;
import org.apache.isis.applib.services.repository.RepositoryService;

@DomainService(
        nature = NatureOfService.DOMAIN
)
public class AuditerServiceUsingJdo implements AuditerService {

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Programmatic
    public void audit(
            final UUID transactionId,
            final int sequence,
            String targetClass, final Bookmark target,
            String memberIdentifier, final String propertyId, 
            final String preValue, final String postValue, 
            final String user, final java.sql.Timestamp timestamp) {
        
        final AuditEntry auditEntry = repositoryService.instantiate(AuditEntry.class);
        
        auditEntry.setTimestamp(timestamp);
        auditEntry.setUser(user);
        auditEntry.setTransactionId(transactionId);
        auditEntry.setSequence(sequence);

        auditEntry.setTargetClass(targetClass);
        auditEntry.setTarget(target);
        
        auditEntry.setMemberIdentifier(memberIdentifier);
        auditEntry.setPropertyId(propertyId);
        
        auditEntry.setPreValue(preValue);
        auditEntry.setPostValue(postValue);
        
        repositoryService.persist(auditEntry);
    }


    @Inject
    RepositoryService repositoryService;

}
