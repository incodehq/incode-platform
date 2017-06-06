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
package org.incode.module.classification.integtests.category;

import javax.inject.Inject;
import javax.jdo.JDODataStoreException;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import org.incode.module.classification.dom.impl.applicability.ApplicabilityRepository;
import org.incode.module.classification.dom.impl.category.CategoryRepository;
import org.incode.module.classification.dom.impl.category.taxonomy.Taxonomy;
import org.incode.module.classification.dom.impl.classification.ClassificationRepository;
import org.incode.module.classification.dom.spi.ApplicationTenancyService;
import org.incode.module.classification.fixture.dom.demo.first.DemoObjectMenu;
import org.incode.module.classification.fixture.scripts.scenarios.ClassifiedDemoObjectsFixture;
import org.incode.module.classification.fixture.scripts.teardown.ClassificationDemoAppTearDownFixture;
import org.incode.module.classification.integtests.ClassificationModuleIntegTest;

import static org.assertj.core.api.Assertions.assertThat;

public class CategoryRepository_createTaxonomy_IntegTest extends ClassificationModuleIntegTest {

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
    public void happy_case() {
        // when
        Taxonomy newTaxonomy = categoryRepository.createTaxonomy("New Taxonomy");

        // then
        assertThat(newTaxonomy.getName()).isEqualTo("New Taxonomy");
        assertThat(newTaxonomy.getParent()).isNull();
        assertThat(newTaxonomy.getReference()).isNull();
        assertThat(newTaxonomy.getOrdinal()).isEqualTo(1);
    }

    @Test
    public void when_name_already_in_use() {
        // given
        Taxonomy italianColours = (Taxonomy) categoryRepository.findByReference("ITACOL");
        assertThat(italianColours.getName()).isEqualTo("Italian Colours");

        // then
        expectedException.expect(JDODataStoreException.class);
        expectedException.expectMessage("Classification_fullyQualifiedName_UNQ");

        // when
        categoryRepository.createTaxonomy("Italian Colours");
    }

}