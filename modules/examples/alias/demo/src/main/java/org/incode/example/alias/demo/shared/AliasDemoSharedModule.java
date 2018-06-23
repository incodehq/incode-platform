package org.incode.example.alias.demo.shared;

import java.util.Set;

import javax.xml.bind.annotation.XmlRootElement;

import com.google.common.collect.Sets;

import org.apache.isis.applib.Module;
import org.apache.isis.applib.ModuleAbstract;
import org.apache.isis.applib.fixturescripts.FixtureScript;

import org.isisaddons.module.fakedata.FakeDataModule;

import org.incode.example.alias.AliasModule;
import org.incode.example.alias.demo.shared.fixture.DemoObject_tearDown;

@XmlRootElement(name = "module")
public class AliasDemoSharedModule extends ModuleAbstract {

    @Override public Set<Module> getDependencies() {
        return Sets.newHashSet(
                new FakeDataModule()
        );
    }

    @Override public FixtureScript getTeardownFixture() {
        return new DemoObject_tearDown();

    }
}
