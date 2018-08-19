package org.isisaddons.module.poly.fixture.demoapp.demomodule.dom.commchannel;

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

import org.isisaddons.module.poly.fixture.demoapp.poly_ccowner_module.dom.PolyDemoCommunicationChannelOwner;
import org.isisaddons.module.poly.fixture.demoapp.poly_ccowner_module.dom.PolyDemoCommunicationChannelOwnerLink;
import org.isisaddons.module.poly.fixture.demoapp.poly_ccowner_module.dom.PolyDemoCommunicationChannelOwnerLinks;

@javax.jdo.annotations.PersistenceCapable(
        identityType=IdentityType.DATASTORE,
        schema = "libPolyFixture"
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
                        + "FROM org.isisaddons.module.poly.fixture.demoapp.demomodule.dom.commchannel.PolyDemoCommunicationChannel "),
        @javax.jdo.annotations.Query(
                name = "findByName", language = "JDOQL",
                value = "SELECT "
                        + "FROM org.isisaddons.module.poly.fixture.demoapp.demomodule.dom.commchannel.PolyDemoCommunicationChannel "
                        + "WHERE name.indexOf(:name) >= 0 ")
})
@DomainObject
@DomainObjectLayout(
        bookmarking = BookmarkPolicy.AS_ROOT
)
public class PolyDemoCommunicationChannel implements Comparable<PolyDemoCommunicationChannel> {

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

    public PolyDemoCommunicationChannel updateDetails(
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
    public PolyDemoCommunicationChannelOwner getOwner() {
        final PolyDemoCommunicationChannelOwnerLink ownerLink = ownerLink();
        return ownerLink != null? ownerLink.getPolymorphicReference(): null;
    }
    //endregion

    //region > ownerLink
    @Action(
            semantics = SemanticsOf.SAFE,
            restrictTo = RestrictTo.PROTOTYPING
    )
    public PolyDemoCommunicationChannelOwnerLink ownerLink() {
        return communicationChannelOwnerLinks.findByCommunicationChannel(this);
    }
    //endregion

    //region > compareTo

    @Override
    public int compareTo(final PolyDemoCommunicationChannel other) {
        return ObjectContracts.compare(this, other, "details");
    }

    //endregion

    //region > injected services

    @javax.inject.Inject
    @SuppressWarnings("unused")
    private DomainObjectContainer container;

    @javax.inject.Inject
    PolyDemoCommunicationChannelOwnerLinks communicationChannelOwnerLinks;
    //endregion

}
