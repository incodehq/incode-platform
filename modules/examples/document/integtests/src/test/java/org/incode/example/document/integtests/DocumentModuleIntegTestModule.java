package org.incode.example.document.integtests;

import java.util.Set;

import javax.xml.bind.annotation.XmlRootElement;

import com.google.common.collect.Sets;

import org.apache.isis.applib.Module;
import org.apache.isis.applib.ModuleAbstract;

import org.isisaddons.module.fakedata.FakeDataModule;

import org.incode.example.document.demo.usage.DocumentDemoUsageModule;

@XmlRootElement(name = "module")
public class DocumentModuleIntegTestModule extends ModuleAbstract {
    @Override
    public Set<Module> getDependencies() {
        return Sets.newHashSet(
                new DocumentDemoUsageModule(),
                new FakeDataModule()
        );
    }
}
