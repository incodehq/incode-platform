package org.incode.example.alias.demo;

import java.util.Set;

import javax.xml.bind.annotation.XmlRootElement;

import com.google.common.collect.Sets;

import org.apache.isis.applib.Module;
import org.apache.isis.applib.ModuleAbstract;
import org.apache.isis.applib.fixturescripts.FixtureScript;

import org.incode.example.alias.demo.shared.AliasDemoSharedModule;
import org.incode.example.alias.demo.shared.fixture.DemoObject_tearDown;
import org.incode.example.alias.demo.usage.AliasDemoUsageModule;

@XmlRootElement(name = "module")
public class AliasDemoModule extends ModuleAbstract {

    @Override public Set<Module> getDependencies() {
        return Sets.newHashSet(
                new AliasDemoSharedModule(),
                new AliasDemoUsageModule()
        );
    }

}
