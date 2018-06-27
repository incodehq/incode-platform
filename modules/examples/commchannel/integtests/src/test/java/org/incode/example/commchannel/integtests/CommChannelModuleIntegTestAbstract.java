package org.incode.example.commchannel.integtests;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.inject.Inject;

import org.apache.isis.applib.ModuleAbstract;
import org.apache.isis.core.integtestsupport.IntegrationTestAbstract3;

import org.isisaddons.module.fakedata.dom.FakeDataService;

import org.incode.example.commchannel.demo.shared.dom.CommChannelCustomer;
import org.incode.example.commchannel.demo.usage.contributions.CommChannelCustomer_addEmailAddress;
import org.incode.example.commchannel.demo.usage.contributions.CommChannelCustomer_addPhoneOrFaxNumber;
import org.incode.example.commchannel.demo.usage.contributions.CommChannelCustomer_addPostalAddress;
import org.incode.example.commchannel.demo.usage.contributions.CommChannelCustomer_communicationChannels;
import org.incode.example.commchannel.dom.impl.channel.CommunicationChannel;
import org.incode.example.commchannel.dom.impl.channel.CommunicationChannel_remove1;
import org.incode.example.commchannel.dom.impl.channel.T_communicationChannels;
import org.incode.example.commchannel.dom.impl.emailaddress.T_addEmailAddress;
import org.incode.example.commchannel.dom.impl.phoneorfax.T_addPhoneOrFaxNumber;
import org.incode.example.commchannel.dom.impl.postaladdress.T_addPostalAddress;

public abstract class CommChannelModuleIntegTestAbstract extends IntegrationTestAbstract3 {

    public static ModuleAbstract module() {
        return new CommChannelModuleIntegTestModule();
    }

    protected CommChannelModuleIntegTestAbstract() {
        super(module());
    }


    protected T_addEmailAddress mixinNewEmailAddress(final CommChannelCustomer owner) {
        return mixin(CommChannelCustomer_addEmailAddress.class, owner);
    }

    protected T_addPostalAddress mixinNewPostalAddress(final CommChannelCustomer owner) {
        return mixin(CommChannelCustomer_addPostalAddress.class, owner);
    }

    protected T_addPhoneOrFaxNumber mixinNewPhoneOrFaxNumber(final CommChannelCustomer owner) {
        return mixin(CommChannelCustomer_addPhoneOrFaxNumber.class, owner);
    }

    protected CommunicationChannel_remove1 mixinRemove(final CommunicationChannel channel) {
        return mixin(CommunicationChannel_remove1.class, channel);
    }

    protected T_communicationChannels mixinCommunicationChannels(final CommChannelCustomer owner) {
        return mixin(CommChannelCustomer_communicationChannels.class, owner);
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

    @Inject
    protected FakeDataService fakeDataService;


}
