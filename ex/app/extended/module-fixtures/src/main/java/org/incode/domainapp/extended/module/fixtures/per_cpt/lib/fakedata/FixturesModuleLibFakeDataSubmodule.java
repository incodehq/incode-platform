package org.incode.domainapp.extended.module.fixtures.per_cpt.lib.fakedata;

import java.util.Set;

import javax.xml.bind.annotation.XmlRootElement;

import com.google.common.collect.Sets;

import org.apache.isis.applib.Module;
import org.apache.isis.applib.ModuleAbstract;

import org.incode.domainapp.extended.module.fixtures.shared.demowithall.FixturesModuleSharedDemoWithAllSubmodule;

@XmlRootElement(name = "module")
public class FixturesModuleLibFakeDataSubmodule extends ModuleAbstract {

    @Override public Set<Module> getDependencies() {
        return Sets.newHashSet(new FixturesModuleSharedDemoWithAllSubmodule());
    }
}
