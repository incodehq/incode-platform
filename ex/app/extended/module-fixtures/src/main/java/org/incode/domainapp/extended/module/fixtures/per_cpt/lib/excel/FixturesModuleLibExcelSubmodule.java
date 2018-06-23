package org.incode.domainapp.extended.module.fixtures.per_cpt.lib.excel;

import java.util.Set;

import javax.xml.bind.annotation.XmlRootElement;

import com.google.common.collect.Sets;

import org.apache.isis.applib.Module;
import org.apache.isis.applib.ModuleAbstract;

import org.incode.examples.commchannel.demo.shared.demo.FixturesModuleSharedDemoSubmodule;
import org.incode.examples.commchannel.demo.shared.todo.FixturesModuleSharedTodoSubmodule;

@XmlRootElement(name = "module")
public class FixturesModuleLibExcelSubmodule extends ModuleAbstract {
    @Override public Set<Module> getDependencies() {
        return Sets.newHashSet(
                new FixturesModuleSharedTodoSubmodule(),
                new FixturesModuleSharedDemoSubmodule()
        );
    }
}
