package org.isisaddons.module.poly.fixture.demoapp.poly_ccowner_module.dom;

import javax.jdo.annotations.Column;
import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.InheritanceStrategy;

import com.google.common.base.Function;

import org.apache.isis.applib.annotation.DomainObject;
import org.apache.isis.applib.annotation.Programmatic;

import org.isisaddons.module.poly.dom.PolymorphicAssociationLink;
import org.isisaddons.module.poly.fixture.demoapp.demomodule.dom.commchannel.PolyDemoCommunicationChannel;

@javax.jdo.annotations.PersistenceCapable(
        identityType=IdentityType.DATASTORE,
        schema = "libPolyFixture"
)
@javax.jdo.annotations.DatastoreIdentity(strategy = IdGeneratorStrategy.IDENTITY, column = "id")
@javax.jdo.annotations.Inheritance(
        strategy = InheritanceStrategy.NEW_TABLE)
@javax.jdo.annotations.Queries({
        @javax.jdo.annotations.Query(
                name = "findByCommunicationChannel", language = "JDOQL",
                value = "SELECT "
                        + "FROM org.isisaddons.module.poly.fixture.demoapp.poly_ccowner_module.dom.PolyDemoCommunicationChannelOwnerLink "
                        + "WHERE communicationChannel == :communicationChannel"),
        @javax.jdo.annotations.Query(
                name = "findByOwner", language = "JDOQL",
                value = "SELECT "
                        + "FROM org.isisaddons.module.poly.fixture.demoapp.poly_ccowner_module.dom.PolyDemoCommunicationChannelOwnerLink "
                        + "WHERE ownerObjectType == :ownerObjectType "
                        + "   && ownerIdentifier == :ownerIdentifier ")
})
@javax.jdo.annotations.Unique(name="CommunicationChannelOwnerLink_commchannel_owner_UNQ", members = {"communicationChannel","ownerObjectType","ownerIdentifier"})
@DomainObject
public abstract class PolyDemoCommunicationChannelOwnerLink
        extends PolymorphicAssociationLink<PolyDemoCommunicationChannel, PolyDemoCommunicationChannelOwner, PolyDemoCommunicationChannelOwnerLink> {


    public static class InstantiateEvent
            extends PolymorphicAssociationLink.InstantiateEvent<PolyDemoCommunicationChannel, PolyDemoCommunicationChannelOwner, PolyDemoCommunicationChannelOwnerLink> {

        public InstantiateEvent(final Object source, final PolyDemoCommunicationChannel subject, final PolyDemoCommunicationChannelOwner owner) {
            super(PolyDemoCommunicationChannelOwnerLink.class, source, subject, owner);
        }
    }

    //region > constructor
    public PolyDemoCommunicationChannelOwnerLink() {
        super("{polymorphicReference} owns {subject}");
    }
    //endregion

    //region > SubjectPolymorphicReferenceLink API
    @Override
    @Programmatic
    public PolyDemoCommunicationChannel getSubject() {
        return getCommunicationChannel();
    }

    @Override
    @Programmatic
    public void setSubject(final PolyDemoCommunicationChannel subject) {
        setCommunicationChannel(subject);
    }

    @Override
    @Programmatic
    public String getPolymorphicObjectType() {
        return getOwnerObjectType();
    }

    @Override
    @Programmatic
    public void setPolymorphicObjectType(final String polymorphicObjectType) {
        setOwnerObjectType(polymorphicObjectType);
    }

    @Override
    @Programmatic
    public String getPolymorphicIdentifier() {
        return getOwnerIdentifier();
    }

    @Override
    @Programmatic
    public void setPolymorphicIdentifier(final String polymorphicIdentifier) {
        setOwnerIdentifier(polymorphicIdentifier);
    }
    //endregion

    //region > communicationChannel (property)
    private PolyDemoCommunicationChannel communicationChannel;
    @Column(
            allowsNull = "false",
            name = "communicationChannel_id"
    )
    public PolyDemoCommunicationChannel getCommunicationChannel() {
        return communicationChannel;
    }

    public void setCommunicationChannel(final PolyDemoCommunicationChannel communicationChannel) {
        this.communicationChannel = communicationChannel;
    }
    //endregion

    //region > ownerObjectType (property)
    private String ownerObjectType;

    @Column(allowsNull = "false", length = 255)
    public String getOwnerObjectType() {
        return ownerObjectType;
    }

    public void setOwnerObjectType(final String ownerObjectType) {
        this.ownerObjectType = ownerObjectType;
    }
    //endregion

    //region > ownerIdentifier (property)
    private String ownerIdentifier;

    @Column(allowsNull = "false", length = 255)
    public String getOwnerIdentifier() {
        return ownerIdentifier;
    }

    public void setOwnerIdentifier(final String ownerIdentifier) {
        this.ownerIdentifier = ownerIdentifier;
    }
    //endregion

    public static class Functions {
        public static Function<PolyDemoCommunicationChannelOwnerLink, PolyDemoCommunicationChannel> GET_COMMUNICATION_CHANNEL = new Function<PolyDemoCommunicationChannelOwnerLink, PolyDemoCommunicationChannel>() {
            @Override
            public PolyDemoCommunicationChannel apply(final PolyDemoCommunicationChannelOwnerLink input) {
                return input.getCommunicationChannel();
            }
        };

    }

}
