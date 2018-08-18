package org.isisaddons.module.poly.fixture;

import javax.xml.bind.annotation.XmlRootElement;

import org.apache.isis.applib.ModuleAbstract;
import org.apache.isis.applib.fixturescripts.FixtureScript;
import org.apache.isis.applib.fixturescripts.teardown.TeardownFixtureAbstract2;

@XmlRootElement(name = "module")
public class FixturesModuleLibPolySubmodule extends ModuleAbstract {

    @Override public FixtureScript getTeardownFixture() {
        return new TeardownFixtureAbstract2() {
            @Override protected void execute(final ExecutionContext executionContext) {

                deleteFrom(CaseContentLinkForFixedAsset.class);
                deleteFrom(CaseContentLinkForParty.class);
                deleteFrom(CaseContentLink.class);

                deleteFrom(CasePrimaryContentLinkForFixedAsset.class);
                deleteFrom(CasePrimaryContentLinkForParty.class);
                deleteFrom(CasePrimaryContentLink.class);

                deleteFrom(CommunicationChannelOwnerLinkForParty.class);
                deleteFrom(CommunicationChannelOwnerLinkForFixedAsset.class);
                deleteFrom(CommunicationChannelOwnerLink.class);
                deleteFrom(CommunicationChannel.class);

                deleteFrom(Party.class);
                deleteFrom(FixedAsset.class);
                deleteFrom(Case.class);
            }
        };
    }
}
