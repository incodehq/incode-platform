package org.incode.platform.dom.classification.integtests.tests.category.taxonomy;

import java.util.List;

import javax.inject.Inject;

import org.junit.Before;
import org.junit.Test;

import org.incode.module.classification.dom.impl.applicability.Applicability;
import org.incode.module.classification.dom.impl.applicability.ApplicabilityRepository;
import org.incode.module.classification.dom.impl.category.CategoryRepository;
import org.incode.module.classification.dom.impl.category.taxonomy.Taxonomy;
import org.incode.module.classification.dom.impl.classification.Classification;
import org.incode.module.classification.dom.impl.classification.ClassificationRepository;
import org.incode.module.classification.dom.spi.ApplicationTenancyService;
import org.incode.domainapp.example.dom.dom.classification.dom.demo.DemoObject;
import org.incode.domainapp.example.dom.dom.classification.dom.demo.DemoObjectMenu;
import org.incode.domainapp.example.dom.dom.classification.fixture.ClassifiedDemoObjectsFixture;
import org.incode.domainapp.example.dom.dom.classification.fixture.ClassificationDemoAppTearDownFixture;
import org.incode.platform.dom.classification.integtests.ClassificationModuleIntegTestAbstract;

import static org.assertj.core.api.Assertions.assertThat;

public class Taxonomy_notApplicable_IntegTest extends ClassificationModuleIntegTestAbstract {

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
        fixtureScripts.runFixtureScript(new ClassificationDemoAppTearDownFixture(), null);
        fixtureScripts.runFixtureScript(new ClassifiedDemoObjectsFixture(), null);
    }

    @Test
    public void happy_case() {
        // given
        Taxonomy italianColours = (Taxonomy) categoryRepository.findByParentAndName(null, "Italian Colours");
        List<Applicability> applicabilities = applicabilityRepository.findByDomainTypeAndUnderAtPath(DemoObject.class, "/ITA");
        assertThat(applicabilities).extracting(Applicability::getTaxonomy).extracting(Taxonomy::getName).containsOnly("Italian Colours", "Sizes");

        Applicability italianColoursApplicability = applicabilities.stream()
                .filter(applicability -> applicability.getTaxonomy().getName().equals("Italian Colours"))
                .findFirst()
                .get();

        // when
        wrap(italianColours).notApplicable(italianColoursApplicability);

        // then
        List<Applicability> newApplicabilities = applicabilityRepository.findByDomainTypeAndUnderAtPath(DemoObject.class, "/ITA");
        assertThat(newApplicabilities).extracting(Applicability::getTaxonomy).extracting(Taxonomy::getName).containsOnly("Sizes");
    }

    @Test
    public void existing_classifications_are_ignored() {
        // given
        Taxonomy italianColours = (Taxonomy) categoryRepository.findByParentAndName(null, "Italian Colours");
        List<Applicability> applicabilities = applicabilityRepository.findByDomainTypeAndUnderAtPath(DemoObject.class, "/ITA");
        assertThat(applicabilities).extracting(Applicability::getTaxonomy).extracting(Taxonomy::getName).contains("Italian Colours");

        Applicability italianColoursApplicability = applicabilities.stream()
                .filter(applicability -> applicability.getTaxonomy().getName().equals("Italian Colours"))
                .findFirst()
                .get();

        DemoObject demoFooInItaly = demoObjectMenu.listAll().stream()
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