package org.incode.example.document.demo.usage;

import java.util.Set;

import javax.xml.bind.annotation.XmlRootElement;

import com.google.common.collect.Sets;

import org.apache.isis.applib.Module;
import org.apache.isis.applib.ModuleAbstract;
import org.apache.isis.applib.fixturescripts.FixtureScript;
import org.apache.isis.applib.fixturescripts.teardown.TeardownFixtureAbstract2;

import org.isisaddons.module.command.CommandModule;
import org.isisaddons.module.fakedata.FakeDataModule;

import org.incode.example.document.demo.usage.dom.paperclips.demowithurl.PaperclipForDemoObjectWithUrl;
import org.incode.example.document.demo.usage.dom.paperclips.other.PaperclipForOtherObject;
import org.incode.example.document.demo.shared.demowithurl.FixturesModuleSharedDemoWithUrlSubmodule;
import org.incode.example.document.demo.shared.other.FixturesModuleSharedOtherSubmodule;
import org.incode.example.docrendering.freemarker.FreemarkerDocRenderingModule;
import org.incode.example.docrendering.stringinterpolator.StringInterpolatorDocRenderingModule;
import org.incode.example.docrendering.xdocreport.XDocReportDocRenderingModule;
import org.incode.example.document.DocumentModule;

@XmlRootElement(name = "module")
public class DocumentDemoUsageModule extends ModuleAbstract {

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
