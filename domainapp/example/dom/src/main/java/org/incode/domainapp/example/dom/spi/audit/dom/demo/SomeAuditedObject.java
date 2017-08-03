package org.incode.domainapp.example.dom.spi.audit.dom.demo;

import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.VersionStrategy;
import org.apache.isis.applib.DomainObjectContainer;
import org.apache.isis.applib.annotation.*;
import org.apache.isis.applib.util.ObjectContracts;

@javax.jdo.annotations.PersistenceCapable(
        identityType=IdentityType.DATASTORE,
        schema = "exampleSpiAudit"
)
@javax.jdo.annotations.DatastoreIdentity(
        strategy=javax.jdo.annotations.IdGeneratorStrategy.IDENTITY,
         column="id")
@javax.jdo.annotations.Version(
        strategy=VersionStrategy.VERSION_NUMBER, 
        column="version")
@DomainObject(
        auditing = Auditing.ENABLED
)
@DomainObjectLayout(
        bookmarking = BookmarkPolicy.AS_ROOT
)
public class SomeAuditedObject implements Comparable<SomeAuditedObject> {

    //region > updateNameAndNumber

    @Action()
    public SomeAuditedObject updateNameAndNumber(
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
    public int compareTo(SomeAuditedObject other) {
        return ObjectContracts.compare(this, other, "name");
    }

    //endregion

    //region > injected services

    @javax.inject.Inject
    @SuppressWarnings("unused")
    private DomainObjectContainer container;

    //endregion

}
