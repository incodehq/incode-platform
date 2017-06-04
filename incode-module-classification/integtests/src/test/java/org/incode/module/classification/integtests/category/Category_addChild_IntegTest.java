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
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

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

public class Category_addChild_IntegTest extends ClassificationModuleIntegTest {

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

    Category italianColours;

    @Before
    public void setUpData() throws Exception {
        fixtureScripts.runFixtureScript(new ClassificationDemoAppTearDownFixture(), null);
        fixtureScripts.runFixtureScript(new ClassifiedDemoObjectsFixture(), null);
        italianColours = categoryRepository.findByReference("ITACOL");
    }

    @Test
    public void happy_case() {
        // given
        assertThat(italianColours.getChildren()).hasSize(3);

        // when
        wrap(italianColours).addChild("Orange", "ORANGE", null);

        // then
        assertThat(italianColours.getChildren()).hasSize(4);
    }

    @Test
    public void cannot_create_a_child_with_same_name_as_some_other_child() {
        // given
        assertThat(italianColours.getChildren()).extracting(Category::getName)
                .contains("Red");

        // then
        expectedException.expect(InvalidException.class);
        expectedException.expectMessage("There is already a child classification with the name of \'Red\'");

        // when
        wrap(italianColours).addChild("Red", "NEWRED", null);
    }

    @Test
    public void cannot_create_a_child_with_same_reference_as_some_other_child() {
        // given
        assertThat(italianColours.getChildren()).extracting(Category::getReference)
                .contains("GREEN");

        // then
        expectedException.expect(InvalidException.class);
        expectedException.expectMessage("There is already a child classification with the reference of \'GREEN\'");

        //when
        wrap(italianColours).addChild("New Green", "GREEN", null);
    }

}