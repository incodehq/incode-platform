package org.isisaddons.module.poly.fixture.demoapp.app.fixturescripts;

import java.util.List;

import javax.inject.Inject;

import org.apache.isis.applib.fixturescripts.FixtureScript;

import org.isisaddons.module.poly.fixture.demoapp.app.fixturescripts.data.democasemgmt.PolyDemoCase_create3;
import org.isisaddons.module.poly.fixture.demoapp.app.fixturescripts.data.demofixedasset.PolyDemoFixedAsset_create6;
import org.isisaddons.module.poly.fixture.demoapp.app.fixturescripts.data.demoparty.PolyDemoParty_create3;
import org.isisaddons.module.poly.fixture.demoapp.demomodule.dom.casemgmt.PolyDemoCase;
import org.isisaddons.module.poly.fixture.demoapp.demomodule.dom.fixedasset.PolyDemoFixedAsset;
import org.isisaddons.module.poly.fixture.demoapp.demomodule.dom.party.PolyDemoParty;
import org.isisaddons.module.poly.fixture.demoapp.poly_casecontent_module.dom.contentlink.PolyDemoCaseContentLinks;
import org.isisaddons.module.poly.fixture.demoapp.poly_caseprimary_module.contributions.PolyDemoCase_makePrimary;

public class PolyDemo_Case_FixedAsset_Party_createAll extends FixtureScript {

    private PolyDemoCase_create3 createCases;
    private PolyDemoFixedAsset_create6 createFixedAssets;
    private PolyDemoParty_create3 createParties;

    public List<PolyDemoFixedAsset> getFixedAssets() {
        return createFixedAssets.getFixedAssets();
    }
    public List<PolyDemoParty> getParties() {
        return createParties.getParties();
    }
    public List<PolyDemoCase> getCases() {
        return createCases.getCases();
    }

    @Override
    protected void execute(final ExecutionContext ec) {

        createFixedAssets = new PolyDemoFixedAsset_create6();
        createParties = new PolyDemoParty_create3();
        createCases = new PolyDemoCase_create3();

        ec.executeChild(this, new PolyDemo_Case_FixedAsset_Party_andLinks_tearDown());
        ec.executeChild(this, createFixedAssets);
        ec.executeChild(this, createParties);
        ec.executeChild(this, createCases);

        final List<PolyDemoParty> parties = createParties.getParties();
        final List<PolyDemoFixedAsset> fixedAssets = createFixedAssets.getFixedAssets();
        final List<PolyDemoCase> cases = createCases.getCases();

        // create comm channels
        int i=0;
        final PolyDemoParty party0 = parties.get(0);
        final PolyDemoParty party1 = parties.get(1);
        final PolyDemoParty party2 = parties.get(2);

        party0.addCommunicationChannel(commChannelDetailsFor(i++));
        party1.addCommunicationChannel(commChannelDetailsFor(i++));
        party1.addCommunicationChannel(commChannelDetailsFor(i++));
        party2.addCommunicationChannel(commChannelDetailsFor(i++));
        party2.addCommunicationChannel(commChannelDetailsFor(i++));
        party2.addCommunicationChannel(commChannelDetailsFor(i++));

        final PolyDemoFixedAsset fixedAsset0 = fixedAssets.get(0);
        final PolyDemoFixedAsset fixedAsset1 = fixedAssets.get(1);
        final PolyDemoFixedAsset fixedAsset2 = fixedAssets.get(2);

        fixedAsset0.createCommunicationChannel(commChannelDetailsFor(i++));
        fixedAsset1.createCommunicationChannel(commChannelDetailsFor(i++));
        fixedAsset2.createCommunicationChannel(commChannelDetailsFor(i++));

        // add content to cases
        final PolyDemoCase case0 = cases.get(0);
        final PolyDemoCase case1 = cases.get(1);

        caseContentLinks.createLink(case0, party0);
        caseContentLinks.createLink(case0, party1);
        caseContentLinks.createLink(case0, fixedAsset0);
        caseContentLinks.createLink(case0, fixedAsset1);

        caseContentLinks.createLink(case1, party1);
        caseContentLinks.createLink(case1, fixedAsset1);

        caseContentLinks.createLink(cases.get(2), party2);
        caseContentLinks.createLink(cases.get(2), fixedAsset2);

        // assign a primary content for cases
        factoryService.mixin(PolyDemoCase_makePrimary.class, case0).act(party1);
        factoryService.mixin(PolyDemoCase_makePrimary.class, case1).act(fixedAsset0);
    }

    private static String commChannelDetailsFor(final int i) {
        return String.format("0207 100 1%03d", i);
    }

    @Inject
    PolyDemoCaseContentLinks caseContentLinks;

}
