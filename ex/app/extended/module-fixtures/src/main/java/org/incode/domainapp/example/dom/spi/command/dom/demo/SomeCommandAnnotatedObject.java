package org.incode.domainapp.example.dom.spi.command.dom.demo;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.VersionStrategy;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import org.apache.isis.applib.annotation.Action;
import org.apache.isis.applib.annotation.ActionLayout;
import org.apache.isis.applib.annotation.BookmarkPolicy;
import org.apache.isis.applib.annotation.CommandExecuteIn;
import org.apache.isis.applib.annotation.CommandPersistence;
import org.apache.isis.applib.annotation.CommandReification;
import org.apache.isis.applib.annotation.DomainObject;
import org.apache.isis.applib.annotation.DomainObjectLayout;
import org.apache.isis.applib.annotation.InvokeOn;
import org.apache.isis.applib.annotation.MemberOrder;
import org.apache.isis.applib.annotation.ParameterLayout;
import org.apache.isis.applib.annotation.Property;
import org.apache.isis.applib.annotation.SemanticsOf;
import org.apache.isis.applib.annotation.Title;
import org.apache.isis.applib.annotation.Where;
import org.apache.isis.applib.services.background.BackgroundService;
import org.apache.isis.applib.services.eventbus.ActionDomainEvent;
import org.apache.isis.applib.services.eventbus.ObjectUpdatedEvent;
import org.apache.isis.applib.services.eventbus.ObjectUpdatingEvent;
import org.apache.isis.applib.util.ObjectContracts;
import org.apache.isis.schema.utils.jaxbadapters.PersistentEntityAdapter;

import lombok.Getter;
import lombok.Setter;

@javax.jdo.annotations.PersistenceCapable(
        identityType=IdentityType.DATASTORE,
        schema = "exampleSpiCommand"
)
@javax.jdo.annotations.DatastoreIdentity(strategy= IdGeneratorStrategy.IDENTITY, column = "id")
@javax.jdo.annotations.Version(strategy=VersionStrategy.VERSION_NUMBER, column = "version")
@javax.jdo.annotations.Unique(name="SomeCommandAnnotatedObject_name_UNQ", members = {"name"})
@DomainObject(
        updatingLifecycleEvent = ObjectUpdatingEvent.Doop.class,
        updatedLifecycleEvent = ObjectUpdatedEvent.Doop.class
)
@DomainObjectLayout(bookmarking = BookmarkPolicy.AS_ROOT )
@XmlJavaTypeAdapter(PersistentEntityAdapter.class)
public class SomeCommandAnnotatedObject implements Comparable<SomeCommandAnnotatedObject> {

    public enum Colour {
        Red, Green, Blue
    }

    @javax.jdo.annotations.Column(allowsNull="false")
    @Title(sequence="1")
    @Property(command = CommandReification.ENABLED)
    @Getter @Setter
    private String name;

    @javax.jdo.annotations.Column(allowsNull="true")
    @Property(command = CommandReification.ENABLED)
    @Getter @Setter
    private String description;

    @javax.jdo.annotations.Column(allowsNull="true")
    @Property(command = CommandReification.ENABLED, commandExecuteIn = CommandExecuteIn.BACKGROUND)
    @Getter @Setter
    private String addressImplicitlyEditBackground;

    @org.apache.isis.applib.annotation.Property()
    @javax.jdo.annotations.Column(allowsNull = "true")
    @lombok.Getter @lombok.Setter
    private Colour colour;

    @javax.jdo.annotations.Column(allowsNull="true")
    @Getter @Setter
    private Boolean flag;

    // not supported...
//    @org.apache.isis.applib.annotation.Property()
//    @javax.jdo.annotations.Column(allowsNull = "true")
//    @lombok.Getter @lombok.Setter
//    private Colour copyOfColorUpdatedBySubscribedBackgroundPropertyEdit;

    @org.apache.isis.applib.annotation.Property()
    @javax.jdo.annotations.Column(allowsNull = "true")
    @lombok.Getter @lombok.Setter
    private Colour copyOfColorUpdatedBySubscribedBackgroundDirectAction;

    @org.apache.isis.applib.annotation.Property()
    @javax.jdo.annotations.Column(allowsNull = "true")
    @lombok.Getter @lombok.Setter
    private Colour copyOfColorUpdatedBySubscribedBackgroundMixinAction;


    //region > changeColour (action)

    public static class ChangeColorActionDomainEvent extends ActionDomainEvent<SomeCommandAnnotatedObject>{}

    @Action(
            domainEvent = ChangeColorActionDomainEvent.class,
            semantics = SemanticsOf.IDEMPOTENT,
            command = CommandReification.ENABLED
    )
    @ActionLayout(
            named = "Change"
    )
    @MemberOrder(name = "colour", sequence = "1")
    public SomeCommandAnnotatedObject changeColour(final Colour newColour) {
        setColour(newColour);
        return this;
    }

    public Colour default0ChangeColour() {
        return getColour();
    }

    //endregion


    //region > updateCopyOfColorUpdatedBySubscribedBackgroundDirectAction (action)

    @MemberOrder(name = "copyOfColorUpdatedBySubscribedBackgroundDirectAction", sequence = "1")
    public void updateCopyOfColorUpdatedBySubscribedBackgroundDirectAction(final Colour colour) {
        setCopyOfColorUpdatedBySubscribedBackgroundDirectAction(colour);
    }


    //endregion


    //region > changeName (action)

    @Action(
            semantics = SemanticsOf.IDEMPOTENT,
            command = CommandReification.ENABLED
    )
    public SomeCommandAnnotatedObject changeName(
            @ParameterLayout(named = "New name")
            final String newName) {
        setName(newName);
        return this;
    }

    public String default0ChangeName() {
        return getName();
    }

    //endregion

    //region > changeNameWithSafeSemantics (action)

    @Action(
            semantics = SemanticsOf.SAFE // obviously, a mistake
    )
    @ActionLayout(
            describedAs = "'Mistakenly' annotated as being invoked with safe semantics, not annotated as a Command; should persist anyway"
    )
    public SomeCommandAnnotatedObject changeNameWithSafeSemantics(
            @ParameterLayout(named = "New name")
            final String newName) {
        setName(newName);
        return this;
    }

    public String default0ChangeNameWithSafeSemantics() {
        return getName();
    }

    //endregion

    //region > changeNameExplicitlyInBackgroundUsingPropertyEdit(action)

    @Action(
            semantics = SemanticsOf.IDEMPOTENT,
            command = CommandReification.ENABLED,
            hidden = Where.EVERYWHERE // otherwise will throw exception: "Only actions can be executed in the background (method _d69setName represents a PROPERTY')"
    )
    @ActionLayout(
            describedAs = "invoke from UI, will change the name in the background using a property edit, background.execute(...).setName(...)"
    )
    public SomeCommandAnnotatedObject changeNameExplicitlyInBackgroundUsingPropertyEdit(
            @ParameterLayout(named = "New name")
            final String newName) {
        backgroundService.execute(this).setName(newName);
        return this;
    }

    public String default0ChangeNameExplicitlyInBackgroundUsingPropertyEdit() {
        return getName();
    }

    //endregion

    //region > changeNameExplicitlyInBackgroundUsingDirectAction (action)

    @Action(
            semantics = SemanticsOf.IDEMPOTENT,
            command = CommandReification.ENABLED
    )
    @ActionLayout(
            describedAs = "invoke from UI, will change the name in the background using a direct action, background.execute(...).changeName(...)"
    )
    public SomeCommandAnnotatedObject changeNameExplicitlyInBackgroundUsingDirectAction(
            @ParameterLayout(named = "New name")
            final String newName) {
        backgroundService.execute(this).changeName(newName);
        return this;
    }

    public String default0ChangeNameExplicitlyInBackgroundUsingDirectAction() {
        return getName();
    }

    //endregion

    //region > changeNameImplicitlyInBackground (action)

    @Action(
            semantics = SemanticsOf.IDEMPOTENT,
            command = CommandReification.ENABLED,
            commandExecuteIn = CommandExecuteIn.BACKGROUND
    )
    @ActionLayout(
            describedAs = "invoke from UI, will create a command implicitly (returned) to change the name in the background"
    )
    public SomeCommandAnnotatedObject changeNameImplicitlyInBackground(
            @ParameterLayout(named = "New name")
            final String newName) {
        setName(newName);
        return this;
    }

    public String default0ChangeNameImplicitlyInBackground() {
        return getName();
    }

    //endregion

    //region > changeNameCommandNotPersisted (action)

    @Action(
            semantics = SemanticsOf.IDEMPOTENT,
            command = CommandReification.ENABLED,
            commandPersistence = CommandPersistence.NOT_PERSISTED
    )
    @ActionLayout(
            describedAs = "Change name directly (commandPersistence = NOT_PERSISTED, so none should be created)"
    )
    public SomeCommandAnnotatedObject changeNameCommandNotPersisted(
            @ParameterLayout(named = "New name")
            final String newName) {
        setName(newName);
        return this;
    }

    public String default0ChangeNameCommandNotPersisted() {
        return getName();
    }

    //endregion

    //region > toggleForBulkActions
    @Action(invokeOn = InvokeOn.OBJECT_AND_COLLECTION)
    @ActionLayout(
            describedAs = "Toggle, for testing (direct) bulk actions"
    )
    public void toggleForBulkActions() {
        boolean flag = getFlag() != null? getFlag(): false;
        setFlag(!flag);
    }
    //endregion

    //region > compareTo

    @Override
    public int compareTo(final SomeCommandAnnotatedObject other) {
        return ObjectContracts.compare(this, other, "name");
    }

    //endregion

    //region > injected services


    @javax.inject.Inject
    private BackgroundService backgroundService;
    //endregion

}
