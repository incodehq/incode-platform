package org.incode.domainapp.extended.module.fixtures.per_cpt.lib.servletapi.fixture;

import org.apache.isis.applib.fixturescripts.teardown.TeardownFixtureAbstract2;
import org.apache.isis.objectstore.jdo.applib.service.support.IsisJdoSupport;

import org.incode.domainapp.extended.module.fixtures.per_cpt.lib.servletapi.dom.demo.ServletApiDemoObject;

public class ServletApiDemoObject_tearDown extends TeardownFixtureAbstract2 {

    @Override
    protected void execute(ExecutionContext executionContext) {
        deleteFrom(ServletApiDemoObject.class);
    }

}
