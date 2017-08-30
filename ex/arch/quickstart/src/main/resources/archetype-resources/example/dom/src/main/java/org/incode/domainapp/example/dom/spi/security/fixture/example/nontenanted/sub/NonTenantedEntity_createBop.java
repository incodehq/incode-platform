#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package org.incode.domainapp.example.dom.spi.security.fixture.example.nontenanted.sub;

public class NonTenantedEntity_createBop extends AbstractNonTenantedEntityFixtureScript {

    @Override
    protected void execute(ExecutionContext executionContext) {
        create("Bop", executionContext);
    }

}
