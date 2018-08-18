package org.isisaddons.module.fakedata.fixture.demoapp.demomodule.fixturescripts;

import java.util.List;

import com.google.common.collect.Lists;

import org.apache.isis.applib.annotation.Programmatic;
import org.apache.isis.applib.fixturescripts.FixtureScript;

import org.isisaddons.module.fakedata.dom.FakeDataService;

import lombok.Getter;
import lombok.Setter;

@lombok.experimental.Accessors(chain = true)
public class DemoObjectWithAll_create3 extends FixtureScript {

    @Getter(onMethod = @__( @Programmatic )) @Setter
    private Integer numberToCreate;
    @Getter(onMethod = @__( @Programmatic )) @Setter
    private Boolean withFakeData;

    @Getter(onMethod = @__( @Programmatic ))
    private List<DemoObjectWithAll> demoObjects = Lists.newArrayList();

    @Override
    protected void execute(final ExecutionContext executionContext) {

        this.defaultParam("numberToCreate", executionContext, 3);
        this.defaultParam("withFakeData", executionContext, true);

        for (int i = 0; i < getNumberToCreate(); i++) {
            final DemoObjectWithAll_create_withFakeData fs = new DemoObjectWithAll_create_withFakeData().setWithFakeData(withFakeData);
            executionContext.executeChildT(this, fs);
            demoObjects.add(fs.getFakeDataDemoObject());
        }

    }

    @javax.inject.Inject
    FakeDataService fakeDataService;
}
