package org.incode.domainapp.example.dom.dom.tags.fixture;

import org.apache.isis.applib.fixturescripts.DiscoverableFixtureScript;

import org.incode.domainapp.example.dom.dom.tags.dom.demo.ExampleTaggableEntityMenu;
import org.incode.domainapp.example.dom.dom.tags.fixture.data.ExampleTaggableEntity_Bar_Pepsi_Drink;
import org.incode.domainapp.example.dom.dom.tags.fixture.data.ExampleTaggableEntity_Baz_McDonalds_FastFood;
import org.incode.domainapp.example.dom.dom.tags.fixture.data.ExampleTaggableEntity_Bip_CocaCola_Drink;
import org.incode.domainapp.example.dom.dom.tags.fixture.data.ExampleTaggableEntity_Bop_Levis_Clothing;

public class ExampleTaggableEntity_withTags_recreate3 extends DiscoverableFixtureScript {

    @javax.inject.Inject
    ExampleTaggableEntityMenu exampleTaggableEntities;


    @Override
    protected void execute(ExecutionContext executionContext) {

        executionContext.executeChild(this, new ExampleTaggableEntitiesTearDownFixture());

        executionContext.executeChild(this, new ExampleTaggableEntity_Bip_CocaCola_Drink());
        executionContext.executeChild(this, new ExampleTaggableEntity_Bar_Pepsi_Drink());
        executionContext.executeChild(this, new ExampleTaggableEntity_Baz_McDonalds_FastFood());
        executionContext.executeChild(this, new ExampleTaggableEntity_Bop_Levis_Clothing());
    }

}
