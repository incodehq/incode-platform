package org.isisaddons.module.docx.fixture;

import java.util.Set;

import javax.xml.bind.annotation.XmlRootElement;

import com.google.common.collect.Sets;

import org.apache.isis.applib.Module;
import org.apache.isis.applib.ModuleAbstract;
import org.apache.isis.applib.fixturescripts.FixtureScript;

import org.isisaddons.module.docx.DocxModule;
import org.isisaddons.module.docx.fixture.fixturescripts.DocxDemoOrderAndOrderLine_tearDown;

@XmlRootElement(name = "module")
public class DocxFixturesModule extends ModuleAbstract {

    @Override public Set<Module> getDependencies() {
        return Sets.newHashSet(new DocxModule());
    }

    @Override
    public FixtureScript getTeardownFixture() {
        return new DocxDemoOrderAndOrderLine_tearDown();
    }

}
