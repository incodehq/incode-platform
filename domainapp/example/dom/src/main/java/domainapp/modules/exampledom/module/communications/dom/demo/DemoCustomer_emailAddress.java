package domainapp.modules.exampledom.module.communications.dom.demo;

import org.apache.isis.applib.annotation.Mixin;

import org.incode.module.communications.dom.impl.commchannel.CommunicationChannelOwner_emailAddressTitles;

@Mixin(method = "prop")
public class DemoCustomer_emailAddress extends
        CommunicationChannelOwner_emailAddressTitles {

    public DemoCustomer_emailAddress(final DemoCustomer demoCustomer) {
        super(demoCustomer, " | ");
    }


}
