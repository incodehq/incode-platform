package org.incode.domainapp.extended.integtests.mml;

import java.util.Set;

import javax.xml.bind.annotation.XmlRootElement;

import com.google.common.collect.Sets;

import org.apache.isis.applib.Module;
import org.apache.isis.applib.ModuleAbstract;

import org.incode.domainapp.extended.integtests.mml.paraname8.integtests.Paraname8ModuleIntegTestAbstract;

@XmlRootElement(name = "module")
public class IntegTestsMmlModule extends ModuleAbstract {

    @Override public Set<Module> getDependencies() {
        return Sets.newHashSet(
                Paraname8ModuleIntegTestAbstract.module()
        );
    }
}
