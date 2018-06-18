package org.incode.domainapp.extended.module.fixtures.per_cpt.examples.communications;

import java.util.Set;

import javax.xml.bind.annotation.XmlRootElement;

import com.google.common.collect.Sets;

import org.apache.isis.applib.Module;
import org.apache.isis.applib.ModuleAbstract;
import org.apache.isis.applib.fixturescripts.FixtureScript;
import org.apache.isis.applib.fixturescripts.teardown.TeardownFixtureAbstract2;

import org.isisaddons.module.freemarker.dom.FreeMarkerModule;
import org.isisaddons.module.pdfbox.dom.PdfBoxModule;

import org.incode.domainapp.extended.module.fixtures.per_cpt.examples.communications.dom.commchannels.CommunicationChannelOwnerLinkForDemoObjectWithNotes;
import org.incode.domainapp.extended.module.fixtures.per_cpt.examples.communications.dom.paperclips.PaperclipForDemoInvoice;
import org.incode.domainapp.extended.module.fixtures.shared.demowithnotes.FixturesModuleSharedDemoWithNotesSubmodule;
import org.incode.example.communications.CommunicationsModule;

@XmlRootElement(name = "module")
public class FixturesModuleExamplesCommunicationsIntegrationSubmodule extends ModuleAbstract {

    @Override
    public Set<Module> getDependencies() {
        return Sets.newHashSet(
                new CommunicationsModule(),
                new FixturesModuleSharedDemoWithNotesSubmodule(),
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
                deleteFrom(CommunicationChannelOwnerLinkForDemoObjectWithNotes.class);
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
