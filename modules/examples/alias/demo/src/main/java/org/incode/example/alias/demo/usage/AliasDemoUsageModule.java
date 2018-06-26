package org.incode.example.alias.demo.usage;

import java.util.Set;

import javax.xml.bind.annotation.XmlRootElement;

import com.google.common.collect.Sets;

import org.apache.isis.applib.Module;
import org.apache.isis.applib.ModuleAbstract;
import org.apache.isis.applib.fixturescripts.FixtureScript;
import org.apache.isis.applib.fixturescripts.teardown.TeardownFixtureAbstract2;

import org.isisaddons.module.fakedata.FakeDataModule;

import org.incode.example.alias.AliasModule;
import org.incode.example.alias.demo.usage.dom.AliasForAliasDemoObject;

@XmlRootElement(name = "module")
public class AliasDemoUsageModule extends ModuleAbstract {

    @Override public Set<Module> getDependencies() {
        return Sets.newHashSet(
                new AliasModule(),
                new FakeDataModule()
        );
    }


    @Override
    public FixtureScript getTeardownFixture() {
        return new TeardownFixtureAbstract2() {
            @Override
            protected void execute(final FixtureScript.ExecutionContext executionContext) {
                deleteFrom(AliasForAliasDemoObject.class);
            }
        };
    }
}
