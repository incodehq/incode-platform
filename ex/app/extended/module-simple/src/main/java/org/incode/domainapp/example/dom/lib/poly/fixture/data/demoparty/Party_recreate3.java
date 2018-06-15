package org.incode.domainapp.example.dom.lib.poly.fixture.data.demoparty;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import com.google.common.collect.Lists;

import org.apache.isis.applib.fixturescripts.FixtureScript;

import org.incode.domainapp.example.dom.lib.poly.dom.demoparty.Party;
import org.incode.domainapp.example.dom.lib.poly.fixture.Case_FixedAsset_Party_andLinks_tearDown;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Accessors(chain = true)
public class Party_recreate3 extends FixtureScript {

    public final List<String> PARTY_NAMES = Collections.unmodifiableList(Arrays.asList(
            "Mary", "Mungo", "Midge"));


    @Getter @Setter
    private Boolean teardown;

    @Getter
    private final List<Party> parties = Lists.newArrayList();

    @Override
    protected void execute(final ExecutionContext ec) {

        defaultParam("teardown", ec, true);

        if(getTeardown()) {
            ec.executeChild(this, new Case_FixedAsset_Party_andLinks_tearDown());
        }

        for (int i = 0; i < PARTY_NAMES.size(); i++) {
            final Party_create fs = new Party_create().setName(PARTY_NAMES.get(i));
            ec.executeChild(this, fs.getName(), fs);
            parties.add(fs.getParty());
        }

    }
}
