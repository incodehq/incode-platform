package org.incode.domainapp.example.dom.dom.commchannel.dom.ccolink.demo;

import javax.jdo.annotations.Column;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.InheritanceStrategy;

import org.apache.isis.applib.annotation.DomainObject;

import org.incode.domainapp.example.dom.demo.dom.demo.DemoObject;
import org.incode.module.commchannel.dom.impl.ownerlink.CommunicationChannelOwnerLink;

import lombok.Getter;
import lombok.Setter;

@javax.jdo.annotations.PersistenceCapable(
        identityType= IdentityType.DATASTORE,
        schema ="exampleDomCommChannel"
)
@javax.jdo.annotations.Inheritance(strategy = InheritanceStrategy.NEW_TABLE)
@DomainObject
public class CommunicationChannelOwnerLinkForDemoObject extends CommunicationChannelOwnerLink {


    @Getter @Setter
    @Column(allowsNull = "false", name = "demoObjectId")
    private DemoObject demoObject;

    @Override
    public Object getOwner() {
        return getDemoObject();
    }

    @Override
    protected void setOwner(final Object object) {
        setDemoObject((DemoObject) object);
    }


}
