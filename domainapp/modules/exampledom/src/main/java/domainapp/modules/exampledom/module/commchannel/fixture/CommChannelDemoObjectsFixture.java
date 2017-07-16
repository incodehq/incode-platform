package domainapp.modules.exampledom.module.commchannel.fixture;

import org.apache.isis.applib.fixturescripts.DiscoverableFixtureScript;
import org.apache.isis.applib.services.clock.ClockService;

import org.incode.module.commchannel.dom.impl.emailaddress.T_addEmailAddress;
import org.incode.module.commchannel.dom.impl.phoneorfax.T_addPhoneOrFaxNumber;
import org.incode.module.commchannel.dom.impl.postaladdress.T_addPostalAddress;
import org.incode.module.commchannel.dom.impl.type.CommunicationChannelType;
import domainapp.modules.exampledom.module.commchannel.dom.demo.CommChannelDemoObject;
import domainapp.modules.exampledom.module.commchannel.dom.demo.CommChannelDemoObjectMenu;
import domainapp.modules.exampledom.module.commchannel.dom.demolink.CommunicationChannelOwnerLinkForDemoObject;

public class CommChannelDemoObjectsFixture extends DiscoverableFixtureScript {

    public CommChannelDemoObjectsFixture() {
        withDiscoverability(Discoverability.DISCOVERABLE);
    }

    @Override
    protected void execute(final ExecutionContext executionContext) {

    // prereqs
	executionContext.executeChild(this, new CommChannelDemoObjectsTearDownFixture());

        final CommChannelDemoObject demoOwner = create("Foo", executionContext);

        wrap(addEmailAddress(demoOwner)).$$("foo@example.com", "Other Email", null);
        wrap(addPhoneOrFaxNumber(demoOwner)).$$(CommunicationChannelType.PHONE_NUMBER, "555 1234", "Home Number", null);
        wrap(addPhoneOrFaxNumber(demoOwner)).$$(CommunicationChannelType.FAX_NUMBER, "555 4321", "Work Fax", null);

        final CommChannelDemoObject bar = create("Bar", executionContext);
        wrap(addPostalAddress(demoOwner)).$$(
                "45", "High Street", "Oxford", null, null, "UK", "Shipping Address", null, false);

        final CommChannelDemoObject baz = create("Baz", executionContext);
    }

    T_addEmailAddress addEmailAddress(final CommChannelDemoObject demoOwner) {
        return mixin(CommunicationChannelOwnerLinkForDemoObject._addEmailAddress.class, demoOwner);
    }

    T_addPhoneOrFaxNumber addPhoneOrFaxNumber(final CommChannelDemoObject demoOwner) {
        return mixin(CommunicationChannelOwnerLinkForDemoObject._addPhoneOrFaxNumber.class, demoOwner);
    }

    T_addPostalAddress addPostalAddress(final CommChannelDemoObject demoOwner) {
        return mixin(CommunicationChannelOwnerLinkForDemoObject._addPostalAddress.class, demoOwner);
    }

    // //////////////////////////////////////

    private CommChannelDemoObject create(
            final String name,
            final ExecutionContext executionContext) {

        return executionContext.addResult(this, wrap(commChannelDemoObjectMenu).create(name));
    }

    // //////////////////////////////////////

    @javax.inject.Inject
    CommChannelDemoObjectMenu commChannelDemoObjectMenu;
    @javax.inject.Inject
    ClockService clockService;

}
