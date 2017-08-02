package domainapp.modules.exampledom.lib.poly.fixture.data.demoparty;

import domainapp.modules.exampledom.lib.poly.dom.demoparty.Party;
import domainapp.modules.exampledom.lib.poly.fixture.PolyAppTearDown;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import com.google.common.collect.Lists;
import org.apache.isis.applib.fixturescripts.FixtureScript;

public class RecreateParties extends FixtureScript {

    public final List<String> PARTY_NAMES = Collections.unmodifiableList(Arrays.asList(
            "Mary", "Mungo", "Midge"));

    public RecreateParties() {
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

    public RecreateParties setTeardown(final Boolean teardown) {
        this.teardown = teardown;
        return this;
    }
    //endregion

    //region > parties (output)
    private final List<Party> parties = Lists.newArrayList();

    /**
     * The {@link Party}s created by this fixture (output).
     */
    public List<Party> getParties() {
        return parties;
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

        for (int i = 0; i < PARTY_NAMES.size(); i++) {
            final PartyCreate fs = new PartyCreate().setName(PARTY_NAMES.get(i));
            ec.executeChild(this, fs.getName(), fs);
            parties.add(fs.getParty());
        }

    }
}
