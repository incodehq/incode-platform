package org.incode.domainapp.example.dom.demo.dom.reminder;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.VersionStrategy;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import com.google.common.collect.Ordering;

import org.apache.isis.applib.annotation.BookmarkPolicy;
import org.apache.isis.applib.annotation.DomainObject;
import org.apache.isis.applib.annotation.DomainObjectLayout;
import org.apache.isis.applib.annotation.Property;
import org.apache.isis.applib.annotation.Title;
import org.apache.isis.applib.util.ObjectContracts;
import org.apache.isis.schema.utils.jaxbadapters.PersistentEntityAdapter;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@javax.jdo.annotations.PersistenceCapable(
        identityType = IdentityType.DATASTORE,
        schema = "exampleDemo"
)
@javax.jdo.annotations.DatastoreIdentity(strategy = IdGeneratorStrategy.IDENTITY, column = "id")
@javax.jdo.annotations.Version(strategy = VersionStrategy.VERSION_NUMBER, column = "version")
@javax.jdo.annotations.Uniques({
        @javax.jdo.annotations.Unique(
                name = "Reminder_description_must_be_unique",
                members = {"description"})
})
@DomainObject
@DomainObjectLayout(bookmarking = BookmarkPolicy.AS_ROOT )
@AllArgsConstructor
@Builder
@XmlJavaTypeAdapter(PersistentEntityAdapter.class)
public class DemoReminder implements Comparable<DemoReminder> {

    @Title
    @javax.jdo.annotations.Column(allowsNull = "false", length = 100)
    @Property(regexPattern = "\\w[@&:\\-\\,\\.\\+ \\w]*")
    @Getter @Setter
    private String description;


    @javax.jdo.annotations.Column(allowsNull = "true", length = 255)
    @Getter @Setter
    private String documentationPage;



    public String toString() {
        return ObjectContracts.toString(this, "description,documentationPage");
    }

    @Override
    public int compareTo(final DemoReminder other) {
        return Ordering.natural().onResultOf(DemoReminder::getDescription).compare(this, other);
    }

}
