package org.incode.domainapp.extended.integtests.spi.security;

import java.util.Set;

import javax.xml.bind.annotation.XmlRootElement;

import com.google.common.collect.Sets;

import org.apache.isis.applib.Module;
import org.apache.isis.applib.ModuleAbstract;

import org.isisaddons.module.fakedata.FakeDataModule;

import org.incode.domainapp.extended.module.fixtures.per_cpt.spi.security.FixturesModuleSpiSecuritySubmodule;

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
                new FixturesModuleSpiSecuritySubmodule(),
                new FakeDataModule()
        );
    }
}
