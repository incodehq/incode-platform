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

import org.isisaddons.module.poly.fixture.demoapp.demomodule.dom.fixedasset.PolyDemoFixedAsset;

@javax.jdo.annotations.PersistenceCapable(
        identityType = IdentityType.DATASTORE,
        schema = "libPolyFixture"
)
@javax.jdo.annotations.Inheritance(
        strategy = InheritanceStrategy.NEW_TABLE)
@DomainObject
public class PolyDemoCommunicationChannelOwnerLinkForFixedAsset extends PolyDemoCommunicationChannelOwnerLink {

    @DomainService(nature = NatureOfService.DOMAIN)
    public static class InstantiationSubscriber extends AbstractSubscriber {

        @EventHandler
        @Subscribe
        public void on(final PolyDemoCommunicationChannelOwnerLink.InstantiateEvent ev) {
            if(ev.getPolymorphicReference() instanceof
                    PolyDemoFixedAsset) {
                ev.setSubtype(PolyDemoCommunicationChannelOwnerLinkForFixedAsset.class);
            }
        }
    }

    @Override
    public void setPolymorphicReference(final PolyDemoCommunicationChannelOwner polymorphicReference) {
        super.setPolymorphicReference(polymorphicReference);
        setFixedAsset((PolyDemoFixedAsset) polymorphicReference);
    }

    //region > party (property)
    private PolyDemoFixedAsset fixedAsset;

    @Column(
            allowsNull = "false",
            name = "fixedAsset_id"
    )
    @MemberOrder(sequence = "1")
    public PolyDemoFixedAsset getFixedAsset() {
        return fixedAsset;
    }

    public void setFixedAsset(final PolyDemoFixedAsset fixedAsset) {
        this.fixedAsset = fixedAsset;
    }
    //endregion


    //region > injected services
    @javax.inject.Inject
    private BookmarkService bookmarkService;
    //endregion

}
