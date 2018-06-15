package org.incode.domainapp.extended.module.fixtures.per_cpt.examples.tags.fixture;

import org.apache.isis.applib.fixturescripts.FixtureScript;
import org.apache.isis.applib.services.jdosupport.IsisJdoSupport;

public class DemoTaggableObjects_tearDown extends FixtureScript {

    @Override
    protected void execute(ExecutionContext executionContext) {
        isisJdoSupport.executeUpdate("delete from \"exampleDomTags\".\"DemoTaggableObject\"");
        isisJdoSupport.executeUpdate("delete from \"isistags\".\"Tag\"");
    }


    @javax.inject.Inject
    IsisJdoSupport isisJdoSupport;

}
