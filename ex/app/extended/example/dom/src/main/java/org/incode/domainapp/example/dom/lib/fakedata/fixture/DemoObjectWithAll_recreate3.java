package org.incode.domainapp.example.dom.lib.fakedata.fixture;

import java.util.List;

import com.google.common.collect.Lists;

import org.apache.isis.applib.annotation.Programmatic;
import org.apache.isis.applib.fixturescripts.FixtureScript;

import org.incode.domainapp.example.dom.demo.dom.demowithall.DemoObjectWithAll;
import org.incode.domainapp.example.dom.demo.fixture.teardown.sub.DemoObjectWithAll_tearDown;

import lombok.Getter;
import lombok.Setter;

public class DemoObjectWithAll_recreate3 extends FixtureScript {

    @Getter(onMethod = @__( @Programmatic )) @Setter
    private Integer numberToCreate;

    @Getter(onMethod = @__( @Programmatic ))
    private List<DemoObjectWithAll> demoObjects = Lists.newArrayList();

    @Override
    protected void execute(final ExecutionContext executionContext) {

        this.defaultParam("numberToCreate", executionContext, 3);

        executionContext.executeChild(this, new DemoObjectWithAll_tearDown());


        final DemoObjectWithAll_create3 fs = new DemoObjectWithAll_create3();
        fs.setNumberToCreate(numberToCreate);

        executionContext.executeChild(this, fs);
        demoObjects = fs.getDemoObjects();

    }

}
