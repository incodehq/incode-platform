package org.incode.domainapp.example.dom.spi.security.fixture;

import java.util.List;

import org.apache.isis.applib.annotation.Action;
import org.apache.isis.applib.annotation.DomainService;
import org.apache.isis.applib.annotation.DomainServiceLayout;
import org.apache.isis.applib.annotation.MemberOrder;
import org.apache.isis.applib.annotation.NatureOfService;
import org.apache.isis.applib.annotation.Optionality;
import org.apache.isis.applib.annotation.Parameter;
import org.apache.isis.applib.annotation.ParameterLayout;
import org.apache.isis.applib.annotation.RestrictTo;
import org.apache.isis.applib.annotation.SemanticsOf;
import org.apache.isis.applib.fixturescripts.FixtureResult;
import org.apache.isis.applib.fixturescripts.FixtureScript;
import org.apache.isis.applib.fixturescripts.FixtureScripts;

import org.isisaddons.module.security.dom.role.ApplicationRole;

/**
 * Enables fixtures to be installed from the application.
 */
@DomainService(
    nature = NatureOfService.VIEW_MENU_ONLY,
    objectType = "isissecurityDemo.FixturesService"
)
@DomainServiceLayout(
        named="Prototyping",
        menuOrder = "99",
        menuBar = DomainServiceLayout.MenuBar.SECONDARY
)
// TODO: convert to FixtureScriptsSpecificationProvider
public class SecurityModuleAppFixturesService extends FixtureScripts {

    //region > constructor
    public SecurityModuleAppFixturesService() {
        super(SecurityModuleAppFixturesService.class.getPackage().getName());
    }
    //endregion

    //region > runFixtureScript
    @Override
    public List<FixtureResult> runFixtureScript(
            final FixtureScript fixtureScript,
            @Parameter(optionality = Optionality.OPTIONAL)
            @ParameterLayout(
                named = "Parameters",
                describedAs = "Script-specific parameters (if any).  The format depends on the script implementation (eg key=value, CSV, JSON, XML etc)",
                multiLine = 10
            )
            final String parameters) {
        return super.runFixtureScript(fixtureScript, parameters);
    }

    @Override
    public FixtureScript default0RunFixtureScript() {
        return findFixtureScriptFor(SecurityModuleAppSetUp.class);
    }

    /**
     * Raising visibility to <tt>public</tt> so that choices are available for first param
     * of {@link #runFixtureScript(FixtureScript, String)}.
     */
    @Override
    public List<FixtureScript> choices0RunFixtureScript() {
        return super.choices0RunFixtureScript();
    }
    //endregion

    //region > installFixturesAndReturnFirstRole
    @Action(
            semantics = SemanticsOf.NON_IDEMPOTENT,
            restrictTo = RestrictTo.PROTOTYPING
    )
    @MemberOrder(sequence="20")
    public Object installFixturesAndReturnFirstRole() {
        final List<FixtureResult> fixtureResultList = findFixtureScriptFor(SecurityModuleAppSetUp.class).run(null);
        for (FixtureResult fixtureResult : fixtureResultList) {
            final Object object = fixtureResult.getObject();
            if(object instanceof ApplicationRole) {
                return object;
            }
        }
        getContainer().warnUser("No rules found in fixture; returning all results");
        return fixtureResultList;
    }
    //endregion

}
