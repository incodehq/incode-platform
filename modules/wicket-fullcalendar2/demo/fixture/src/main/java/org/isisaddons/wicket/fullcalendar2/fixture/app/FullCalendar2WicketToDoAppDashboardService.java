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
package org.isisaddons.wicket.fullcalendar2.fixture.app;

import org.apache.isis.applib.DomainObjectContainer;
import org.apache.isis.applib.annotation.Action;
import org.apache.isis.applib.annotation.DomainService;
import org.apache.isis.applib.annotation.HomePage;
import org.apache.isis.applib.annotation.NatureOfService;
import org.apache.isis.applib.annotation.SemanticsOf;

@DomainService(
        nature = NatureOfService.VIEW_CONTRIBUTIONS_ONLY // trick
)
public class FullCalendar2WicketToDoAppDashboardService {

    //region > identification in the UI

    private static final String ID = "dashboard";

    public String getId() {
        return ID;
    }

    public String iconName() {
        return ID;
    }

    //endregion

    //region > homePage

    @Action(
            semantics = SemanticsOf.SAFE
    )
    @HomePage
    public FullCalendar2WicketToDoAppDashboard lookup() {
        return container.newViewModelInstance(FullCalendar2WicketToDoAppDashboard.class, ID);
    }

    //endregion

    //region > injected services

    @javax.inject.Inject
    private DomainObjectContainer container;

    //endregion
}
