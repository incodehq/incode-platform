/*
 *  Licensed to the Apache Software Foundation (ASF) under one
 *  or more contributor license agreements.  See the NOTICE file
 *  distributed with this work for additional information
 *  regarding copyright ownership.  The ASF licenses this file
 *  to you under the Apache License, Version 2.0 (the
 *  "License"); you may not use this file except in compliance
 *  with the License.  You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing,
 *  software distributed under the License is distributed on an
 *  "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 *  KIND, either express or implied.  See the License for the
 *  specific language governing permissions and limitations
 *  under the License.
 */

package domainapp.fixture.scenarios;

import domainapp.dom.modules.casemgmt.Case;
import domainapp.dom.modules.casemgmt.CaseContentContributions;
import domainapp.dom.modules.casemgmt.CasePrimaryContentContributions;
import domainapp.dom.modules.fixedasset.FixedAsset;
import domainapp.dom.modules.party.Party;
import domainapp.fixture.modules.PolyAppTearDown;

import java.util.List;
import javax.inject.Inject;
import org.apache.isis.applib.fixturescripts.FixtureScript;

public class RecreateAll extends FixtureScript {

    private RecreateCases recreateCases;
    private RecreateFixedAssets recreateFixedAssets;
    private RecreateParties recreateParties;

    public RecreateAll() {
        withDiscoverability(Discoverability.DISCOVERABLE);
    }

    //region > fixedAssets (output)
    /**
     * The {@link domainapp.dom.modules.fixedasset.FixedAsset}s created by this fixture (output).
     */
    public List<FixedAsset> getFixedAssets() {
        return recreateFixedAssets.getFixedAssets();
    }
    //endregion

    //region > parties (output)
    /**
     * The {@link domainapp.dom.modules.party.Party}s created by this fixture (output).
     */
    public List<Party> getParties() {
        return recreateParties.getParties();
    }
    //endregion

    //region > cases (output)
    /**
     * The {@link domainapp.dom.modules.casemgmt.Case}s created by this fixture (output).
     */
    public List<Case> getCases() {
        return recreateCases.getCases();
    }
    //endregion

    @Override
    protected void execute(final ExecutionContext ec) {

        recreateFixedAssets = new RecreateFixedAssets().setTeardown(false);
        recreateParties = new RecreateParties().setTeardown(false);
        recreateCases = new RecreateCases().setTeardown(false);

        //
        // execute
        //
        ec.executeChild(this, new PolyAppTearDown());
        ec.executeChild(this, recreateFixedAssets);
        ec.executeChild(this, recreateParties);
        ec.executeChild(this, recreateCases);

        final List<Party> parties = recreateParties.getParties();
        final List<FixedAsset> fixedAssets = recreateFixedAssets.getFixedAssets();
        final List<Case> cases = recreateCases.getCases();

        // create comm channels
        int i=0;
        parties.get(0).addCommunicationChannel(detailsFor(i++));
        parties.get(1).addCommunicationChannel(detailsFor(i++));
        parties.get(1).addCommunicationChannel(detailsFor(i++));
        parties.get(2).addCommunicationChannel(detailsFor(i++));
        parties.get(2).addCommunicationChannel(detailsFor(i++));
        parties.get(2).addCommunicationChannel(detailsFor(i++));

        fixedAssets.get(0).createCommunicationChannel(detailsFor(i++));
        fixedAssets.get(1).createCommunicationChannel(detailsFor(i++));
        fixedAssets.get(2).createCommunicationChannel(detailsFor(i++));

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


    @Inject
    private CaseContentContributions caseContentContributions;

    @Inject
    private CasePrimaryContentContributions casePrimaryContentContributions;


    private String detailsFor(final int i) {
        return String.format("0207 100 1%03d", i);
    }
}
