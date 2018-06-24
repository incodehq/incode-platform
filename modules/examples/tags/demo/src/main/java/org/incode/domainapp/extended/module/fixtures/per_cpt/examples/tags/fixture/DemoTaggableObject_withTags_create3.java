package org.incode.domainapp.extended.module.fixtures.per_cpt.examples.tags.fixture;

import org.apache.isis.applib.fixturescripts.FixtureScript;

import org.incode.domainapp.extended.module.fixtures.per_cpt.examples.tags.dom.demo.DemoTaggableObjectMenu;
import org.incode.domainapp.extended.module.fixtures.per_cpt.examples.tags.fixture.data.DemoTaggableObject_Bar_Pepsi_Drink;
import org.incode.domainapp.extended.module.fixtures.per_cpt.examples.tags.fixture.data.DemoTaggableObject_Baz_McDonalds_FastFood;
import org.incode.domainapp.extended.module.fixtures.per_cpt.examples.tags.fixture.data.DemoTaggableObject_Bip_CocaCola_Drink;
import org.incode.domainapp.extended.module.fixtures.per_cpt.examples.tags.fixture.data.DemoTaggableObject_Bop_Levis_Clothing;

public class DemoTaggableObject_withTags_create3 extends FixtureScript {

    @javax.inject.Inject
    DemoTaggableObjectMenu demoTaggableObjects;


    @Override
    protected void execute(ExecutionContext executionContext) {

        executionContext.executeChild(this, new DemoTaggableObject_Bip_CocaCola_Drink());
        executionContext.executeChild(this, new DemoTaggableObject_Bar_Pepsi_Drink());
        executionContext.executeChild(this, new DemoTaggableObject_Baz_McDonalds_FastFood());
        executionContext.executeChild(this, new DemoTaggableObject_Bop_Levis_Clothing());
    }

}
