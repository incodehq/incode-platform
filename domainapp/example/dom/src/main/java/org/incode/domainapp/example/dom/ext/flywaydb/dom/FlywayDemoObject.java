package org.incode.domainapp.example.dom.ext.flywaydb.dom;

import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.VersionStrategy;

import org.apache.isis.applib.annotation.Action;
import org.apache.isis.applib.annotation.ActionLayout;
import org.apache.isis.applib.annotation.Auditing;
import org.apache.isis.applib.annotation.CommandReification;
import org.apache.isis.applib.annotation.Contributed;
import org.apache.isis.applib.annotation.DomainObject;
import org.apache.isis.applib.annotation.Editing;
import org.apache.isis.applib.annotation.Mixin;
import org.apache.isis.applib.annotation.Parameter;
import org.apache.isis.applib.annotation.ParameterLayout;
import org.apache.isis.applib.annotation.Property;
import org.apache.isis.applib.annotation.Publishing;
import org.apache.isis.applib.annotation.SemanticsOf;
import org.apache.isis.applib.services.i18n.TranslatableString;
import org.apache.isis.applib.services.message.MessageService;
import org.apache.isis.applib.services.repository.RepositoryService;
import org.apache.isis.applib.services.title.TitleService;
import org.apache.isis.applib.util.ObjectContracts;

import org.incode.domainapp.example.dom.ExampleDomSubmodule;
import lombok.Getter;
import lombok.Setter;

@javax.jdo.annotations.PersistenceCapable(
        identityType=IdentityType.DATASTORE,
        schema = "flywayDemo",
        table = "FlywayDemoObject"
)
@javax.jdo.annotations.DatastoreIdentity(
        strategy=javax.jdo.annotations.IdGeneratorStrategy.IDENTITY,
         column="id")
@javax.jdo.annotations.Version(
        strategy= VersionStrategy.DATE_TIME,
        column="version")
@javax.jdo.annotations.Queries({
        @javax.jdo.annotations.Query(
                name = "findByName", language = "JDOQL",
                value = "SELECT "
                        + "FROM org.incode.domainapp.example.dom.ext.flywaydb.dom.FlywayDemoObject "
                        + "WHERE name.indexOf(:name) >= 0 ")
})
@javax.jdo.annotations.Unique(name="FlywayDemoObject_name_UNQ", members = {"name"})
@DomainObject(
        objectType = "flywayDemo.FlywayDemoObject",
        auditing = Auditing.ENABLED,
        publishing = Publishing.ENABLED
)
public class FlywayDemoObject implements Comparable<FlywayDemoObject> {

    //region > title
    public TranslatableString title() {
        return TranslatableString.tr("Object: {name}", "name", getName());
    }
    //endregion

    //region > constructor
    public FlywayDemoObject(final String name) {
        setName(name);
    }
    //endregion

    //region > name (read-only property)
    public static class NameType {
        private NameType() {
        }

        public static class Meta {
            public static final int MAX_LEN = 40;

            private Meta() {
            }
        }

        public static class PropertyDomainEvent
                extends ExampleDomSubmodule.PropertyDomainEvent<FlywayDemoObject, String> { }
    }


    @javax.jdo.annotations.Column(allowsNull = "false", length = NameType.Meta.MAX_LEN)
    @Property(
            editing = Editing.DISABLED,
            domainEvent = NameType.PropertyDomainEvent.class
    )
    @Getter @Setter
    private String name;

    // endregion

    //region > notes (editable property)
    public static class NotesType {
        private NotesType() {
        }

        public static class Meta {
            public static final int MAX_LEN = 4000;

            private Meta() {
            }
        }

        public static class PropertyDomainEvent
                extends ExampleDomSubmodule.PropertyDomainEvent<FlywayDemoObject, String> { }
    }


    @javax.jdo.annotations.Column(
            allowsNull = "true",
            length = NotesType.Meta.MAX_LEN
    )
    @Property(
            command = CommandReification.ENABLED,
            publishing = Publishing.ENABLED,
            domainEvent = NotesType.PropertyDomainEvent.class
    )
    @Getter @Setter
    private String notes;
    //endregion

    //region > updateName (action)
    @Mixin(method = "exec")
    public static class updateName {

        public static class ActionDomainEvent extends ExampleDomSubmodule.ActionDomainEvent<FlywayDemoObject> {
        }

        private final FlywayDemoObject flywayDemoObject;

        public updateName(final FlywayDemoObject flywayDemoObject) {
            this.flywayDemoObject = flywayDemoObject;
        }

        @Action(
                command = CommandReification.ENABLED,
                publishing = Publishing.ENABLED,
                semantics = SemanticsOf.IDEMPOTENT,
                domainEvent = ActionDomainEvent.class
        )
        @ActionLayout(
                contributed = Contributed.AS_ACTION
        )
        public FlywayDemoObject exec(
                @Parameter(maxLength = FlywayDemoObject.NameType.Meta.MAX_LEN)
                @ParameterLayout(named = "Name")
                final String name) {
            flywayDemoObject.setName(name);
            return flywayDemoObject;
        }

        public String default0Exec() {
            return flywayDemoObject.getName();
        }

        public TranslatableString validate0Exec(final String name) {
            return name != null && name.contains("!") ? TranslatableString.tr("Exclamation mark is not allowed") : null;
        }

    }
    //endregion

    //region > delete (action)
    @Mixin(method = "exec")
    public static class delete {

        public static class ActionDomainEvent extends ExampleDomSubmodule.ActionDomainEvent<FlywayDemoObject> {
        }

        private final FlywayDemoObject flywayDemoObject;
        public delete(final FlywayDemoObject flywayDemoObject) {
            this.flywayDemoObject = flywayDemoObject;
        }

        @Action(
                domainEvent = ActionDomainEvent.class,
                semantics = SemanticsOf.NON_IDEMPOTENT_ARE_YOU_SURE
        )
        @ActionLayout(
                contributed = Contributed.AS_ACTION
        )
        public void exec() {
            final String title = titleService.titleOf(flywayDemoObject);
            messageService.informUser(String.format("'%s' deleted", title));
            repositoryService.remove(flywayDemoObject);
        }

        @javax.inject.Inject
        RepositoryService repositoryService;

        @javax.inject.Inject
        TitleService titleService;

        @javax.inject.Inject
        MessageService messageService;
    }

    //endregion

    //region > toString, compareTo
    @Override
    public String toString() {
        return ObjectContracts.toString(this, "name");
    }

    @Override
    public int compareTo(final FlywayDemoObject other) {
        return ObjectContracts.compare(this, other, "name");
    }

    //endregion

}