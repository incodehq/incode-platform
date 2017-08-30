#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package org.incode.domainapp.example.dom.dom.classification.fixture;

import org.apache.isis.applib.fixturescripts.FixtureScript;
import org.apache.isis.applib.services.jdosupport.IsisJdoSupport;

public class ClassificationModule_tearDown extends FixtureScript {

    @Override
    protected void execute(final ExecutionContext executionContext) {

        // classifications
        isisJdoSupport.executeUpdate("delete from ${symbol_escape}"exampleDomClassification${symbol_escape}".${symbol_escape}"ClassificationForOtherObjectWithAtPath${symbol_escape}"");

        isisJdoSupport.executeUpdate("delete from ${symbol_escape}"exampleDomClassification${symbol_escape}".${symbol_escape}"ClassificationForDemoObjectWithAtPath${symbol_escape}"");


        // classification refdata
        isisJdoSupport.executeUpdate("delete from ${symbol_escape}"incodeClassification${symbol_escape}".${symbol_escape}"Classification${symbol_escape}"");
        isisJdoSupport.executeUpdate("delete from ${symbol_escape}"incodeClassification${symbol_escape}".${symbol_escape}"Applicability${symbol_escape}"");
        isisJdoSupport.executeUpdate("delete from ${symbol_escape}"incodeClassification${symbol_escape}".${symbol_escape}"Category${symbol_escape}"");


    }


    @javax.inject.Inject
    IsisJdoSupport isisJdoSupport;

}
