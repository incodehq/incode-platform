package org.incode.domainapp.example.dom.lib.fakedata.fixture;

import java.util.List;

import com.google.common.collect.Lists;

import org.apache.isis.applib.annotation.Programmatic;
import org.apache.isis.applib.fixturescripts.DiscoverableFixtureScript;

import org.isisaddons.module.fakedata.dom.FakeDataService;

import org.incode.domainapp.example.dom.demo.dom.demowithall.DemoObjectWithAll;
import org.incode.domainapp.example.dom.lib.fakedata.fixture.data.DemoObjectWithAllCreate;

import lombok.Getter;
import lombok.Setter;

public class FakeDataDemoObjectsScenario extends DiscoverableFixtureScript {

    public FakeDataDemoObjectsScenario() {
        withDiscoverability(Discoverability.DISCOVERABLE);
    }

    @Getter(onMethod = @__( @Programmatic )) @Setter
    private Integer numberToCreate;

    @Getter(onMethod = @__( @Programmatic ))
    private List<DemoObjectWithAll> fakeDataDemoObjects = Lists.newArrayList();

    @Override
    protected void execute(final ExecutionContext executionContext) {

        this.defaultParam("numberToCreate", executionContext, new Integer(3));

        // prereqs
        executionContext.executeChild(this, new FakeDataDemoObjectsTearDownFixture());

        // create as many as requested
        for (int i = 0; i < getNumberToCreate(); i++) {
            final DemoObjectWithAllCreate fs = new DemoObjectWithAllCreate();
            executionContext.executeChildT(this, fs);
            fakeDataDemoObjects.add(fs.getFakeDataDemoObject());
        }

    }

    @javax.inject.Inject
    FakeDataService fakeDataService;
}
