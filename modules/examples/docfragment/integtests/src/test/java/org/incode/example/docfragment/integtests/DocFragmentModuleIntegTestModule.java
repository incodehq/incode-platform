package org.incode.example.docfragment.integtests;

import java.util.Set;

import javax.xml.bind.annotation.XmlRootElement;

import com.google.common.collect.Sets;

import org.apache.isis.applib.Module;
import org.apache.isis.applib.ModuleAbstract;

import org.isisaddons.module.fakedata.FakeDataModule;
import org.isisaddons.module.freemarker.dom.service.FreeMarkerService;

import org.incode.example.docfragment.demo.shared.DocFragmentDemoSharedModule;

@XmlRootElement(name = "module")
public class DocFragmentModuleIntegTestModule extends ModuleAbstract {

    public DocFragmentModuleIntegTestModule() {
        withConfigurationProperty(FreeMarkerService.JODA_SUPPORT_KEY, "true");
    }

    @Override
    public Set<Module> getDependencies() {
        return Sets.newHashSet(
                new DocFragmentDemoSharedModule(),
                new FakeDataModule()
        );
    }
}
