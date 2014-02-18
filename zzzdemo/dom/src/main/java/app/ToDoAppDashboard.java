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
package app;

import java.util.List;

import dom.todo.ToDoItem;
import dom.todo.ToDoItems;

import org.apache.isis.applib.AbstractViewModel;
import org.apache.isis.applib.annotation.MemberGroupLayout;
import org.apache.isis.applib.annotation.Render;
import org.apache.isis.applib.annotation.Render.Type;

@MemberGroupLayout(columnSpans={0,0,0,12})
public class ToDoAppDashboard extends AbstractViewModel {

    public String title() {
        return "Dashboard";
    }
    
    public String iconName() {
        return "Dashboard";
    }
    
    // //////////////////////////////////////
    // ViewModel contract
    // //////////////////////////////////////

    private String memento;
    
    @Override
    public String viewModelMemento() {
        return memento;
    }

    @Override
    public void viewModelInit(String memento) {
        this.memento = memento;
    }


    
    // //////////////////////////////////////
    // notYetComplete (derived collection)
    // //////////////////////////////////////

    @Render(Type.EAGERLY)
    public List<ToDoItem> getNotYetComplete() {
        return toDoItems.notYetCompleteNoUi();
    }

    
    // //////////////////////////////////////
    // injected services
    // //////////////////////////////////////
    
    @javax.inject.Inject
    private ToDoItems toDoItems;

}
