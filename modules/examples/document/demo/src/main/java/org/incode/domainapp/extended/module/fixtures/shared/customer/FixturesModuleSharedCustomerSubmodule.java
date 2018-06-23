package org.incode.examples.commchannel.demo.shared.customer;

import javax.xml.bind.annotation.XmlRootElement;

import org.apache.isis.applib.ModuleAbstract;
import org.apache.isis.applib.fixturescripts.FixtureScript;
import org.apache.isis.applib.fixturescripts.teardown.TeardownFixtureAbstract2;

import org.incode.examples.commchannel.demo.shared.customer.dom.DemoCustomer;

@XmlRootElement(name = "module")
public class FixturesModuleSharedCustomerSubmodule extends ModuleAbstract {

    @Override public FixtureScript getTeardownFixture() {
        return new TeardownFixtureAbstract2() {
            @Override
            protected void execute(final FixtureScript.ExecutionContext executionContext) {
                deleteFrom(DemoCustomer.class);
            }
        };
    }

}
