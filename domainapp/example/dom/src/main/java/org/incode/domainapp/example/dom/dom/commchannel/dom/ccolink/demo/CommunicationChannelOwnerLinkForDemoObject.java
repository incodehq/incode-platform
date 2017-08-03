package org.incode.domainapp.example.dom.dom.commchannel.dom.ccolink.demo;

import javax.jdo.annotations.Column;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.InheritanceStrategy;

import org.apache.isis.applib.annotation.DomainObject;
import org.apache.isis.applib.annotation.DomainService;
import org.apache.isis.applib.annotation.Mixin;
import org.apache.isis.applib.annotation.NatureOfService;

import org.incode.module.commchannel.dom.impl.channel.T_communicationChannels;
import org.incode.module.commchannel.dom.impl.emailaddress.T_addEmailAddress;
import org.incode.module.commchannel.dom.impl.ownerlink.CommunicationChannelOwnerLink;
import org.incode.module.commchannel.dom.impl.ownerlink.CommunicationChannelOwnerLinkRepository;
import org.incode.module.commchannel.dom.impl.phoneorfax.T_addPhoneOrFaxNumber;
import org.incode.module.commchannel.dom.impl.postaladdress.T_addPostalAddress;

import org.incode.domainapp.example.dom.dom.commchannel.dom.demo.CommChannelDemoObject;

@javax.jdo.annotations.PersistenceCapable(
        identityType= IdentityType.DATASTORE,
        schema ="exampleDomCommChannel"
)
@javax.jdo.annotations.Inheritance(strategy = InheritanceStrategy.NEW_TABLE)
@DomainObject
public class CommunicationChannelOwnerLinkForDemoObject extends CommunicationChannelOwnerLink {

    //region > demoObject (property)
    private CommChannelDemoObject demoObject;

    @Column(
            allowsNull = "false",
            name = "demoObjectId"
    )
    public CommChannelDemoObject getDemoObject() {
        return demoObject;
    }

    public void setDemoObject(final CommChannelDemoObject demoObject) {
        this.demoObject = demoObject;
    }
    //endregion

    //region > owner (hook, derived)
    @Override
    public Object getOwner() {
        return getDemoObject();
    }

    @Override
    protected void setOwner(final Object object) {
        setDemoObject((CommChannelDemoObject) object);
    }
    //endregion

    //region > SubtypeProvider SPI implementation
    @DomainService(nature = NatureOfService.DOMAIN)
    public static class SubtypeProvider extends CommunicationChannelOwnerLinkRepository.SubtypeProviderAbstract {
        public SubtypeProvider() {
            super(CommChannelDemoObject.class, CommunicationChannelOwnerLinkForDemoObject.class);
        }
    }
    //endregion

    //region > mixins

    @Mixin
    public static class _communicationChannels extends T_communicationChannels<CommChannelDemoObject> {
        public _communicationChannels(final CommChannelDemoObject owner) {
            super(owner);
        }
    }

    @Mixin
    public static class _addEmailAddress extends T_addEmailAddress<CommChannelDemoObject> {
        public _addEmailAddress(final CommChannelDemoObject owner) {
            super(owner);
        }
    }

    @Mixin
    public static class _addPhoneOrFaxNumber extends T_addPhoneOrFaxNumber<CommChannelDemoObject> {
        public _addPhoneOrFaxNumber(final CommChannelDemoObject owner) {
            super(owner);
        }
    }

    @Mixin
    public static class _addPostalAddress extends T_addPostalAddress<CommChannelDemoObject> {
        public _addPostalAddress(final CommChannelDemoObject owner) {
            super(owner);
        }
    }

    //endregion

}
