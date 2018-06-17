package org.incode.domainapp.extended.integtests.spi.sessionlogger;

import java.util.Set;

import javax.xml.bind.annotation.XmlRootElement;

import com.google.common.collect.Sets;

import org.apache.isis.applib.Module;
import org.apache.isis.applib.ModuleAbstract;
import org.apache.isis.core.integtestsupport.IntegrationTestAbstract3;

import org.isisaddons.module.fakedata.FakeDataModule;
import org.isisaddons.module.sessionlogger.SessionLoggerModule;

public abstract class SessionLoggerModuleIntegTestAbstract extends IntegrationTestAbstract3 {

    @XmlRootElement(name = "module")
    public static class MyModule extends ModuleAbstract {

        @Override
        public Set<Module> getDependencies() {
            return Sets.newHashSet(
                    new SessionLoggerModule(),
                    new FakeDataModule()
            );
        }
    }

    public static ModuleAbstract module() {
        return new MyModule();
    }

    protected SessionLoggerModuleIntegTestAbstract() {
        super(module());
    }

}
