#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package domainapp.appdefn.integtests;

import org.apache.isis.core.integtestsupport.IntegrationTestAbstract3;

import domainapp.appdefn.DomainAppAppDefnModule;

public abstract class DomainAppIntegTestAbstract extends IntegrationTestAbstract3 {

    public DomainAppIntegTestAbstract() {
        super(new DomainAppAppDefnModule());
    }

}
