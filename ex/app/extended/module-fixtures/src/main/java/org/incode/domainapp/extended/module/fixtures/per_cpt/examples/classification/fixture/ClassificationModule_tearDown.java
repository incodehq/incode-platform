package org.incode.domainapp.extended.module.fixtures.per_cpt.examples.classification.fixture;

import org.apache.isis.applib.fixturescripts.FixtureScript;
import org.apache.isis.applib.services.jdosupport.IsisJdoSupport;

public class ClassificationModule_tearDown extends FixtureScript {

    @Override
    protected void execute(final ExecutionContext executionContext) {

        // classifications
        isisJdoSupport.executeUpdate("delete from \"exampleDomClassification\".\"ClassificationForOtherObjectWithAtPath\"");

        isisJdoSupport.executeUpdate("delete from \"exampleDomClassification\".\"ClassificationForDemoObjectWithAtPath\"");


        // classification refdata
        isisJdoSupport.executeUpdate("delete from \"incodeClassification\".\"Classification\"");
        isisJdoSupport.executeUpdate("delete from \"incodeClassification\".\"Applicability\"");
        isisJdoSupport.executeUpdate("delete from \"incodeClassification\".\"Category\"");


    }


    @javax.inject.Inject
    IsisJdoSupport isisJdoSupport;

}
