package org.incode.module.commchannel.integtests;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.inject.Inject;

import org.junit.BeforeClass;

import org.apache.isis.core.integtestsupport.IntegrationTestAbstract2;

import org.isisaddons.module.fakedata.FakeDataModule;
import org.isisaddons.module.fakedata.dom.FakeDataService;

import org.incode.module.commchannel.app.CommChannelModuleAppManifest;
import org.incode.module.commchannel.dom.api.GeocodingService;
import org.incode.module.commchannel.dom.impl.channel.CommunicationChannel;
import org.incode.module.commchannel.dom.impl.channel.CommunicationChannel_remove1;
import org.incode.module.commchannel.dom.impl.channel.T_communicationChannels;
import org.incode.module.commchannel.dom.impl.emailaddress.T_addEmailAddress;
import org.incode.module.commchannel.dom.impl.phoneorfax.T_addPhoneOrFaxNumber;
import org.incode.module.commchannel.dom.impl.postaladdress.T_addPostalAddress;

import domainapp.modules.exampledom.module.commchannel.dom.ccolink.demo.CommunicationChannelOwnerLinkForDemoObject;
import domainapp.modules.exampledom.module.commchannel.dom.demo.CommChannelDemoObject;

public abstract class CommChannelModuleIntegTestAbstract extends IntegrationTestAbstract2 {

    @Inject
    protected FakeDataService fakeDataService;

    @BeforeClass
    public static void initClass() {
        bootstrapUsing(CommChannelModuleAppManifest.BUILDER
                .withAdditionalModules(CommChannelModuleIntegTestAbstract.class, FakeDataModule.class)
                .withConfigurationProperty(GeocodingService.class.getCanonicalName() + ".demo", "true")
                .build()
        );
    }

    protected T_addEmailAddress mixinNewEmailAddress(final CommChannelDemoObject owner) {
        return mixin(CommunicationChannelOwnerLinkForDemoObject._addEmailAddress.class, owner);
    }

    protected T_addPostalAddress mixinNewPostalAddress(final CommChannelDemoObject owner) {
        return mixin(CommunicationChannelOwnerLinkForDemoObject._addPostalAddress.class, owner);
    }

    protected T_addPhoneOrFaxNumber mixinNewPhoneOrFaxNumber(final CommChannelDemoObject owner) {
        return mixin(CommunicationChannelOwnerLinkForDemoObject._addPhoneOrFaxNumber.class, owner);
    }

    protected CommunicationChannel_remove1 mixinRemove(final CommunicationChannel channel) {
        return mixin(CommunicationChannel_remove1.class, channel);
    }

    protected T_communicationChannels mixinCommunicationChannels(final CommChannelDemoObject owner) {
        return mixin(CommunicationChannelOwnerLinkForDemoObject._communicationChannels.class, owner);
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
