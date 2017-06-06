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

import java.util.List;

import javax.inject.Inject;

import org.junit.Before;
import org.junit.Test;

import org.apache.isis.applib.services.factory.FactoryService;

import org.incode.module.classification.dom.impl.applicability.ApplicabilityRepository;
import org.incode.module.classification.dom.impl.category.CategoryRepository;
import org.incode.module.classification.dom.impl.classification.Classification;
import org.incode.module.classification.dom.impl.classification.ClassificationRepository;
import org.incode.module.classification.dom.spi.ApplicationTenancyService;
import org.incode.module.classification.fixture.app.classification.demo.ClassificationForDemoObject;
import org.incode.module.classification.fixture.app.classification.other.ClassificationForOtherObject;
import org.incode.module.classification.fixture.dom.demo.first.DemoObject;
import org.incode.module.classification.fixture.dom.demo.first.DemoObjectMenu;
import org.incode.module.classification.fixture.dom.demo.other.OtherObject;
import org.incode.module.classification.fixture.dom.demo.other.OtherObjectMenu;
import org.incode.module.classification.fixture.scripts.scenarios.ClassifiedDemoObjectsFixture;
import org.incode.module.classification.integtests.ClassificationModuleIntegTest;

import static org.assertj.core.api.Assertions.assertThat;

public class T_unclassify_IntegTest extends ClassificationModuleIntegTest {

    @Inject
    ClassificationRepository classificationRepository;
    @Inject
    CategoryRepository categoryRepository;
    @Inject
    ApplicabilityRepository applicabilityRepository;

    @Inject
    DemoObjectMenu demoObjectMenu;
    @Inject
    OtherObjectMenu otherObjectMenu;

    @Inject
    ApplicationTenancyService applicationTenancyService;
    @Inject
    FactoryService factoryService;

    @Before
    public void setUpData() throws Exception {
        fixtureScripts.runFixtureScript(new ClassifiedDemoObjectsFixture(), null);
    }

    @Test
    public void enabled_when_classifications_exist() {
        // given
        DemoObject demoFooInItaly = demoObjectMenu.listAll()
                .stream()
                .filter(demoObject -> demoObject.getName().equals("Demo foo (in Italy)"))
                .findFirst()
                .get();
        final List<Classification> byClassified = classificationRepository.findByClassified(demoFooInItaly);
        assertThat(byClassified).hasSize(2);

        // when
        final ClassificationForDemoObject._unclassify unclassify = factoryService.mixin(ClassificationForDemoObject._unclassify.class, demoFooInItaly);
        wrap(unclassify).unclassify(byClassified.get(0));

        // then
        assertThat(classificationRepository.findByClassified(demoFooInItaly)).hasSize(1);
    }

    @Test
    public void disabled_when_no_classification_to_remove() {

        // given "Other bar (in Paris)", that has no classifications
        // given
        OtherObject otherBarInFrance = otherObjectMenu.listAll()
                .stream()
                .filter(otherObject -> otherObject.getName().equals("Other bar (in France)"))
                .findFirst()
                .get();
        assertThat(classificationRepository.findByClassified(otherBarInFrance)).isEmpty();

        final ClassificationForOtherObject._unclassify unclassify = factoryService.mixin(ClassificationForOtherObject._unclassify.class, otherBarInFrance);

        // when
        final String message = unclassify.disableUnclassify().toString();

        // then
        assertThat(message).isEqualTo("tr: No classifications to delete");
    }

}