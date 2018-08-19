package org.isisaddons.module.flywaydb.fixture;

import java.util.Set;

import javax.xml.bind.annotation.XmlRootElement;

import com.google.common.collect.Sets;

import org.apache.isis.applib.Module;
import org.apache.isis.applib.ModuleAbstract;

import org.isisaddons.module.flywaydb.fixture.demomodule.FlywayDbFixturesDemoSubmodule;

@XmlRootElement(name = "module")
public class FlywayDbFixturesModule extends ModuleAbstract {

    @Override
    public Set<Module> getDependencies() {
        return Sets.newHashSet(new FlywayDbFixturesDemoSubmodule());
    }
}
