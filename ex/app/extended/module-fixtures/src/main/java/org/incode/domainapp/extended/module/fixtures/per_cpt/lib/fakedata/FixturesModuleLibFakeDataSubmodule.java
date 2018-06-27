package org.incode.domainapp.extended.module.fixtures.per_cpt.lib.fakedata;

import javax.xml.bind.annotation.XmlRootElement;

import org.apache.isis.applib.ModuleAbstract;
import org.apache.isis.applib.fixturescripts.FixtureScript;

import org.incode.domainapp.extended.module.fixtures.per_cpt.lib.fakedata.fixture.demowithall.DemoObjectWithAll_tearDown;
import org.incode.domainapp.extended.module.fixtures.per_cpt.lib.fakedata.fixture.demowithblob.DemoObjectWithBlob_tearDown;

@XmlRootElement(name = "module")
public class FixturesModuleLibFakeDataSubmodule extends ModuleAbstract {

    @Override public FixtureScript getTeardownFixture() {
        return new FixtureScript() {
                    @Override
                    protected void execute(final ExecutionContext ec) {
                        ec.executeChild(this, new DemoObjectWithAll_tearDown());
                        ec.executeChild(this, new DemoObjectWithBlob_tearDown());
                    }
                };
    }

}
