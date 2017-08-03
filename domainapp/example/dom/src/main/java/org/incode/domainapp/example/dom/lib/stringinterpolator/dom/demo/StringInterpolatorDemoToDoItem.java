package org.incode.domainapp.example.dom.lib.stringinterpolator.dom.demo;

import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.VersionStrategy;
import org.apache.isis.applib.DomainObjectContainer;
import org.apache.isis.applib.annotation.*;
import org.apache.isis.applib.util.ObjectContracts;

@javax.jdo.annotations.PersistenceCapable(
        identityType = IdentityType.DATASTORE,
        schema = "exampleLibStringInterpolator"
)
@javax.jdo.annotations.DatastoreIdentity(
        strategy = javax.jdo.annotations.IdGeneratorStrategy.IDENTITY,
        column = "id")
@javax.jdo.annotations.Version(
        strategy = VersionStrategy.VERSION_NUMBER,
        column = "version")
@javax.jdo.annotations.Uniques({
        @javax.jdo.annotations.Unique(
                name = "ToDoItem_description_must_be_unique",
                members = {"description"})
})
@DomainObject
@DomainObjectLayout(
        named = "To Do Item",
        bookmarking = BookmarkPolicy.AS_ROOT
)
public class StringInterpolatorDemoToDoItem implements Comparable<StringInterpolatorDemoToDoItem> {

    //region > description
    private String description;

    @Title
    @javax.jdo.annotations.Column(allowsNull = "false", length = 100)
    @Property(
            regexPattern = "\\w[@&:\\-\\,\\.\\+ \\w]*"
    )
    @PropertyLayout(
            typicalLength = 50
    )
    public String getDescription() {
        return description;
    }

    public void setDescription(final String description) {
        this.description = description;
    }
    //endregion

    //region > documentationPage

    private String documentationPage;

    @javax.jdo.annotations.Column(allowsNull = "true", length = 255)
    public String getDocumentationPage() {
        return documentationPage;
    }

    public void setDocumentationPage(final String documentationPage) {
        this.documentationPage = documentationPage;
    }
    //endregion

    //region > toString, compareTo

    @Override
    public String toString() {
        return ObjectContracts.toString(this, "description,complete,ownedBy");
    }

    @Override
    public int compareTo(final StringInterpolatorDemoToDoItem other) {
        return ObjectContracts.compare(this, other, "description");
    }
    //endregion

    //region > injected services

    @javax.inject.Inject
    private DomainObjectContainer container;

    @javax.inject.Inject
    private StringInterpolatorDemoToDoItems toDoItems;
    //endregion
}
