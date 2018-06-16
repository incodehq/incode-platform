package org.incode.domainapp.extended.integtests.examples.classification.tests.applicability;

import javax.inject.Inject;

import org.junit.Before;
import org.junit.Ignore;

import org.incode.example.classification.dom.impl.applicability.ApplicabilityRepository;
import org.incode.example.classification.dom.impl.category.CategoryRepository;
import org.incode.example.classification.dom.impl.classification.ClassificationRepository;
import org.incode.example.classification.dom.spi.ApplicationTenancyService;
import org.incode.domainapp.extended.integtests.examples.classification.ClassificationModuleIntegTestAbstract;
import org.incode.domainapp.extended.integtests.examples.classification.demo.dom.demowithatpath.DemoObjectWithAtPathMenu;
import org.incode.domainapp.extended.integtests.examples.classification.dom.classification.fixture.DemoObjectWithAtPath_and_OtherObjectWithAtPath_recreate3;

public class Applicability_UiSubscriber_IntegTest extends ClassificationModuleIntegTestAbstract {

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
        fixtureScripts.runFixtureScript(new DemoObjectWithAtPath_and_OtherObjectWithAtPath_recreate3(), null);
    }


    @Ignore
    public void override_title_subscriber() {

        // given a service subscribing on Applicability.TitleUiEvent

        // then the title of an applicability should be...

    }

    @Ignore
    public void override_icon_subscriber() {

        // given a service subscribing on Applicability.IconUiEvent

        // then the icon of an applicability should be...

    }

    @Ignore
    public void override_cssClass_subscriber() {

        // given a service subscribing on Applicability.CssClassUiEvent

        // then the CSS class of an applicability should be...

    }


}