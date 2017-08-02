package domainapp.modules.exampledom.lib.poly.fixture.data.democasemgmt;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import com.google.common.collect.Lists;

import org.apache.isis.applib.fixturescripts.FixtureScript;

import domainapp.modules.exampledom.lib.poly.dom.democasemgmt.Case;
import domainapp.modules.exampledom.lib.poly.fixture.PolyAppTearDown;

public class RecreateCases extends FixtureScript {

    public final List<String> CASE_NAMES = Collections.unmodifiableList(Arrays.asList(
            "Red", "Green", "Blue"));

    public RecreateCases() {
        withDiscoverability(Discoverability.DISCOVERABLE);
    }


    //region > teardown (input)

    private Boolean teardown;

    /**
     * Defaults to true
     */
    public Boolean getTeardown() {
        return teardown;
    }

    public RecreateCases setTeardown(final Boolean teardown) {
        this.teardown = teardown;
        return this;
    }
    //endregion

    //region > cases (output)
    private final List<Case> cases = Lists.newArrayList();

    /**
     * The {@link Case}s created by this fixture (output).
     */
    public List<Case> getCases() {
        return cases;
    }
    //endregion

    @Override
    protected void execute(final ExecutionContext ec) {

        // defaults
        defaultParam("teardown", ec, true);

        //
        // execute
        //
        if(getTeardown()) {
            ec.executeChild(this, new PolyAppTearDown());
        }

        for (int i = 0; i < CASE_NAMES.size(); i++) {
            final CaseCreate fs = new CaseCreate().setName(CASE_NAMES.get(i));
            ec.executeChild(this, fs.getName(), fs);
            cases.add(fs.getCase());
        }

    }
}
