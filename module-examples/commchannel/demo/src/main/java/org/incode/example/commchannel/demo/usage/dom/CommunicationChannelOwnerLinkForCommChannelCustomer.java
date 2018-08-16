package org.incode.example.commchannel.demo.usage.dom;

import javax.jdo.annotations.Column;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.InheritanceStrategy;

import org.apache.isis.applib.annotation.DomainObject;

import org.incode.example.commchannel.demo.shared.dom.CommChannelCustomer;
import org.incode.example.commchannel.dom.impl.ownerlink.CommunicationChannelOwnerLink;

import lombok.Getter;
import lombok.Setter;

@javax.jdo.annotations.PersistenceCapable(
        identityType= IdentityType.DATASTORE,
        schema ="commchannelDemoUsage"
)
@javax.jdo.annotations.Inheritance(strategy = InheritanceStrategy.NEW_TABLE)
@DomainObject
public class CommunicationChannelOwnerLinkForCommChannelCustomer extends CommunicationChannelOwnerLink {


    @Getter @Setter
    @Column(allowsNull = "false", name = "demoObjectId")
    private CommChannelCustomer commChannelCustomer;

    @Override
    public Object getOwner() {
        return this.getCommChannelCustomer();
    }

    @Override
    protected void setOwner(final Object object) {
        this.setCommChannelCustomer((CommChannelCustomer) object);
    }


}
