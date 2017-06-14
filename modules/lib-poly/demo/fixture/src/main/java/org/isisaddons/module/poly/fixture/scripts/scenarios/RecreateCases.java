package org.isisaddons.module.poly.fixture.scripts.scenarios;

import org.isisaddons.module.poly.fixture.dom.modules.casemgmt.Case;
import org.isisaddons.module.poly.fixture.scripts.modules.PolyAppTearDown;
import org.isisaddons.module.poly.fixture.scripts.modules.casemgmt.CaseCreate;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import com.google.common.collect.Lists;
import org.apache.isis.applib.fixturescripts.FixtureScript;

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
     * The {@link org.isisaddons.module.poly.fixture.dom.modules.casemgmt.Case}s created by this fixture (output).
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
