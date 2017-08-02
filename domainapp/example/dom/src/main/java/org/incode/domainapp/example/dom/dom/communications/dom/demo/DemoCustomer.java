package org.incode.domainapp.example.dom.dom.communications.dom.demo;

import java.util.List;

import javax.inject.Inject;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.VersionStrategy;

import org.apache.isis.applib.annotation.Action;
import org.apache.isis.applib.annotation.ActionLayout;
import org.apache.isis.applib.annotation.Contributed;
import org.apache.isis.applib.annotation.DomainObject;
import org.apache.isis.applib.annotation.Editing;
import org.apache.isis.applib.annotation.Mixin;
import org.apache.isis.applib.annotation.Parameter;
import org.apache.isis.applib.annotation.ParameterLayout;
import org.apache.isis.applib.annotation.Programmatic;
import org.apache.isis.applib.annotation.Property;
import org.apache.isis.applib.annotation.SemanticsOf;
import org.apache.isis.applib.annotation.Title;
import org.apache.isis.applib.services.eventbus.ActionDomainEvent;
import org.apache.isis.applib.services.i18n.TranslatableString;
import org.apache.isis.applib.services.message.MessageService;
import org.apache.isis.applib.services.repository.RepositoryService;
import org.apache.isis.applib.services.title.TitleService;
import org.apache.isis.applib.util.ObjectContracts;

import org.incode.domainapp.example.dom.dom.communications.dom.demo2.DemoInvoice;
import org.incode.domainapp.example.dom.dom.communications.dom.demo2.DemoInvoiceRepository;
import org.incode.module.communications.dom.impl.commchannel.CommunicationChannelOwner;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@javax.jdo.annotations.PersistenceCapable(
        identityType=IdentityType.DATASTORE,
        schema = "incodeCommunicationsDemo"
)
@javax.jdo.annotations.DatastoreIdentity(
        strategy=javax.jdo.annotations.IdGeneratorStrategy.IDENTITY,
        column="id")
@javax.jdo.annotations.Version(
        strategy= VersionStrategy.DATE_TIME,
        column="version")
@javax.jdo.annotations.Queries({
        @javax.jdo.annotations.Query(
                name = "findByName",
                value = "SELECT "
                        + "FROM org.incode.domainapp.example.dom.dom.communications.dom.demo.DemoCustomer "
                        + "WHERE name.indexOf(:name) >= 0 ")
})
@javax.jdo.annotations.Unique(name="DemoCustomer_name_UNQ", members = {"name"})
@DomainObject(
        // objectType inferred from schema
)
public class DemoCustomer implements Comparable<DemoCustomer>, CommunicationChannelOwner {

    @Builder
    public DemoCustomer(final String name) {
        setName(name);
    }

    @javax.jdo.annotations.Column(allowsNull = "false", length = 40)
    @Property(editing = Editing.DISABLED)
    @Getter @Setter
    @Title
    private String name;

    @javax.jdo.annotations.Column(allowsNull = "true", length = 4000)
    @Property(editing = Editing.ENABLED)
    @Getter @Setter
    private String notes;


    //region > updateName (action)
    @Action(semantics = SemanticsOf.IDEMPOTENT)
    public DemoCustomer updateName(
            @Parameter(maxLength = 40)
            @ParameterLayout(named = "Name")
            final String name) {
        setName(name);
        return this;
    }

    public String default0UpdateName() {
        return getName();
    }

    public TranslatableString validate0UpdateName(final String name) {
        return name != null && name.contains("!") ? TranslatableString.tr("Exclamation mark is not allowed") : null;
    }
    //endregion

    //region > delete (action)
    @Action(semantics = SemanticsOf.NON_IDEMPOTENT_ARE_YOU_SURE)
    public void delete() {
        final String title = titleService.titleOf(this);
        messageService.informUser(String.format("'%s' deleted", title));
        repositoryService.remove(this);
    }
    //endregion

    
    //region > invoices (derived collection)
    @Mixin(method="coll")
    public static class invoices {
        private final DemoCustomer demoCustomer;
        public invoices(final DemoCustomer demoCustomer) {
            this.demoCustomer = demoCustomer;
        }
        public static class DomainEvent extends ActionDomainEvent<DemoCustomer> {
        }
        @Action(semantics = SemanticsOf.SAFE, domainEvent = DomainEvent.class)
        @ActionLayout(contributed= Contributed.AS_ASSOCIATION)
        public List<DemoInvoice> coll() {
            return invoiceRepository.findByCustomer(demoCustomer);
        }

        @Inject
        DemoInvoiceRepository invoiceRepository;
    }
    //endregion

    @Programmatic
    @Override
    public String getAtPath() {
        return "/";
    }


    //region > toString, compareTo
    @Override
    public String toString() {
        return ObjectContracts.toString(this, "name");
    }

    @Override
    public int compareTo(final DemoCustomer other) {
        return ObjectContracts.compare(this, other, "name");
    }
    //endregion

    //region > injected services
    @javax.inject.Inject
    RepositoryService repositoryService;

    @javax.inject.Inject
    TitleService titleService;

    @javax.inject.Inject
    MessageService messageService;

    //endregion



}