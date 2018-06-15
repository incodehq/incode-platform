package org.incode.domainapp.module.fixtures.per_cpt.examples.classification.fixture;

import java.util.List;

import com.google.common.collect.Lists;

import org.apache.isis.applib.fixturescripts.FixtureScript;

import org.incode.domainapp.module.fixtures.shared.demowithatpath.dom.DemoObjectWithAtPath;
import org.incode.domainapp.module.fixtures.shared.otherwithatpath.dom.OtherObjectWithAtPath;

import lombok.Getter;

public class DemoObjectWithAtPath_and_OtherObjectWithAtPath_recreate3 extends
        FixtureScript {

    @Getter
    private List<DemoObjectWithAtPath> demoObjects = Lists.newArrayList();

    @Getter
    private List<OtherObjectWithAtPath> otherObjects = Lists.newArrayList();

    @Override
    protected void execute(final ExecutionContext executionContext) {
        // prereqs
        executionContext.executeChild(this,
                new DemoObjectWithAtPath_and_OtherObjectWithAtPath_tearDown());
        executionContext.executeChild(this,
                new DemoObjectWithAtPath_and_OtherObjectWithAtPath_create3());

    }


}
