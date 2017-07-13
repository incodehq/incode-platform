package org.isisaddons.module.togglz.fixture.scripts;

import java.util.List;
import org.apache.isis.applib.annotation.Action;
import org.apache.isis.applib.annotation.DomainService;
import org.apache.isis.applib.annotation.DomainServiceLayout;
import org.apache.isis.applib.annotation.MemberOrder;
import org.apache.isis.applib.annotation.NatureOfService;
import org.apache.isis.applib.annotation.RestrictTo;
import org.apache.isis.applib.fixturescripts.FixtureResult;
import org.apache.isis.applib.fixturescripts.FixtureScript;
import org.apache.isis.applib.fixturescripts.FixtureScripts;
import org.apache.isis.applib.fixturescripts.SimpleFixtureScript;
import org.isisaddons.module.togglz.fixture.scripts.scenarios.TogglzDemoObjectsFixture;

/**
 * Enables fixtures to be installed from the application.
 */
@DomainService(
        nature = NatureOfService.VIEW_MENU_ONLY
)
@DomainServiceLayout(
        named = "Prototyping",
        menuOrder = "20",
        menuBar = DomainServiceLayout.MenuBar.SECONDARY
)
public class TogglzDemoObjectsFixturesService extends FixtureScripts {

    public TogglzDemoObjectsFixturesService() {
        super(TogglzDemoObjectsFixturesService.class.getPackage().getName());
    }

    @Override // compatibility with core 1.5.0
    public FixtureScript default0RunFixtureScript() {
        return findFixtureScriptFor(SimpleFixtureScript.class);
    }

    /**
     * Raising visibility to <tt>public</tt> so that choices are available for first param
     * of {@link #runFixtureScript(FixtureScript, String)}.
     */
    @Override
    public List<FixtureScript> choices0RunFixtureScript() {
        return super.choices0RunFixtureScript();
    }


    // //////////////////////////////////////

    @Action(
            restrictTo = RestrictTo.PROTOTYPING
    )
    @MemberOrder(sequence="20")
    public Object installFixturesAndReturnFirst() {
        final FixtureScript script = findFixtureScriptFor(TogglzDemoObjectsFixture.class);
        final List<FixtureResult> run = script.run(null);
        return run.get(0).getObject();
    }


}
