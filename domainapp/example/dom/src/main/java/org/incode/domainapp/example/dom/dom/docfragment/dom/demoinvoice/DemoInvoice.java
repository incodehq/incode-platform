package org.incode.domainapp.example.dom.dom.docfragment.dom.demoinvoice;

import java.io.IOException;

import javax.inject.Inject;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.VersionStrategy;

import org.joda.time.LocalDate;

import org.apache.isis.applib.annotation.Action;
import org.apache.isis.applib.annotation.DomainObject;
import org.apache.isis.applib.annotation.Editing;
import org.apache.isis.applib.annotation.ParameterLayout;
import org.apache.isis.applib.annotation.Property;
import org.apache.isis.applib.annotation.SemanticsOf;
import org.apache.isis.applib.annotation.Title;
import org.apache.isis.applib.util.ObjectContracts;

import org.incode.module.docfragment.dom.api.DocFragmentService;
import org.incode.module.docfragment.dom.types.AtPathType;

import freemarker.template.TemplateException;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@javax.jdo.annotations.PersistenceCapable(
        identityType=IdentityType.DATASTORE,
        schema = "incodeDocFragmentDemo",
        table = "DemoInvoice"
)
@javax.jdo.annotations.DatastoreIdentity(
        strategy=javax.jdo.annotations.IdGeneratorStrategy.IDENTITY,
        column="id")
@javax.jdo.annotations.Version(
        strategy= VersionStrategy.VERSION_NUMBER,
        column="version")
@DomainObject(
        objectType = "docfragmentdemo.DemoInvoice"
)
public class DemoInvoice implements Comparable<DemoInvoice> {

    @Builder
    public DemoInvoice(final int num, final LocalDate dueBy, final int numDays, final String atPath) {
        this.num = num;
        this.dueBy = dueBy;
        this.numDays = numDays;
        this.atPath = atPath;
    }

    @javax.jdo.annotations.Column(allowsNull = "false")
    @Property(editing = Editing.DISABLED)
    @Getter @Setter
    @Title(sequence = "1", prepend = "Invoice #")
    private int num;


    @javax.jdo.annotations.Column(allowsNull = "false")
    @Property(editing = Editing.ENABLED)
    @Getter @Setter
    private LocalDate dueBy;


    @javax.jdo.annotations.Column(allowsNull = "false")
    @Property(editing = Editing.ENABLED)
    @Getter @Setter
    private int numDays;


    @javax.jdo.annotations.Column(allowsNull = "false", length = AtPathType.Meta.MAX_LEN)
    @Property(editing = Editing.DISABLED)
    @Getter @Setter
    private String atPath;


    @Property(editing = Editing.DISABLED)
    @javax.jdo.annotations.Column(allowsNull = "true")
    @Getter @Setter
    private String rendered;


    @Action(semantics = SemanticsOf.IDEMPOTENT)
    public DemoInvoice render(
            @ParameterLayout(named = "Fragment name")
            final String fragmentName) {
        final String rendered = doRender(fragmentName);
        setRendered(rendered);
        return this;
    }

    public String default0Render() {
        return "due";
    }
    

    private String doRender(final String name) {
        try {
            return docFragmentService.render(this, name);
        } catch (IOException | TemplateException e) {
            return "failed to render";
        }
    }



    //region > toString, compareTo, equals, hashCode
    private static final String[] PROPERTY_NAMES = {"num"};

    @Override
    public String toString() {
        return ObjectContracts.toString(this, PROPERTY_NAMES);
    }

    @Override
    public int compareTo(final DemoInvoice other) {
        return ObjectContracts.compare(this, other, PROPERTY_NAMES);
    }

    @Override
    public boolean equals(final Object o) {
        return ObjectContracts.equals(this, o, PROPERTY_NAMES);
    }

    @Override
    public int hashCode() {
        return ObjectContracts.hashCode(this, PROPERTY_NAMES);
    }

    //endregion

    @Inject
    DocFragmentService docFragmentService;


}