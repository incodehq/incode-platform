package domainapp.modules.exampledom.module.docfragment.fixture.teardown;

import org.apache.isis.applib.fixturescripts.FixtureScript;
import org.apache.isis.applib.services.jdosupport.IsisJdoSupport;

public class DocFragmentModuleTearDown extends FixtureScript {

    @Override
    protected void execute(ExecutionContext executionContext) {
        isisJdoSupport.executeUpdate("delete from \"incodeDocFragment\".\"DocFragment\"");
    }


    @javax.inject.Inject
    private IsisJdoSupport isisJdoSupport;

}
