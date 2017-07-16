package domainapp.modules.exampledom.wicket.fullcalendar2.dom.demo;

import java.util.Comparator;
import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.VersionStrategy;
import com.google.common.base.Objects;
import com.google.common.base.Predicate;
import com.google.common.collect.Ordering;
import org.isisaddons.wicket.fullcalendar2.cpt.applib.CalendarEvent;
import org.isisaddons.wicket.fullcalendar2.cpt.applib.CalendarEventable;
import org.joda.time.LocalDate;
import org.apache.isis.applib.DomainObjectContainer;
import org.apache.isis.applib.annotation.BookmarkPolicy;
import org.apache.isis.applib.annotation.CollectionLayout;
import org.apache.isis.applib.annotation.DomainObject;
import org.apache.isis.applib.annotation.DomainObjectLayout;
import org.apache.isis.applib.annotation.Editing;
import org.apache.isis.applib.annotation.MemberOrder;
import org.apache.isis.applib.annotation.MinLength;
import org.apache.isis.applib.annotation.Programmatic;
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
                    + "FROM org.isisaddons.wicket.fullcalendar2.fixture.dom.FullCalendar2WicketToDoItem "
                    + "WHERE ownedBy == :ownedBy"),
    @javax.jdo.annotations.Query(
            name = "todo_notYetComplete", language = "JDOQL",
            value = "SELECT "
                    + "FROM org.isisaddons.wicket.fullcalendar2.fixture.dom.FullCalendar2WicketToDoItem "
                    + "WHERE ownedBy == :ownedBy "
                    + "   && complete == false"),
    @javax.jdo.annotations.Query(
            name = "todo_complete", language = "JDOQL",
            value = "SELECT "
                    + "FROM org.isisaddons.wicket.fullcalendar2.fixture.dom.FullCalendar2WicketToDoItem "
                    + "WHERE ownedBy == :ownedBy "
                    + "&& complete == true"),
    @javax.jdo.annotations.Query(
            name = "todo_autoComplete", language = "JDOQL",
            value = "SELECT "
                    + "FROM org.isisaddons.wicket.fullcalendar2.fixture.dom.FullCalendar2WicketToDoItem "
                    + "WHERE ownedBy == :ownedBy && "
                    + "description.indexOf(:description) >= 0")
})
@DomainObject(
        objectType = "TODO",
        autoCompleteRepository = FullCalendar2WicketToDoItems.class,
        autoCompleteAction = "autoComplete"
)
@DomainObjectLayout(
        named = "ToDo Item",
        bookmarking = BookmarkPolicy.AS_ROOT
)
public class FullCalendar2WicketToDoItem implements Comparable<FullCalendar2WicketToDoItem>, CalendarEventable {

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
    @Property(
            regexPattern = "\\w[@&:\\-\\,\\.\\+ \\w]*"
    )
    @PropertyLayout(
            typicalLength = 50
    )
    @MemberOrder(sequence="1")
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

    //region > dueBy (property)

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

    //endregion

    //region > complete (property), completed (action), notYetCompleted (action)

    private boolean complete;

    @Property(
            editing = Editing.DISABLED
    )
    @MemberOrder(sequence="4")
    public boolean isComplete() {
        return complete;
    }

    public void setComplete(final boolean complete) {
        this.complete = complete;
    }

    @MemberOrder(name="complete", sequence="1")
    public FullCalendar2WicketToDoItem completed() {
        setComplete(true);
        return this;
    }
    // disable action dependent on state of object
    public String disableCompleted() {
        return isComplete() ? "Already completed" : null;
    }

    @MemberOrder(name="complete", sequence="2")
    public FullCalendar2WicketToDoItem notYetCompleted() {
        setComplete(false);

        return this;
    }
    // disable action dependent on state of object
    public String disableNotYetCompleted() {
        return !complete ? "Not yet completed" : null;
    }

    //endregion

    //region > category (property)
    
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

    //endregion

    //region > dependencies (collection), add (action), remove (action)

    // overrides the natural ordering
    public static class DependenciesComparator implements Comparator<FullCalendar2WicketToDoItem> {
        @Override
        public int compare(final FullCalendar2WicketToDoItem p, final FullCalendar2WicketToDoItem q) {
            final Ordering<FullCalendar2WicketToDoItem> byDescription = new Ordering<FullCalendar2WicketToDoItem>() {
                public int compare(final FullCalendar2WicketToDoItem p, final FullCalendar2WicketToDoItem q) {
                    return Ordering.natural().nullsFirst().compare(p.getDescription(), q.getDescription());
                }
            };
            return byDescription
                    .compound(Ordering.<FullCalendar2WicketToDoItem>natural())
                    .compare(p, q);
        }
    }

    

    @javax.jdo.annotations.Persistent(table="FullCalendar2WicketToDoItemDependencies")
    @javax.jdo.annotations.Join(column="dependingId")
    @javax.jdo.annotations.Element(column="dependentId")
    private SortedSet<FullCalendar2WicketToDoItem> dependencies = new TreeSet<>();

    @CollectionLayout(
            sortedBy = DependenciesComparator.class,
            render = RenderType.EAGERLY
    )
    public SortedSet<FullCalendar2WicketToDoItem> getDependencies() {
        return dependencies;
    }

    public void setDependencies(final SortedSet<FullCalendar2WicketToDoItem> dependencies) {
        this.dependencies = dependencies;
    }

    
    @MemberOrder(name="dependencies", sequence="1")
    public FullCalendar2WicketToDoItem add(final FullCalendar2WicketToDoItem toDoItem) {
        getDependencies().add(toDoItem);
        return this;
    }
    public List<FullCalendar2WicketToDoItem> autoComplete0Add(final @MinLength(2) String search) {
        final List<FullCalendar2WicketToDoItem> list = toDoItems.autoComplete(search);
        list.removeAll(getDependencies());
        list.remove(this);
        return list;
    }

    public String disableAdd(final FullCalendar2WicketToDoItem toDoItem) {
        if(isComplete()) {
            return "Cannot add dependencies for items that are complete";
        }
        return null;
    }
    // validate the provided argument prior to invoking action
    public String validateAdd(final FullCalendar2WicketToDoItem toDoItem) {
        if(getDependencies().contains(toDoItem)) {
            return "Already a dependency";
        }
        if(toDoItem == this) {
            return "Can't set up a dependency to self";
        }
        return null;
    }

    @MemberOrder(name="dependencies", sequence="2")
    public FullCalendar2WicketToDoItem remove(final FullCalendar2WicketToDoItem toDoItem) {
        getDependencies().remove(toDoItem);
        return this;
    }
    // disable action dependent on state of object
    public String disableRemove(final FullCalendar2WicketToDoItem toDoItem) {
        if(isComplete()) {
            return "Cannot remove dependencies for items that are complete";
        }
        return getDependencies().isEmpty()? "No dependencies to remove": null;
    }
    // validate the provided argument prior to invoking action
    public String validateRemove(final FullCalendar2WicketToDoItem toDoItem) {
        if(!getDependencies().contains(toDoItem)) {
            return "Not a dependency";
        }
        return null;
    }
    // provide a drop-down
    public java.util.Collection<FullCalendar2WicketToDoItem> choices0Remove() {
        return getDependencies();
    }

    //endregion

    //region > Predicates

    public static class Predicates {
        
        public static Predicate<FullCalendar2WicketToDoItem> thoseOwnedBy(final String currentUser) {
            return new Predicate<FullCalendar2WicketToDoItem>() {
                @Override
                public boolean apply(final FullCalendar2WicketToDoItem toDoItem) {
                    return Objects.equal(toDoItem.getOwnedBy(), currentUser);
                }
            };
        }

        public static Predicate<FullCalendar2WicketToDoItem>thoseCompleted(
            final boolean completed) {
            return new Predicate<FullCalendar2WicketToDoItem>() {
                @Override
                public boolean apply(final FullCalendar2WicketToDoItem t) {
                    return Objects.equal(t.isComplete(), completed);
                }
            };
        }

        public static Predicate<FullCalendar2WicketToDoItem> thoseWithSimilarDescription(final String description) {
            return new Predicate<FullCalendar2WicketToDoItem>() {
                @Override
                public boolean apply(final FullCalendar2WicketToDoItem t) {
                    return t.getDescription().contains(description);
                }
            };
        }

        public static Predicate<FullCalendar2WicketToDoItem> thoseNot(final FullCalendar2WicketToDoItem toDoItem) {
            return new Predicate<FullCalendar2WicketToDoItem>() {
                @Override
                public boolean apply(final FullCalendar2WicketToDoItem t) {
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
    public int compareTo(final FullCalendar2WicketToDoItem other) {
        return ObjectContracts.compare(this, other, "complete,description");
    }

    //endregion

    //region > CalendarEventable impl

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

    //endregion

    //region > injected services

    @javax.inject.Inject
    @SuppressWarnings("unused")
    private DomainObjectContainer container;

    @javax.inject.Inject
    private FullCalendar2WicketToDoItems toDoItems;

    @javax.inject.Inject
    @SuppressWarnings("unused")
    private ClockService clockService;

    //endregion

}
