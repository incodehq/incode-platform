package org.isisaddons.module.poly.fixture.demoapp.poly_ccowner_module.dom;

import javax.jdo.annotations.Column;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.InheritanceStrategy;

import com.google.common.eventbus.Subscribe;

import org.axonframework.eventhandling.annotation.EventHandler;

import org.apache.isis.applib.AbstractSubscriber;
import org.apache.isis.applib.annotation.DomainObject;
import org.apache.isis.applib.annotation.DomainService;
import org.apache.isis.applib.annotation.MemberOrder;
import org.apache.isis.applib.annotation.NatureOfService;
import org.apache.isis.applib.services.bookmark.BookmarkService;

import org.isisaddons.module.poly.fixture.demoapp.demomodule.dom.party.PolyDemoParty;

@javax.jdo.annotations.PersistenceCapable(
        identityType = IdentityType.DATASTORE,
        schema = "libPolyFixture"
)
@javax.jdo.annotations.Inheritance(
        strategy = InheritanceStrategy.NEW_TABLE)
@DomainObject
public class PolyDemoCommunicationChannelOwnerLinkForParty extends PolyDemoCommunicationChannelOwnerLink {

    @DomainService(nature = NatureOfService.DOMAIN)
    public static class InstantiationSubscriber extends AbstractSubscriber {

        @EventHandler
        @Subscribe
        public void on(final InstantiateEvent ev) {
            if(ev.getPolymorphicReference() instanceof PolyDemoParty) {
                ev.setSubtype(PolyDemoCommunicationChannelOwnerLinkForParty.class);
            }
        }
    }

    @Override
    public void setPolymorphicReference(final PolyDemoCommunicationChannelOwner polymorphicReference) {
        super.setPolymorphicReference(polymorphicReference);
        setParty((PolyDemoParty) polymorphicReference);
    }

    //region > party (property)
    private PolyDemoParty party;

    @Column(
            allowsNull = "false",
            name = "party_id"
    )
    @MemberOrder(sequence = "1")
    public PolyDemoParty getParty() {
        return party;
    }

    public void setParty(final PolyDemoParty party) {
        this.party = party;
    }
    //endregion


    //region > injected services
    @javax.inject.Inject
    private BookmarkService bookmarkService;
    //endregion

}
