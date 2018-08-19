package org.isisaddons.module.poly.fixture.demoapp.app.fixturescripts.data.demofixedasset;

import org.apache.isis.applib.fixturescripts.FixtureScript;

import org.isisaddons.module.poly.fixture.demoapp.demomodule.dom.fixedasset.PolyDemoFixedAsset;
import org.isisaddons.module.poly.fixture.demoapp.demomodule.dom.fixedasset.PolyDemoFixedAssets;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Accessors(chain = true)
public class PolyDemoFixedAsset_create extends FixtureScript {

    @Getter @Setter
    private String name;

    @Getter
    private PolyDemoFixedAsset fixedAsset;

    @Override
    protected void execute(final ExecutionContext ec) {

        final String name = checkParam("name", ec, String.class);

        this.fixedAsset = fixedAssets.createFixedAsset(name);

        // also make available to UI
        ec.addResult(this, fixedAsset);
    }

    @javax.inject.Inject
    PolyDemoFixedAssets fixedAssets;

}
