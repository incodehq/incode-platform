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

package org.isisaddons.wicket.excel.fixture.scripts;

import org.apache.isis.applib.fixturescripts.FixtureScript;
import org.apache.isis.applib.services.jdosupport.IsisJdoSupport;

public class ExcelWicketTearDownFixture extends FixtureScript {

    private final String ownedBy;

    public ExcelWicketTearDownFixture(String ownedBy) {
        this.ownedBy = ownedBy;
    }

    @Override
    protected void execute(ExecutionContext executionContext) {

        if(ownedBy != null) {
            isisJdoSupport.executeUpdate("delete from \"ExcelWicketToDoItemDependencies\" where \"dependingId\" IN (select \"id\" from \"ExcelWicketToDoItem\" where \"ownedBy\" = '" + ownedBy + "') ");
            isisJdoSupport.executeUpdate("delete from \"ExcelWicketToDoItemDependencies\" where \"dependentId\" IN (select \"id\" from \"ExcelWicketToDoItem\" where \"ownedBy\" = '" + ownedBy + "') ");
            isisJdoSupport.executeUpdate("delete from \"ExcelWicketToDoItem\" where \"ownedBy\" = '" + ownedBy + "'");
        } else {
            isisJdoSupport.executeUpdate("delete from \"ExcelWicketToDoItemDependencies\"");
            isisJdoSupport.executeUpdate("delete from \"ExcelWicketToDoItem\"");
        }
    }


    @javax.inject.Inject
    private IsisJdoSupport isisJdoSupport;

}
