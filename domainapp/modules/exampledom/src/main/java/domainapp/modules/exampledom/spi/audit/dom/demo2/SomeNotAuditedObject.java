package domainapp.modules.exampledom.spi.audit.dom.demo2;

import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.VersionStrategy;

import org.apache.isis.applib.DomainObjectContainer;
import org.apache.isis.applib.annotation.Action;
import org.apache.isis.applib.annotation.Auditing;
import org.apache.isis.applib.annotation.BookmarkPolicy;
import org.apache.isis.applib.annotation.DomainObject;
import org.apache.isis.applib.annotation.DomainObjectLayout;
import org.apache.isis.applib.annotation.Optionality;
import org.apache.isis.applib.annotation.Parameter;
import org.apache.isis.applib.annotation.ParameterLayout;
import org.apache.isis.applib.annotation.Title;
import org.apache.isis.applib.util.ObjectContracts;

@javax.jdo.annotations.PersistenceCapable(
        identityType=IdentityType.DATASTORE,
        schema = "isisauditdemo",
        table = "SomeNotAuditedObject"
)
@javax.jdo.annotations.DatastoreIdentity(
        strategy=javax.jdo.annotations.IdGeneratorStrategy.IDENTITY,
         column="id")
@javax.jdo.annotations.Version(
        strategy=VersionStrategy.VERSION_NUMBER, 
        column="version")
@DomainObject(
        objectType = "isisauditdemo.SomeNotAuditedObject",
        auditing = Auditing.DISABLED
)
@DomainObjectLayout(
        bookmarking = BookmarkPolicy.AS_ROOT
)
public class SomeNotAuditedObject implements Comparable<SomeNotAuditedObject> {

    //region > updateNameAndNumber

    @Action()
    public SomeNotAuditedObject updateNameAndNumber(
            @ParameterLayout(named = "Name")
            final String name,
            @Parameter(optionality = Optionality.OPTIONAL)
            @ParameterLayout(named = "Number")
            final Integer number) {
        setName(name);
        setNumber(number);
        return this;
    }

    public String default0UpdateNameAndNumber() {
        return getName();
    }
    public Integer default1UpdateNameAndNumber() {
        return getNumber();
    }

    //endregion

    //region > name (property)

    private String name;

    @javax.jdo.annotations.Column(allowsNull="false")
    @Title(sequence="1")
    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    //endregion

    //region > number (property)
    private Integer number;

    @javax.jdo.annotations.Column(allowsNull = "true")
    public Integer getNumber() {
        return number;
    }

    public void setNumber(final Integer number) {
        this.number = number;
    }
    //endregion

    //region > compareTo

    @Override
    public int compareTo(SomeNotAuditedObject other) {
        return ObjectContracts.compare(this, other, "name");
    }

    //endregion

    //region > injected services

    @javax.inject.Inject
    @SuppressWarnings("unused")
    private DomainObjectContainer container;

    //endregion

}
