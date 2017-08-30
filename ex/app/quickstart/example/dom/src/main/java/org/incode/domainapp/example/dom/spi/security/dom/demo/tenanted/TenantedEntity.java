package org.incode.domainapp.example.dom.spi.security.dom.demo.tenanted;

import javax.jdo.annotations.Column;
import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.VersionStrategy;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import com.google.common.collect.Ordering;

import org.apache.isis.applib.annotation.DomainObject;
import org.apache.isis.applib.annotation.MemberGroupLayout;
import org.apache.isis.applib.annotation.MemberOrder;
import org.apache.isis.applib.annotation.Title;
import org.apache.isis.schema.utils.jaxbadapters.PersistentEntityAdapter;

import org.isisaddons.module.security.dom.tenancy.HasAtPath;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@javax.jdo.annotations.PersistenceCapable(
        identityType= IdentityType.DATASTORE,
        schema = "exampleSpiSecurity"
)
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
@DomainObject
@MemberGroupLayout(columnSpans = {4,4,4,12},
        left = {"General"},
        middle = {},
        right = {}
)
@AllArgsConstructor
@Builder
@XmlJavaTypeAdapter(PersistentEntityAdapter.class)
public class TenantedEntity implements HasAtPath, Comparable<TenantedEntity> {


    public static final int MAX_LENGTH_NAME = 30;
    public static final int MAX_LENGTH_DESCRIPTION = 254;


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

    @Override public int compareTo(final TenantedEntity o) {
        return Ordering.natural()
                .onResultOf(TenantedEntity::getAtPath)
                .thenComparing(TenantedEntity::getName)
                .compare(this, o);
    }

}
