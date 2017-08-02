package org.incode.module.classification.integtests.category;

import javax.inject.Inject;

import org.junit.Before;
import org.junit.Test;

import org.apache.isis.applib.services.wrapper.InvalidException;

import org.incode.module.classification.dom.impl.applicability.ApplicabilityRepository;
import org.incode.module.classification.dom.impl.category.Category;
import org.incode.module.classification.dom.impl.category.CategoryRepository;
import org.incode.module.classification.dom.impl.classification.ClassificationRepository;
import org.incode.module.classification.dom.spi.ApplicationTenancyService;
import domainapp.modules.exampledom.module.classification.dom.demo.DemoObjectMenu;
import domainapp.modules.exampledom.module.classification.fixture.ClassifiedDemoObjectsFixture;
import domainapp.modules.exampledom.module.classification.fixture.ClassificationDemoAppTearDownFixture;
import org.incode.module.classification.integtests.ClassificationModuleIntegTestAbstract;

import static org.assertj.core.api.Assertions.assertThat;

public class Category_name_IntegTest extends ClassificationModuleIntegTestAbstract {

    @Inject
    ClassificationRepository classificationRepository;
    @Inject
    CategoryRepository categoryRepository;
    @Inject
    ApplicabilityRepository applicabilityRepository;

    @Inject
    DemoObjectMenu demoObjectMenu;
    @Inject
    ApplicationTenancyService applicationTenancyService;

    @Before
    public void setUpData() throws Exception {
        fixtureScripts.runFixtureScript(new ClassificationDemoAppTearDownFixture(), null);
        fixtureScripts.runFixtureScript(new ClassifiedDemoObjectsFixture(), null);
    }

    @Test
    public void happy_case() {
        // given
        Category medium = categoryRepository.findByReference("M");
        assertThat(medium.getFullyQualifiedName()).isEqualTo("Sizes/Medium");

        // when
        medium.modifyName("Middle");

        // then
        assertThat(medium.getName()).isEqualTo("Middle");
        assertThat(medium.getFullyQualifiedName()).isEqualTo("Sizes/Middle");
    }

    @Test
    public void fully_qualified_name_of_children_also_updated() {
        // given
        Category large = categoryRepository.findByReference("LGE");
        assertThat(large.getFullyQualifiedName()).isEqualTo("Sizes/Large");
        assertThat(large.getChildren()).allMatch(c -> c.getFullyQualifiedName().split("/")[1].equals("Large"));

        // when
        large.modifyName("LRG");

        // then
        assertThat(large.getFullyQualifiedName()).isEqualTo("Sizes/LRG");
        assertThat(large.getChildren()).allMatch(c -> c.getFullyQualifiedName().split("/")[1].equals("LRG"));
    }

    @Test
    public void cannot_rename_to_a_name_already_in_use() {
        // given
        Category red = categoryRepository.findByReference("FRRED");

        // then
        expectedException.expect(InvalidException.class);
        expectedException.expectMessage("A category with name 'White' already exists (under this parent)");

        // when
        wrap(red).setName("White");
    }

}