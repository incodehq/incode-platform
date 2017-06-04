/*
 *  Copyright 2014 Dan Haywood
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
package org.isisaddons.module.togglz.fixture.dom.module.demo;

import java.util.List;

import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;

import org.apache.isis.applib.DomainObjectContainer;
import org.apache.isis.applib.annotation.Action;
import org.apache.isis.applib.annotation.ActionLayout;
import org.apache.isis.applib.annotation.BookmarkPolicy;
import org.apache.isis.applib.annotation.DomainService;
import org.apache.isis.applib.annotation.DomainServiceLayout;
import org.apache.isis.applib.annotation.MemberOrder;
import org.apache.isis.applib.annotation.NatureOfService;
import org.apache.isis.applib.annotation.SemanticsOf;
import org.apache.isis.applib.services.jdosupport.IsisJdoSupport;

import org.isisaddons.module.togglz.fixture.dom.module.featuretoggle.TogglzDemoFeature;

@DomainService(
        nature = NatureOfService.VIEW_MENU_ONLY,
        objectType = "isistogglzDemo.TogglzDemoObjects",
        repositoryFor = TogglzDemoObject.class
)
@DomainServiceLayout(
        menuOrder = "10"
)
public class TogglzDemoObjects {

    //region > listAll (action)

    @Action(
            semantics = SemanticsOf.SAFE
    )
    @ActionLayout(
            bookmarking = BookmarkPolicy.AS_ROOT
    )
    @MemberOrder(sequence = "1")
    public List<TogglzDemoObject> listAll() {
        return container.allInstances(TogglzDemoObject.class);
    }
    public boolean hideListAll() {
        return !TogglzDemoFeature.listAll.isActive();
    }

    //endregion

    //region > listAll (action)
    @Action(
            semantics = SemanticsOf.SAFE
    )
    @ActionLayout(
            bookmarking = BookmarkPolicy.AS_ROOT
    )
    @MemberOrder(sequence = "2")
    public List<TogglzDemoObject> findByName(final String name) {
        return Lists.newArrayList(
                Iterables.filter(listAll(), o -> o.getName() != null && o.getName().contains(name))
        );
    }
    public boolean hideFindByName() {
        return !TogglzDemoFeature.findByName.isActive();
    }

    //endregion

    //region > create (action)
    
    @MemberOrder(sequence = "3")
    public TogglzDemoObject create(final String name) {
        final TogglzDemoObject obj = container.newTransientInstance(TogglzDemoObject.class);
        obj.setName(name);
        container.persistIfNotAlready(obj);
        return obj;
    }
    public boolean hideCreate() {
        return !TogglzDemoFeature.create.isActive();
    }

    //endregion

    //region > injected services

    @javax.inject.Inject
    private IsisJdoSupport isisJdoSupport;

    @javax.inject.Inject 
    DomainObjectContainer container;

    //endregion

}
