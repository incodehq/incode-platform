/*
 *  Licensed to the Apache Software Foundation (ASF) under one
 *  or more contributor license agreements.  See the NOTICE file
 *  distributed with this work for additional information
 *  regarding copyright ownership.  The ASF licenses this file
 *  to you under the Apache License, Version 2.0 (the
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

import java.util.List;
import org.isisaddons.wicket.fullcalendar2.fixture.dom.FullCalendar2WicketToDoItem;
import org.isisaddons.wicket.fullcalendar2.fixture.dom.FullCalendar2WicketToDoItems;
import org.apache.isis.applib.AbstractViewModel;
import org.apache.isis.applib.annotation.MemberGroupLayout;
import org.apache.isis.applib.annotation.Named;
import org.apache.isis.applib.annotation.Render;
import org.apache.isis.applib.annotation.Render.Type;

@Named("Dashboard")
@MemberGroupLayout(columnSpans={0,0,0,12})
public class FullCalendar2WicketToDoAppDashboard extends AbstractViewModel {

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

    @Render(Type.EAGERLY)
    public List<FullCalendar2WicketToDoItem> getNotYetComplete() {
        return toDoItems.notYetCompleteNoUi();
    }
    //endregion

    //region > injected services
    @javax.inject.Inject
    private FullCalendar2WicketToDoItems toDoItems;
    //endregion

}
