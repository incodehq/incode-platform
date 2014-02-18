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

import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.VersionStrategy;

import com.danhaywood.isis.wicket.fullcalendar2.applib.CalendarEvent;
import com.danhaywood.isis.wicket.fullcalendar2.applib.CalendarEventable;
import com.danhaywood.isis.wicket.fullcalendar2.applib.Calendarable;
import com.google.common.base.Objects;
import com.google.common.base.Predicate;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Ordering;
import com.google.common.collect.Sets;

import org.joda.time.LocalDate;

import org.apache.isis.applib.DomainObjectContainer;
import org.apache.isis.applib.annotation.AutoComplete;
import org.apache.isis.applib.annotation.Bookmarkable;
import org.apache.isis.applib.annotation.Disabled;
import org.apache.isis.applib.annotation.Hidden;
import org.apache.isis.applib.annotation.MemberOrder;
import org.apache.isis.applib.annotation.MinLength;
import org.apache.isis.applib.annotation.ObjectType;
import org.apache.isis.applib.annotation.Programmatic;
import org.apache.isis.applib.annotation.RegEx;
import org.apache.isis.applib.annotation.Render;
import org.apache.isis.applib.annotation.Render.Type;
import org.apache.isis.applib.annotation.SortedBy;
import org.apache.isis.applib.annotation.TypicalLength;
import org.apache.isis.applib.util.ObjectContracts;
import org.apache.isis.applib.util.TitleBuffer;

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
            name = "todo_autoComplete", language = "JDOQL",
            value = "SELECT "
                    + "FROM dom.todo.ToDoItem "
                    + "WHERE ownedBy == :ownedBy && "
                    + "description.indexOf(:description) >= 0")
})
@ObjectType("TODO")
@AutoComplete(repository=ToDoItems.class, action="autoComplete")
@Bookmarkable
public class ToDoItem implements Comparable<ToDoItem>, CalendarEventable {


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
    @MemberOrder(sequence="1")
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
    @Hidden
    public String getOwnedBy() {
        return ownedBy;
    }

    public void setOwnedBy(final String ownedBy) {
        this.ownedBy = ownedBy;
    }


    // //////////////////////////////////////
    // DueBy (property)
    // //////////////////////////////////////

    private LocalDate dueBy;

    @javax.jdo.annotations.Persistent(defaultFetchGroup="true")
    @javax.jdo.annotations.Column(allowsNull="true")
    @MemberOrder(sequence="2")
    public LocalDate getDueBy() {
        return dueBy;
    }

    public void setDueBy(final LocalDate dueBy) {
        this.dueBy = dueBy;
    }
    public void clearDueBy() {
        setDueBy(null);
    }

    
    
    // //////////////////////////////////////
    // Complete (property), 
    // Done (action), Undo (action)
    // //////////////////////////////////////

    private boolean complete;

    @Disabled
    @MemberOrder(sequence="4")
    public boolean isComplete() {
        return complete;
    }

    public void setComplete(final boolean complete) {
        this.complete = complete;
    }

    @MemberOrder(name="complete", sequence="1")
    public ToDoItem completed() {
        setComplete(true);
        return this;
    }
    // disable action dependent on state of object
    public String disableCompleted() {
        return isComplete() ? "Already completed" : null;
    }

    @MemberOrder(name="complete", sequence="2")
    public ToDoItem notYetCompleted() {
        setComplete(false);

        return this;
    }
    // disable action dependent on state of object
    public String disableNotYetCompleted() {
        return !complete ? "Not yet completed" : null;
    }


    // //////////////////////////////////////
    // Category (property)
    // //////////////////////////////////////

    public static enum Category {
        Professional,
        Domestic, 
        Other;
    }
    
    private Category category;

    @javax.jdo.annotations.Column(allowsNull="false")
    @MemberOrder(sequence="3")
    public Category getCategory() {
        return category;
    }

    public void setCategory(final Category category) {
        this.category = category;
    }


    // //////////////////////////////////////
    // Dependencies (collection), 
    // Add (action), Remove (action)
    // //////////////////////////////////////

    // overrides the natural ordering
    public static class DependenciesComparator implements Comparator<ToDoItem> {
        @Override
        public int compare(ToDoItem p, ToDoItem q) {
            Ordering<ToDoItem> byDescription = new Ordering<ToDoItem>() {
                public int compare(final ToDoItem p, final ToDoItem q) {
                    return Ordering.natural().nullsFirst().compare(p.getDescription(), q.getDescription());
                }
            };
            return byDescription
                    .compound(Ordering.<ToDoItem>natural())
                    .compare(p, q);
        }
    }

    

    @javax.jdo.annotations.Persistent(table="ToDoItemDependencies")
    @javax.jdo.annotations.Join(column="dependingId")
    @javax.jdo.annotations.Element(column="dependentId")
    private SortedSet<ToDoItem> dependencies = new TreeSet<ToDoItem>();

    @SortedBy(DependenciesComparator.class)
    @Render(Type.EAGERLY)
    public SortedSet<ToDoItem> getDependencies() {
        return dependencies;
    }

    public void setDependencies(final SortedSet<ToDoItem> dependencies) {
        this.dependencies = dependencies;
    }

    
    @MemberOrder(name="dependencies", sequence="1")
    public ToDoItem add(
            @TypicalLength(20)
            final ToDoItem toDoItem) {
        getDependencies().add(toDoItem);
        return this;
    }
    public List<ToDoItem> autoComplete0Add(final @MinLength(2) String search) {
        final List<ToDoItem> list = toDoItems.autoComplete(search);
        list.removeAll(getDependencies());
        list.remove(this);
        return list;
    }

    public String disableAdd(final ToDoItem toDoItem) {
        if(isComplete()) {
            return "Cannot add dependencies for items that are complete";
        }
        return null;
    }
    // validate the provided argument prior to invoking action
    public String validateAdd(final ToDoItem toDoItem) {
        if(getDependencies().contains(toDoItem)) {
            return "Already a dependency";
        }
        if(toDoItem == this) {
            return "Can't set up a dependency to self";
        }
        return null;
    }

    @MemberOrder(name="dependencies", sequence="2")
    public ToDoItem remove(
            @TypicalLength(20)
            final ToDoItem toDoItem) {
        getDependencies().remove(toDoItem);
        return this;
    }
    // disable action dependent on state of object
    public String disableRemove(final ToDoItem toDoItem) {
        if(isComplete()) {
            return "Cannot remove dependencies for items that are complete";
        }
        return getDependencies().isEmpty()? "No dependencies to remove": null;
    }
    // validate the provided argument prior to invoking action
    public String validateRemove(final ToDoItem toDoItem) {
        if(!getDependencies().contains(toDoItem)) {
            return "Not a dependency";
        }
        return null;
    }
    // provide a drop-down
    public Collection<ToDoItem> choices0Remove() {
        return getDependencies();
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
    // CalendarEventable impl
    // //////////////////////////////////////
    
    @Programmatic
    @Override
    public String getCalendarName() {
        return getCategory().name();
    }

    @Programmatic
    @Override
    public CalendarEvent toCalendarEvent() {
        if(getDueBy() == null) {
            return null;
        }
        return new CalendarEvent(getDueBy().toDateTimeAtStartOfDay(), getCalendarName(), container.titleOf(this));
    }

    
    // //////////////////////////////////////
    // Injected Services
    // //////////////////////////////////////

    @javax.inject.Inject
    @SuppressWarnings("unused")
    private DomainObjectContainer container;

    @javax.inject.Inject
    private ToDoItems toDoItems;

    @javax.inject.Inject
    @SuppressWarnings("unused")
    private ClockService clockService;

    

    
    
}
