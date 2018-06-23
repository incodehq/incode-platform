package org.incode.examples.commchannel.demo.shared.otherwithatpath.fixture;

import org.apache.isis.applib.fixturescripts.FixtureScript;
import org.apache.isis.applib.fixturescripts.teardown.TeardownFixtureAbstract2;

import org.incode.examples.commchannel.demo.shared.otherwithatpath.dom.OtherObjectWithAtPath;

public class OtherObjectWithAtPath_tearDown extends TeardownFixtureAbstract2 {

    @Override
    protected void execute(final FixtureScript.ExecutionContext executionContext) {
        deleteFrom(OtherObjectWithAtPath.class);
    }


}
