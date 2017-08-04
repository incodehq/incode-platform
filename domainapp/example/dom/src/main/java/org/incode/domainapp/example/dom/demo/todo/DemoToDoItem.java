package org.incode.domainapp.example.dom.demo.todo;

import java.math.BigDecimal;
import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;

import javax.annotation.Nullable;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.VersionStrategy;

import com.google.common.base.Objects;
import com.google.common.base.Predicate;

import org.joda.time.LocalDate;

import org.apache.isis.applib.DomainObjectContainer;
import org.apache.isis.applib.annotation.Action;
import org.apache.isis.applib.annotation.BookmarkPolicy;
import org.apache.isis.applib.annotation.CollectionLayout;
import org.apache.isis.applib.annotation.DomainObject;
import org.apache.isis.applib.annotation.DomainObjectLayout;
import org.apache.isis.applib.annotation.Editing;
import org.apache.isis.applib.annotation.InvokeOn;
import org.apache.isis.applib.annotation.MinLength;
import org.apache.isis.applib.annotation.Programmatic;
import org.apache.isis.applib.annotation.Property;
import org.apache.isis.applib.annotation.SemanticsOf;
import org.apache.isis.applib.clock.Clock;
import org.apache.isis.applib.util.ObjectContracts;
import org.apache.isis.applib.util.TitleBuffer;
import org.apache.isis.applib.value.Blob;

import org.isisaddons.module.excel.dom.ExcelService;

import lombok.Getter;
import lombok.Setter;

@javax.jdo.annotations.PersistenceCapable(
        identityType=IdentityType.DATASTORE,
        schema = "exampleDemo"
)
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
                    + "FROM org.incode.domainapp.example.dom.demo.todo.DemoToDoItem "
                    + "WHERE ownedBy == :ownedBy"),
    @javax.jdo.annotations.Query(
            name = "todo_notYetComplete", language = "JDOQL",
            value = "SELECT "
                    + "FROM org.incode.domainapp.example.dom.demo.todo.DemoToDoItem "
                    + "WHERE ownedBy == :ownedBy "
                    + "   && complete == false"),
    @javax.jdo.annotations.Query(
            name = "findByDescription", language = "JDOQL",
            value = "SELECT "
                    + "FROM org.incode.domainapp.example.dom.demo.todo.DemoToDoItem "
                    + "WHERE ownedBy == :ownedBy "
                    + "   && description == :description"),
    @javax.jdo.annotations.Query(
            name = "todo_complete", language = "JDOQL",
            value = "SELECT "
                    + "FROM org.incode.domainapp.example.dom.demo.todo.DemoToDoItem "
                    + "WHERE ownedBy == :ownedBy "
                    + "&& complete == true"),
    @javax.jdo.annotations.Query(
            name = "todo_similarTo", language = "JDOQL",
            value = "SELECT "
                    + "FROM org.incode.domainapp.example.dom.demo.todo.DemoToDoItem "
                    + "WHERE ownedBy == :ownedBy "
                    + "&& category == :category"),
    @javax.jdo.annotations.Query(
            name = "todo_autoComplete", language = "JDOQL",
            value = "SELECT "
                    + "FROM org.incode.domainapp.example.dom.demo.todo.DemoToDoItem "
                    + "WHERE ownedBy == :ownedBy && "
                    + "description.indexOf(:description) >= 0")
})
@DomainObject(
        autoCompleteRepository = DemoToDoItemMenu.class
)
@DomainObjectLayout(
        named = "To Do Item",
        bookmarking = BookmarkPolicy.AS_ROOT
)
public class DemoToDoItem implements Comparable<DemoToDoItem> {

    //region > title, iconName

    public String title() {
        final TitleBuffer buf = new TitleBuffer();
        buf.append(getDescription());
        if (isComplete()) {
            buf.append("- Completed!");
        } else {
            if (getDueBy() != null) {
                buf.append(" due by", getDueBy());
            }
        }
        return buf.toString();
    }
    
    public String iconName() {
        return "ExcelModuleDemoToDoItem-" + (!isComplete() ? "todo" : "done");
    }

    //endregion

    @javax.jdo.annotations.Column(allowsNull="false", length=100)
    @Property(regexPattern = "\\w[@&:\\-\\,\\.\\+ \\w]*")
    @Getter @Setter
    private String description;

    @javax.jdo.annotations.Persistent(defaultFetchGroup="true")
    @javax.jdo.annotations.Column(allowsNull="true")
    @Getter @Setter
    private LocalDate dueBy;

    @javax.jdo.annotations.Column(allowsNull="false")
    @Getter @Setter
    private Category category;

    @javax.jdo.annotations.Column(allowsNull="true")
    @Getter @Setter
    private Subcategory subcategory;

    @javax.jdo.annotations.Column(allowsNull="false")
    @Getter @Setter
    private String ownedBy;

    @Property(editing = Editing.DISABLED)
    @Getter @Setter
    private boolean complete;
    public boolean isComplete() {
        return complete;
    }

    @javax.jdo.annotations.Column(allowsNull="true", scale=2)
    @javax.validation.constraints.Digits(integer=10, fraction=2)
    @Property(editing = Editing.DISABLED, editingDisabledReason = "Update using action")
    @Getter @Setter
    private BigDecimal cost;

    @Getter @Setter
    @javax.jdo.annotations.Column(allowsNull="true", length=400)
    private String notes;

    @Getter @Setter
    @javax.jdo.annotations.Persistent(defaultFetchGroup="false")
    @javax.jdo.annotations.Column(allowsNull="true", jdbcType="BLOB", sqlType="LONGBINARY")
    private Blob attachment;

    @Getter @Setter
    @javax.jdo.annotations.Persistent(table="ToDoItemDependencies")
    @javax.jdo.annotations.Join(column="dependingId")
    @javax.jdo.annotations.Element(column="dependentId")
    @CollectionLayout(sortedBy = DependenciesComparator.class)
    private SortedSet<DemoToDoItem> dependencies = new TreeSet<>();


    public String validateDueBy(final LocalDate dueBy) {
        if (dueBy == null) {
            return null;
        }
        return isMoreThanOneWeekInPast(dueBy) ? "Due by date cannot be more than one week old" : null;
    }


    //region > completed (action)

    public DemoToDoItem completed() {
        setComplete(true);
        return this;
    }
    public String disableCompleted() {
        return isComplete() ? "Already completed" : null;
    }

    //endregion

    //region > notYetCompleted (action)

    public DemoToDoItem notYetCompleted() {
        setComplete(false);

        return this;
    }
    // disable action dependent on state of object
    public String disableNotYetCompleted() {
        return !complete ? "Not yet completed" : null;
    }

    //endregion

    //region > updateCost (action)


    public DemoToDoItem updateCost(
            @javax.validation.constraints.Digits(integer=10, fraction=2)
            @Nullable
            final BigDecimal newCost) {
        setCost(newCost);
        return this;
    }

    // provide a default value for argument #0
    public BigDecimal default0UpdateCost() {
        return getCost();
    }

    // validate action arguments
    public String validateUpdateCost(final BigDecimal proposedCost) {
        if(proposedCost == null) { return null; }
        return proposedCost.compareTo(BigDecimal.ZERO) < 0? "Cost must be positive": null;
    }

    //endregion

    //region > add (action)


    public DemoToDoItem add(
            final DemoToDoItem toDoItem) {
        getDependencies().add(toDoItem);
        return this;
    }
    public List<DemoToDoItem> autoComplete0Add(final @MinLength(2) String search) {
        final List<DemoToDoItem> list = toDoItems.autoComplete(search);
        list.removeAll(getDependencies());
        list.remove(this);
        return list;
    }

    public String disableAdd() {
        if(isComplete()) {
            return "Cannot add dependencies for items that are complete";
        }
        return null;
    }
    // validate the provided argument prior to invoking action
    public String validateAdd(final DemoToDoItem toDoItem) {
        if(getDependencies().contains(toDoItem)) {
            return "Already a dependency";
        }
        if(toDoItem == this) {
            return "Can't set up a dependency to self";
        }
        return null;
    }
    //endregion

    //region > remove (action)

    public DemoToDoItem remove(
            final DemoToDoItem toDoItem) {
        getDependencies().remove(toDoItem);
        return this;
    }
    // disable action dependent on state of object
    public String disableRemove() {
        if(isComplete()) {
            return "Cannot remove dependencies for items that are complete";
        }
        return getDependencies().isEmpty()? "No dependencies to remove": null;
    }
    // validate the provided argument prior to invoking action
    public String validateRemove(final DemoToDoItem toDoItem) {
        if(!getDependencies().contains(toDoItem)) {
            return "Not a dependency";
        }
        return null;
    }
    // provide a drop-down
    public SortedSet<DemoToDoItem> choices0Remove() {
        return getDependencies();
    }

    //endregion

    //region > delete (action)

    @Action(
            invokeOn = InvokeOn.OBJECT_AND_COLLECTION,
            semantics = SemanticsOf.IDEMPOTENT_ARE_YOU_SURE
    )
    public List<DemoToDoItem> delete() {
        container.removeIfNotAlready(this);
        container.informUser("Deleted " + container.titleOf(this));
        // invalid to return 'this' (cannot render a deleted object)
        return toDoItems.notYetComplete(); 
    }

    //endregion

    //region > Programmatic Helpers
    private static final long ONE_WEEK_IN_MILLIS = 7 * 24 * 60 * 60 * 1000L;

    @Programmatic // excluded from the framework's metamodel
    public boolean isDue() {
        if (getDueBy() == null) {
            return false;
        }
        return !isMoreThanOneWeekInPast(getDueBy());
    }

    private static boolean isMoreThanOneWeekInPast(final LocalDate dueBy) {
        return dueBy.toDateTimeAtStartOfDay().getMillis() < Clock.getTime() - ONE_WEEK_IN_MILLIS;
    }

    //endregion

    public static class Predicates {
        
        public static Predicate<DemoToDoItem> thoseOwnedBy(final String currentUser) {
            return toDoItem -> Objects.equal(toDoItem.getOwnedBy(), currentUser);
        }

        public static Predicate<DemoToDoItem> thoseCompleted(
                final boolean completed) {
            return t -> Objects.equal(t.isComplete(), completed);
        }

        public static Predicate<DemoToDoItem> thoseWithSimilarDescription(final String description) {
            return t -> t.getDescription().contains(description);
        }

        @SuppressWarnings("unchecked")
        public static Predicate<DemoToDoItem> thoseSimilarTo(final DemoToDoItem toDoItem) {
            return com.google.common.base.Predicates.and(
                    thoseNot(toDoItem),
                    thoseOwnedBy(toDoItem.getOwnedBy()),
                    thoseCategorised(toDoItem.getCategory()));
        }

        public static Predicate<DemoToDoItem> thoseNot(final DemoToDoItem toDoItem) {
            return t -> t != toDoItem;
        }

        public static Predicate<DemoToDoItem> thoseCategorised(final Category category) {
            return toDoItem -> Objects.equal(toDoItem.getCategory(), category);
        }

        public static Predicate<DemoToDoItem> thoseSubcategorised(
                final Subcategory subcategory) {
            return t -> Objects.equal(t.getSubcategory(), subcategory);
        }

        public static Predicate<DemoToDoItem> thoseCategorised(
                final Category category, final Subcategory subcategory) {
            return com.google.common.base.Predicates.and(
                    thoseCategorised(category), 
                    thoseSubcategorised(subcategory)); 
        }

    }

    //region > toString,compareTo
    @Override
    public String toString() {
        return ObjectContracts.toString(this, "description","complete","dueBy","ownedBy");
    }
        

    @Override
    public int compareTo(final DemoToDoItem other) {
        return ObjectContracts.compare(this, other, "complete","dueBy","description");
    }

    //endregion

    //region > dependencies

    @javax.inject.Inject
    private DomainObjectContainer container;

    @javax.inject.Inject
    private DemoToDoItemMenu toDoItems;

    @javax.inject.Inject
    private ExcelService excelService;

    //endregion

}
