package org.isisaddons.module.poly.fixture.demoapp.app.fixturescripts.data.demoparty;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import com.google.common.collect.Lists;

import org.apache.isis.applib.fixturescripts.FixtureScript;

import org.isisaddons.module.poly.fixture.demoapp.demomodule.dom.party.Party;

import lombok.Getter;
import lombok.experimental.Accessors;

@Accessors(chain = true)
public class Party_create3 extends FixtureScript {

    public final List<String> PARTY_NAMES = Collections.unmodifiableList(Arrays.asList(
            "Mary", "Mungo", "Midge"));


    @Getter
    private final List<Party> parties = Lists.newArrayList();

    @Override
    protected void execute(final ExecutionContext ec) {

        for (int i = 0; i < PARTY_NAMES.size(); i++) {
            final Party_create fs = new Party_create().setName(PARTY_NAMES.get(i));
            ec.executeChild(this, fs.getName(), fs);
            parties.add(fs.getParty());
        }

    }
}
