package org.incode.extended.integtests.examples.classification.tests.category.taxonomy;

import java.util.List;

import javax.inject.Inject;

import org.junit.Before;
import org.junit.Test;

import org.incode.example.classification.dom.impl.applicability.Applicability;
import org.incode.example.classification.dom.impl.applicability.ApplicabilityRepository;
import org.incode.example.classification.dom.impl.category.CategoryRepository;
import org.incode.example.classification.dom.impl.category.taxonomy.Taxonomy;
import org.incode.example.classification.dom.impl.classification.Classification;
import org.incode.example.classification.dom.impl.classification.ClassificationRepository;
import org.incode.example.classification.dom.spi.ApplicationTenancyService;
import org.incode.extended.integtests.examples.classification.ClassificationModuleIntegTestAbstract;
import org.incode.extended.integtests.examples.classification.demo.dom.demowithatpath.DemoObjectWithAtPath;
import org.incode.extended.integtests.examples.classification.demo.dom.demowithatpath.DemoObjectWithAtPathMenu;
import org.incode.extended.integtests.examples.classification.dom.classification.fixture.DemoObjectWithAtPath_and_OtherObjectWithAtPath_recreate3;
import org.incode.extended.integtests.examples.classification.dom.classification.fixture.DemoObjectWithAtPath_and_OtherObjectWithAtPath_tearDown;

import static org.assertj.core.api.Assertions.assertThat;

public class Taxonomy_notApplicable_IntegTest extends ClassificationModuleIntegTestAbstract {

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
        fixtureScripts.runFixtureScript(new DemoObjectWithAtPath_and_OtherObjectWithAtPath_tearDown(), null);
        fixtureScripts.runFixtureScript(new DemoObjectWithAtPath_and_OtherObjectWithAtPath_recreate3(), null);
    }

    @Test
    public void happy_case() {
        // given
        Taxonomy italianColours = (Taxonomy) categoryRepository.findByParentAndName(null, "Italian Colours");
        List<Applicability> applicabilities = applicabilityRepository.findByDomainTypeAndUnderAtPath(DemoObjectWithAtPath.class, "/ITA");
        assertThat(applicabilities).extracting(Applicability::getTaxonomy).extracting(Taxonomy::getName).containsOnly("Italian Colours", "Sizes");

        Applicability italianColoursApplicability = applicabilities.stream()
                .filter(applicability -> applicability.getTaxonomy().getName().equals("Italian Colours"))
                .findFirst()
                .get();

        // when
        wrap(italianColours).notApplicable(italianColoursApplicability);

        // then
        List<Applicability> newApplicabilities = applicabilityRepository.findByDomainTypeAndUnderAtPath(DemoObjectWithAtPath.class, "/ITA");
        assertThat(newApplicabilities).extracting(Applicability::getTaxonomy).extracting(Taxonomy::getName).containsOnly("Sizes");
    }

    @Test
    public void existing_classifications_are_ignored() {
        // given
        Taxonomy italianColours = (Taxonomy) categoryRepository.findByParentAndName(null, "Italian Colours");
        List<Applicability> applicabilities = applicabilityRepository.findByDomainTypeAndUnderAtPath(DemoObjectWithAtPath.class, "/ITA");
        assertThat(applicabilities).extracting(Applicability::getTaxonomy).extracting(Taxonomy::getName).contains("Italian Colours");

        Applicability italianColoursApplicability = applicabilities.stream()
                .filter(applicability -> applicability.getTaxonomy().getName().equals("Italian Colours"))
                .findFirst()
                .get();

        DemoObjectWithAtPath demoFooInItaly = demoObjectMenu.listAllDemoObjectsWithAtPath().stream()
                .filter(d -> d.getName().equals("Demo foo (in Italy)"))
                .findFirst()
                .get();

        List<Classification> classifications = classificationRepository.findByClassified(demoFooInItaly);
        assertThat(classifications).extracting(Classification::getTaxonomy).contains(italianColours);

        // when
        wrap(italianColours).notApplicable(italianColoursApplicability);

        // then
        assertThat(classificationRepository.findByClassified(demoFooInItaly)).extracting(Classification::getTaxonomy).contains(italianColours);
    }

    @Test
    public void cannot_() {
        // given
        Taxonomy dutchColours = categoryRepository.createTaxonomy("Dutch Colours");
        assertThat(dutchColours.getAppliesTo()).isEmpty();

        // when
        String message = dutchColours.disableNotApplicable().toString();

        // then
        assertThat(message).isEqualTo("tr: No applicabilities to remove");
    }

}