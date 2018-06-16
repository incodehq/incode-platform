package org.incode.domainapp.extended.integtests.examples.classification.tests.category;

import javax.inject.Inject;

import org.junit.Before;
import org.junit.Ignore;

import org.apache.isis.applib.services.title.TitleService;

import org.incode.example.classification.dom.impl.applicability.ApplicabilityRepository;
import org.incode.example.classification.dom.impl.category.Category;
import org.incode.example.classification.dom.impl.category.CategoryRepository;
import org.incode.example.classification.dom.impl.classification.ClassificationRepository;
import org.incode.example.classification.dom.spi.ApplicationTenancyService;
import org.incode.domainapp.extended.integtests.examples.classification.ClassificationModuleIntegTestAbstract;
import org.incode.domainapp.extended.integtests.examples.classification.demo.dom.demowithatpath.DemoObjectWithAtPathMenu;
import org.incode.domainapp.extended.integtests.examples.classification.dom.classification.fixture.DemoObjectWithAtPath_and_OtherObjectWithAtPath_recreate3;

import static org.assertj.core.api.Assertions.assertThat;

public class Category_UiSubscriber_IntegTest extends ClassificationModuleIntegTestAbstract {

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

    @Inject
    TitleService titleService;

    Category category;

    @Before
    public void setUpData() throws Exception {
        fixtureScripts.runFixtureScript(new DemoObjectWithAtPath_and_OtherObjectWithAtPath_recreate3(), null);
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