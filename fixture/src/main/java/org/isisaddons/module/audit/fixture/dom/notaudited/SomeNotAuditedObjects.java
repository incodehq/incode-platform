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
package org.isisaddons.module.audit.fixture.dom.notaudited;

import java.util.List;

import org.apache.isis.applib.annotation.Action;
import org.apache.isis.applib.annotation.ActionLayout;
import org.apache.isis.applib.annotation.BookmarkPolicy;
import org.apache.isis.applib.annotation.DomainService;
import org.apache.isis.applib.annotation.DomainServiceLayout;
import org.apache.isis.applib.annotation.MemberOrder;
import org.apache.isis.applib.annotation.ParameterLayout;
import org.apache.isis.applib.annotation.Programmatic;
import org.apache.isis.applib.annotation.SemanticsOf;
import org.apache.isis.applib.services.repository.RepositoryService;
import org.apache.isis.applib.services.xactn.TransactionService;

import org.isisaddons.module.audit.fixture.dom.audited.SomeAuditedObject;

@DomainService (
        repositoryFor = SomeAuditedObject.class
)
@DomainServiceLayout(
        menuOrder = "10"
)
public class SomeNotAuditedObjects {

    //region > listAll (action)

    @Action(semantics = SemanticsOf.SAFE)
    @ActionLayout(bookmarking = BookmarkPolicy.AS_ROOT)
    @MemberOrder(sequence = "1")
    public List<SomeNotAuditedObject> listAll() {
        return repositoryService.allInstances(SomeNotAuditedObject.class);
    }

    //endregion

    //region > create (action)

    @MemberOrder(sequence = "2")
    public SomeNotAuditedObject create(
            @ParameterLayout(named = "Name")
            final String name) {
        final SomeNotAuditedObject obj = repositoryService.instantiate(SomeNotAuditedObject.class);
        obj.setName(name);
        repositoryService.persist(obj);
        return obj;
    }

    //endregion

    //region > delete (action)

    @Programmatic
    public List<SomeNotAuditedObject> delete(final SomeNotAuditedObject object) {
        repositoryService.remove(object);
        transactionService.flushTransaction();
        return listAll();
    }

    //endregion

    //region > injected services

    @javax.inject.Inject
    RepositoryService repositoryService;

    @javax.inject.Inject
    TransactionService transactionService;

    //endregion

}
