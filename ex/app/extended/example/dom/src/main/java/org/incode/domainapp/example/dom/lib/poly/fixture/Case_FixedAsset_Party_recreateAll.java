package org.incode.domainapp.example.dom.lib.poly.fixture;

import java.util.List;

import javax.inject.Inject;

import org.apache.isis.applib.fixturescripts.FixtureScript;

import org.incode.domainapp.example.dom.lib.poly.dom.demofixedasset.FixedAsset;
import org.incode.domainapp.example.dom.lib.poly.dom.demoparty.Party;

import org.incode.domainapp.example.dom.lib.poly.dom.democasemgmt.Case;
import org.incode.domainapp.example.dom.lib.poly.dom.poly.casecontent.CaseContentContributions;
import org.incode.domainapp.example.dom.lib.poly.dom.poly.caseprimary.CasePrimaryContentContributions;
import org.incode.domainapp.example.dom.lib.poly.fixture.data.democasemgmt.Case_recreate3;
import org.incode.domainapp.example.dom.lib.poly.fixture.data.demofixedasset.FixedAsset_recreate6;
import org.incode.domainapp.example.dom.lib.poly.fixture.data.demoparty.Party_recreate3;

public class Case_FixedAsset_Party_recreateAll extends FixtureScript {

    private Case_recreate3 recreateCases;
    private FixedAsset_recreate6 recreateFixedAssets;
    private Party_recreate3 recreateParties;

    public List<FixedAsset> getFixedAssets() {
        return recreateFixedAssets.getFixedAssets();
    }
    public List<Party> getParties() {
        return recreateParties.getParties();
    }
    public List<Case> getCases() {
        return recreateCases.getCases();
    }

    @Override
    protected void execute(final ExecutionContext ec) {

        recreateFixedAssets = new FixedAsset_recreate6().setTeardown(false);
        recreateParties = new Party_recreate3().setTeardown(false);
        recreateCases = new Case_recreate3().setTeardown(false);

        ec.executeChild(this, new Case_FixedAsset_Party_andLinks_tearDown());
        ec.executeChild(this, recreateFixedAssets);
        ec.executeChild(this, recreateParties);
        ec.executeChild(this, recreateCases);

        final List<Party> parties = recreateParties.getParties();
        final List<FixedAsset> fixedAssets = recreateFixedAssets.getFixedAssets();
        final List<Case> cases = recreateCases.getCases();

        // create comm channels
        int i=0;
        final Party party0 = parties.get(0);
        final Party party1 = parties.get(1);
        final Party party2 = parties.get(2);

        party0.addCommunicationChannel(commChannelDetailsFor(i++));
        party1.addCommunicationChannel(commChannelDetailsFor(i++));
        party1.addCommunicationChannel(commChannelDetailsFor(i++));
        party2.addCommunicationChannel(commChannelDetailsFor(i++));
        party2.addCommunicationChannel(commChannelDetailsFor(i++));
        party2.addCommunicationChannel(commChannelDetailsFor(i++));

        final FixedAsset fixedAsset0 = fixedAssets.get(0);
        final FixedAsset fixedAsset1 = fixedAssets.get(1);
        final FixedAsset fixedAsset2 = fixedAssets.get(2);

        fixedAsset0.createCommunicationChannel(commChannelDetailsFor(i++));
        fixedAsset1.createCommunicationChannel(commChannelDetailsFor(i++));
        fixedAsset2.createCommunicationChannel(commChannelDetailsFor(i++));

        // add content to cases
        final Case case0 = cases.get(0);
        final Case case1 = cases.get(1);

        caseContentContributions.addToCase(case0, party0);
        caseContentContributions.addToCase(case0, party1);
        caseContentContributions.addToCase(case0, fixedAsset0);
        caseContentContributions.addToCase(case0, fixedAsset1);

        caseContentContributions.addToCase(case1, party1);
        caseContentContributions.addToCase(case1, fixedAsset1);

        caseContentContributions.addToCase(cases.get(2), party2);
        caseContentContributions.addToCase(cases.get(2), fixedAsset2);

        // assign a primary content for cases
        casePrimaryContentContributions.makePrimary(case0, party1);
        casePrimaryContentContributions.makePrimary(case1, fixedAsset0);
    }

    private static String commChannelDetailsFor(final int i) {
        return String.format("0207 100 1%03d", i);
    }

    @Inject
    private CaseContentContributions caseContentContributions;
    @Inject
    private CasePrimaryContentContributions casePrimaryContentContributions;

}
