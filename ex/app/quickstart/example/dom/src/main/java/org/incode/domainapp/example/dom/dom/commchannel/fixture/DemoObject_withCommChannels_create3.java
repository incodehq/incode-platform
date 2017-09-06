package org.incode.domainapp.example.dom.dom.commchannel.fixture;

import org.apache.isis.applib.fixturescripts.FixtureScript;

import org.incode.domainapp.example.dom.demo.dom.demo.DemoObject;
import org.incode.domainapp.example.dom.demo.dom.demo.DemoObjectMenu;
import org.incode.domainapp.example.dom.dom.commchannel.dom.ccolink.demo.CommunicationChannelOwnerLinkForDemoObject_addEmailAddress;
import org.incode.domainapp.example.dom.dom.commchannel.dom.ccolink.demo.CommunicationChannelOwnerLinkForDemoObject_addPhoneOrFaxNumber;
import org.incode.domainapp.example.dom.dom.commchannel.dom.ccolink.demo.CommunicationChannelOwnerLinkForDemoObject_addPostalAddress;
import org.incode.module.commchannel.dom.impl.emailaddress.T_addEmailAddress;
import org.incode.module.commchannel.dom.impl.phoneorfax.T_addPhoneOrFaxNumber;
import org.incode.module.commchannel.dom.impl.postaladdress.T_addPostalAddress;
import org.incode.module.commchannel.dom.impl.type.CommunicationChannelType;

public class DemoObject_withCommChannels_create3 extends FixtureScript {

    @Override
    protected void execute(final ExecutionContext executionContext) {

        final DemoObject demoOwner = create("Foo", executionContext);

        wrap(addEmailAddress(demoOwner)).$$("foo@example.com", "Other Email", null);
        wrap(addPhoneOrFaxNumber(demoOwner)).$$(CommunicationChannelType.PHONE_NUMBER, "555 1234", "Home Number", null);
        wrap(addPhoneOrFaxNumber(demoOwner)).$$(CommunicationChannelType.FAX_NUMBER, "555 4321", "Work Fax", null);

        final DemoObject bar = create("Bar", executionContext);
        wrap(addPostalAddress(demoOwner)).$$(
                "45", "High Street", "Oxford", null, null, "UK", "Shipping Address", null, false);

        final DemoObject baz = create("Baz", executionContext);
    }


    T_addEmailAddress addEmailAddress(final DemoObject demoOwner) {
        return mixin(CommunicationChannelOwnerLinkForDemoObject_addEmailAddress.class, demoOwner);
    }

    T_addPhoneOrFaxNumber addPhoneOrFaxNumber(final DemoObject demoOwner) {
        return mixin(CommunicationChannelOwnerLinkForDemoObject_addPhoneOrFaxNumber.class, demoOwner);
    }

    T_addPostalAddress addPostalAddress(final DemoObject demoOwner) {
        return mixin(CommunicationChannelOwnerLinkForDemoObject_addPostalAddress.class, demoOwner);
    }



    private DemoObject create(
            final String name,
            final ExecutionContext executionContext) {

        return executionContext.addResult(this, wrap(demoObjectMenu).createDemoObject(name));
    }


    @javax.inject.Inject
    DemoObjectMenu demoObjectMenu;


}
