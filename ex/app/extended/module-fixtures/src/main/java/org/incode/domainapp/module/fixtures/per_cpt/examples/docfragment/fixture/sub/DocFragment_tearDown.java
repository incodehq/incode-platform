package org.incode.domainapp.module.fixtures.per_cpt.examples.docfragment.fixture.sub;

import org.apache.isis.applib.fixturescripts.FixtureScript;
import org.apache.isis.applib.services.jdosupport.IsisJdoSupport;

public class DocFragment_tearDown extends FixtureScript {

    @Override
    protected void execute(ExecutionContext executionContext) {
        isisJdoSupport.executeUpdate("delete from \"incodeDocFragment\".\"DocFragment\"");
    }


    @javax.inject.Inject
    IsisJdoSupport isisJdoSupport;

}
