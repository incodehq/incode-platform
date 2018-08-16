package org.incode.example.classification.integtests.tests.classification;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.inject.Inject;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import org.apache.isis.applib.services.factory.FactoryService;

import org.incode.example.classification.demo.shared.demowithatpath.dom.SomeClassifiedObject;
import org.incode.example.classification.demo.shared.demowithatpath.dom.SomeClassifiedObjectMenu;
import org.incode.example.classification.demo.shared.otherwithatpath.dom.OtherClassifiedObject;
import org.incode.example.classification.demo.shared.otherwithatpath.dom.OtherClassifiedObjectMenu;
import org.incode.example.classification.demo.usage.dom.classification.demowithatpath.ClassificationForSomeClassifiedObject;
import org.incode.example.classification.demo.usage.dom.classification.otherwithatpath.ClassificationForOtherClassifiedObject;
import org.incode.example.classification.demo.usage.fixture.DemoObjectWithAtPath_and_OtherObjectWithAtPath_create3;
import org.incode.example.classification.dom.impl.applicability.ApplicabilityRepository;
import org.incode.example.classification.dom.impl.category.Category;
import org.incode.example.classification.dom.impl.category.CategoryRepository;
import org.incode.example.classification.dom.impl.category.taxonomy.Taxonomy;
import org.incode.example.classification.dom.impl.classification.Classification;
import org.incode.example.classification.dom.impl.classification.ClassificationRepository;
import org.incode.example.classification.dom.spi.ApplicationTenancyService;
import org.incode.example.classification.integtests.ClassificationModuleIntegTestAbstract;

import static org.assertj.core.api.Assertions.assertThat;

public class T_classify_IntegTest extends ClassificationModuleIntegTestAbstract {

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

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Before
    public void setUpData() throws Exception {
        fixtureScripts.runFixtureScript(new DemoObjectWithAtPath_and_OtherObjectWithAtPath_create3(), null);
    }

    @Test
    public void when_applicability_and_no_classification() {
        // given
        SomeClassifiedObject demoBip = demoObjectMenu.listAllOfSomeClassifiedObjects()
                .stream()
                .filter(demoObject -> demoObject.getName().equals("Demo bip (in Milan)"))
                .findFirst()
                .get();
        assertThat(classificationRepository.findByClassified(demoBip)).isEmpty();

        // when
        final ClassificationForSomeClassifiedObject.classify classification = factoryService.mixin(ClassificationForSomeClassifiedObject.classify.class, demoBip);
        Collection<Taxonomy> choices0Classify = classification.choices0Classify();
        assertThat(choices0Classify)
                .extracting(Taxonomy::getName)
                .containsOnly("Italian Colours", "Sizes");

        List<String> categoryNames = new ArrayList<>();

        for (Taxonomy taxonomy : choices0Classify) {
            Category category = classification.default1Classify(taxonomy);
            categoryNames.add(category.getName());
            wrap(classification).classify(taxonomy, category);
        }

        // then
        assertThat(classificationRepository.findByClassified(demoBip))
                .extracting(Classification::getCategory)
                .extracting(Category::getName)
                .containsOnlyElementsOf(categoryNames);
    }

    @Test
    public void cannot_classify_when_applicability_but_classifications_already_defined() {
        // given
        SomeClassifiedObject demoFooInItaly = demoObjectMenu.listAllOfSomeClassifiedObjects()
                .stream()
                .filter(demoObject -> demoObject.getName().equals("Demo foo (in Italy)"))
                .findFirst()
                .get();
        assertThat(classificationRepository.findByClassified(demoFooInItaly))
                .extracting(Classification::getCategory)
                .extracting(Category::getName)
                .contains("Red", "Medium");

        final ClassificationForSomeClassifiedObject.classify classification = factoryService.mixin(ClassificationForSomeClassifiedObject.classify.class, demoFooInItaly);

        // when
        final String message = classification.disableClassify().toString();

        // then
        assertThat(message).isEqualTo("tr: There are no classifications that can be added");
    }

    @Test
    public void cannot_classify_when_no_applicability_for_domain_type() {
        // given
        OtherClassifiedObject otherBaz = otherObjectMenu.listAllOtherObjectsWithAtPath()
                .stream()
                .filter(otherObject -> otherObject.getName().equals("Other baz (Global)"))
                .findFirst()
                .get();
        assertThat(applicabilityRepository.findByDomainTypeAndUnderAtPath(otherBaz.getClass(), otherBaz.getAtPath())).isEmpty();

        final ClassificationForOtherClassifiedObject.classify classification = factoryService.mixin(ClassificationForOtherClassifiedObject.classify.class, otherBaz);

        // when
        final String message = classification.disableClassify().toString();

        // then
        assertThat(message).isEqualTo("tr: There are no classifications that can be added");
    }

    @Test
    public void cannot_classify_when_no_applicability_for_atPath() {
        // given
        OtherClassifiedObject otherBarInFrance = otherObjectMenu.listAllOtherObjectsWithAtPath()
                .stream()
                .filter(otherObject -> otherObject.getName().equals("Other bar (in France)"))
                .findFirst()
                .get();
        assertThat(applicabilityRepository.findByDomainTypeAndUnderAtPath(otherBarInFrance.getClass(), otherBarInFrance.getAtPath())).isEmpty();

        final ClassificationForOtherClassifiedObject.classify classification = factoryService.mixin(ClassificationForOtherClassifiedObject.classify.class, otherBarInFrance);

        // when
        final String message = classification.disableClassify().toString();

        // then
        assertThat(message).isEqualTo("tr: There are no classifications that can be added");
    }

}