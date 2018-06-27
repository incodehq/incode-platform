package org.incode.domainapp.extended.module.fixtures.per_cpt.lib.docx;

import java.util.Set;

import javax.xml.bind.annotation.XmlRootElement;

import com.google.common.collect.Sets;

import org.apache.isis.applib.Module;
import org.apache.isis.applib.ModuleAbstract;
import org.apache.isis.applib.fixturescripts.FixtureScript;

import org.isisaddons.module.docx.DocxModule;

import org.incode.domainapp.extended.module.fixtures.per_cpt.lib.docx.fixture.order.DemoOrderAndOrderLine_tearDown;

@XmlRootElement(name = "module")
public class FixturesModuleLibDocxSubmodule extends ModuleAbstract {

    @Override public Set<Module> getDependencies() {
        return Sets.newHashSet(
                new DocxModule()
        );
    }

    @Override
    public FixtureScript getTeardownFixture() {
        return new DemoOrderAndOrderLine_tearDown();
    }

}
