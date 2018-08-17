package org.incode.domainapp.extended.integtests.spi.sessionlogger;

import java.util.Set;

import javax.xml.bind.annotation.XmlRootElement;

import com.google.common.collect.Sets;

import org.apache.isis.applib.Module;
import org.apache.isis.applib.ModuleAbstract;

import org.isisaddons.module.fakedata.FakeDataModule;
import org.isisaddons.module.sessionlogger.SessionLoggerModule;

@XmlRootElement(name = "module")
public class SessionLoggerModuleIntegTestModule extends ModuleAbstract {

    @Override
    public Set<Module> getDependencies() {
        return Sets.newHashSet(
                new SessionLoggerModule(),
                new FakeDataModule()
        );
    }
}
