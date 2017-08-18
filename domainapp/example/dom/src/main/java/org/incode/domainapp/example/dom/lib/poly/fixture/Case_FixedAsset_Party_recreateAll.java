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
        parties.get(0).addCommunicationChannel(commChannelDetailsFor(i++));
        parties.get(1).addCommunicationChannel(commChannelDetailsFor(i++));
        parties.get(1).addCommunicationChannel(commChannelDetailsFor(i++));
        parties.get(2).addCommunicationChannel(commChannelDetailsFor(i++));
        parties.get(2).addCommunicationChannel(commChannelDetailsFor(i++));
        parties.get(2).addCommunicationChannel(commChannelDetailsFor(i++));

        fixedAssets.get(0).createCommunicationChannel(commChannelDetailsFor(i++));
        fixedAssets.get(1).createCommunicationChannel(commChannelDetailsFor(i++));
        fixedAssets.get(2).createCommunicationChannel(commChannelDetailsFor(i++));

        // add content to cases
        caseContentContributions.addToCase(cases.get(0), parties.get(0));
        caseContentContributions.addToCase(cases.get(0), parties.get(1));
        caseContentContributions.addToCase(cases.get(0), fixedAssets.get(0));
        caseContentContributions.addToCase(cases.get(0), fixedAssets.get(1));

        caseContentContributions.addToCase(cases.get(1), parties.get(1));
        caseContentContributions.addToCase(cases.get(1), fixedAssets.get(1));

        caseContentContributions.addToCase(cases.get(2), parties.get(2));
        caseContentContributions.addToCase(cases.get(2), fixedAssets.get(2));

        // assign a primary content for cases
        casePrimaryContentContributions.makePrimary(cases.get(0), parties.get(1));
        casePrimaryContentContributions.makePrimary(cases.get(1), fixedAssets.get(0));
    }

    private static String commChannelDetailsFor(final int i) {
        return String.format("0207 100 1%03d", i);
    }

    @Inject
    private CaseContentContributions caseContentContributions;
    @Inject
    private CasePrimaryContentContributions casePrimaryContentContributions;

}
