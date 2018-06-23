package org.incode.domainapp.extended.module.fixtures.per_cpt.examples.commchannel.fixture;
//package org.incode.example.alias.demo.examples.commchannel.fixture;

import org.apache.isis.applib.fixturescripts.FixtureScript;

import org.incode.domainapp.extended.module.fixtures.shared.demo.dom.DemoObject;
import org.incode.domainapp.extended.module.fixtures.shared.demo.dom.DemoObjectMenu;
import org.incode.example.alias.demo.examples.commchannel.contributions.DemoObject_addEmailAddress;
import org.incode.example.alias.demo.examples.commchannel.contributions.DemoObject_addPhoneOrFaxNumber;
import org.incode.example.alias.demo.examples.commchannel.contributions.DemoObject_addPostalAddress;
import org.incode.example.commchannel.dom.impl.emailaddress.T_addEmailAddress;
import org.incode.example.commchannel.dom.impl.phoneorfax.T_addPhoneOrFaxNumber;
import org.incode.example.commchannel.dom.impl.postaladdress.T_addPostalAddress;
import org.incode.example.commchannel.dom.impl.type.CommunicationChannelType;

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
        return mixin(DemoObject_addEmailAddress.class, demoOwner);
    }

    T_addPhoneOrFaxNumber addPhoneOrFaxNumber(final DemoObject demoOwner) {
        return mixin(DemoObject_addPhoneOrFaxNumber.class, demoOwner);
    }

    T_addPostalAddress addPostalAddress(final DemoObject demoOwner) {
        return mixin(DemoObject_addPostalAddress.class, demoOwner);
    }



    private DemoObject create(
            final String name,
            final ExecutionContext executionContext) {

        return executionContext.addResult(this, wrap(demoObjectMenu).createDemoObject(name));
    }


    @javax.inject.Inject
    DemoObjectMenu demoObjectMenu;


}
