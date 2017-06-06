/*
 *  Copyright 2016 Dan Haywood
 *
 *  Licensed under the Apache License, Version 2.0 (the
 *  "License"); you may not use this file except in compliance
 *  with the License.  You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing,
 *  software distributed under the License is distributed on an
 *  "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 *  KIND, either express or implied.  See the License for the
 *  specific language governing permissions and limitations
 *  under the License.
 */
package org.incode.module.classification.integtests.applicability;

import java.util.List;

import javax.inject.Inject;

import org.junit.Before;
import org.junit.Test;

import org.incode.module.classification.dom.impl.applicability.Applicability;
import org.incode.module.classification.dom.impl.applicability.ApplicabilityRepository;
import org.incode.module.classification.dom.impl.category.CategoryRepository;
import org.incode.module.classification.dom.impl.classification.ClassificationRepository;
import org.incode.module.classification.dom.spi.ApplicationTenancyService;
import org.incode.module.classification.fixture.dom.demo.first.DemoObject;
import org.incode.module.classification.fixture.scripts.scenarios.ClassifiedDemoObjectsFixture;
import org.incode.module.classification.fixture.scripts.teardown.ClassificationDemoAppTearDownFixture;
import org.incode.module.classification.integtests.ClassificationModuleIntegTest;

import static org.assertj.core.api.Assertions.assertThat;

public class ApplicabilityRepository_findByDomainTypeAndUnderAtPath_IntegTest extends ClassificationModuleIntegTest {

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
        fixtureScripts.runFixtureScript(new ClassificationDemoAppTearDownFixture(), null);
        fixtureScripts.runFixtureScript(new ClassifiedDemoObjectsFixture(), null);
    }

    @Test
    public void exact_match_on_application_tenancy() {

        // /ITA matches /ITA and /
        assertThat(applicabilityRepository.findByDomainTypeAndUnderAtPath(DemoObject.class, "/ITA").size()).isEqualTo(2);
    }

    @Test
    public void matches_on_sub_application_tenancy() {

        final List<Applicability> byDomainTypeAndUnderAtPath = applicabilityRepository.findByDomainTypeAndUnderAtPath(DemoObject.class, "/ITA/XYZ");
        assertThat(byDomainTypeAndUnderAtPath.size()).isEqualTo(2);
        assertThat(byDomainTypeAndUnderAtPath).extracting(Applicability::getAtPath).containsOnly("/ITA", "/");
        // eg set up for "/ITA", search for app tenancy "/ITA/MIL"

    }

    @Test
    public void does_not_match_on_super_application_tenancy() {
        assertThat(applicabilityRepository.findByDomainTypeAndUnderAtPath(DemoObject.class, "/").size()).isEqualTo(1);
    }

}