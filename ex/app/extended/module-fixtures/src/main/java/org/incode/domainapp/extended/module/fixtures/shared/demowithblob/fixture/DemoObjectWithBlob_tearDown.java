package org.incode.domainapp.extended.module.fixtures.shared.demowithblob.fixture;

import org.apache.isis.applib.fixturescripts.teardown.TeardownFixtureAbstract2;
import org.apache.isis.applib.services.jdosupport.IsisJdoSupport;

import org.incode.domainapp.extended.module.fixtures.shared.demowithblob.dom.DemoObjectWithBlob;

public class DemoObjectWithBlob_tearDown extends TeardownFixtureAbstract2 {

    @Override
    protected void execute(final ExecutionContext executionContext) {
        deleteFrom(DemoObjectWithBlob.class);
    }

}
