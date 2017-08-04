
package org.incode.domainapp.example.dom.spi.security.dom.demo.nontenanted;

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

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@javax.jdo.annotations.PersistenceCapable(
        identityType= IdentityType.DATASTORE,
        schema = "exampleSpiSecurity"
)
@javax.jdo.annotations.DatastoreIdentity(strategy= IdGeneratorStrategy.IDENTITY, column ="id")
@javax.jdo.annotations.Version(strategy = VersionStrategy.VERSION_NUMBER, column = "version")
@javax.jdo.annotations.Uniques({
        @javax.jdo.annotations.Unique(
                name = "NonTenantedEntity_name_UNQ", members = { "name" })
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
public class NonTenantedEntity implements Comparable<NonTenantedEntity> {


    public static final int MAX_LENGTH_NAME = 30;
    public static final int MAX_LENGTH_DESCRIPTION = 254;


    @javax.jdo.annotations.Column(allowsNull="false", length = MAX_LENGTH_NAME)
    @Getter @Setter
    @Title(sequence="1")
    @MemberOrder(sequence="1")
    private String name;
    public String getName() { return name; }


    @javax.jdo.annotations.Column(allowsNull="true", length = MAX_LENGTH_DESCRIPTION)
    @Getter @Setter
    @MemberOrder(sequence="2")
    private String description;


    @Override
    public int compareTo(final NonTenantedEntity o) {
        return Ordering.natural().onResultOf(NonTenantedEntity::getName).compare(this, o);
    }
}
