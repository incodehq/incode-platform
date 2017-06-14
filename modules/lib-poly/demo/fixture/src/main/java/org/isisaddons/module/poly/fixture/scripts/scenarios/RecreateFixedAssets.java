package org.isisaddons.module.poly.fixture.scripts.scenarios;

import org.isisaddons.module.poly.fixture.dom.modules.fixedasset.FixedAsset;
import org.isisaddons.module.poly.fixture.scripts.modules.PolyAppTearDown;
import org.isisaddons.module.poly.fixture.scripts.modules.fixedasset.FixedAssetCreate;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import com.google.common.collect.Lists;
import org.apache.isis.applib.fixturescripts.FixtureScript;

public class RecreateFixedAssets extends FixtureScript {

    public final List<String> FIXED_ASSET_NAMES = Collections.unmodifiableList(Arrays.asList(
            "Westgate Centre, Oxford", "Bond Street, London", "Arndale Centre, Manchester"));

    public RecreateFixedAssets() {
        withDiscoverability(Discoverability.DISCOVERABLE);
    }

    //region > teardown (input)

    private Boolean teardown;

    /**
     * Defaults to true
     */
    public Boolean getTeardown() {
        return teardown;
    }

    public RecreateFixedAssets setTeardown(final Boolean teardown) {
        this.teardown = teardown;
        return this;
    }
    //endregion

    //region > fixedAssets (output)
    private final List<FixedAsset> fixedAssets = Lists.newArrayList();

    /**
     * The {@link org.isisaddons.module.poly.fixture.dom.modules.fixedasset.FixedAsset}s created by this fixture (output).
     */
    public List<FixedAsset> getFixedAssets() {
        return fixedAssets;
    }
    //endregion

    @Override
    protected void execute(final ExecutionContext ec) {

        // defaults
        defaultParam("teardown", ec, true);

        //
        // execute
        //
        if(getTeardown()) {
            ec.executeChild(this, new PolyAppTearDown());
        }

        for (int i = 0; i < FIXED_ASSET_NAMES.size(); i++) {
            final FixedAssetCreate fs = new FixedAssetCreate().setName(FIXED_ASSET_NAMES.get(i));
            ec.executeChild(this, fs.getName(), fs);
            fixedAssets.add(fs.getFixedAsset());
        }

    }

}
