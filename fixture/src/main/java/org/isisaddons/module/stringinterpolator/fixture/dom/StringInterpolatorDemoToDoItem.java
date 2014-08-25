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
package org.isisaddons.module.stringinterpolator.fixture.dom;

import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.VersionStrategy;
import org.apache.isis.applib.DomainObjectContainer;
import org.apache.isis.applib.annotation.*;
import org.apache.isis.applib.util.ObjectContracts;

@javax.jdo.annotations.PersistenceCapable(identityType=IdentityType.DATASTORE)
@javax.jdo.annotations.DatastoreIdentity(
        strategy=javax.jdo.annotations.IdGeneratorStrategy.IDENTITY,
         column="id")
@javax.jdo.annotations.Version(
        strategy=VersionStrategy.VERSION_NUMBER, 
        column="version")
@javax.jdo.annotations.Uniques({
    @javax.jdo.annotations.Unique(
            name="ToDoItem_description_must_be_unique", 
            members={"description"})
})
@Named("To Do Item")
@ObjectType("TODO")
@Bookmarkable
public class StringInterpolatorDemoToDoItem implements Comparable<StringInterpolatorDemoToDoItem> {

    //region > description
    private String description;

    @Title
    @javax.jdo.annotations.Column(allowsNull="false", length=100)
    @RegEx(validation = "\\w[@&:\\-\\,\\.\\+ \\w]*")
    @TypicalLength(50)
    public String getDescription() {
        return description;
    }

    public void setDescription(final String description) {
        this.description = description;
    }
    //endregion

    //region > documentationPage

    private String documentationPage;

    @javax.jdo.annotations.Column(allowsNull="true", length=255)
    public String getDocumentationPage() {
        return documentationPage;
    }

    public void setDocumentationPage(final String documentationPage) {
        this.documentationPage = documentationPage;
    }
    //endregion

    //region > toString, compareTo

    @Override
    public String toString() {
        return ObjectContracts.toString(this, "description,complete,ownedBy");
    }

    @Override
    public int compareTo(final StringInterpolatorDemoToDoItem other) {
        return ObjectContracts.compare(this, other, "description");
    }
    //endregion

    //region > injected services

    @javax.inject.Inject
    private DomainObjectContainer container;

    @javax.inject.Inject
    private StringInterpolatorDemoToDoItems toDoItems;
    //endregion
}
