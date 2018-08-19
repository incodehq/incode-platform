package org.isisaddons.module.poly.fixture.demoapp.demomodule.dom.party;

import java.util.List;

import javax.jdo.annotations.Column;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.VersionStrategy;

import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;

import org.apache.isis.applib.DomainObjectContainer;
import org.apache.isis.applib.annotation.BookmarkPolicy;
import org.apache.isis.applib.annotation.CollectionLayout;
import org.apache.isis.applib.annotation.DomainObject;
import org.apache.isis.applib.annotation.DomainObjectLayout;
import org.apache.isis.applib.annotation.Editing;
import org.apache.isis.applib.annotation.MemberOrder;
import org.apache.isis.applib.annotation.Parameter;
import org.apache.isis.applib.annotation.ParameterLayout;
import org.apache.isis.applib.annotation.Property;
import org.apache.isis.applib.annotation.PropertyLayout;
import org.apache.isis.applib.annotation.RenderType;
import org.apache.isis.applib.annotation.Title;
import org.apache.isis.applib.annotation.Where;
import org.apache.isis.applib.services.i18n.TranslatableString;
import org.apache.isis.applib.util.ObjectContracts;

import org.isisaddons.module.poly.fixture.demoapp.demomodule.dom.commchannel.PolyDemoCommunicationChannel;
import org.isisaddons.module.poly.fixture.demoapp.poly_casecontent_module.dom.content.PolyDemoCaseContent;
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
                        + "FROM org.isisaddons.module.poly.fixture.demoapp.demomodule.dom.party.PolyDemoParty ")
})
@javax.jdo.annotations.Unique(name="Party_name_UNQ", members = {"name"})
@DomainObject(
        bounded = true
)
@DomainObjectLayout(
        bookmarking = BookmarkPolicy.AS_ROOT
)
public class PolyDemoParty implements PolyDemoCommunicationChannelOwner, PolyDemoCaseContent, Comparable<PolyDemoParty> {

    //region > identification
    public TranslatableString title() {
        return TranslatableString.tr("{name}", "name", getName());
    }

    //endregion

    //region > name (property)

    private String name;

    @Column(allowsNull="false", length = 40)
    @Title(sequence="1")
    @Property(
            editing = Editing.DISABLED
    )
    @PropertyLayout(
            // contributed 'title' property (from CaseContentContributions) is shown instead on tables
            hidden = Where.ALL_TABLES
    )
    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    // endregion

    //region > updateName (action)

    public PolyDemoParty updateName(
            @Parameter(maxLength = 40)
            @ParameterLayout(named = "New name")
            final String name) {
        setName(name);
        return this;
    }

    public String default0UpdateName() {
        return getName();
    }

    //endregion


    //region > communicationChannels (derived collection)
    @CollectionLayout(
            render = RenderType.EAGERLY
    )
    public List<PolyDemoCommunicationChannel> getCommunicationChannels() {
        final List<PolyDemoCommunicationChannelOwnerLink> ownerLinks =
                communicationChannelOwnerLinks.findByOwner(this);
        return Lists.newArrayList(
                Iterables.transform(ownerLinks, PolyDemoCommunicationChannelOwnerLink.Functions.GET_COMMUNICATION_CHANNEL)
        );
    }
    //endregion

    //region > createCommunicationChannel (action)
    @MemberOrder(name="PolyDemoCommunicationChannel",sequence = "3")
    public PolyDemoCommunicationChannelOwner addCommunicationChannel(
            @ParameterLayout(named = "Details")
            final String details) {

        final PolyDemoCommunicationChannel communicationChannel = container.newTransientInstance(PolyDemoCommunicationChannel.class);
        communicationChannel.setDetails(details);
        container.persist(communicationChannel);
        container.flush();

        communicationChannelOwnerLinks.createLink(communicationChannel, this);
        return this;
    }

    public String validate0AddCommunicationChannel(
            final String details) {
        final List<PolyDemoCommunicationChannel> communicationChannels = getCommunicationChannels();
        for (PolyDemoCommunicationChannel communicationChannel : communicationChannels) {
            if(communicationChannel.getDetails().equals(details)) {
                return "Already have a communication channel with those details";
            }
        }
        return null;
    }

    //endregion

    //region > removeCommunicationChannel (action)
    @MemberOrder(name="PolyDemoCommunicationChannel", sequence = "4")
    public PolyDemoCommunicationChannelOwner removeCommunicationChannel(
            final PolyDemoCommunicationChannel communicationChannel) {

        final List<PolyDemoCommunicationChannelOwnerLink> ownerLinks =
                communicationChannelOwnerLinks.findByOwner(this);
        final PolyDemoCommunicationChannelOwnerLink ownerLink = communicationChannelOwnerLinks.findByCommunicationChannel(communicationChannel);

        if(ownerLinks.contains(ownerLink)) {
            container.removeIfNotAlready(ownerLink);
            container.removeIfNotAlready(communicationChannel);
        }

        return this;
    }

    public String disableRemoveCommunicationChannel() {
        final List<PolyDemoCommunicationChannelOwnerLink> ownerLinks =
                communicationChannelOwnerLinks.findByOwner(this);
        return ownerLinks.isEmpty()? "Does not own a communication channel": null;
    }
    public String validate0RemoveCommunicationChannel(final PolyDemoCommunicationChannel communicationChannel) {
        final List<PolyDemoCommunicationChannelOwnerLink> ownerLinks =
                communicationChannelOwnerLinks.findByOwner(this);
        final PolyDemoCommunicationChannelOwnerLink ownerLink = communicationChannelOwnerLinks.findByCommunicationChannel(communicationChannel);
        return ownerLinks.contains(ownerLink)? null: "Not a communication channel of this party";
    }

    public List<PolyDemoCommunicationChannel> choices0RemoveCommunicationChannel() {
        final List<PolyDemoCommunicationChannelOwnerLink> ownerLinks =
                communicationChannelOwnerLinks.findByOwner(this);
        return Lists.newArrayList(
                Iterables.transform(ownerLinks, PolyDemoCommunicationChannelOwnerLink.Functions.GET_COMMUNICATION_CHANNEL)
        );
    }

    //endregion



    //region > compareTo

    @Override
    public int compareTo(final PolyDemoParty other) {
        return ObjectContracts.compare(this, other, "name");
    }

    //endregion

    //region > injected services

    @javax.inject.Inject
    private DomainObjectContainer container;

    @javax.inject.Inject
    PolyDemoCommunicationChannelOwnerLinks communicationChannelOwnerLinks;

    //endregion

}
