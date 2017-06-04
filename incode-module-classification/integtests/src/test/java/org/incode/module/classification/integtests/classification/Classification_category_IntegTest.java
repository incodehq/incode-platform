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
package org.incode.module.classification.integtests.classification;

import javax.inject.Inject;

import org.junit.Before;
import org.junit.Ignore;

import org.incode.module.classification.dom.impl.applicability.ApplicabilityRepository;
import org.incode.module.classification.dom.impl.category.Category;
import org.incode.module.classification.dom.impl.category.CategoryRepository;
import org.incode.module.classification.dom.impl.classification.Classification;
import org.incode.module.classification.dom.impl.classification.ClassificationRepository;
import org.incode.module.classification.dom.spi.ApplicationTenancyService;
import org.incode.module.classification.fixture.dom.demo.first.DemoObject;
import org.incode.module.classification.fixture.dom.demo.first.DemoObjectMenu;
import org.incode.module.classification.fixture.scripts.scenarios.ClassifiedDemoObjectsFixture;
import org.incode.module.classification.integtests.ClassificationModuleIntegTest;

import static org.assertj.core.api.Assertions.assertThat;

public class Classification_category_IntegTest extends ClassificationModuleIntegTest {

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

    Object classified;

    @Before
    public void setUpData() throws Exception {
        fixtureScripts.runFixtureScript(new ClassifiedDemoObjectsFixture(), null);
    }

    @Ignore
    public void happy_case() {
        // given
        DemoObject demoFooInItaly = demoObjectMenu.listAll().stream()
                .filter(demoObject -> demoObject.getName().equals("Demo foo (in Italy)"))
                .findFirst()
                .get();

        Classification italianClassificationRed = classificationRepository.findByClassified(demoFooInItaly)
                .stream()
                .filter(classification -> classification.getCategory().getName().equals("Red"))
                .findFirst()
                .get();

        Category italianGreen = categoryRepository.findByReference("GREEN");

        // when
        wrap(italianClassificationRed).setCategory(italianGreen);

        // then
        assertThat(classificationRepository.findByClassified(demoFooInItaly))
                .extracting(Classification::getCategory)
                .extracting(Category::getName)
                .contains("Green")
                .doesNotContain("Red");
    }

}