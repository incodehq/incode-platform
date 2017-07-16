package domainapp.modules.exampledom.module.communications.dom.demo2;

import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.VersionStrategy;

import org.apache.isis.applib.annotation.BookmarkPolicy;
import org.apache.isis.applib.annotation.DomainObject;
import org.apache.isis.applib.annotation.DomainObjectLayout;
import org.apache.isis.applib.annotation.Editing;
import org.apache.isis.applib.annotation.Property;
import org.apache.isis.applib.annotation.Title;
import org.apache.isis.applib.util.ObjectContracts;

import domainapp.modules.exampledom.module.communications.dom.demo.DemoCustomer;

import lombok.Getter;
import lombok.Setter;

@javax.jdo.annotations.PersistenceCapable(
        identityType=IdentityType.DATASTORE,
        schema="incodeCommunicationsDemo")
@javax.jdo.annotations.DatastoreIdentity(
        strategy=javax.jdo.annotations.IdGeneratorStrategy.IDENTITY,
         column="id")
@javax.jdo.annotations.Version(
        strategy=VersionStrategy.VERSION_NUMBER, 
        column="version")
@DomainObject(
        // objectType inferred from schema
)
@DomainObjectLayout(
        bookmarking = BookmarkPolicy.AS_ROOT
)
public class DemoInvoice implements Comparable<DemoInvoice> {

    @javax.jdo.annotations.Column(allowsNull="false")
    @Title(sequence="1", prepend = "Invoice #")
    @Property(editing = Editing.DISABLED)
    @Getter @Setter
    private String num;

    @javax.jdo.annotations.Column(
            allowsNull = "false",
            name = "customerId"
    )
    @Title(sequence="2", prepend = " for ")
    @Property
    @Getter @Setter
    private DemoCustomer customer;

    //region > toString, compareTo

    @Override
    public String toString() {
        return ObjectContracts.toString(this, "num", "customer");
    }

    @Override
    public int compareTo(final DemoInvoice other) {
        return ObjectContracts.compare(this, other, "num", "customer");
    }

    //endregion

    //region > injected services

}
