package org.incode.domainapp.example.dom.dom.tags.fixture;

import org.incode.domainapp.example.dom.dom.tags.dom.demo.ExampleTaggableEntityMenu;
import org.incode.domainapp.example.dom.dom.tags.fixture.data.Bar_Pepsi_Drink;
import org.incode.domainapp.example.dom.dom.tags.fixture.data.Baz_McDonalds_FastFood;
import org.incode.domainapp.example.dom.dom.tags.fixture.data.Bip_CocaCola_Drink;
import org.incode.domainapp.example.dom.dom.tags.fixture.data.Bop_Levis_Clothing;
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
    private ExampleTaggableEntityMenu exampleTaggableEntities;

}
