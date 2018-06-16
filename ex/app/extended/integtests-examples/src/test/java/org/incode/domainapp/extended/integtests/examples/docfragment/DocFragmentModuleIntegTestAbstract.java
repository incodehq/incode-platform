package org.incode.domainapp.extended.integtests.examples.docfragment;

import java.util.Set;

import javax.xml.bind.annotation.XmlRootElement;

import com.google.common.collect.Sets;

import org.apache.isis.applib.ModuleAbstract;
import org.apache.isis.core.integtestsupport.IntegrationTestAbstract3;

import org.isisaddons.module.fakedata.FakeDataModule;
import org.isisaddons.module.freemarker.dom.service.FreeMarkerService;

import org.incode.domainapp.extended.module.fixtures.per_cpt.examples.docfragment.FixturesModuleExamplesDocFragmentIntegrationSubmodule;

public abstract class DocFragmentModuleIntegTestAbstract extends IntegrationTestAbstract3 {

    @XmlRootElement(name = "module")
    public static class MyModule extends ModuleAbstract {

        public MyModule() {
            withConfigurationProperty(FreeMarkerService.JODA_SUPPORT_KEY, "true");
        }

        @Override
        public Set<org.apache.isis.applib.Module> getDependencies() {
            return Sets.newHashSet(
                    new FixturesModuleExamplesDocFragmentIntegrationSubmodule(),
                    new FakeDataModule()
            );
        }
    }

    public static ModuleAbstract module() {
        return new MyModule();
    }

    protected DocFragmentModuleIntegTestAbstract() {
        super(module());
    }

}
