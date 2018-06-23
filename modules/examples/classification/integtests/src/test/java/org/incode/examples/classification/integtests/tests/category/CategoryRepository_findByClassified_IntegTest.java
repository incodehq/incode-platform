package org.incode.examples.classification.integtests.tests.category;

import java.util.List;

import javax.inject.Inject;

import org.junit.Before;
import org.junit.Test;

import org.incode.example.classification.dom.impl.applicability.ApplicabilityRepository;
import org.incode.example.classification.dom.impl.category.Category;
import org.incode.example.classification.dom.impl.category.CategoryRepository;
import org.incode.example.classification.dom.impl.classification.Classification;
import org.incode.example.classification.dom.impl.classification.ClassificationRepository;
import org.incode.example.classification.dom.spi.ApplicationTenancyService;
import org.incode.examples.classification.integtests.ClassificationModuleIntegTestAbstract;
import org.incode.example.classification.demo.shared.demowithatpath.dom.DemoObjectWithAtPath;
import org.incode.example.classification.demo.shared.demowithatpath.dom.DemoObjectWithAtPathMenu;
import org.incode.example.classification.demo.usage.fixture.DemoObjectWithAtPath_and_OtherObjectWithAtPath_create3;

import static org.assertj.core.api.Assertions.assertThat;

public class CategoryRepository_findByClassified_IntegTest extends ClassificationModuleIntegTestAbstract {

    @Inject
    ClassificationRepository classificationRepository;
    @Inject
    CategoryRepository categoryRepository;
    @Inject
    ApplicabilityRepository applicabilityRepository;

    @Inject
    DemoObjectWithAtPathMenu demoObjectMenu;
    @Inject
    ApplicationTenancyService applicationTenancyService;

    @Before
    public void setUpData() throws Exception {
        fixtureScripts.runFixtureScript(new DemoObjectWithAtPath_and_OtherObjectWithAtPath_create3(), null);
    }

    @Test
    public void happy_case() {
        // given
        DemoObjectWithAtPath demoFooInItaly = demoObjectMenu.listAllDemoObjectsWithAtPath().stream()
                .filter(d -> d.getName().equals("Demo foo (in Italy)"))
                .findFirst().get();
        assertThat(demoFooInItaly).isNotNull();

        // when
        List<Classification> classifications = classificationRepository.findByClassified(demoFooInItaly);

        // then
        assertThat(classifications.size()).isEqualTo(2);
        assertThat(classifications).extracting(Classification::getCategory)
                .extracting(Category::getName)
                .containsOnly("Red", "Medium");
    }

}