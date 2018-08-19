package org.isisaddons.module.poly.fixture.demoapp.app.fixturescripts.data.democasemgmt;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import com.google.common.collect.Lists;

import org.apache.isis.applib.fixturescripts.FixtureScript;

import org.isisaddons.module.poly.fixture.demoapp.demomodule.dom.casemgmt.Case;

import lombok.Getter;
import lombok.experimental.Accessors;

@Accessors(chain = true)
public class Case_create3 extends FixtureScript {

    public final List<String> CASE_NAMES = Collections.unmodifiableList(Arrays.asList(
            "Red", "Green", "Blue"));


    @Getter
    private final List<Case> cases = Lists.newArrayList();

    @Override
    protected void execute(final ExecutionContext ec) {

        for (int i = 0; i < CASE_NAMES.size(); i++) {
            final Case_create fs = new Case_create().setName(CASE_NAMES.get(i));
            ec.executeChild(this, fs.getName(), fs);
            cases.add(fs.getCase());
        }

    }
}
