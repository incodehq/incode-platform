package org.incode.example.commchannel.demo.usage.fixture;
//package org.incode.examples.commchannel.demo.usage.fixture;

import org.apache.isis.applib.fixturescripts.FixtureScript;

import org.incode.example.commchannel.demo.shared.dom.CommChannelCustomer;
import org.incode.example.commchannel.demo.shared.dom.CommChannelCustomerMenu;
import org.incode.example.commchannel.demo.usage.contributions.CommChannelCustomer_addEmailAddress;
import org.incode.example.commchannel.demo.usage.contributions.CommChannelCustomer_addPhoneOrFaxNumber;
import org.incode.example.commchannel.demo.usage.contributions.CommChannelCustomer_addPostalAddress;
import org.incode.example.commchannel.dom.impl.emailaddress.T_addEmailAddress;
import org.incode.example.commchannel.dom.impl.phoneorfax.T_addPhoneOrFaxNumber;
import org.incode.example.commchannel.dom.impl.postaladdress.T_addPostalAddress;
import org.incode.example.commchannel.dom.impl.type.CommunicationChannelType;

public class CommChannelCustomer_withCommChannels_create3 extends FixtureScript {

    @Override
    protected void execute(final ExecutionContext executionContext) {

        final CommChannelCustomer demoOwner = create("Foo", executionContext);

        wrap(addEmailAddress(demoOwner)).$$("foo@example.com", "Other Email", null);
        wrap(addPhoneOrFaxNumber(demoOwner)).$$(CommunicationChannelType.PHONE_NUMBER, "555 1234", "Home Number", null);
        wrap(addPhoneOrFaxNumber(demoOwner)).$$(CommunicationChannelType.FAX_NUMBER, "555 4321", "Work Fax", null);

        final CommChannelCustomer bar = create("Bar", executionContext);
        wrap(addPostalAddress(demoOwner)).$$(
                "45", "High Street", "Oxford", null, null, "UK", "Shipping Address", null, false);

        final CommChannelCustomer baz = create("Baz", executionContext);
    }


    T_addEmailAddress addEmailAddress(final CommChannelCustomer demoOwner) {
        return mixin(CommChannelCustomer_addEmailAddress.class, demoOwner);
    }

    T_addPhoneOrFaxNumber addPhoneOrFaxNumber(final CommChannelCustomer demoOwner) {
        return mixin(CommChannelCustomer_addPhoneOrFaxNumber.class, demoOwner);
    }

    T_addPostalAddress addPostalAddress(final CommChannelCustomer demoOwner) {
        return mixin(CommChannelCustomer_addPostalAddress.class, demoOwner);
    }



    private CommChannelCustomer create(
            final String name,
            final ExecutionContext executionContext) {

        return executionContext.addResult(this, wrap(demoObjectMenu).createDemoObject(name));
    }


    @javax.inject.Inject
    CommChannelCustomerMenu demoObjectMenu;


}
