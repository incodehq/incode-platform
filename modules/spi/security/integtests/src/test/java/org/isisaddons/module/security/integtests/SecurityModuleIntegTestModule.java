package org.isisaddons.module.security.integtests;

import java.util.Set;

import javax.xml.bind.annotation.XmlRootElement;

import com.google.common.collect.Sets;

import org.apache.isis.applib.Module;
import org.apache.isis.applib.ModuleAbstract;

import org.isisaddons.module.fakedata.FakeDataModule;
import org.isisaddons.module.security.fixture.SecurityFixturesModule;

@XmlRootElement(name = "module")
public class SecurityModuleIntegTestModule extends ModuleAbstract {

public SecurityModuleIntegTestModule() {
    withAdditionalServices(
            org.isisaddons.module.security.dom.password.PasswordEncryptionServiceUsingJBcrypt.class
            ,org.isisaddons.module.security.dom.permission.PermissionsEvaluationServiceAllowBeatsVeto.class
            //,org.isisaddons.module.security.dom.permission.PermissionsEvaluationServiceVetoBeatsAllow.class

    );
}

@Override
    public Set<Module> getDependencies() {
        return Sets.newHashSet(
                new SecurityFixturesModule(),
                new FakeDataModule()
        );
    }
}
