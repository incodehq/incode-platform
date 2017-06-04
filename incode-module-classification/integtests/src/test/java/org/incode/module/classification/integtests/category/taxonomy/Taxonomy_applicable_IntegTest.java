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
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import org.apache.isis.applib.services.wrapper.InvalidException;

import org.incode.module.classification.dom.impl.applicability.Applicability;
import org.incode.module.classification.dom.impl.applicability.ApplicabilityRepository;
import org.incode.module.classification.dom.impl.category.CategoryRepository;
import org.incode.module.classification.dom.impl.category.taxonomy.Taxonomy;
import org.incode.module.classification.dom.impl.classification.ClassificationRepository;
import org.incode.module.classification.dom.spi.ApplicationTenancyService;
import org.incode.module.classification.fixture.dom.demo.first.DemoObject;
import org.incode.module.classification.fixture.dom.demo.first.DemoObjectMenu;
import org.incode.module.classification.fixture.dom.demo.other.OtherObject;
import org.incode.module.classification.fixture.scripts.scenarios.ClassifiedDemoObjectsFixture;
import org.incode.module.classification.fixture.scripts.teardown.ClassificationDemoAppTearDownFixture;
import org.incode.module.classification.integtests.ClassificationModuleIntegTest;

import static org.assertj.core.api.Assertions.assertThat;

public class Taxonomy_applicable_IntegTest extends ClassificationModuleIntegTest {

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

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Before
    public void setUpData() throws Exception {
        fixtureScripts.runFixtureScript(new ClassificationDemoAppTearDownFixture(), null);
        fixtureScripts.runFixtureScript(new ClassifiedDemoObjectsFixture(), null);
    }

    @Test
    public void can_add_applicability_for_different_domain_types_with_same_atPath() {
        // given
        Taxonomy frenchColours = (Taxonomy) categoryRepository.findByParentAndName(null, "French Colours");
        assertThat(applicabilityRepository.findByDomainTypeAndUnderAtPath(OtherObject.class, "/FRA")).isEmpty();

        // when
        wrap(frenchColours).applicable("/FRA", OtherObject.class.getName());

        // then
        assertThat(applicabilityRepository.findByDomainTypeAndUnderAtPath(OtherObject.class, "/FRA")).hasSize(1);
    }

    @Test
    public void can_add_applicability_for_same_domain_types_with_different_atPath() {

        // eg given an applicability for "/ITA" and 'DemoObject', can also add an applicability for "/ITA/MIL" and 'DemoObject'
        Taxonomy italianColours = (Taxonomy) categoryRepository.findByParentAndName(null, "Italian Colours");
        final List<Applicability> byDomainTypeAndUnderAtPath = applicabilityRepository.findByDomainTypeAndUnderAtPath(DemoObject.class, "/ITA");
        assertThat(byDomainTypeAndUnderAtPath).hasSize(2);
        assertThat(byDomainTypeAndUnderAtPath).extracting(Applicability::getTaxonomy).extracting(Taxonomy::getFullyQualifiedName).containsOnly("Italian Colours", "Sizes");

        // when
        wrap(italianColours).applicable("/ITA/MIL", DemoObject.class.getName());

        // then
        final List<Applicability> byDomainTypeAndUnderAtPathNew = applicabilityRepository.findByDomainTypeAndUnderAtPath(DemoObject.class, "/ITA/MIL");
        assertThat(byDomainTypeAndUnderAtPathNew).hasSize(3);
        assertThat(byDomainTypeAndUnderAtPathNew).extracting(Applicability::getTaxonomy).extracting(Taxonomy::getFullyQualifiedName).containsOnly("Italian Colours", "Sizes");
    }

    @Test
    public void cannot_add_applicability_if_already_has_applicability_for_given_domainType_and_atPath() {

        // eg set up for "/ITA" and 'DemoObject', cannot add again
        // given
        Taxonomy italianColours = (Taxonomy) categoryRepository.findByParentAndName(null, "Italian Colours");

        // then
        expectedException.expect(InvalidException.class);
        expectedException.expectMessage("Already applicable for '/ITA' and '" + DemoObject.class.getName() + "'");

        // when
        wrap(italianColours).applicable("/ITA", DemoObject.class.getName());

    }

}