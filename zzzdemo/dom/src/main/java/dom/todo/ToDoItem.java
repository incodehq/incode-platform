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
package dom.todo;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;

import javax.jdo.JDOHelper;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.VersionStrategy;

import com.google.common.base.Objects;
import com.google.common.base.Predicate;
import com.google.common.collect.Ordering;

import org.joda.time.LocalDate;

import org.apache.isis.applib.DomainObjectContainer;
import org.apache.isis.applib.annotation.AutoComplete;
import org.apache.isis.applib.annotation.Bookmarkable;
import org.apache.isis.applib.annotation.Bulk;
import org.apache.isis.applib.annotation.Disabled;
import org.apache.isis.applib.annotation.MinLength;
import org.apache.isis.applib.annotation.Named;
import org.apache.isis.applib.annotation.ObjectType;
import org.apache.isis.applib.annotation.Optional;
import org.apache.isis.applib.annotation.Programmatic;
import org.apache.isis.applib.annotation.PublishedAction;
import org.apache.isis.applib.annotation.RegEx;
import org.apache.isis.applib.annotation.SortedBy;
import org.apache.isis.applib.annotation.TypicalLength;
import org.apache.isis.applib.clock.Clock;
import org.apache.isis.applib.util.ObjectContracts;
import org.apache.isis.applib.util.TitleBuffer;
import org.apache.isis.applib.value.Blob;

import services.ClockService;

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
            members={"ownedBy","description"})
})
@javax.jdo.annotations.Queries( {
    @javax.jdo.annotations.Query(
            name = "todo_all", language = "JDOQL",
            value = "SELECT "
                    + "FROM dom.todo.ToDoItem "
                    + "WHERE ownedBy == :ownedBy"),
    @javax.jdo.annotations.Query(
            name = "todo_notYetComplete", language = "JDOQL",
            value = "SELECT "
                    + "FROM dom.todo.ToDoItem "
                    + "WHERE ownedBy == :ownedBy "
                    + "   && complete == false"),
    @javax.jdo.annotations.Query(
            name = "todo_complete", language = "JDOQL",
            value = "SELECT "
                    + "FROM dom.todo.ToDoItem "
                    + "WHERE ownedBy == :ownedBy "
                    + "&& complete == true"),
    @javax.jdo.annotations.Query(
            name = "todo_similarTo", language = "JDOQL",
            value = "SELECT "
                    + "FROM dom.todo.ToDoItem "
                    + "WHERE ownedBy == :ownedBy "
                    + "&& category == :category"),
    @javax.jdo.annotations.Query(
            name = "todo_autoComplete", language = "JDOQL",
            value = "SELECT "
                    + "FROM dom.todo.ToDoItem "
                    + "WHERE ownedBy == :ownedBy && "
                    + "description.indexOf(:description) >= 0")
})
@ObjectType("TODO")
@AutoComplete(repository=ToDoItems.class, action="autoComplete")
@Bookmarkable
public class ToDoItem implements Comparable<ToDoItem> {

    // //////////////////////////////////////
    // Identification in the UI
    // //////////////////////////////////////

    public String title() {
        final TitleBuffer buf = new TitleBuffer();
        buf.append(getDescription());
        if (isComplete()) {
            buf.append("- Completed!");
        }
        return buf.toString();
    }
    
    public String iconName() {
        return "ToDoItem-" + (!isComplete() ? "todo" : "done");
    }

    // //////////////////////////////////////
    // Description (property)
    // //////////////////////////////////////
    
    private String description;

    @javax.jdo.annotations.Column(allowsNull="false", length=100)
    @RegEx(validation = "\\w[@&:\\-\\,\\.\\+ \\w]*") 
    @TypicalLength(50)
    public String getDescription() {
        return description;
    }

    public void setDescription(final String description) {
        this.description = description;
    }



    // //////////////////////////////////////
    // OwnedBy (property)
    // //////////////////////////////////////
    
    private String ownedBy;

    @javax.jdo.annotations.Column(allowsNull="false")
    public String getOwnedBy() {
        return ownedBy;
    }

    public void setOwnedBy(final String ownedBy) {
        this.ownedBy = ownedBy;
    }


    // //////////////////////////////////////
    // Complete (property), 
    // Done (action), Undo (action)
    // //////////////////////////////////////

    private boolean complete;

    @Disabled
    public boolean isComplete() {
        return complete;
    }

    public void setComplete(final boolean complete) {
        this.complete = complete;
    }

    public ToDoItem completed() {
        setComplete(true);
        return this;
    }
    // disable action dependent on state of object
    public String disableCompleted() {
        return isComplete() ? "Already completed" : null;
    }

    public ToDoItem notYetCompleted() {
        setComplete(false);

        return this;
    }
    // disable action dependent on state of object
    public String disableNotYetCompleted() {
        return !complete ? "Not yet completed" : null;
    }


    // //////////////////////////////////////
    // DocumentationPage (property)
    // //////////////////////////////////////

    private String documentationPage;

    @javax.jdo.annotations.Column(allowsNull="true", length=255)
    public String getDocumentationPage() {
        return documentationPage;
    }

    public void setDocumentationPage(final String documentationPage) {
        this.documentationPage = documentationPage;
    }

    

    // //////////////////////////////////////
    // Notes (property)
    // //////////////////////////////////////
    
    private String notes;
    
    @javax.jdo.annotations.Column(allowsNull="true", length=400)
    public String getNotes() {
        return notes;
    }
    
    public void setNotes(final String notes) {
        this.notes = notes;
    }
    
    
    
    // //////////////////////////////////////
    // Delete (action)
    // //////////////////////////////////////

    @Bulk
    public List<ToDoItem> delete() {
        container.removeIfNotAlready(this);
        container.informUser("Deleted " + container.titleOf(this));
        // invalid to return 'this' (cannot render a deleted object)
        return toDoItems.notYetComplete(); 
    }

    


    // //////////////////////////////////////
    // Predicates
    // //////////////////////////////////////

    public static class Predicates {
        
        public static Predicate<ToDoItem> thoseOwnedBy(final String currentUser) {
            return new Predicate<ToDoItem>() {
                @Override
                public boolean apply(final ToDoItem toDoItem) {
                    return Objects.equal(toDoItem.getOwnedBy(), currentUser);
                }
            };
        }

		public static Predicate<ToDoItem> thoseCompleted(
				final boolean completed) {
            return new Predicate<ToDoItem>() {
                @Override
                public boolean apply(final ToDoItem t) {
                    return Objects.equal(t.isComplete(), completed);
                }
            };
		}

        public static Predicate<ToDoItem> thoseWithSimilarDescription(final String description) {
            return new Predicate<ToDoItem>() {
                @Override
                public boolean apply(final ToDoItem t) {
                    return t.getDescription().contains(description);
                }
            };
        }

        public static Predicate<ToDoItem> thoseNot(final ToDoItem toDoItem) {
            return new Predicate<ToDoItem>() {
                @Override
                public boolean apply(final ToDoItem t) {
                    return t != toDoItem;
                }
            };
        }

    }
    
    // //////////////////////////////////////
    // toString
    // //////////////////////////////////////

    @Override
    public String toString() {
        return ObjectContracts.toString(this, "description,complete,ownedBy");
    }
        
    // //////////////////////////////////////
    // compareTo
    // //////////////////////////////////////

    @Override
    public int compareTo(final ToDoItem other) {
        return ObjectContracts.compare(this, other, "complete,description");
    }

    // //////////////////////////////////////
    // Injected Services
    // //////////////////////////////////////

    @javax.inject.Inject
    private DomainObjectContainer container;

    @javax.inject.Inject
    private ToDoItems toDoItems;

    @javax.inject.Inject
    @SuppressWarnings("unused")
    private ClockService clockService;

}
