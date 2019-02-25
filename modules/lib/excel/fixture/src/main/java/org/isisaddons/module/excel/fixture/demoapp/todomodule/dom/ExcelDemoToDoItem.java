package org.isisaddons.module.excel.fixture.demoapp.todomodule.dom;

import java.math.BigDecimal;
import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.function.Predicate;

import javax.annotation.Nullable;
import javax.inject.Inject;
import javax.jdo.annotations.Column;
import javax.jdo.annotations.DatastoreIdentity;
import javax.jdo.annotations.Element;
import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.Join;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.Queries;
import javax.jdo.annotations.Query;
import javax.jdo.annotations.Unique;
import javax.jdo.annotations.Uniques;
import javax.jdo.annotations.Version;
import javax.jdo.annotations.VersionStrategy;
import javax.validation.constraints.Digits;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import com.google.common.base.Objects;
import com.google.common.collect.ComparisonChain;

import org.joda.time.LocalDate;

import org.apache.isis.applib.annotation.Action;
import org.apache.isis.applib.annotation.BookmarkPolicy;
import org.apache.isis.applib.annotation.CollectionLayout;
import org.apache.isis.applib.annotation.DomainObject;
import org.apache.isis.applib.annotation.DomainObjectLayout;
import org.apache.isis.applib.annotation.Editing;
import org.apache.isis.applib.annotation.MinLength;
import org.apache.isis.applib.annotation.Property;
import org.apache.isis.applib.annotation.SemanticsOf;
import org.apache.isis.applib.clock.Clock;
import org.apache.isis.applib.services.message.MessageService;
import org.apache.isis.applib.services.repository.RepositoryService;
import org.apache.isis.applib.services.title.TitleService;
import org.apache.isis.applib.util.TitleBuffer;
import org.apache.isis.applib.value.Blob;
import org.apache.isis.schema.utils.jaxbadapters.PersistentEntityAdapter;

import lombok.Getter;
import lombok.Setter;

@PersistenceCapable(
        identityType=IdentityType.DATASTORE,
        schema = "libExcelFixture"
)
@DatastoreIdentity(
        strategy= IdGeneratorStrategy.IDENTITY,
         column="id")
@Version(
        strategy=VersionStrategy.VERSION_NUMBER, 
        column="version")
@Uniques({
    @Unique(
            name="ToDoItem_description_must_be_unique",
            members={"ownedBy","description"})
})
@Queries( {
    @Query(
            name = "todo_all", language = "JDOQL",
            value = "SELECT "
                    + "FROM org.isisaddons.module.excel.fixture.demoapp.todomodule.dom.ExcelDemoToDoItem "
                    + "WHERE ownedBy == :ownedBy"),
    @Query(
            name = "todo_notYetComplete", language = "JDOQL",
            value = "SELECT "
                    + "FROM org.isisaddons.module.excel.fixture.demoapp.todomodule.dom.ExcelDemoToDoItem "
                    + "WHERE ownedBy == :ownedBy "
                    + "   && complete == false"),
    @Query(
            name = "findByDescription", language = "JDOQL",
            value = "SELECT "
                    + "FROM org.isisaddons.module.excel.fixture.demoapp.todomodule.dom.ExcelDemoToDoItem "
                    + "WHERE ownedBy == :ownedBy "
                    + "   && description == :description"),
    @Query(
            name = "todo_complete", language = "JDOQL",
            value = "SELECT "
                    + "FROM org.isisaddons.module.excel.fixture.demoapp.todomodule.dom.ExcelDemoToDoItem "
                    + "WHERE ownedBy == :ownedBy "
                    + "&& complete == true"),
    @Query(
            name = "todo_similarTo", language = "JDOQL",
            value = "SELECT "
                    + "FROM org.isisaddons.module.excel.fixture.demoapp.todomodule.dom.ExcelDemoToDoItem "
                    + "WHERE ownedBy == :ownedBy "
                    + "&& category == :category"),
    @Query(
            name = "todo_autoComplete", language = "JDOQL",
            value = "SELECT "
                    + "FROM org.isisaddons.module.excel.fixture.demoapp.todomodule.dom.ExcelDemoToDoItem "
                    + "WHERE ownedBy == :ownedBy && "
                    + "description.indexOf(:description) >= 0")
})
@DomainObject(
        autoCompleteRepository = ExcelDemoToDoItemMenu.class
)
@DomainObjectLayout(
        named = "To Do Item",
        bookmarking = BookmarkPolicy.AS_ROOT
)
@XmlJavaTypeAdapter(PersistentEntityAdapter.class)
public class ExcelDemoToDoItem implements Comparable<ExcelDemoToDoItem> {

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

    @Column(allowsNull="false", length=100)
    @Property(regexPattern = "\\w[@&:\\-\\,\\.\\+ \\w]*")
    @Getter @Setter
    private String description;

    @Persistent(defaultFetchGroup="true")
    @Column(allowsNull="true")
    @Getter @Setter
    private LocalDate dueBy;

    @Column(allowsNull="true")
    @Getter @Setter
    private Category category;

    @Column(allowsNull="true")
    @Getter @Setter
    private Subcategory subcategory;

    @Column(allowsNull="false")
    @Getter @Setter
    private String ownedBy;

    @Property(editing = Editing.DISABLED)
    @Getter @Setter
    private boolean complete;
    public boolean isComplete() {
        return complete;
    }

    @Column(allowsNull="true", scale=2)
    @Digits(integer=10, fraction=2)
    @Property(editing = Editing.DISABLED, editingDisabledReason = "Update using action")
    @Getter @Setter
    private BigDecimal cost;

    @Column(allowsNull="true", scale=2)
    @Digits(integer=10, fraction=2)
    @Property(
            editing = Editing.DISABLED,
            editingDisabledReason = "Update using action"
    )
    @Getter @Setter
    private BigDecimal previousCost;


    @Getter @Setter
    @Column(allowsNull="true", length=400)
    private String notes;
    @Property(editing = Editing.ENABLED)
    public String getNotes() {
        return notes;
    }



    @Getter @Setter
    @Persistent(defaultFetchGroup="false")
    @Column(allowsNull="true", jdbcType="BLOB", sqlType="LONGBINARY")
    private Blob attachment;

    @Getter @Setter
    @Persistent(table="ExcelDemoToDoItemDependencies")
    @Join(column="dependingId")
    @Element(column="dependentId")
    @CollectionLayout(sortedBy = DependenciesComparator.class)
    private SortedSet<ExcelDemoToDoItem> dependencies = new TreeSet<>();



    // no getter/setter (therefore persisted but not part of Isis' metamodel)
    private Double locationLatitude;
    private Double locationLongitude;


    public String validateDueBy(final LocalDate dueBy) {
        if (dueBy == null) {
            return null;
        }
        return isMoreThanOneWeekInPast(dueBy) ? "Due by date cannot be more than one week old" : null;
    }




    //region > completed (action)

    public ExcelDemoToDoItem completed() {
        setComplete(true);
        return this;
    }
    public String disableCompleted() {
        return isComplete() ? "Already completed" : null;
    }

    //endregion

    //region > notYetCompleted (action)

    public ExcelDemoToDoItem notYetCompleted() {
        setComplete(false);

        return this;
    }
    // disable action dependent on state of object
    public String disableNotYetCompleted() {
        return !complete ? "Not yet completed" : null;
    }

    //endregion

    //region > updateCosts (action)
    public ExcelDemoToDoItem updateCosts(
            @Nullable
            @Digits(integer=10, fraction=2)
            final BigDecimal cost,
            @Nullable
            @Digits(integer=10, fraction=2)
            final BigDecimal previousCost
    ) {
        setCost(cost);
        setPreviousCost(previousCost);
        return this;
    }
    public BigDecimal default0UpdateCosts() {
        return getCost();
    }
    public BigDecimal default1UpdateCosts() {
        return getPreviousCost();
    }
    public String validateUpdateCosts(final BigDecimal proposedCost, final BigDecimal proposedPreviousCost) {
        if (proposedCost != null && proposedCost.compareTo(BigDecimal.ZERO) < 0) {
            return "Cost must be positive";
        }
        if (proposedPreviousCost != null && proposedPreviousCost.compareTo(BigDecimal.ZERO) < 0) {
            return "Previous cost must be positive";
        }
        return null;
    }
    //endregion


    //region > add (action)


    public ExcelDemoToDoItem add(
            final ExcelDemoToDoItem toDoItem) {
        getDependencies().add(toDoItem);
        return this;
    }
    public List<ExcelDemoToDoItem> autoComplete0Add(final @MinLength(2) String search) {
        final List<ExcelDemoToDoItem> list = toDoItems.autoComplete(search);
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
    public String validateAdd(final ExcelDemoToDoItem toDoItem) {
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

    public ExcelDemoToDoItem remove(
            final ExcelDemoToDoItem toDoItem) {
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
    public String validateRemove(final ExcelDemoToDoItem toDoItem) {
        if(!getDependencies().contains(toDoItem)) {
            return "Not a dependency";
        }
        return null;
    }
    // provide a drop-down
    public SortedSet<ExcelDemoToDoItem> choices0Remove() {
        return getDependencies();
    }

    //endregion

    //region > delete (action)

    @Action(
            semantics = SemanticsOf.IDEMPOTENT_ARE_YOU_SURE
    )
    public List<ExcelDemoToDoItem> delete() {
        repositoryService.remove(this);
        messageService.informUser("Deleted " + titleService.titleOf(this));
        // invalid to return 'this' (cannot render a deleted object)
        return toDoItems.toDoItemsNotYetComplete();
    }

    //endregion

    //region > Programmatic Helpers
    private static final long ONE_WEEK_IN_MILLIS = 7 * 24 * 60 * 60 * 1000L;

    private static boolean isMoreThanOneWeekInPast(final LocalDate dueBy) {
        return dueBy.toDateTimeAtStartOfDay().getMillis() < Clock.getTime() - ONE_WEEK_IN_MILLIS;
    }

    //endregion

    public static class Predicates {
        
        public static Predicate<ExcelDemoToDoItem> thoseOwnedBy(final String currentUser) {
            return toDoItem -> Objects.equal(toDoItem.getOwnedBy(), currentUser);
        }

        public static Predicate<ExcelDemoToDoItem> thoseCompleted(final boolean completed) {
            return t -> Objects.equal(t.isComplete(), completed);
        }

        public static Predicate<ExcelDemoToDoItem> thoseCategorised(final Category category) {
            return toDoItem -> Objects.equal(toDoItem.getCategory(), category);
        }

        public static Predicate<ExcelDemoToDoItem> thoseSubcategorised(
                final Subcategory subcategory) {
            return t -> Objects.equal(t.getSubcategory(), subcategory);
        }

        public static Predicate<ExcelDemoToDoItem> thoseCategorised(
                final Category category, final Subcategory subcategory) {
            return thoseCategorised(category).and(thoseSubcategorised(subcategory));
        }

    }

    //region > toString,compareTo

    @Override public String toString() {
        return "ExcelDemoToDoItem{" +
                "description='" + getDescription() + '\'' +
                ", dueBy=" + getDueBy() +
                ", ownedBy='" + getOwnedBy() + '\'' +
                ", complete=" + isComplete() +
                '}';
    }

    @Override
    public int compareTo(final ExcelDemoToDoItem other) {
        return ComparisonChain.start()
                .compareTrueFirst(this.isComplete(), other.isComplete())
                .compare(this.getDueBy(), other.getDueBy())
                .compare(this.getDescription(), other.getDescription())
                .result();
    }

    //endregion

    @Inject
    RepositoryService repositoryService;

    @Inject
    MessageService messageService;

    @Inject
    TitleService titleService;

    @Inject
    ExcelDemoToDoItemMenu toDoItems;



}
