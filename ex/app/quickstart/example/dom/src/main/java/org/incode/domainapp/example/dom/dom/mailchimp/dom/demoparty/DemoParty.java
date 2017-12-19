package org.incode.domainapp.example.dom.dom.mailchimp.dom.demoparty;

import javax.jdo.annotations.Column;
import javax.jdo.annotations.DatastoreIdentity;
import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Queries;
import javax.jdo.annotations.Query;
import javax.jdo.annotations.Unique;
import javax.jdo.annotations.Version;
import javax.jdo.annotations.VersionStrategy;

import org.apache.isis.applib.annotation.BookmarkPolicy;
import org.apache.isis.applib.annotation.DomainObject;
import org.apache.isis.applib.annotation.DomainObjectLayout;
import org.apache.isis.applib.annotation.Editing;

import org.incode.module.mailchimp.dom.api.IMailChimpParty;

import lombok.Getter;
import lombok.Setter;

@PersistenceCapable(
        identityType = IdentityType.DATASTORE,
        schema = "exampleDomMailchimp"
)
@DatastoreIdentity(
        strategy = IdGeneratorStrategy.IDENTITY,
        column = "id")
@Version(
        strategy = VersionStrategy.VERSION_NUMBER,
        column = "version")
@Queries({
        @Query(
                name = "findByEmail", language = "JDOQL",
                value = "SELECT "
                        + "FROM org.incode.domainapp.example.dom.dom.mailchimp.dom.demoparty.DemoParty "
                        + "WHERE email == :email ")
})
@Unique(name = "DemoParty_email_UNQ", members = { "email" })
@DomainObject(
        editing = Editing.DISABLED
)
@DomainObjectLayout(
        bookmarking = BookmarkPolicy.AS_ROOT
)
public class DemoParty implements Comparable<DemoParty>, IMailChimpParty {

    public DemoParty(final String name, final String email, final boolean sendMail){
        this.name = name;
        this.email = email;
        this.sendMail = sendMail;
    }

    @Column(allowsNull = "false")
    @Getter @Setter
    private String name;

    @Column(allowsNull = "false")
    @Getter @Setter
    private String email;

    @Column(allowsNull = "false")
    @Getter @Setter
    private boolean sendMail;

    //region > IMailChimpParty implementation
    @Override public String getFirstName() {
        return getName().contains(" ") ? getName().substring(0, getName().indexOf(" ")) : "";
    }

    @Override public String getLastName() {
        return getName().contains(" ") ? getName().substring(getName().indexOf(" ")+1) : getName();
    }

    @Override public String getEmailAddress() {
        return getEmail();
    }

    @Override public Boolean excludeFromLists() {
        return !isSendMail();
    }

    //region > compareTo, toString
    @Override
    public int compareTo(final DemoParty other) {
        return org.apache.isis.applib.util.ObjectContracts.compare(this, other, "email");
    }

    @Override
    public String toString() {
        return org.apache.isis.applib.util.ObjectContracts.toString(this, "email");
    }

}
