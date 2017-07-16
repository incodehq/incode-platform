package org.isisaddons.module.poly.fixture.dom.app.homepage;

import domainapp.modules.exampledom.lib.poly.dom.democasemgmt.Case;
import domainapp.modules.exampledom.lib.poly.dom.democasemgmt.Cases;
import domainapp.modules.exampledom.lib.poly.dom.democommchannel.CommunicationChannel;
import domainapp.modules.exampledom.lib.poly.dom.democommchannel.CommunicationChannels;
import domainapp.modules.exampledom.lib.poly.dom.demofixedasset.FixedAsset;
import domainapp.modules.exampledom.lib.poly.dom.demofixedasset.FixedAssets;
import domainapp.modules.exampledom.lib.poly.dom.demoparty.Parties;
import domainapp.modules.exampledom.lib.poly.dom.demoparty.Party;

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

