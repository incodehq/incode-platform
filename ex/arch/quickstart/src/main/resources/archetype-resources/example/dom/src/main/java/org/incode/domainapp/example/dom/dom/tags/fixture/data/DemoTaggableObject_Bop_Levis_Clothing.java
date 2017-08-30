#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package org.incode.domainapp.example.dom.dom.tags.fixture.data;

public class DemoTaggableObject_Bop_Levis_Clothing extends AbstractTaggableObjectFixture {

    @Override
    protected void execute(ExecutionContext executionContext) {
        create("Bop", "Levi's", "Clothing", executionContext);
    }

}
