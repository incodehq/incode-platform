package org.incode.domainapp.example.dom.lib.poly.dom.poly.caseprimary.party;

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

import org.incode.domainapp.example.dom.lib.poly.dom.demoparty.Party;
import org.incode.domainapp.example.dom.lib.poly.dom.poly.casecontent.CaseContent;
import org.incode.domainapp.example.dom.lib.poly.dom.poly.caseprimary.CasePrimaryContentLink;

@javax.jdo.annotations.PersistenceCapable(
        identityType = IdentityType.DATASTORE,
        schema = "exampleLibPoly"
)
@javax.jdo.annotations.Inheritance(
        strategy = InheritanceStrategy.NEW_TABLE)
@DomainObject
public class CasePrimaryContentLinkForParty extends CasePrimaryContentLink {

    @DomainService(nature = NatureOfService.DOMAIN)
    public static class InstantiationSubscriber extends AbstractSubscriber {

        @EventHandler
        @Subscribe
        public void on(final InstantiateEvent ev) {
            if(ev.getPolymorphicReference() instanceof Party) {
                ev.setSubtype(CasePrimaryContentLinkForParty.class);
            }
        }
    }

    @Override
    public void setPolymorphicReference(final CaseContent polymorphicReference) {
        super.setPolymorphicReference(polymorphicReference);
        setParty((Party) polymorphicReference);
    }

    //region > party (property)
    private Party party;

    @Column(
            allowsNull = "false",
            name = "party_id"
    )
    @MemberOrder(sequence = "1")
    public Party getParty() {
        return party;
    }

    public void setParty(final Party party) {
        this.party = party;
    }
    //endregion

    //region > injected services
    @javax.inject.Inject
    private BookmarkService bookmarkService;
    //endregion

}
