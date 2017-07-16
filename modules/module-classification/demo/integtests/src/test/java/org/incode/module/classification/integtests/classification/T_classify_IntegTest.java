package org.incode.module.classification.integtests.classification;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.inject.Inject;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import org.apache.isis.applib.services.factory.FactoryService;

import org.incode.module.classification.dom.impl.applicability.ApplicabilityRepository;
import org.incode.module.classification.dom.impl.category.Category;
import org.incode.module.classification.dom.impl.category.CategoryRepository;
import org.incode.module.classification.dom.impl.category.taxonomy.Taxonomy;
import org.incode.module.classification.dom.impl.classification.Classification;
import org.incode.module.classification.dom.impl.classification.ClassificationRepository;
import org.incode.module.classification.dom.spi.ApplicationTenancyService;
import domainapp.modules.exampledom.module.classification.dom.classification.demo.ClassificationForDemoObject;
import domainapp.modules.exampledom.module.classification.dom.classification.demo2.ClassificationForOtherObject;
import domainapp.modules.exampledom.module.classification.dom.demo.DemoObject;
import domainapp.modules.exampledom.module.classification.dom.demo.DemoObjectMenu;
import domainapp.modules.exampledom.module.classification.dom.demo2.OtherObject;
import domainapp.modules.exampledom.module.classification.dom.demo2.OtherObjectMenu;
import domainapp.modules.exampledom.module.classification.fixture.ClassifiedDemoObjectsFixture;
import org.incode.module.classification.integtests.ClassificationModuleIntegTest;

import static org.assertj.core.api.Assertions.assertThat;

public class T_classify_IntegTest extends ClassificationModuleIntegTest {

    @Inject
    ClassificationRepository classificationRepository;
    @Inject
    CategoryRepository categoryRepository;
    @Inject
    ApplicabilityRepository applicabilityRepository;

    @Inject
    DemoObjectMenu demoObjectMenu;
    @Inject
    OtherObjectMenu otherObjectMenu;

    @Inject
    ApplicationTenancyService applicationTenancyService;
    @Inject
    FactoryService factoryService;

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Before
    public void setUpData() throws Exception {
        fixtureScripts.runFixtureScript(new ClassifiedDemoObjectsFixture(), null);
    }

    @Test
    public void when_applicability_and_no_classification() {
        // given
        DemoObject demoBip = demoObjectMenu.listAll()
                .stream()
                .filter(demoObject -> demoObject.getName().equals("Demo bip (in Milan)"))
                .findFirst()
                .get();
        assertThat(classificationRepository.findByClassified(demoBip)).isEmpty();

        // when
        final ClassificationForDemoObject._classify classification = factoryService.mixin(ClassificationForDemoObject._classify.class, demoBip);
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
        DemoObject demoFooInItaly = demoObjectMenu.listAll()
                .stream()
                .filter(demoObject -> demoObject.getName().equals("Demo foo (in Italy)"))
                .findFirst()
                .get();
        assertThat(classificationRepository.findByClassified(demoFooInItaly))
                .extracting(Classification::getCategory)
                .extracting(Category::getName)
                .contains("Red", "Medium");

        final ClassificationForDemoObject._classify classification = factoryService.mixin(ClassificationForDemoObject._classify.class, demoFooInItaly);

        // when
        final String message = classification.disableClassify().toString();

        // then
        assertThat(message).isEqualTo("tr: There are no classifications that can be added");
    }

    @Test
    public void cannot_classify_when_no_applicability_for_domain_type() {
        // given
        OtherObject otherBaz = otherObjectMenu.listAll()
                .stream()
                .filter(otherObject -> otherObject.getName().equals("Other baz (Global)"))
                .findFirst()
                .get();
        assertThat(applicabilityRepository.findByDomainTypeAndUnderAtPath(otherBaz.getClass(), otherBaz.getAtPath())).isEmpty();

        final ClassificationForOtherObject._classify classification = factoryService.mixin(ClassificationForOtherObject._classify.class, otherBaz);

        // when
        final String message = classification.disableClassify().toString();

        // then
        assertThat(message).isEqualTo("tr: There are no classifications that can be added");
    }

    @Test
    public void cannot_classify_when_no_applicability_for_atPath() {
        // given
        OtherObject otherBarInFrance = otherObjectMenu.listAll()
                .stream()
                .filter(otherObject -> otherObject.getName().equals("Other bar (in France)"))
                .findFirst()
                .get();
        assertThat(applicabilityRepository.findByDomainTypeAndUnderAtPath(otherBarInFrance.getClass(), otherBarInFrance.getAtPath())).isEmpty();

        final ClassificationForOtherObject._classify classification = factoryService.mixin(ClassificationForOtherObject._classify.class, otherBarInFrance);

        // when
        final String message = classification.disableClassify().toString();

        // then
        assertThat(message).isEqualTo("tr: There are no classifications that can be added");
    }

}