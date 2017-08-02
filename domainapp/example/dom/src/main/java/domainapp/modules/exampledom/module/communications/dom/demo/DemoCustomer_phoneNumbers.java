package domainapp.modules.exampledom.module.communications.dom.demo;

import org.apache.isis.applib.annotation.Mixin;

import org.incode.module.communications.dom.impl.commchannel.CommunicationChannelOwner_phoneNumberTitles;

@Mixin(method = "prop")
public class DemoCustomer_phoneNumbers extends
        CommunicationChannelOwner_phoneNumberTitles {

    public DemoCustomer_phoneNumbers(final DemoCustomer demoCustomer) {
        super(demoCustomer, " | ");
    }

}
