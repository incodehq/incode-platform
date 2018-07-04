package org.incode.example.commchannel.demo.usage;

import java.util.Set;

import javax.xml.bind.annotation.XmlRootElement;

import com.google.common.collect.Sets;

import org.apache.isis.applib.Module;
import org.apache.isis.applib.ModuleAbstract;
import org.apache.isis.applib.fixturescripts.FixtureScript;
import org.apache.isis.applib.fixturescripts.teardown.TeardownFixtureAbstract2;

import org.incode.example.commchannel.CommChannelModule;
import org.incode.example.commchannel.demo.shared.CommChannelDemoSharedModule;
import org.incode.example.commchannel.demo.usage.dom.CommunicationChannelOwnerLinkForCommChannelCustomer;

@XmlRootElement(name = "module")
public class CommChannelDemoUsageModule extends ModuleAbstract {

    @Override public Set<Module> getDependencies() {
        return Sets.newHashSet(
                new CommChannelModule(),
                new CommChannelDemoSharedModule()
        );
    }

    @Override public FixtureScript getTeardownFixture() {
        return new TeardownFixtureAbstract2() {
            @Override protected void execute(final ExecutionContext executionContext) {
                deleteFrom(CommunicationChannelOwnerLinkForCommChannelCustomer.class);
            }
        };
    }
}
