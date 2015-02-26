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
package org.isisaddons.wicket.gmap3.fixture.dom;

import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.VersionStrategy;
import com.google.common.base.Objects;
import com.google.common.base.Predicate;
import com.google.common.collect.Ordering;
import org.isisaddons.wicket.gmap3.cpt.applib.Locatable;
import org.isisaddons.wicket.gmap3.cpt.applib.Location;
import org.isisaddons.wicket.gmap3.cpt.service.LocationLookupService;
import org.apache.isis.applib.DomainObjectContainer;
import org.apache.isis.applib.annotation.BookmarkPolicy;
import org.apache.isis.applib.annotation.CollectionLayout;
import org.apache.isis.applib.annotation.DomainObject;
import org.apache.isis.applib.annotation.DomainObjectLayout;
import org.apache.isis.applib.annotation.Editing;
import org.apache.isis.applib.annotation.MemberOrder;
import org.apache.isis.applib.annotation.MinLength;
import org.apache.isis.applib.annotation.Optionality;
import org.apache.isis.applib.annotation.ParameterLayout;
import org.apache.isis.applib.annotation.Property;
import org.apache.isis.applib.annotation.PropertyLayout;
import org.apache.isis.applib.annotation.RenderType;
import org.apache.isis.applib.annotation.Where;
import org.apache.isis.applib.services.clock.ClockService;
import org.apache.isis.applib.util.ObjectContracts;
import org.apache.isis.applib.util.TitleBuffer;

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
                    + "FROM org.isisaddons.wicket.gmap3.fixture.dom.Gmap3WicketToDoItem "
                    + "WHERE ownedBy == :ownedBy"),
    @javax.jdo.annotations.Query(
            name = "todo_notYetComplete", language = "JDOQL",
            value = "SELECT "
                    + "FROM org.isisaddons.wicket.gmap3.fixture.dom.Gmap3WicketToDoItem "
                    + "WHERE ownedBy == :ownedBy "
                    + "   && complete == false"),
    @javax.jdo.annotations.Query(
            name = "todo_complete", language = "JDOQL",
            value = "SELECT "
                    + "FROM org.isisaddons.wicket.gmap3.fixture.dom.Gmap3WicketToDoItem "
                    + "WHERE ownedBy == :ownedBy "
                    + "&& complete == true"),
    @javax.jdo.annotations.Query(
            name = "todo_autoComplete", language = "JDOQL",
            value = "SELECT "
                    + "FROM org.isisaddons.wicket.gmap3.fixture.dom.Gmap3WicketToDoItem "
                    + "WHERE ownedBy == :ownedBy && "
                    + "description.indexOf(:description) >= 0")
})
@DomainObject(
        objectType = "TODO",
        autoCompleteRepository = Gmap3WicketToDoItems.class,
        autoCompleteAction = "autoComplete"
)
@DomainObjectLayout(
        named = "ToDo Item",
        bookmarking = BookmarkPolicy.AS_ROOT
)
public class Gmap3WicketToDoItem implements Comparable<Gmap3WicketToDoItem>, Locatable {

    //region > identification in the UI

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

    //endregion

    //region > description (property)

    private String description;

    @javax.jdo.annotations.Column(allowsNull="false", length=100)
    @MemberOrder(sequence="1")
    @Property(
            regexPattern = "\\w[@&:\\-\\,\\.\\+ \\w]*"
    )
    @PropertyLayout(
            typicalLength = 50
    )
    public String getDescription() {
        return description;
    }

    public void setDescription(final String description) {
        this.description = description;
    }

    //endregion

    //region > ownedBy (property)

    private String ownedBy;

    @javax.jdo.annotations.Column(allowsNull="false")
    @Property(
            hidden = Where.EVERYWHERE
    )
    public String getOwnedBy() {
        return ownedBy;
    }

    public void setOwnedBy(final String ownedBy) {
        this.ownedBy = ownedBy;
    }

    //endregion

    //region > complete (property), completed (action), notYetCompleted (action)
    
    private boolean complete;

    @Property(
            editing = Editing.DISABLED
    )
    @MemberOrder(sequence="2")
    public boolean isComplete() {
        return complete;
    }

    public void setComplete(final boolean complete) {
        this.complete = complete;
    }

    @MemberOrder(name="complete", sequence="1")
    public Gmap3WicketToDoItem completed() {
        setComplete(true);
        return this;
    }
    // disable action dependent on state of object
    public String disableCompleted() {
        return isComplete() ? "Already completed" : null;
    }

    @MemberOrder(name="complete", sequence="2")
    public Gmap3WicketToDoItem notYetCompleted() {
        setComplete(false);

        return this;
    }
    // disable action dependent on state of object
    public String disableNotYetCompleted() {
        return !complete ? "Not yet completed" : null;
    }

    //endregion

    //region > location

    private Double locationLatitude;
    private Double locationLongitude;

    @Property(
            optionality = Optionality.OPTIONAL
    )
    @MemberOrder(sequence="3")
    public Location getLocation() {
        return locationLatitude != null && locationLongitude != null? new Location(locationLatitude, locationLongitude): null;
    }
    public void setLocation(final Location location) {
        locationLongitude = location != null ? location.getLongitude() : null;
        locationLatitude = location != null ? location.getLatitude() : null;
    }

    @MemberOrder(name="location", sequence="1")
    public Gmap3WicketToDoItem updateLocation(
            @ParameterLayout(named="Address") final String address) {
        final Location location = this.locationLookupService.lookup(address);
        setLocation(location);
        return this;
    }

    //endregion

    //region > dependencies (collection), add (action), remove (action)

    // overrides the natural ordering
    public static class DependenciesComparator implements Comparator<Gmap3WicketToDoItem> {
        @Override
        public int compare(final Gmap3WicketToDoItem p, final Gmap3WicketToDoItem q) {
            final Ordering<Gmap3WicketToDoItem> byDescription = new Ordering<Gmap3WicketToDoItem>() {
                public int compare(final Gmap3WicketToDoItem p, final Gmap3WicketToDoItem q) {
                    return Ordering.natural().nullsFirst().compare(p.getDescription(), q.getDescription());
                }
            };
            return byDescription
                    .compound(Ordering.<Gmap3WicketToDoItem>natural())
                    .compare(p, q);
        }
    }



    @javax.jdo.annotations.Persistent(table="Gmap3WicketToDoItemDependencies")
    @javax.jdo.annotations.Join(column="dependingId")
    @javax.jdo.annotations.Element(column="dependentId")
    private SortedSet<Gmap3WicketToDoItem> dependencies = new TreeSet<>();

    @CollectionLayout(
            sortedBy = DependenciesComparator.class,
            render = RenderType.EAGERLY
    )
    public SortedSet<Gmap3WicketToDoItem> getDependencies() {
        return dependencies;
    }

    public void setDependencies(final SortedSet<Gmap3WicketToDoItem> dependencies) {
        this.dependencies = dependencies;
    }

    
    @MemberOrder(name="dependencies", sequence="1")
    public Gmap3WicketToDoItem add(final Gmap3WicketToDoItem toDoItem) {
        getDependencies().add(toDoItem);
        return this;
    }
    public List<Gmap3WicketToDoItem> autoComplete0Add(final @MinLength(2) String search) {
        final List<Gmap3WicketToDoItem> list = toDoItems.autoComplete(search);
        list.removeAll(getDependencies());
        list.remove(this);
        return list;
    }

    public String disableAdd(final Gmap3WicketToDoItem toDoItem) {
        if(isComplete()) {
            return "Cannot add dependencies for items that are complete";
        }
        return null;
    }
    // validate the provided argument prior to invoking action
    public String validateAdd(final Gmap3WicketToDoItem toDoItem) {
        if(getDependencies().contains(toDoItem)) {
            return "Already a dependency";
        }
        if(toDoItem == this) {
            return "Can't set up a dependency to self";
        }
        return null;
    }

    @MemberOrder(name="dependencies", sequence="2")
    public Gmap3WicketToDoItem remove(final Gmap3WicketToDoItem toDoItem) {
        getDependencies().remove(toDoItem);
        return this;
    }
    // disable action dependent on state of object
    public String disableRemove(final Gmap3WicketToDoItem toDoItem) {
        if(isComplete()) {
            return "Cannot remove dependencies for items that are complete";
        }
        return getDependencies().isEmpty()? "No dependencies to remove": null;
    }
    // validate the provided argument prior to invoking action
    public String validateRemove(final Gmap3WicketToDoItem toDoItem) {
        if(!getDependencies().contains(toDoItem)) {
            return "Not a dependency";
        }
        return null;
    }
    // provide a drop-down
    public Collection<Gmap3WicketToDoItem> choices0Remove() {
        return getDependencies();
    }

    //endregion

    //region > predicates

    public static class Predicates {
        
        public static Predicate<Gmap3WicketToDoItem> thoseOwnedBy(final String currentUser) {
            return new Predicate<Gmap3WicketToDoItem>() {
                @Override
                public boolean apply(final Gmap3WicketToDoItem toDoItem) {
                    return Objects.equal(toDoItem.getOwnedBy(), currentUser);
                }
            };
        }

        public static Predicate<Gmap3WicketToDoItem> thoseCompleted(
                final boolean completed) {
            return new Predicate<Gmap3WicketToDoItem>() {
                @Override
                public boolean apply(final Gmap3WicketToDoItem t) {
                    return Objects.equal(t.isComplete(), completed);
                }
            };
        }

        public static Predicate<Gmap3WicketToDoItem> thoseWithSimilarDescription(final String description) {
            return new Predicate<Gmap3WicketToDoItem>() {
                @Override
                public boolean apply(final Gmap3WicketToDoItem t) {
                    return t.getDescription().contains(description);
                }
            };
        }

        public static Predicate<Gmap3WicketToDoItem> thoseNot(final Gmap3WicketToDoItem toDoItem) {
            return new Predicate<Gmap3WicketToDoItem>() {
                @Override
                public boolean apply(final Gmap3WicketToDoItem t) {
                    return t != toDoItem;
                }
            };
        }

    }

    //endregion

    //region > toString, compareTo

    @Override
    public String toString() {
        return ObjectContracts.toString(this, "description,complete,ownedBy");
    }
        
    @Override
    public int compareTo(final Gmap3WicketToDoItem other) {
        return ObjectContracts.compare(this, other, "complete,description");
    }

    //endregion

    //region > injected services

    @javax.inject.Inject
    @SuppressWarnings("unused")
    private DomainObjectContainer container;

    @javax.inject.Inject
    private Gmap3WicketToDoItems toDoItems;

    @javax.inject.Inject
    @SuppressWarnings("unused")
    private ClockService clockService;

    @javax.inject.Inject
    private LocationLookupService locationLookupService;

    //endregion

}
