package org.incode.module.classification.integtests.category;

import javax.inject.Inject;

import org.junit.Before;
import org.junit.Test;

import org.incode.module.classification.dom.impl.applicability.ApplicabilityRepository;
import org.incode.module.classification.dom.impl.category.Category;
import org.incode.module.classification.dom.impl.category.CategoryRepository;
import org.incode.module.classification.dom.impl.classification.ClassificationRepository;
import org.incode.module.classification.dom.spi.ApplicationTenancyService;
import domainapp.modules.exampledom.module.classification.dom.demo.DemoObjectMenu;
import domainapp.modules.exampledom.module.classification.fixture.ClassifiedDemoObjectsFixture;
import org.incode.module.classification.integtests.ClassificationModuleIntegTestAbstract;

import static org.assertj.core.api.Assertions.assertThat;

public class CategoryRepository_findByParentAndReference_IntegTest extends ClassificationModuleIntegTestAbstract {

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
        fixtureScripts.runFixtureScript(new ClassifiedDemoObjectsFixture(), null);
    }

    @Test
    public void happy_case() {
        // given
        Category parentLarge = categoryRepository.findByReference("LGE");

        // when
        Category childLarge = categoryRepository.findByParentAndReference(parentLarge, "XL");

        // then
        assertThat(childLarge.getName()).isEqualTo("Larger");
        assertThat(childLarge.getParent()).isEqualTo(parentLarge);
    }

    @Test
    public void when_none() {
        // given
        Category parentLarge = categoryRepository.findByReference("LGE");

        // when
        Category childLarge = categoryRepository.findByParentAndReference(parentLarge, "XXXXL");

        // then
        assertThat(childLarge).isNull();
    }

}