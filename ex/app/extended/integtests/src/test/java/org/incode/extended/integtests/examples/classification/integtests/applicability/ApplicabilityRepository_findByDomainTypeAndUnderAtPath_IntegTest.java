package org.incode.extended.integtests.examples.classification.integtests.applicability;

import java.util.List;

import javax.inject.Inject;

import org.junit.Before;
import org.junit.Test;

import org.incode.domainapp.extended.module.fixtures.per_cpt.examples.classification.fixture.DemoObjectWithAtPath_and_OtherObjectWithAtPath_recreate3;
import org.incode.domainapp.extended.module.fixtures.per_cpt.examples.classification.fixture.DemoObjectWithAtPath_and_OtherObjectWithAtPath_tearDown;
import org.incode.domainapp.extended.module.fixtures.shared.demowithatpath.dom.DemoObjectWithAtPath;
import org.incode.example.classification.dom.impl.applicability.Applicability;
import org.incode.example.classification.dom.impl.applicability.ApplicabilityRepository;
import org.incode.example.classification.dom.impl.category.CategoryRepository;
import org.incode.example.classification.dom.impl.classification.ClassificationRepository;
import org.incode.example.classification.dom.spi.ApplicationTenancyService;
import org.incode.extended.integtests.examples.classification.ClassificationModuleIntegTestAbstract;

import static org.assertj.core.api.Assertions.assertThat;

public class ApplicabilityRepository_findByDomainTypeAndUnderAtPath_IntegTest extends
        ClassificationModuleIntegTestAbstract {

    @Inject
    ClassificationRepository classificationRepository;
    @Inject
    CategoryRepository categoryRepository;
    @Inject
    ApplicabilityRepository applicabilityRepository;

    @Inject
    ApplicationTenancyService applicationTenancyService;

    @Before
    public void setUpData() throws Exception {
        fixtureScripts.runFixtureScript(new DemoObjectWithAtPath_and_OtherObjectWithAtPath_tearDown(), null);
        fixtureScripts.runFixtureScript(new DemoObjectWithAtPath_and_OtherObjectWithAtPath_recreate3(), null);
    }

    @Test
    public void exact_match_on_application_tenancy() {

        // /ITA matches /ITA and /
        assertThat(applicabilityRepository.findByDomainTypeAndUnderAtPath(DemoObjectWithAtPath.class, "/ITA").size()).isEqualTo(2);
    }

    @Test
    public void matches_on_sub_application_tenancy() {

        final List<Applicability> byDomainTypeAndUnderAtPath = applicabilityRepository.findByDomainTypeAndUnderAtPath(DemoObjectWithAtPath.class, "/ITA/XYZ");
        assertThat(byDomainTypeAndUnderAtPath.size()).isEqualTo(2);
        assertThat(byDomainTypeAndUnderAtPath).extracting(Applicability::getAtPath).containsOnly("/ITA", "/");
        // eg set up for "/ITA", search for app tenancy "/ITA/MIL"

    }

    @Test
    public void does_not_match_on_super_application_tenancy() {
        assertThat(applicabilityRepository.findByDomainTypeAndUnderAtPath(DemoObjectWithAtPath.class, "/").size()).isEqualTo(1);
    }

}