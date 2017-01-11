/*
 *
 *  Copyright 2012-2015 Eurocommercial Properties NV
 *
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

package org.isisaddons.wicket.gmap3.fixture.app;

import java.util.List;

import org.apache.isis.applib.AbstractViewModel;
import org.apache.isis.applib.annotation.CollectionLayout;
import org.apache.isis.applib.annotation.DomainObject;
import org.apache.isis.applib.annotation.DomainObjectLayout;
import org.apache.isis.applib.annotation.MemberGroupLayout;
import org.apache.isis.applib.annotation.MemberOrder;
import org.apache.isis.applib.annotation.Nature;
import org.apache.isis.applib.annotation.RenderType;

import org.isisaddons.wicket.gmap3.fixture.dom.Gmap3ToDoItem;
import org.isisaddons.wicket.gmap3.fixture.dom.Gmap3WicketToDoItems;

@DomainObject(
        nature = Nature.VIEW_MODEL
)
@DomainObjectLayout(
        named = "Dashboard"
)
@MemberGroupLayout(columnSpans = { 0, 0, 0, 12 })
public class Gmap3Dashboard extends AbstractViewModel {

    //region > title, iconName

    public String title() {
        return "Dashboard";
    }

    public String iconName() {
        return "Dashboard";
    }
    //endregion

    //region > Viewmodel contract

    @Override
    public String viewModelMemento() {
        return "dashboard";
    }

    @Override
    public void viewModelInit(String memento) {
        // nothing to do
    }
    //endregion

    //region > notYetComplete (derived collection)

    @MemberOrder(sequence = "1")
    @CollectionLayout(
            render = RenderType.EAGERLY
    )
    public List<Gmap3ToDoItem> getNotYetComplete() {
        return gmap3WicketToDoItems.notYetCompleteNoUi();
    }
    //endregion

    //region > complete (derived collection)

    @MemberOrder(sequence = "2")
    @CollectionLayout(
            render = RenderType.EAGERLY
    )
    public List<Gmap3ToDoItem> getComplete() {
        return gmap3WicketToDoItems.completeNoUi();
    }
    //endregion

    //region > injected services
    @javax.inject.Inject
    private Gmap3WicketToDoItems gmap3WicketToDoItems;
    //endregion

}