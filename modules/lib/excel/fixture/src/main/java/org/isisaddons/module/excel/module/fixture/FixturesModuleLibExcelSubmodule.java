package org.isisaddons.module.excel.module.fixture;

import java.util.Set;

import javax.xml.bind.annotation.XmlRootElement;

import com.google.common.collect.Sets;

import org.apache.isis.applib.Module;
import org.apache.isis.applib.ModuleAbstract;

import org.isisaddons.module.excel.ExcelModule;

@XmlRootElement(name = "module")
public class FixturesModuleLibExcelSubmodule extends ModuleAbstract {

    @Override public Set<Module> getDependencies() {
        return Sets.newHashSet(
                new ExcelModule(),
                new FixturesModuleSharedTodoSubmodule()
        );
    }


}
