#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package org.incode.domainapp.example.dom.lib.poly.fixture;

import org.apache.isis.applib.fixturescripts.FixtureScript;
import org.apache.isis.applib.services.jdosupport.IsisJdoSupport;

public class Case_FixedAsset_Party_andLinks_tearDown extends FixtureScript {

    @Override
    protected void execute(ExecutionContext executionContext) {
        isisJdoSupport.executeUpdate("delete from ${symbol_escape}"exampleLibPoly${symbol_escape}".${symbol_escape}"CaseContentLinkForParty${symbol_escape}"");
        isisJdoSupport.executeUpdate("delete from ${symbol_escape}"exampleLibPoly${symbol_escape}".${symbol_escape}"CaseContentLinkForFixedAsset${symbol_escape}"");
        isisJdoSupport.executeUpdate("delete from ${symbol_escape}"exampleLibPoly${symbol_escape}".${symbol_escape}"CaseContentLink${symbol_escape}"");
        isisJdoSupport.executeUpdate("delete from ${symbol_escape}"exampleLibPoly${symbol_escape}".${symbol_escape}"Case${symbol_escape}"");

        isisJdoSupport.executeUpdate("delete from ${symbol_escape}"exampleLibPoly${symbol_escape}".${symbol_escape}"CommunicationChannelOwnerLinkForParty${symbol_escape}"");
        isisJdoSupport.executeUpdate("delete from ${symbol_escape}"exampleLibPoly${symbol_escape}".${symbol_escape}"CommunicationChannelOwnerLinkForFixedAsset${symbol_escape}"");
        isisJdoSupport.executeUpdate("delete from ${symbol_escape}"exampleLibPoly${symbol_escape}".${symbol_escape}"CommunicationChannelOwnerLink${symbol_escape}"");
        isisJdoSupport.executeUpdate("delete from ${symbol_escape}"exampleLibPoly${symbol_escape}".${symbol_escape}"CommunicationChannel${symbol_escape}"");

        isisJdoSupport.executeUpdate("delete from ${symbol_escape}"exampleLibPoly${symbol_escape}".${symbol_escape}"FixedAsset${symbol_escape}"");
        isisJdoSupport.executeUpdate("delete from ${symbol_escape}"exampleLibPoly${symbol_escape}".${symbol_escape}"Party${symbol_escape}"");
    }


    @javax.inject.Inject
    IsisJdoSupport isisJdoSupport;

}
