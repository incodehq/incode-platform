package org.isisaddons.module.fakedata.fixture.scripts.scenarios;

import java.util.List;
import com.google.common.collect.Lists;
import org.isisaddons.module.fakedata.dom.FakeDataService;
import org.isisaddons.module.fakedata.fixture.dom.FakeDataDemoObject;
import org.isisaddons.module.fakedata.fixture.scripts.FakeDataDemoObjectsTearDownFixture;
import org.isisaddons.module.fakedata.fixture.scripts.modules.fakedata.FakeDataDemoObjectCreate;
import org.apache.isis.applib.annotation.Programmatic;
import org.apache.isis.applib.fixturescripts.DiscoverableFixtureScript;

public class FakeDataDemoObjectsScenario extends DiscoverableFixtureScript {

    public FakeDataDemoObjectsScenario() {
        withDiscoverability(Discoverability.DISCOVERABLE);
    }

    //region > numberToCreate (input property)
    private Integer numberToCreate;
    @Programmatic
    public Integer getNumberToCreate() {
        return numberToCreate;
    }
    public void setNumberToCreate(final Integer numberToCreate) {
        this.numberToCreate = numberToCreate;
    }
    //endregion

    //region > fakeDataDemoObjects (output property)
    private List<FakeDataDemoObject> fakeDataDemoObjects = Lists.newArrayList();

    @Programmatic
    public List<FakeDataDemoObject> getFakeDataDemoObjects() {
        return fakeDataDemoObjects;
    }
    //endregion

    @Override
    protected void execute(final ExecutionContext executionContext) {

        this.defaultParam("numberToCreate", executionContext, new Integer(3));

        // prereqs
        executionContext.executeChild(this, new FakeDataDemoObjectsTearDownFixture());

        // create as many as requested
        for (int i = 0; i < getNumberToCreate(); i++) {
            final FakeDataDemoObjectCreate fs = new FakeDataDemoObjectCreate();
            executionContext.executeChildT(this, fs);
            fakeDataDemoObjects.add(fs.getFakeDataDemoObject());
        }

    }

    // //////////////////////////////////////

    // //////////////////////////////////////

    @javax.inject.Inject
    private FakeDataService fakeDataService;
}
