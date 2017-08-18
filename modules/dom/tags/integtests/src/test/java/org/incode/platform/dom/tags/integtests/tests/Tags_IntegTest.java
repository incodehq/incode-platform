package org.incode.platform.dom.tags.integtests.tests;

import java.util.List;
import java.util.Set;

import javax.inject.Inject;
import javax.jdo.Query;

import com.google.common.collect.Iterables;
import com.google.common.collect.Sets;

import org.junit.Before;
import org.junit.Test;

import org.apache.isis.applib.services.jdosupport.IsisJdoSupport;

import org.isisaddons.module.tags.dom.Tag;
import org.isisaddons.module.tags.dom.Tags;

import org.incode.domainapp.example.dom.dom.tags.dom.demo.DemoTaggableObject;
import org.incode.platform.dom.tags.integtests.TagsModuleIntegTestAbstract;

import org.incode.domainapp.example.dom.dom.tags.dom.demo.DemoTaggableObjectMenu;
import org.incode.domainapp.example.dom.dom.tags.fixture.DemoTaggableObjects_tearDown;
import org.incode.domainapp.example.dom.dom.tags.fixture.data.DemoTaggableObject_Bar_Pepsi_Drink;
import org.incode.domainapp.example.dom.dom.tags.fixture.data.DemoTaggableObject_Baz_McDonalds_FastFood;
import org.incode.domainapp.example.dom.dom.tags.fixture.data.DemoTaggableObject_Bip_CocaCola_Drink;
import org.incode.domainapp.example.dom.dom.tags.fixture.data.DemoTaggableObject_Bop_Levis_Clothing;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.Assert.assertThat;

public class Tags_IntegTest extends TagsModuleIntegTestAbstract {

    DemoTaggableObject entity;

    @Before
    public void setUpData() throws Exception {
        scenarioExecution().install(
                new DemoTaggableObjects_tearDown(),
                new DemoTaggableObject_Bip_CocaCola_Drink(),
                new DemoTaggableObject_Bar_Pepsi_Drink(),
                new DemoTaggableObject_Baz_McDonalds_FastFood(),
                new DemoTaggableObject_Bop_Levis_Clothing()
                );
    }

    @Inject
    DemoTaggableObjectMenu exampleTaggableEntities;

    @Inject
    Tags tags;

    @Inject
    IsisJdoSupport isisJdoSupport;

    @Before
    public void setUp() throws Exception {
        final List<DemoTaggableObject> all = wrap(exampleTaggableEntities).listAllTaggableObjects();
        assertThat(all.size(), is(4));

        entity = all.get(0);
        assertThat(entity.getBrand(), is("Coca Cola"));
        assertThat(entity.getSector(), is("Drink"));
    }

    public static class TagsFor extends Tags_IntegTest {

        @Test
        public void whenOthersUseTheTag() throws Exception {

            // given
            assertThat(entity.choicesSector(), containsInAnyOrder("Drink", "Fast food", "Clothing"));
            assertThat(uniqueValuesOf(tagsWithKey("Sector")), hasSize(3));
            assertThat(entity.getSector(), is("Drink")); // 2 using Drink

            // when
            entity.setSector("Fast food");
            nextTransaction();

            // then updated...
            assertThat(entity.getSector(), is("Fast food"));
            // but no tags removed
            assertThat(entity.choicesSector(), containsInAnyOrder("Drink", "Fast food", "Clothing"));
            assertThat(uniqueValuesOf(tagsWithKey("Sector")), hasSize(3));
        }

        @Test
        public void whenNoOthersUseTheTag() throws Exception {

            // given
            assertThat(entity.choicesBrand(), containsInAnyOrder("Coca Cola", "Levi's", "McDonalds", "Pepsi"));
            assertThat(uniqueValuesOf(tagsWithKey("Brand")), hasSize(4));

            // when
            entity.setBrand("Pepsi");
            nextTransaction();

            // then updated...
            assertThat(entity.getBrand(), is("Pepsi"));
            // then tag removed
            assertThat(entity.choicesBrand(), containsInAnyOrder("Levi's", "McDonalds", "Pepsi"));
            assertThat(uniqueValuesOf(tagsWithKey("Brand")), hasSize(3));
        }

        private List<Tag> tagsWithKey(String key) {
            final Query query = isisJdoSupport.getJdoPersistenceManager().newQuery("SELECT FROM " + Tag.class.getName() + " where key == :key");
            return (List<Tag>) query.execute(key);
        }

        private static Set<String> uniqueValuesOf(List<Tag> tags) {
            return Sets.newTreeSet(Iterables.transform(tags, Tag.Functions.GET_VALUE));
        }

    }



}