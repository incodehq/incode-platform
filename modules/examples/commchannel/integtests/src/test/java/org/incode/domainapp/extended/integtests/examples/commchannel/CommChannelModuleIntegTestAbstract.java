package org.incode.domainapp.extended.integtests.examples.commchannel;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.inject.Inject;

import org.apache.isis.applib.ModuleAbstract;
import org.apache.isis.core.integtestsupport.IntegrationTestAbstract3;

import org.isisaddons.module.fakedata.dom.FakeDataService;

import org.incode.example.alias.demo.examples.commchannel.contributions.DemoObject_addEmailAddress;
import org.incode.example.alias.demo.examples.commchannel.contributions.DemoObject_addPhoneOrFaxNumber;
import org.incode.example.alias.demo.examples.commchannel.contributions.DemoObject_addPostalAddress;
import org.incode.example.alias.demo.examples.commchannel.contributions.DemoObject_communicationChannels;
import org.incode.domainapp.extended.module.fixtures.shared.demo.dom.DemoObject;
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


    protected T_addEmailAddress mixinNewEmailAddress(final DemoObject owner) {
        return mixin(DemoObject_addEmailAddress.class, owner);
    }

    protected T_addPostalAddress mixinNewPostalAddress(final DemoObject owner) {
        return mixin(DemoObject_addPostalAddress.class, owner);
    }

    protected T_addPhoneOrFaxNumber mixinNewPhoneOrFaxNumber(final DemoObject owner) {
        return mixin(DemoObject_addPhoneOrFaxNumber.class, owner);
    }

    protected CommunicationChannel_remove1 mixinRemove(final CommunicationChannel channel) {
        return mixin(CommunicationChannel_remove1.class, channel);
    }

    protected T_communicationChannels mixinCommunicationChannels(final DemoObject owner) {
        return mixin(DemoObject_communicationChannels.class, owner);
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
