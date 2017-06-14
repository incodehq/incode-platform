package org.incode.module.classification.integtests.category;

import javax.inject.Inject;

import org.junit.Before;
import org.junit.Ignore;

import org.apache.isis.applib.services.title.TitleService;

import org.incode.module.classification.dom.impl.applicability.ApplicabilityRepository;
import org.incode.module.classification.dom.impl.category.Category;
import org.incode.module.classification.dom.impl.category.CategoryRepository;
import org.incode.module.classification.dom.impl.classification.ClassificationRepository;
import org.incode.module.classification.dom.spi.ApplicationTenancyService;
import org.incode.module.classification.fixture.dom.demo.first.DemoObjectMenu;
import org.incode.module.classification.fixture.scripts.scenarios.ClassifiedDemoObjectsFixture;
import org.incode.module.classification.integtests.ClassificationModuleIntegTest;

import static org.assertj.core.api.Assertions.assertThat;

public class Category_UiSubscriber_IntegTest extends ClassificationModuleIntegTest {

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

    @Inject
    TitleService titleService;

    Category category;

    @Before
    public void setUpData() throws Exception {
        fixtureScripts.runFixtureScript(new ClassifiedDemoObjectsFixture(), null);
        category = categoryRepository.createChild(null, "Jeroen", "KOEKENBAKKER", 1);
    }

    @Ignore
    public void override_title_subscriber() {
        assertThat(titleService.titleOf(category)).isEqualTo("Holtkamp");
    }

    @Ignore
    public void override_icon_subscriber() {
        assertThat(titleService.iconNameOf(category)).isEqualTo("Jodekoek.png");
    }

}