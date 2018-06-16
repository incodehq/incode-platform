package org.incode.domainapp.extended.module.fixtures.per_cpt.examples.document;

import java.util.Set;

import javax.xml.bind.annotation.XmlRootElement;

import com.google.common.collect.Sets;

import org.apache.isis.applib.Module;
import org.apache.isis.applib.ModuleAbstract;
import org.apache.isis.applib.fixturescripts.FixtureScript;
import org.apache.isis.applib.fixturescripts.teardown.TeardownFixtureAbstract2;

import org.isisaddons.module.command.CommandModule;
import org.isisaddons.module.fakedata.FakeDataModule;

import org.incode.domainapp.extended.module.fixtures.per_cpt.examples.document.dom.paperclips.demowithurl.PaperclipForDemoObjectWithUrl;
import org.incode.domainapp.extended.module.fixtures.per_cpt.examples.document.dom.paperclips.other.PaperclipForOtherObject;
import org.incode.domainapp.extended.module.fixtures.shared.demowithurl.FixturesModuleSharedDemoWithUrlSubmodule;
import org.incode.domainapp.extended.module.fixtures.shared.other.FixturesModuleSharedOtherSubmodule;
import org.incode.example.docrendering.freemarker.dom.FreemarkerDocRenderingModule;
import org.incode.example.docrendering.stringinterpolator.dom.StringInterpolatorDocRenderingModule;
import org.incode.example.docrendering.xdocreport.dom.XDocReportDocRenderingModule;
import org.incode.example.document.dom.DocumentModule;

@XmlRootElement(name = "module")
public class FixturesModuleExamplesDocumentIntegrationSubmodule extends ModuleAbstract {

    @Override public Set<Module> getDependencies() {
        return Sets.newHashSet(
                    new DocumentModule(),
                    new FixturesModuleSharedDemoWithUrlSubmodule(),
                    new FixturesModuleSharedOtherSubmodule(),
                    new FreemarkerDocRenderingModule(),
                    new StringInterpolatorDocRenderingModule(),
                    new XDocReportDocRenderingModule(),
                    new CommandModule(),
                    new FakeDataModule()
            );
    }

    @Override
    public FixtureScript getTeardownFixture() {
        return new TeardownFixtureAbstract2() {

            @Override
            protected void execute(final FixtureScript.ExecutionContext executionContext) {
                deleteFrom(PaperclipForDemoObjectWithUrl.class);
                deleteFrom(PaperclipForOtherObject.class);
            }

        };
    }

}