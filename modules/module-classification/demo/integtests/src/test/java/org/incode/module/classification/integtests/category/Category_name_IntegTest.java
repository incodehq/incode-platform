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

import org.junit.Before;
import org.junit.Test;

import org.apache.isis.applib.services.wrapper.InvalidException;

import org.incode.module.classification.dom.impl.applicability.ApplicabilityRepository;
import org.incode.module.classification.dom.impl.category.Category;
import org.incode.module.classification.dom.impl.category.CategoryRepository;
import org.incode.module.classification.dom.impl.classification.ClassificationRepository;
import org.incode.module.classification.dom.spi.ApplicationTenancyService;
import org.incode.module.classification.fixture.dom.demo.first.DemoObjectMenu;
import org.incode.module.classification.fixture.scripts.scenarios.ClassifiedDemoObjectsFixture;
import org.incode.module.classification.fixture.scripts.teardown.ClassificationDemoAppTearDownFixture;
import org.incode.module.classification.integtests.ClassificationModuleIntegTest;

import static org.assertj.core.api.Assertions.assertThat;

public class Category_name_IntegTest extends ClassificationModuleIntegTest {

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
        Category medium = categoryRepository.findByReference("M");
        assertThat(medium.getFullyQualifiedName()).isEqualTo("Sizes/Medium");

        // when
        medium.modifyName("Middle");

        // then
        assertThat(medium.getName()).isEqualTo("Middle");
        assertThat(medium.getFullyQualifiedName()).isEqualTo("Sizes/Middle");
    }

    @Test
    public void fully_qualified_name_of_children_also_updated() {
        // given
        Category large = categoryRepository.findByReference("LGE");
        assertThat(large.getFullyQualifiedName()).isEqualTo("Sizes/Large");
        assertThat(large.getChildren()).allMatch(c -> c.getFullyQualifiedName().split("/")[1].equals("Large"));

        // when
        large.modifyName("LRG");

        // then
        assertThat(large.getFullyQualifiedName()).isEqualTo("Sizes/LRG");
        assertThat(large.getChildren()).allMatch(c -> c.getFullyQualifiedName().split("/")[1].equals("LRG"));
    }

    @Test
    public void cannot_rename_to_a_name_already_in_use() {
        // given
        Category red = categoryRepository.findByReference("FRRED");

        // then
        expectedException.expect(InvalidException.class);
        expectedException.expectMessage("A category with name 'White' already exists (under this parent)");

        // when
        wrap(red).setName("White");
    }

}