package org.incode.example.tags.demo.usage.fixture;

import org.apache.isis.applib.fixturescripts.FixtureScript;

import org.incode.example.tags.demo.usage.dom.demo.TaggableObjectMenu;
import org.incode.example.tags.demo.usage.fixture.data.TaggableObject_Bar_Pepsi_Drink;
import org.incode.example.tags.demo.usage.fixture.data.TaggableObject_Baz_McDonalds_FastFood;
import org.incode.example.tags.demo.usage.fixture.data.TaggableObject_Bip_CocaCola_Drink;
import org.incode.example.tags.demo.usage.fixture.data.TaggableObject_Bop_Levis_Clothing;

public class TaggableObject_withTags_create3 extends FixtureScript {

    @javax.inject.Inject
    TaggableObjectMenu demoTaggableObjects;


    @Override
    protected void execute(ExecutionContext executionContext) {

        executionContext.executeChild(this, new TaggableObject_Bip_CocaCola_Drink());
        executionContext.executeChild(this, new TaggableObject_Bar_Pepsi_Drink());
        executionContext.executeChild(this, new TaggableObject_Baz_McDonalds_FastFood());
        executionContext.executeChild(this, new TaggableObject_Bop_Levis_Clothing());
    }

}
