package org.incode.example.communications.demo.usage;

import java.util.Set;

import javax.xml.bind.annotation.XmlRootElement;

import com.google.common.collect.Sets;

import org.apache.isis.applib.Module;
import org.apache.isis.applib.ModuleAbstract;
import org.apache.isis.applib.fixturescripts.FixtureScript;
import org.apache.isis.applib.fixturescripts.teardown.TeardownFixtureAbstract2;

import org.isisaddons.module.freemarker.dom.FreeMarkerModule;
import org.isisaddons.module.pdfbox.dom.PdfBoxModule;

import org.incode.example.communications.demo.shared.demowithnotes.CommunicationsDemoSharedCustomerSubmodule;
import org.incode.example.communications.CommunicationsModule;
import org.incode.example.communications.demo.usage.dom.commchannels.CommunicationChannelOwnerLinkForCustomer;
import org.incode.example.communications.demo.usage.dom.paperclips.PaperclipForDemoInvoice;

@XmlRootElement(name = "module")
public class CommunicationsDemoUsageModule extends ModuleAbstract {

    @Override
    public Set<Module> getDependencies() {
        return Sets.newHashSet(
                new CommunicationsModule(),
                new CommunicationsDemoSharedCustomerSubmodule(),
                new PdfBoxModule(),
                new FreeMarkerModule()
            );
    }

    @Override
    public FixtureScript getTeardownFixture() {
        return new TeardownFixtureAbstract2() {
            @Override
            protected void execute(final FixtureScript.ExecutionContext executionContext) {
                deleteFrom(PaperclipForDemoInvoice.class);
                deleteFrom(CommunicationChannelOwnerLinkForCustomer.class);
            }
        };
    }

    public static class PropertyDomainEvent<S,T>
            extends org.apache.isis.applib.services.eventbus.PropertyDomainEvent<S,T> {}
    public static class CollectionDomainEvent<S,T>
            extends org.apache.isis.applib.services.eventbus.CollectionDomainEvent<S,T> {}
    public static class ActionDomainEvent<S> extends
            org.apache.isis.applib.services.eventbus.ActionDomainEvent<S> {}


}
