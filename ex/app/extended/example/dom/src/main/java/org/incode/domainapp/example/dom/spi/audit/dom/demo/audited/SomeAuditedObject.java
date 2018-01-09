package org.incode.domainapp.example.dom.spi.audit.dom.demo.audited;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.VersionStrategy;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import com.google.common.collect.Ordering;

import org.apache.isis.applib.annotation.Auditing;
import org.apache.isis.applib.annotation.BookmarkPolicy;
import org.apache.isis.applib.annotation.DomainObject;
import org.apache.isis.applib.annotation.DomainObjectLayout;
import org.apache.isis.applib.annotation.Title;
import org.apache.isis.schema.utils.jaxbadapters.PersistentEntityAdapter;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@javax.jdo.annotations.PersistenceCapable(
        identityType=IdentityType.DATASTORE,
        schema = "exampleSpiAudit"
)
@javax.jdo.annotations.DatastoreIdentity(strategy= IdGeneratorStrategy.IDENTITY, column = "id")
@javax.jdo.annotations.Version(strategy=VersionStrategy.VERSION_NUMBER, column = "version")
@DomainObjectLayout(bookmarking = BookmarkPolicy.AS_ROOT )
@AllArgsConstructor
@Builder
@XmlJavaTypeAdapter(PersistentEntityAdapter.class)
@DomainObject(auditing = Auditing.ENABLED )        // <<<<<<<<<<< ENABLED
public class SomeAuditedObject implements Comparable<SomeAuditedObject> {


    @javax.jdo.annotations.Column(allowsNull="false")
    @Getter @Setter
    @Title(sequence="1")
    private String name;


    @javax.jdo.annotations.Column(allowsNull = "true")
    @Getter @Setter
    private Integer number;




    @Override
    public int compareTo(SomeAuditedObject other) {
        return Ordering.natural().onResultOf(SomeAuditedObject::getName).compare(this, other);
    }


}
