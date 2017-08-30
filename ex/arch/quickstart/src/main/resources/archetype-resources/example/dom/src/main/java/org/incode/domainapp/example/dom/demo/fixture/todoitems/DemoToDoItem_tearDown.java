#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package org.incode.domainapp.example.dom.demo.fixture.todoitems;

import org.apache.isis.applib.fixturescripts.FixtureScript;
import org.apache.isis.applib.services.jdosupport.IsisJdoSupport;

public class DemoToDoItem_tearDown extends FixtureScript {

    private final String user;

    public DemoToDoItem_tearDown() {
        this(null);
    }

    public DemoToDoItem_tearDown(String ownedBy) {
        this.user = ownedBy;
    }

    @Override
    public void execute(ExecutionContext executionContext) {

        final String ownedBy = this.user != null ? this.user : userService.getUser().getName();

        isisJdoSupport.executeUpdate(String.format(
                "delete "
                        + "from ${symbol_escape}"exampleDemo${symbol_escape}".${symbol_escape}"DemoToDoItemDependencies${symbol_escape}" "
                        + "where ${symbol_escape}"dependingId${symbol_escape}" IN "
                        + "(select ${symbol_escape}"id${symbol_escape}" from ${symbol_escape}"exampleDemo${symbol_escape}".${symbol_escape}"DemoToDoItem${symbol_escape}" where ${symbol_escape}"ownedBy${symbol_escape}" = '%s') ",
                ownedBy));

        isisJdoSupport.executeUpdate(String.format(
                "delete from ${symbol_escape}"exampleDemo${symbol_escape}".${symbol_escape}"DemoToDoItem${symbol_escape}" "
                        + "where ${symbol_escape}"ownedBy${symbol_escape}" = '%s'", ownedBy));
    }


    @javax.inject.Inject
    IsisJdoSupport isisJdoSupport;

}
