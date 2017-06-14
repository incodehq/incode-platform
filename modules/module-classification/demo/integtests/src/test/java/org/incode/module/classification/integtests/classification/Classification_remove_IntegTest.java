package org.incode.module.classification.integtests.classification;

import javax.inject.Inject;

import org.junit.Before;
import org.junit.Test;

import org.incode.module.classification.dom.impl.applicability.ApplicabilityRepository;
import org.incode.module.classification.dom.impl.category.Category;
import org.incode.module.classification.dom.impl.category.CategoryRepository;
import org.incode.module.classification.dom.impl.classification.Classification;
import org.incode.module.classification.dom.impl.classification.ClassificationRepository;
import org.incode.module.classification.dom.spi.ApplicationTenancyService;
import org.incode.module.classification.fixture.dom.demo.first.DemoObject;
import org.incode.module.classification.fixture.dom.demo.first.DemoObjectMenu;
import org.incode.module.classification.fixture.scripts.scenarios.ClassifiedDemoObjectsFixture;
import org.incode.module.classification.integtests.ClassificationModuleIntegTest;

import static org.assertj.core.api.Assertions.assertThat;

public class Classification_remove_IntegTest extends ClassificationModuleIntegTest {

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

    Object classified;

    @Before
    public void setUpData() throws Exception {
        fixtureScripts.runFixtureScript(new ClassifiedDemoObjectsFixture(), null);
    }

    @Test
    public void happy_case() {
        // given
        DemoObject demoFooInItaly = demoObjectMenu.listAll().stream()
                .filter(demoObject -> demoObject.getName().equals("Demo foo (in Italy)"))
                .findFirst()
                .get();

        Classification italianClassificationRed = classificationRepository.findByClassified(demoFooInItaly)
                .stream()
                .filter(classification -> classification.getCategory().getName().equals("Red"))
                .findFirst()
                .get();

        // when
        wrap(italianClassificationRed).remove();

        // then
        assertThat(classificationRepository.findByClassified(demoFooInItaly))
                .extracting(Classification::getCategory)
                .extracting(Category::getName)
                .doesNotContain("Red");

    }

}