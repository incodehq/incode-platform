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
package domainapp.fixture.dom.app.homepage;

import domainapp.fixture.dom.modules.casemgmt.Case;
import domainapp.fixture.dom.modules.casemgmt.Cases;
import domainapp.fixture.dom.modules.comms.CommunicationChannel;
import domainapp.fixture.dom.modules.comms.CommunicationChannels;
import domainapp.fixture.dom.modules.fixedasset.FixedAsset;
import domainapp.fixture.dom.modules.fixedasset.FixedAssets;
import domainapp.fixture.dom.modules.party.Parties;
import domainapp.fixture.dom.modules.party.Party;

import java.util.List;
import org.apache.isis.applib.annotation.ViewModel;

@ViewModel
public class HomePage {

    //region > title
    public String title() {
        return getParties().size() + " parties, " + getFixedAssets().size() + " fixed assets, "+ getCases().size() + " cases, " +  getCommunicationChannels().size() + " comm channels";
    }
    //endregion

    public List<CommunicationChannel> getCommunicationChannels() {
        return communicationChannelsMenu.listAll();
    }

    public List<Party> getParties() {
        return parties.listAll();
    }

    public List<FixedAsset> getFixedAssets() {
        return fixedAssets.listAll();
    }

    public List<Case> getCases() {
        return cases.listAll();
    }

    @javax.inject.Inject
    CommunicationChannels communicationChannelsMenu;

    @javax.inject.Inject
    Parties parties;

    @javax.inject.Inject
    FixedAssets fixedAssets;

    @javax.inject.Inject
    Cases cases;
}

