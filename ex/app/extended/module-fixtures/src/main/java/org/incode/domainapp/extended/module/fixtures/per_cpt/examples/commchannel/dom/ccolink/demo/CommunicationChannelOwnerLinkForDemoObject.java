package org.incode.domainapp.extended.module.fixtures.per_cpt.examples.commchannel.dom.ccolink.demo;

import javax.jdo.annotations.Column;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.InheritanceStrategy;

import org.apache.isis.applib.annotation.DomainObject;

import org.incode.domainapp.extended.module.fixtures.shared.demo.dom.DemoObject;
import org.incode.example.commchannel.dom.impl.ownerlink.CommunicationChannelOwnerLink;

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
