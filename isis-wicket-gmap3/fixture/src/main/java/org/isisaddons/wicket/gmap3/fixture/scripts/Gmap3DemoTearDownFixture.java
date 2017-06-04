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

package org.isisaddons.wicket.gmap3.fixture.scripts;

import org.apache.isis.applib.fixturescripts.FixtureScript;
import org.apache.isis.applib.services.jdosupport.IsisJdoSupport;

public class Gmap3DemoTearDownFixture extends FixtureScript {

    private final String ownedBy;

    public Gmap3DemoTearDownFixture(String ownedBy) {
        this.ownedBy = ownedBy;
    }


    @Override
    protected void execute(ExecutionContext executionContext) {

        if(ownedBy != null) {
            isisJdoSupport.executeUpdate("delete from \"Gmap3ToDoItemDependencies\" where \"dependingId\" IN (select \"id\" from \"Gmap3ToDoItem\" where \"ownedBy\" = '" + ownedBy + "') ");
            isisJdoSupport.executeUpdate("delete from \"Gmap3ToDoItemDependencies\" where \"dependentId\" IN (select \"id\" from \"Gmap3ToDoItem\" where \"ownedBy\" = '" + ownedBy + "') ");
            isisJdoSupport.executeUpdate("delete from \"Gmap3ToDoItem\" where \"ownedBy\" = '" + ownedBy + "'");
        } else {
            isisJdoSupport.executeUpdate("delete from \"Gmap3ToDoItemDependencies\"");
            isisJdoSupport.executeUpdate("delete from \"Gmap3ToDoItem\"");
        }

    }


    @javax.inject.Inject
    private IsisJdoSupport isisJdoSupport;

}
