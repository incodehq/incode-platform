package org.isisaddons.module.poly.fixture.dom.app.homepage;

import org.isisaddons.module.poly.fixture.dom.modules.casemgmt.Case;
import org.isisaddons.module.poly.fixture.dom.modules.casemgmt.Cases;
import org.isisaddons.module.poly.fixture.dom.modules.comms.CommunicationChannel;
import org.isisaddons.module.poly.fixture.dom.modules.comms.CommunicationChannels;
import org.isisaddons.module.poly.fixture.dom.modules.fixedasset.FixedAsset;
import org.isisaddons.module.poly.fixture.dom.modules.fixedasset.FixedAssets;
import org.isisaddons.module.poly.fixture.dom.modules.party.Parties;
import org.isisaddons.module.poly.fixture.dom.modules.party.Party;

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

