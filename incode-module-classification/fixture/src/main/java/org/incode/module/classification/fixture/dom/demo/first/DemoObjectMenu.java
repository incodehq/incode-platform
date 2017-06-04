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
package org.incode.module.classification.fixture.dom.demo.first;

import org.apache.isis.applib.annotation.*;
import org.apache.isis.applib.services.repository.RepositoryService;

import java.util.List;

@DomainService(
        nature = NatureOfService.VIEW,
        repositoryFor = DemoObject.class
)
@DomainServiceLayout(
        named = "Some",
        menuOrder = "10"
)
public class DemoObjectMenu {


    //region > listAll (action)

    @Action(
            semantics = SemanticsOf.SAFE
    )
    @ActionLayout(
            bookmarking = BookmarkPolicy.AS_ROOT
    )
    @MemberOrder(sequence = "1")
    public List<DemoObject> listAll() {
        return repositoryService.allInstances(DemoObject.class);
    }

    //endregion

    //region > createTopLevel (action)
    
    @MemberOrder(sequence = "2")
    public DemoObject create(
            @ParameterLayout(named = "Name")
            final String name,
            @ParameterLayout(named = "Application tenancy")
            final String atPath) {
        final DemoObject obj = new DemoObject(name, atPath);
        repositoryService.persist(obj);
        return obj;
    }

    //endregion

    //region > injected services

    @javax.inject.Inject
    RepositoryService repositoryService;

    //endregion

}
