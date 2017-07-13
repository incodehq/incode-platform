package org.isisaddons.module.docx.fixture.scripts;

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

/**
 * Enables fixtures to be installed from the application.
 */
@DomainService(
        nature = NatureOfService.VIEW_MENU_ONLY
)
@DomainServiceLayout(
        named="Prototyping",
        menuBar = DomainServiceLayout.MenuBar.SECONDARY,
        menuOrder = "400"
)
public class DocxModuleAppFixturesService extends FixtureScripts {

    public DocxModuleAppFixturesService() {
        super(DocxModuleAppFixturesService.class.getPackage().getName());
    }

    @Override
    public FixtureScript default0RunFixtureScript() {
        return findFixtureScriptFor(DocxModuleAppSetupFixture.class);
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
        final List<FixtureResult> run = findFixtureScriptFor(DocxModuleAppSetupFixture.class).run(null);
        return run.get(0).getObject();
    }


}
