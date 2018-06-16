package org.incode.domainapp.extended.integtests.mml.paraname8.integtests;

import java.util.Set;

import javax.xml.bind.annotation.XmlRootElement;

import org.apache.isis.applib.Module;
import org.apache.isis.applib.ModuleAbstract;
import org.apache.isis.core.integtestsupport.IntegrationTestAbstract3;

import org.isisaddons.module.docx.DocxModule;
import org.isisaddons.module.fakedata.FakeDataModule;

import org.incode.domainapp.extended.module.fixtures.per_cpt.lib.docx.FixturesModuleLibDocxSubmodule;

public abstract class Paraname8ModuleIntegTest extends IntegrationTestAbstract3 {

//    @BeforeClass
//    public static void initClass() {
//        bootstrapUsing(Paraname8MetaModelAppManifest.BUILDER
//                .withAdditionalModules(FixturesModuleSharedSubmodule.class)
//        );
//    }

    public static ModuleAbstract module() {
        return new DocxModuleIntegTestAbstract.MyModule();
    }

    @XmlRootElement(name = "module")
    public static class MyModule extends ModuleAbstract {
        @Override
        public Set<Module> getDependencies() {
            final Set<Module> dependencies = super.getDependencies();
            dependencies.addAll(Sets.newHashSet(
                    new DocxModule(),
                    new FixturesModuleLibDocxSubmodule(),
                    new FakeDataModule()
            ));
            return dependencies;
        }
    }

    protected DocxModuleIntegTestAbstract() {
        super(module());
    }


}
