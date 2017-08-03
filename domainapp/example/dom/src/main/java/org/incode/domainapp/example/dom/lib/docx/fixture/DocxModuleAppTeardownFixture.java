package org.incode.domainapp.example.dom.lib.docx.fixture;

import org.apache.isis.applib.fixturescripts.FixtureScript;
import org.apache.isis.objectstore.jdo.applib.service.support.IsisJdoSupport;

public class DocxModuleAppTeardownFixture extends FixtureScript {

    @Override
    protected void execute(ExecutionContext executionContext) {
        isisJdoSupport.executeUpdate("delete from \"exampleLibDocx\".\"OrderLine\"");
        isisJdoSupport.executeUpdate("delete from \"exampleLibDocx\".\"Order\"");
    }


    @javax.inject.Inject
    private IsisJdoSupport isisJdoSupport;

}
