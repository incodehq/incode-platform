package domainapp.modules.exampledom.lib.excel.dom.demo;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;

import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.VersionStrategy;

import com.google.common.base.Objects;
import com.google.common.base.Predicate;
import com.google.common.collect.Ordering;

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
import org.apache.isis.applib.annotation.Optionality;
import org.apache.isis.applib.annotation.Parameter;
import org.apache.isis.applib.annotation.ParameterLayout;
import org.apache.isis.applib.annotation.Programmatic;
import org.apache.isis.applib.annotation.Property;
import org.apache.isis.applib.annotation.PropertyLayout;
import org.apache.isis.applib.clock.Clock;
import org.apache.isis.applib.util.ObjectContracts;
import org.apache.isis.applib.util.TitleBuffer;
import org.apache.isis.applib.value.Blob;

import org.isisaddons.module.excel.dom.ExcelService;

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
                    + "FROM org.isisaddons.module.excel.fixture.dom.ExcelModuleDemoToDoItem "
                    + "WHERE ownedBy == :ownedBy"),
    @javax.jdo.annotations.Query(
            name = "todo_notYetComplete", language = "JDOQL",
            value = "SELECT "
                    + "FROM org.isisaddons.module.excel.fixture.dom.ExcelModuleDemoToDoItem "
                    + "WHERE ownedBy == :ownedBy "
                    + "   && complete == false"),
    @javax.jdo.annotations.Query(
            name = "findByDescription", language = "JDOQL",
            value = "SELECT "
                    + "FROM org.isisaddons.module.excel.fixture.dom.ExcelModuleDemoToDoItem "
                    + "WHERE ownedBy == :ownedBy "
                    + "   && description == :description"),
    @javax.jdo.annotations.Query(
            name = "todo_complete", language = "JDOQL",
            value = "SELECT "
                    + "FROM org.isisaddons.module.excel.fixture.dom.ExcelModuleDemoToDoItem "
                    + "WHERE ownedBy == :ownedBy "
                    + "&& complete == true"),
    @javax.jdo.annotations.Query(
            name = "todo_similarTo", language = "JDOQL",
            value = "SELECT "
                    + "FROM org.isisaddons.module.excel.fixture.dom.ExcelModuleDemoToDoItem "
                    + "WHERE ownedBy == :ownedBy "
                    + "&& category == :category"),
    @javax.jdo.annotations.Query(
            name = "todo_autoComplete", language = "JDOQL",
            value = "SELECT "
                    + "FROM org.isisaddons.module.excel.fixture.dom.ExcelModuleDemoToDoItem "
                    + "WHERE ownedBy == :ownedBy && "
                    + "description.indexOf(:description) >= 0")
})
@DomainObject(
        objectType = "TODO",
        autoCompleteRepository = ExcelModuleDemoToDoItems.class,
        autoCompleteAction = "autoComplete"
)
@DomainObjectLayout(
        named = "To Do Item",
        bookmarking = BookmarkPolicy.AS_ROOT
)
public class ExcelModuleDemoToDoItem implements Comparable<ExcelModuleDemoToDoItem> {

    // //////////////////////////////////////
    // Identification in the UI
    // //////////////////////////////////////

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

    // //////////////////////////////////////
    // Description (property)
    // //////////////////////////////////////
    
    private String description;

    @javax.jdo.annotations.Column(allowsNull="false", length=100)
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

    // //////////////////////////////////////
    // DueBy (property)
    // //////////////////////////////////////

    @javax.jdo.annotations.Persistent(defaultFetchGroup="true")
    private LocalDate dueBy;

    @javax.jdo.annotations.Column(allowsNull="true")
    public LocalDate getDueBy() {
        return dueBy;
    }

    public void setDueBy(final LocalDate dueBy) {
        this.dueBy = dueBy;
    }
    public void clearDueBy() {
        setDueBy(null);
    }
    // proposed new value is validated before setting
    public String validateDueBy(final LocalDate dueBy) {
        if (dueBy == null) {
            return null;
        }
        return isMoreThanOneWeekInPast(dueBy) ? "Due by date cannot be more than one week old" : null;
    }

    // //////////////////////////////////////
    // Category and Subcategory (property)
    // //////////////////////////////////////

    public static enum Category {
        Professional {
            @Override
            public List<Subcategory> subcategories() {
                return Arrays.asList(null, Subcategory.OpenSource, Subcategory.Consulting, Subcategory.Education);
            }
        }, Domestic {
            @Override
            public List<Subcategory> subcategories() {
                return Arrays.asList(null, Subcategory.Shopping, Subcategory.Housework, Subcategory.Garden, Subcategory.Chores);
            }
        }, Other {
            @Override
            public List<Subcategory> subcategories() {
                return Arrays.asList(null, Subcategory.Other);
            }
        };
        
        public abstract List<Subcategory> subcategories();
    }

    public static enum Subcategory {
        // professional
        OpenSource, Consulting, Education, Marketing,
        // domestic
        Shopping, Housework, Garden, Chores,
        // other
        Other;

        public static List<Subcategory> listFor(final Category category) {
            return category != null? category.subcategories(): Collections.<Subcategory>emptyList();
        }

        public static String validate(final Category category, final Subcategory subcategory) {
            if(category == null) {
                return "Enter category first";
            }
            return !category.subcategories().contains(subcategory) 
                    ? "Invalid subcategory for category '" + category + "'" 
                    : null;
        }
        
        public static Predicate<Subcategory> thoseFor(final Category category) {
            return new Predicate<Subcategory>() {

                @Override
                public boolean apply(final Subcategory subcategory) {
                    return category.subcategories().contains(subcategory);
                }
            };
        }
    }

    // //////////////////////////////////////


    private Category category;

    @javax.jdo.annotations.Column(allowsNull="false")
    public Category getCategory() {
        return category;
    }

    public void setCategory(final Category category) {
        this.category = category;
    }

    // //////////////////////////////////////

    private Subcategory subcategory;

    @javax.jdo.annotations.Column(allowsNull="true")
    public Subcategory getSubcategory() {
        return subcategory;
    }
    public void setSubcategory(final Subcategory subcategory) {
        this.subcategory = subcategory;
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

    @Property(
            editing = Editing.DISABLED
    )
    public boolean isComplete() {
        return complete;
    }

    public void setComplete(final boolean complete) {
        this.complete = complete;
    }

    public ExcelModuleDemoToDoItem completed() {
        setComplete(true);
        return this;
    }
    // disable action dependent on state of object
    public String disableCompleted() {
        return isComplete() ? "Already completed" : null;
    }

    public ExcelModuleDemoToDoItem notYetCompleted() {
        setComplete(false);

        return this;
    }
    // disable action dependent on state of object
    public String disableNotYetCompleted() {
        return !complete ? "Not yet completed" : null;
    }

    // //////////////////////////////////////
    // Cost (property), UpdateCost (action)
    // //////////////////////////////////////

    private BigDecimal cost;

    @javax.jdo.annotations.Column(allowsNull="true", scale=2)
    @javax.validation.constraints.Digits(integer=10, fraction=2)
    @Property(
            editing = Editing.DISABLED,
            editingDisabledReason = "Update using action"
    )
    public BigDecimal getCost() {
        return cost;
    }

    public void setCost(final BigDecimal cost) {
        this.cost = cost!=null?cost.setScale(2, BigDecimal.ROUND_HALF_EVEN):null;
    }
    
    public ExcelModuleDemoToDoItem updateCost(
            @javax.validation.constraints.Digits(integer=10, fraction=2)
            @ParameterLayout(named="New cost") @Parameter(optionality = Optionality.OPTIONAL)
            final BigDecimal cost) {
        setCost(cost);
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
    // Attachment (property)
    // //////////////////////////////////////

    private Blob attachment;

    @javax.jdo.annotations.Persistent(defaultFetchGroup="false")
    @javax.jdo.annotations.Column(allowsNull="true", jdbcType="BLOB", sqlType="LONGBINARY")
    public Blob getAttachment() {
        return attachment;
    }

    public void setAttachment(final Blob attachment) {
        this.attachment = attachment;
    }

    // //////////////////////////////////////
    // Dependencies (collection), 
    // Add (action), Remove (action)
    // //////////////////////////////////////

    // overrides the natural ordering
    public static class DependenciesComparator implements Comparator<ExcelModuleDemoToDoItem> {
        @Override
        public int compare(final ExcelModuleDemoToDoItem p, final ExcelModuleDemoToDoItem q) {
            final Ordering<ExcelModuleDemoToDoItem> byDescription = new Ordering<ExcelModuleDemoToDoItem>() {
                public int compare(final ExcelModuleDemoToDoItem p, final ExcelModuleDemoToDoItem q) {
                    return Ordering.natural().nullsFirst().compare(p.getDescription(), q.getDescription());
                }
            };
            return byDescription
                    .compound(Ordering.<ExcelModuleDemoToDoItem>natural())
                    .compare(p, q);
        }
    }

    

    @javax.jdo.annotations.Persistent(table="ToDoItemDependencies")
    @javax.jdo.annotations.Join(column="dependingId")
    @javax.jdo.annotations.Element(column="dependentId")
    private SortedSet<ExcelModuleDemoToDoItem> dependencies = new TreeSet<>();

    @CollectionLayout(
            sortedBy = DependenciesComparator.class
    )
    public SortedSet<ExcelModuleDemoToDoItem> getDependencies() {
        return dependencies;
    }

    public void setDependencies(final SortedSet<ExcelModuleDemoToDoItem> dependencies) {
        this.dependencies = dependencies;
    }


    public ExcelModuleDemoToDoItem add(
            final ExcelModuleDemoToDoItem toDoItem) {
        getDependencies().add(toDoItem);
        return this;
    }
    public List<ExcelModuleDemoToDoItem> autoComplete0Add(final @MinLength(2) String search) {
        final List<ExcelModuleDemoToDoItem> list = toDoItems.autoComplete(search);
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
    public String validateAdd(final ExcelModuleDemoToDoItem toDoItem) {
        if(getDependencies().contains(toDoItem)) {
            return "Already a dependency";
        }
        if(toDoItem == this) {
            return "Can't set up a dependency to self";
        }
        return null;
    }

    // //////////////////////////////////////

    public ExcelModuleDemoToDoItem remove(
            final ExcelModuleDemoToDoItem toDoItem) {
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
    public String validateRemove(final ExcelModuleDemoToDoItem toDoItem) {
        if(!getDependencies().contains(toDoItem)) {
            return "Not a dependency";
        }
        return null;
    }
    // provide a drop-down
    public SortedSet<ExcelModuleDemoToDoItem> choices0Remove() {
        return getDependencies();
    }
    

    // //////////////////////////////////////
    // Delete (action)
    // //////////////////////////////////////

    @Action(
            invokeOn = InvokeOn.OBJECT_AND_COLLECTION
    )
    public List<ExcelModuleDemoToDoItem> delete() {
        container.removeIfNotAlready(this);
        container.informUser("Deleted " + container.titleOf(this));
        // invalid to return 'this' (cannot render a deleted object)
        return toDoItems.notYetComplete(); 
    }

    

    // //////////////////////////////////////
    // Programmatic Helpers
    // //////////////////////////////////////

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

    // //////////////////////////////////////
    // Predicates
    // //////////////////////////////////////

    public static class Predicates {
        
        public static Predicate<ExcelModuleDemoToDoItem> thoseOwnedBy(final String currentUser) {
            return new Predicate<ExcelModuleDemoToDoItem>() {
                @Override
                public boolean apply(final ExcelModuleDemoToDoItem toDoItem) {
                    return Objects.equal(toDoItem.getOwnedBy(), currentUser);
                }
            };
        }

        public static Predicate<ExcelModuleDemoToDoItem> thoseCompleted(
                final boolean completed) {
            return new Predicate<ExcelModuleDemoToDoItem>() {
                @Override
                public boolean apply(final ExcelModuleDemoToDoItem t) {
                    return Objects.equal(t.isComplete(), completed);
                }
            };
        }

        public static Predicate<ExcelModuleDemoToDoItem> thoseWithSimilarDescription(final String description) {
            return new Predicate<ExcelModuleDemoToDoItem>() {
                @Override
                public boolean apply(final ExcelModuleDemoToDoItem t) {
                    return t.getDescription().contains(description);
                }
            };
        }

        @SuppressWarnings("unchecked")
        public static Predicate<ExcelModuleDemoToDoItem> thoseSimilarTo(final ExcelModuleDemoToDoItem toDoItem) {
            return com.google.common.base.Predicates.and(
                    thoseNot(toDoItem),
                    thoseOwnedBy(toDoItem.getOwnedBy()),
                    thoseCategorised(toDoItem.getCategory()));
        }

        public static Predicate<ExcelModuleDemoToDoItem> thoseNot(final ExcelModuleDemoToDoItem toDoItem) {
            return new Predicate<ExcelModuleDemoToDoItem>() {
                @Override
                public boolean apply(final ExcelModuleDemoToDoItem t) {
                    return t != toDoItem;
                }
            };
        }

        public static Predicate<ExcelModuleDemoToDoItem> thoseCategorised(final Category category) {
            return new Predicate<ExcelModuleDemoToDoItem>() {
                @Override
                public boolean apply(final ExcelModuleDemoToDoItem toDoItem) {
                    return Objects.equal(toDoItem.getCategory(), category);
                }
            };
        }

        public static Predicate<ExcelModuleDemoToDoItem> thoseSubcategorised(
                final Subcategory subcategory) {
            return new Predicate<ExcelModuleDemoToDoItem>() {
                @Override
                public boolean apply(final ExcelModuleDemoToDoItem t) {
                    return Objects.equal(t.getSubcategory(), subcategory);
                }
            };
        }

        public static Predicate<ExcelModuleDemoToDoItem> thoseCategorised(
                final Category category, final Subcategory subcategory) {
            return com.google.common.base.Predicates.and(
                    thoseCategorised(category), 
                    thoseSubcategorised(subcategory)); 
        }

    }
    
    // //////////////////////////////////////
    // toString
    // //////////////////////////////////////

    @Override
    public String toString() {
        return ObjectContracts.toString(this, "description","complete","dueBy","ownedBy");
    }
        
    // //////////////////////////////////////
    // compareTo
    // //////////////////////////////////////

    /**
     * Required so can store in {@link SortedSet sorted set}s (eg {@link #getDependencies()}). 
     */
    @Override
    public int compareTo(final ExcelModuleDemoToDoItem other) {
        return ObjectContracts.compare(this, other, "complete","dueBy","description");
    }

    // //////////////////////////////////////
    // Injected Services
    // //////////////////////////////////////

    @javax.inject.Inject
    private DomainObjectContainer container;

    @javax.inject.Inject
    private ExcelModuleDemoToDoItems toDoItems;

    @javax.inject.Inject
    private ExcelService excelService;


}
