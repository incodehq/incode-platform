package org.incode.domainapp.example.dom.lib.poly.fixture.data.demofixedasset;

import org.apache.isis.applib.fixturescripts.FixtureScript;

import org.incode.domainapp.example.dom.lib.poly.dom.demofixedasset.FixedAsset;
import org.incode.domainapp.example.dom.lib.poly.dom.demofixedasset.FixedAssets;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Accessors(chain = true)
public class FixedAsset_create extends FixtureScript {

    @Getter @Setter
    private String name;

    @Getter
    private FixedAsset fixedAsset;

    @Override
    protected void execute(final ExecutionContext ec) {

        final String name = checkParam("name", ec, String.class);

        this.fixedAsset = fixedAssets.createFixedAsset(name);

        // also make available to UI
        ec.addResult(this, fixedAsset);
    }

    @javax.inject.Inject
    FixedAssets fixedAssets;

}
