package org.isisaddons.module.tags.fixture.scripts;

import org.isisaddons.module.tags.fixture.dom.ExampleTaggableEntities;
import org.isisaddons.module.tags.fixture.scripts.entities.Bar_Pepsi_Drink;
import org.isisaddons.module.tags.fixture.scripts.entities.Baz_McDonalds_FastFood;
import org.isisaddons.module.tags.fixture.scripts.entities.Bip_CocaCola_Drink;
import org.isisaddons.module.tags.fixture.scripts.entities.Bop_Levis_Clothing;
import org.apache.isis.applib.fixturescripts.DiscoverableFixtureScript;

public class ExampleTaggableEntitiesSetUpFixture extends DiscoverableFixtureScript {

    @Override
    protected void execute(ExecutionContext executionContext) {

        execute(new ExampleTaggableEntitiesTearDownFixture(), executionContext);

        execute(new Bip_CocaCola_Drink(), executionContext);
        execute(new Bar_Pepsi_Drink(), executionContext);
        execute(new Baz_McDonalds_FastFood(), executionContext);
        execute(new Bop_Levis_Clothing(), executionContext);
    }

    // //////////////////////////////////////

    @javax.inject.Inject
    private ExampleTaggableEntities exampleTaggableEntities;

}
