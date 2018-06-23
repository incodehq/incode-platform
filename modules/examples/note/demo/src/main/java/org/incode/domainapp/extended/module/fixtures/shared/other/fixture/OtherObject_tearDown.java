package org.incode.examples.commchannel.demo.shared.other.fixture;

import org.apache.isis.applib.fixturescripts.FixtureScript;
import org.apache.isis.applib.fixturescripts.teardown.TeardownFixtureAbstract2;

import org.incode.examples.commchannel.demo.shared.other.dom.OtherObject;

public class OtherObject_tearDown extends TeardownFixtureAbstract2 {

    @Override
    protected void execute(final FixtureScript.ExecutionContext executionContext) {
        deleteFrom(OtherObject.class);
    }


}
