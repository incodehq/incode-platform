package org.incode.domainapp.extended.appdefn;

import java.util.Set;

import javax.xml.bind.annotation.XmlRootElement;

import org.apache.isis.applib.Module;
import org.apache.isis.applib.ModuleAbstract;
import org.apache.isis.core.integtestsupport.IntegrationTestAbstract3;

import org.incode.domainapp.extended.appdefn.overrides.MyTranslationResolver;
import org.incode.platformapp.appdefn.ExtendedAppAppDefnModule;

public abstract class ExtendedAppIntegTestAbstract extends IntegrationTestAbstract3 {

    public static ModuleAbstract module() {
        return new MyModule();
    }

    protected ExtendedAppIntegTestAbstract() {
        super(module());
    }

    @XmlRootElement(name = "module")
    public static class MyModule extends ExtendedAppAppDefnModule {
        @Override
        public Set<Module> getDependencies() {
            final Set<Module> dependencies = super.getDependencies();
            dependencies.add(new MyTranslationResolver.Module());
            return dependencies;
        }
    }
}
