package org.incode.example.classification.integtests.tests.classification;

import java.util.List;

import javax.inject.Inject;

import org.junit.Before;
import org.junit.Test;

import org.apache.isis.applib.services.factory.FactoryService;

import org.incode.example.classification.demo.shared.demowithatpath.dom.SomeClassifiedObject;
import org.incode.example.classification.demo.shared.demowithatpath.dom.SomeClassifiedObjectMenu;
import org.incode.example.classification.demo.shared.otherwithatpath.dom.OtherClassifiedObject;
import org.incode.example.classification.demo.shared.otherwithatpath.dom.OtherClassifiedObjectMenu;
import org.incode.example.classification.demo.usage.dom.classification.demowithatpath.ClassificationForSomeClassifiedObject;
import org.incode.example.classification.demo.usage.dom.classification.otherwithatpath.ClassificationForOtherClassifiedObject;
import org.incode.example.classification.demo.usage.fixture.DemoObjectWithAtPath_and_OtherObjectWithAtPath_create3;
import org.incode.example.classification.dom.impl.applicability.ApplicabilityRepository;
import org.incode.example.classification.dom.impl.category.CategoryRepository;
import org.incode.example.classification.dom.impl.classification.Classification;
import org.incode.example.classification.dom.impl.classification.ClassificationRepository;
import org.incode.example.classification.dom.spi.ApplicationTenancyService;
import org.incode.example.classification.integtests.ClassificationModuleIntegTestAbstract;

import static org.assertj.core.api.Assertions.assertThat;

public class T_unclassify_IntegTest extends ClassificationModuleIntegTestAbstract {

    @Inject
    ClassificationRepository classificationRepository;
    @Inject
    CategoryRepository categoryRepository;
    @Inject
    ApplicabilityRepository applicabilityRepository;

    @Inject
    SomeClassifiedObjectMenu demoObjectMenu;
    @Inject
    OtherClassifiedObjectMenu otherObjectMenu;

    @Inject
    ApplicationTenancyService applicationTenancyService;
    @Inject
    FactoryService factoryService;

    @Before
    public void setUpData() throws Exception {
        fixtureScripts.runFixtureScript(new DemoObjectWithAtPath_and_OtherObjectWithAtPath_create3(), null);
    }

    @Test
    public void enabled_when_classifications_exist() {
        // given
        SomeClassifiedObject demoFooInItaly = demoObjectMenu.listAllOfSomeClassifiedObjects()
                .stream()
                .filter(demoObject -> demoObject.getName().equals("Demo foo (in Italy)"))
                .findFirst()
                .get();
        final List<Classification> byClassified = classificationRepository.findByClassified(demoFooInItaly);
        assertThat(byClassified).hasSize(2);

        // when
        final ClassificationForSomeClassifiedObject.unclassify unclassify = factoryService.mixin(ClassificationForSomeClassifiedObject.unclassify.class, demoFooInItaly);
        wrap(unclassify).unclassify(byClassified.get(0));

        // then
        assertThat(classificationRepository.findByClassified(demoFooInItaly)).hasSize(1);
    }

    @Test
    public void disabled_when_no_classification_to_remove() {

        // given "Other bar (in Paris)", that has no classifications
        // given
        OtherClassifiedObject otherBarInFrance = otherObjectMenu.listAllOtherObjectsWithAtPath()
                .stream()
                .filter(otherObject -> otherObject.getName().equals("Other bar (in France)"))
                .findFirst()
                .get();
        assertThat(classificationRepository.findByClassified(otherBarInFrance)).isEmpty();

        final ClassificationForOtherClassifiedObject.unclassify unclassify = factoryService.mixin(ClassificationForOtherClassifiedObject.unclassify.class, otherBarInFrance);

        // when
        final String message = unclassify.disableUnclassify().toString();

        // then
        assertThat(message).isEqualTo("tr: No classifications to delete");
    }

}