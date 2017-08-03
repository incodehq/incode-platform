package org.incode.domainapp.example.dom.lib.poly.dom.democommchannel;

import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.VersionStrategy;
import org.apache.isis.applib.DomainObjectContainer;
import org.apache.isis.applib.annotation.Action;
import org.apache.isis.applib.annotation.BookmarkPolicy;
import org.apache.isis.applib.annotation.DomainObject;
import org.apache.isis.applib.annotation.DomainObjectLayout;
import org.apache.isis.applib.annotation.Editing;
import org.apache.isis.applib.annotation.Parameter;
import org.apache.isis.applib.annotation.ParameterLayout;
import org.apache.isis.applib.annotation.Property;
import org.apache.isis.applib.annotation.RestrictTo;
import org.apache.isis.applib.annotation.SemanticsOf;
import org.apache.isis.applib.annotation.Title;
import org.apache.isis.applib.services.i18n.TranslatableString;
import org.apache.isis.applib.util.ObjectContracts;

import org.incode.domainapp.example.dom.lib.poly.dom.poly.ccowner.CommunicationChannelOwner;
import org.incode.domainapp.example.dom.lib.poly.dom.poly.ccowner.CommunicationChannelOwnerLink;
import org.incode.domainapp.example.dom.lib.poly.dom.poly.ccowner.CommunicationChannelOwnerLinks;

@javax.jdo.annotations.PersistenceCapable(
        identityType=IdentityType.DATASTORE,
        schema = "exampleLibPoly"
)
@javax.jdo.annotations.DatastoreIdentity(
        strategy=javax.jdo.annotations.IdGeneratorStrategy.IDENTITY,
         column="id")
@javax.jdo.annotations.Version(
        strategy=VersionStrategy.VERSION_NUMBER, 
        column="version")
@javax.jdo.annotations.Queries({
        @javax.jdo.annotations.Query(
                name = "find", language = "JDOQL",
                value = "SELECT "
                        + "FROM org.incode.domainapp.example.dom.lib.poly.dom.democommchannel.CommunicationChannel "),
        @javax.jdo.annotations.Query(
                name = "findByName", language = "JDOQL",
                value = "SELECT "
                        + "FROM org.incode.domainapp.example.dom.lib.poly.dom.democommchannel.CommunicationChannel "
                        + "WHERE name.indexOf(:name) >= 0 ")
})
@DomainObject
@DomainObjectLayout(
        bookmarking = BookmarkPolicy.AS_ROOT
)
public class CommunicationChannel implements Comparable<CommunicationChannel> {

    //region > identificatiom
    public TranslatableString title() {
        return TranslatableString.tr("{details}", "details", getDetails());
    }
    //endregion

    //region > details (property)

    private String details;

    @javax.jdo.annotations.Column(allowsNull="false", length = 40)
    @Title(sequence="1")
    @Property(
            editing = Editing.DISABLED
    )
    public String getDetails() {
        return details;
    }

    public void setDetails(final String details) {
        this.details = details;
    }

    // endregion

    //region > updateDetails (action)

    public CommunicationChannel updateDetails(
            @Parameter(maxLength = 40)
            @ParameterLayout(named = "New details")
            final String details) {
        setDetails(details);
        return this;
    }

    public String default0UpdateDetails() {
        return getDetails();
    }

    //endregion

    //region > owner (derived property)
    public CommunicationChannelOwner getOwner() {
        final CommunicationChannelOwnerLink ownerLink = ownerLink();
        return ownerLink != null? ownerLink.getPolymorphicReference(): null;
    }
    //endregion

    //region > ownerLink
    @Action(
            semantics = SemanticsOf.SAFE,
            restrictTo = RestrictTo.PROTOTYPING
    )
    public CommunicationChannelOwnerLink ownerLink() {
        return communicationChannelOwnerLinks.findByCommunicationChannel(this);
    }
    //endregion

    //region > compareTo

    @Override
    public int compareTo(final CommunicationChannel other) {
        return ObjectContracts.compare(this, other, "details");
    }

    //endregion

    //region > injected services

    @javax.inject.Inject
    @SuppressWarnings("unused")
    private DomainObjectContainer container;

    @javax.inject.Inject
    CommunicationChannelOwnerLinks communicationChannelOwnerLinks;
    //endregion

}
