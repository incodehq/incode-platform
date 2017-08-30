#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package org.incode.domainapp.example.dom.dom.note.fixture;

import org.apache.isis.applib.fixturescripts.FixtureScript;
import org.apache.isis.applib.services.jdosupport.IsisJdoSupport;

import org.incode.domainapp.example.dom.demo.fixture.teardown.DemoModuleTearDown;

public class DemoModule_withNotes_tearDown extends FixtureScript {

    @Override
    protected void execute(final ExecutionContext executionContext) {

        // links to demo objects
        isisJdoSupport.executeUpdate("delete from ${symbol_escape}"exampleDomNote${symbol_escape}".${symbol_escape}"NotableLinkForDemoObject${symbol_escape}"");

        // notes
        isisJdoSupport.executeUpdate("delete from ${symbol_escape}"incodeNote${symbol_escape}".${symbol_escape}"NotableLink${symbol_escape}"");
        isisJdoSupport.executeUpdate("delete from ${symbol_escape}"incodeNote${symbol_escape}".${symbol_escape}"Note${symbol_escape}"");

        // demo objects
        executionContext.executeChild(this, new DemoModuleTearDown());
    }


    @javax.inject.Inject
    IsisJdoSupport isisJdoSupport;

}
