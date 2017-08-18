package org.incode.domainapp.example.dom.lib.poly.fixture.data.democasemgmt;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import com.google.common.collect.Lists;

import org.apache.isis.applib.fixturescripts.FixtureScript;

import org.incode.domainapp.example.dom.lib.poly.dom.democasemgmt.Case;
import org.incode.domainapp.example.dom.lib.poly.fixture.Case_FixedAsset_Party_andLinks_tearDown;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Accessors(chain = true)
public class Case_recreate3 extends FixtureScript {

    public final List<String> CASE_NAMES = Collections.unmodifiableList(Arrays.asList(
            "Red", "Green", "Blue"));


    @Getter @Setter
    private Boolean teardown;

    @Getter
    private final List<Case> cases = Lists.newArrayList();

    @Override
    protected void execute(final ExecutionContext ec) {

        defaultParam("teardown", ec, true);
        if(getTeardown()) {
            ec.executeChild(this, new Case_FixedAsset_Party_andLinks_tearDown());
        }


        for (int i = 0; i < CASE_NAMES.size(); i++) {
            final Case_create fs = new Case_create().setName(CASE_NAMES.get(i));
            ec.executeChild(this, fs.getName(), fs);
            cases.add(fs.getCase());
        }

    }
}
