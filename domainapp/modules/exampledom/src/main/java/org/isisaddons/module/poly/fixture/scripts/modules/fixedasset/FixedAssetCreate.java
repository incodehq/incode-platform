package org.isisaddons.module.poly.fixture.scripts.modules.fixedasset;

import org.isisaddons.module.poly.fixture.dom.modules.fixedasset.FixedAsset;
import org.isisaddons.module.poly.fixture.dom.modules.fixedasset.FixedAssets;

import org.apache.isis.applib.fixturescripts.FixtureScript;

public class FixedAssetCreate extends FixtureScript {

    //region > name (input)
    private String name;
    public String getName() {
        return name;
    }

    public FixedAssetCreate setName(final String name) {
        this.name = name;
        return this;
    }
    //endregion

    //region > party (output)
    private FixedAsset fixedAsset;

    /**
     * The created fixed asset (output).
     */
    public FixedAsset getFixedAsset() {
        return fixedAsset;
    }
    //endregion

    @Override
    protected void execute(final ExecutionContext ec) {

        final String name = checkParam("name", ec, String.class);

        this.fixedAsset = fixedAssets.create(name);

        // also make available to UI
        ec.addResult(this, fixedAsset);
    }

    @javax.inject.Inject
    private FixedAssets fixedAssets;

}
