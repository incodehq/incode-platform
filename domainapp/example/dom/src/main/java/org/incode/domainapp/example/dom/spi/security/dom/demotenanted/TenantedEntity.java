package org.incode.domainapp.example.dom.spi.security.dom.demotenanted;

import javax.jdo.annotations.Column;
import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.VersionStrategy;

import org.apache.isis.applib.annotation.DomainObject;
import org.apache.isis.applib.annotation.MemberGroupLayout;
import org.apache.isis.applib.annotation.MemberOrder;
import org.apache.isis.applib.annotation.Title;

import org.isisaddons.module.security.dom.tenancy.HasAtPath;

import lombok.Getter;
import lombok.Setter;

@javax.jdo.annotations.PersistenceCapable(identityType= IdentityType.DATASTORE)
@javax.jdo.annotations.DatastoreIdentity(
        strategy= IdGeneratorStrategy.NATIVE,
        column="id")
@javax.jdo.annotations.Version(
        strategy = VersionStrategy.VERSION_NUMBER,
        column = "version")
@javax.jdo.annotations.Uniques({
        @javax.jdo.annotations.Unique(
                name = "TenantedEntity_name_UNQ", members = { "name" })
})
@DomainObject(
        objectType = "isissecurityDemo.TenantedEntity"
)
@MemberGroupLayout(columnSpans = {4,4,4,12},
        left = {"General"},
        middle = {},
        right = {}
)
public class TenantedEntity implements HasAtPath {

    public static final int MAX_LENGTH_NAME = 30;
    public static final int MAX_LENGTH_DESCRIPTION = 254;

    public TenantedEntity(String name, String description, String atPath) {
        this.name = name;
        this.description = description;
        this.atPath = atPath;
    }

    @javax.jdo.annotations.Column(allowsNull="false", length = MAX_LENGTH_NAME)
    @Title(sequence="1")
    @MemberOrder(sequence="1")
    @Getter @Setter
    private String name;


    @javax.jdo.annotations.Column(allowsNull="true", length = MAX_LENGTH_DESCRIPTION)
    @MemberOrder(sequence="2")
    @Getter @Setter
    private String description;


    @Column(allowsNull = "true")
    @Getter @Setter
    @MemberOrder(sequence = "3")
    private String atPath;


}
