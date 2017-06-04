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
package org.incode.module.classification.integtests.category.taxonomy;

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
import org.incode.module.classification.fixture.dom.demo.first.DemoObject;
import org.incode.module.classification.fixture.dom.demo.first.DemoObjectMenu;
import org.incode.module.classification.fixture.scripts.scenarios.ClassifiedDemoObjectsFixture;
import org.incode.module.classification.fixture.scripts.teardown.ClassificationDemoAppTearDownFixture;
import org.incode.module.classification.integtests.ClassificationModuleIntegTest;

import static org.assertj.core.api.Assertions.assertThat;

public class Taxonomy_notApplicable_IntegTest extends ClassificationModuleIntegTest {

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