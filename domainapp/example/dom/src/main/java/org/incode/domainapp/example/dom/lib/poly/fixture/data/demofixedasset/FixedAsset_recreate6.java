package org.incode.domainapp.example.dom.lib.poly.fixture.data.demofixedasset;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import com.google.common.collect.Lists;

import org.apache.isis.applib.fixturescripts.FixtureScript;

import org.incode.domainapp.example.dom.lib.poly.dom.demofixedasset.FixedAsset;
import org.incode.domainapp.example.dom.lib.poly.fixture.Case_FixedAsset_Party_andLinks_tearDown;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Accessors(chain = true)
public class FixedAsset_recreate6 extends FixtureScript {

    public final List<String> FIXED_ASSET_NAMES = Collections.unmodifiableList(Arrays.asList(
            "Westgate Centre, Oxford", "Bond Street, London", "Arndale Centre, Manchester"));


    @Getter @Setter
    private Boolean teardown;

    @Getter
    private final List<FixedAsset> fixedAssets = Lists.newArrayList();

    @Override
    protected void execute(final ExecutionContext ec) {

        // defaults
        defaultParam("teardown", ec, true);

        if(getTeardown()) {
            ec.executeChild(this, new Case_FixedAsset_Party_andLinks_tearDown());
        }

        for (int i = 0; i < FIXED_ASSET_NAMES.size(); i++) {
            final FixedAsset_create fs = new FixedAsset_create().setName(FIXED_ASSET_NAMES.get(i));
            ec.executeChild(this, fs.getName(), fs);
            fixedAssets.add(fs.getFixedAsset());
        }

    }

}
