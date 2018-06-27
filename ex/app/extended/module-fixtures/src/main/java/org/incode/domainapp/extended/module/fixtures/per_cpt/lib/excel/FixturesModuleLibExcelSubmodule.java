package org.incode.domainapp.extended.module.fixtures.per_cpt.lib.excel;

import java.util.Set;

import javax.xml.bind.annotation.XmlRootElement;

import com.google.common.collect.Sets;

import org.apache.isis.applib.Module;
import org.apache.isis.applib.ModuleAbstract;
import org.apache.isis.applib.fixturescripts.FixtureScript;

import org.isisaddons.module.excel.ExcelModule;

import org.incode.domainapp.extended.module.fixtures.per_cpt.lib.excel.fixture.DemoToDoItem_tearDown;
import org.incode.domainapp.extended.module.fixtures.shared.demo.FixturesModuleSharedDemoSubmodule;

@XmlRootElement(name = "module")
public class FixturesModuleLibExcelSubmodule extends ModuleAbstract {
    @Override public Set<Module> getDependencies() {
        return Sets.newHashSet(
                new ExcelModule(),
                new FixturesModuleSharedDemoSubmodule()
        );
    }

    @Override public FixtureScript getTeardownFixture() {
        return new DemoToDoItem_tearDown();
    }

}
