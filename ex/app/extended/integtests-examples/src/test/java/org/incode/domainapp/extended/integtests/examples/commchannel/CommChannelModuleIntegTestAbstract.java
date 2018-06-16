package org.incode.domainapp.extended.integtests.examples.commchannel;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.inject.Inject;

import org.junit.BeforeClass;

import org.apache.isis.core.integtestsupport.IntegrationTestAbstract2;

import org.isisaddons.module.fakedata.FakeDataModule;
import org.isisaddons.module.fakedata.dom.FakeDataService;

import org.incode.domainapp.extended.module.fixtures.per_cpt.examples.commchannel.dom.ccolink.demo.CommunicationChannelOwnerLinkForDemoObject_addEmailAddress;
import org.incode.domainapp.extended.module.fixtures.per_cpt.examples.commchannel.dom.ccolink.demo.CommunicationChannelOwnerLinkForDemoObject_addPhoneOrFaxNumber;
import org.incode.domainapp.extended.module.fixtures.per_cpt.examples.commchannel.dom.ccolink.demo.CommunicationChannelOwnerLinkForDemoObject_addPostalAddress;
import org.incode.domainapp.extended.module.fixtures.per_cpt.examples.commchannel.dom.ccolink.demo.CommunicationChannelOwnerLinkForDemoObject_communicationChannels;
import org.incode.domainapp.extended.module.fixtures.shared.FixturesModuleSharedSubmodule;
import org.incode.domainapp.extended.module.fixtures.shared.demo.dom.DemoObject;
import org.incode.example.commchannel.dom.api.GeocodingService;
import org.incode.example.commchannel.dom.impl.channel.CommunicationChannel;
import org.incode.example.commchannel.dom.impl.channel.CommunicationChannel_remove1;
import org.incode.example.commchannel.dom.impl.channel.T_communicationChannels;
import org.incode.example.commchannel.dom.impl.emailaddress.T_addEmailAddress;
import org.incode.example.commchannel.dom.impl.phoneorfax.T_addPhoneOrFaxNumber;
import org.incode.example.commchannel.dom.impl.postaladdress.T_addPostalAddress;
import org.incode.domainapp.extended.integtests.examples.commchannel.app.CommChannelModuleAppManifest;

public abstract class CommChannelModuleIntegTestAbstract extends IntegrationTestAbstract2 {

    @Inject
    protected FakeDataService fakeDataService;

    @BeforeClass
    public static void initClass() {
        bootstrapUsing(CommChannelModuleAppManifest.BUILDER
                .withAdditionalModules(
                        FixturesModuleSharedSubmodule.class,
                        CommChannelModuleIntegTestAbstract.class,
                        FakeDataModule.class
                )
                .withConfigurationProperty(GeocodingService.class.getCanonicalName() + ".demo", "true")
                .build()
        );
    }

    protected T_addEmailAddress mixinNewEmailAddress(final DemoObject owner) {
        return mixin(CommunicationChannelOwnerLinkForDemoObject_addEmailAddress.class, owner);
    }

    protected T_addPostalAddress mixinNewPostalAddress(final DemoObject owner) {
        return mixin(CommunicationChannelOwnerLinkForDemoObject_addPostalAddress.class, owner);
    }

    protected T_addPhoneOrFaxNumber mixinNewPhoneOrFaxNumber(final DemoObject owner) {
        return mixin(CommunicationChannelOwnerLinkForDemoObject_addPhoneOrFaxNumber.class, owner);
    }

    protected CommunicationChannel_remove1 mixinRemove(final CommunicationChannel channel) {
        return mixin(CommunicationChannel_remove1.class, channel);
    }

    protected T_communicationChannels mixinCommunicationChannels(final DemoObject owner) {
        return mixin(CommunicationChannelOwnerLinkForDemoObject_communicationChannels.class, owner);
    }

    /**
     * Tries to retrieve some content, 1 second timeout.
     */
    protected static boolean isInternetReachable()
    {
        try {
            final URL url = new URL("http://www.google.com");
            final HttpURLConnection urlConnect = (HttpURLConnection)url.openConnection();
            urlConnect.setConnectTimeout(1000);
            urlConnect.getContent();
            urlConnect.disconnect();
        } catch (IOException e) {
            return false;
        }
        return true;
    }

}
