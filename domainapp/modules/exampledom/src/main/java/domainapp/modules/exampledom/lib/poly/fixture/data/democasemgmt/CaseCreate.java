package domainapp.modules.exampledom.lib.poly.fixture.data.democasemgmt;

import domainapp.modules.exampledom.lib.poly.dom.democasemgmt.Case;
import domainapp.modules.exampledom.lib.poly.dom.democasemgmt.Cases;

import org.apache.isis.applib.fixturescripts.FixtureScript;

public class CaseCreate extends FixtureScript {

    //region > name (input)
    private String name;
    public String getName() {
        return name;
    }

    public CaseCreate setName(final String name) {
        this.name = name;
        return this;
    }
    //endregion

    //region > party (output)
    private Case aCase;

    /**
     * The created case (output).
     */
    public Case getCase() {
        return aCase;
    }
    //endregion

    @Override
    protected void execute(final ExecutionContext ec) {

        final String name = checkParam("name", ec, String.class);

        this.aCase = cases.create(name);

        // also make available to UI
        ec.addResult(this, aCase);
    }

    @javax.inject.Inject
    private Cases cases;

}
