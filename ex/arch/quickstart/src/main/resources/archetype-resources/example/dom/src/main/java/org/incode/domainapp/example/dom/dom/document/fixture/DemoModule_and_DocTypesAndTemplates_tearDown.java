#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package org.incode.domainapp.example.dom.dom.document.fixture;

import org.apache.isis.applib.fixturescripts.FixtureScript;
import org.apache.isis.applib.services.jdosupport.IsisJdoSupport;

import org.incode.domainapp.example.dom.demo.fixture.teardown.sub.DemoObjectWithUrl_tearDown;
import org.incode.domainapp.example.dom.demo.fixture.teardown.sub.OtherObject_tearDown;
import org.incode.example.document.fixture.teardown.DocumentModule_tearDown;

public class DemoModule_and_DocTypesAndTemplates_tearDown extends FixtureScript {

    @Override
    protected void execute(final ExecutionContext executionContext) {

        // paperclip links
        isisJdoSupport.executeUpdate("delete from ${symbol_escape}"exampleDomDocument${symbol_escape}".${symbol_escape}"PaperclipForDemoObjectWithUrl${symbol_escape}"");

        isisJdoSupport.executeUpdate("delete from ${symbol_escape}"exampleDomDocument${symbol_escape}".${symbol_escape}"PaperclipForOtherObject${symbol_escape}"");

        // documents
        executionContext.executeChild(this, new DocumentModule_tearDown());

        // demo objects
        executionContext.executeChild(this, new DemoObjectWithUrl_tearDown());
        executionContext.executeChild(this, new OtherObject_tearDown());

    }


    @javax.inject.Inject
    IsisJdoSupport isisJdoSupport;

}
