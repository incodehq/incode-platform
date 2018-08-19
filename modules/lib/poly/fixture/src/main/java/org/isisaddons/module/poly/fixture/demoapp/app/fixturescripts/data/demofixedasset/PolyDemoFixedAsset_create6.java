package org.isisaddons.module.poly.fixture.demoapp.app.fixturescripts.data.demofixedasset;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import com.google.common.collect.Lists;

import org.apache.isis.applib.fixturescripts.FixtureScript;

import org.isisaddons.module.poly.fixture.demoapp.demomodule.dom.fixedasset.PolyDemoFixedAsset;

import lombok.Getter;
import lombok.experimental.Accessors;

@Accessors(chain = true)
public class PolyDemoFixedAsset_create6 extends FixtureScript {

    public final List<String> FIXED_ASSET_NAMES = Collections.unmodifiableList(Arrays.asList(
            "Westgate Centre, Oxford", "Bond Street, London", "Arndale Centre, Manchester"));


    @Getter
    private final List<PolyDemoFixedAsset> fixedAssets = Lists.newArrayList();

    @Override
    protected void execute(final ExecutionContext ec) {

        for (int i = 0; i < FIXED_ASSET_NAMES.size(); i++) {
            final PolyDemoFixedAsset_create fs = new PolyDemoFixedAsset_create().setName(FIXED_ASSET_NAMES.get(i));
            ec.executeChild(this, fs.getName(), fs);
            fixedAssets.add(fs.getFixedAsset());
        }

    }

}
