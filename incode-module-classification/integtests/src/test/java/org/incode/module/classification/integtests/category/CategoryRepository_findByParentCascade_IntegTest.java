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

import java.util.List;

import javax.inject.Inject;

import org.junit.Before;
import org.junit.Test;

import org.incode.module.classification.dom.impl.applicability.ApplicabilityRepository;
import org.incode.module.classification.dom.impl.category.Category;
import org.incode.module.classification.dom.impl.category.CategoryRepository;
import org.incode.module.classification.dom.impl.category.taxonomy.Taxonomy;
import org.incode.module.classification.dom.impl.classification.ClassificationRepository;
import org.incode.module.classification.dom.spi.ApplicationTenancyService;
import org.incode.module.classification.fixture.dom.demo.first.DemoObjectMenu;
import org.incode.module.classification.fixture.scripts.scenarios.ClassifiedDemoObjectsFixture;
import org.incode.module.classification.fixture.scripts.teardown.ClassificationDemoAppTearDownFixture;
import org.incode.module.classification.integtests.ClassificationModuleIntegTest;

import static org.assertj.core.api.Assertions.assertThat;

public class CategoryRepository_findByParentCascade_IntegTest extends ClassificationModuleIntegTest {

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
    public void when_no_grandchildren() {
        // given
        Category parentLarge = categoryRepository.findByReference("LGE");

        // when
        List<Category> childrenLarge = categoryRepository.findByParentCascade(parentLarge);

        // then
        assertThat(childrenLarge).hasSize(3);
        assertThat(childrenLarge).extracting(Category::getFullyQualifiedName)
                .containsOnly(
                        "Sizes/Large/Large",
                        "Sizes/Large/Larger",
                        "Sizes/Large/Largest");
    }

    @Test
    public void when_grandchildren() {
        // given
        Taxonomy parentSizes = (Taxonomy) categoryRepository.findByReference("SIZES");

        // when
        List<Category> childrenSizes = categoryRepository.findByParentCascade(parentSizes);

        // then
        assertThat(childrenSizes).hasSize(9);
        assertThat(childrenSizes).extracting(Category::getFullyQualifiedName)
                .containsOnly(
                        "Sizes/Large",
                        "Sizes/Medium",
                        "Sizes/Small",
                        "Sizes/Large/Largest",
                        "Sizes/Large/Larger",
                        "Sizes/Large/Large",
                        "Sizes/Small/Small",
                        "Sizes/Small/Smaller",
                        "Sizes/Small/Smallest");
    }

}