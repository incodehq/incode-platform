package org.isisaddons.wicket.fullcalendar2.fixture.scripts;

import java.util.List;
import org.apache.isis.applib.annotation.Action;
import org.apache.isis.applib.annotation.DomainService;
import org.apache.isis.applib.annotation.DomainServiceLayout;
import org.apache.isis.applib.annotation.MemberOrder;
import org.apache.isis.applib.annotation.RestrictTo;
import org.apache.isis.applib.fixturescripts.FixtureResult;
import org.apache.isis.applib.fixturescripts.FixtureScript;
import org.apache.isis.applib.fixturescripts.FixtureScripts;
import org.apache.isis.applib.fixturescripts.SimpleFixtureScript;

/**
 * Enables fixtures to be installed from the application.
 */
@DomainService()
@DomainServiceLayout(
        named = "Prototyping",
        menuBar = DomainServiceLayout.MenuBar.SECONDARY,
        menuOrder = "499"
)
public class FullCalendar2WicketFixturesService extends FixtureScripts {

    public FullCalendar2WicketFixturesService() {
        super(FullCalendar2WicketFixturesService.class.getPackage().getName());
    }

    @Override
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
        final List<FixtureResult> run = findFixtureScriptFor(FullCalendar2WicketSetUpFixture.class).run(null);
        return run.get(0).getObject();
    }


}
