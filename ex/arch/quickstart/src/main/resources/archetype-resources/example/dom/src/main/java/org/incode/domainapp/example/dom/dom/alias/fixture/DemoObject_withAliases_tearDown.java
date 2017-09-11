#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package org.incode.domainapp.example.dom.dom.alias.fixture;

import org.apache.isis.applib.fixturescripts.FixtureScript;
import org.apache.isis.applib.services.jdosupport.IsisJdoSupport;

import org.incode.domainapp.example.dom.demo.fixture.teardown.sub.DemoObject_tearDown;

public class DemoObject_withAliases_tearDown extends FixtureScript {

    @Override
    protected void execute(final ExecutionContext executionContext) {

        // aliases
        isisJdoSupport.executeUpdate("delete from ${symbol_escape}"exampleDomAlias${symbol_escape}".${symbol_escape}"AliasForDemoObject${symbol_escape}"");
        isisJdoSupport.executeUpdate("delete from ${symbol_escape}"incodeAlias${symbol_escape}".${symbol_escape}"Alias${symbol_escape}"");

        // demo objects
        executionContext.executeChild(this, new DemoObject_tearDown());
    }

    @javax.inject.Inject
    IsisJdoSupport isisJdoSupport;

}
