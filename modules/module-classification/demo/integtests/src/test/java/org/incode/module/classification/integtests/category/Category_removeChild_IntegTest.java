package org.incode.module.classification.integtests.category;

import javax.inject.Inject;

import org.junit.Before;
import org.junit.Test;

import org.apache.isis.applib.services.wrapper.InvalidException;
import org.apache.isis.applib.services.xactn.TransactionService;

import org.incode.module.classification.dom.impl.applicability.ApplicabilityRepository;
import org.incode.module.classification.dom.impl.category.Category;
import org.incode.module.classification.dom.impl.category.CategoryRepository;
import org.incode.module.classification.dom.impl.classification.ClassificationRepository;
import org.incode.module.classification.dom.spi.ApplicationTenancyService;
import org.incode.module.classification.fixture.dom.demo.first.DemoObjectMenu;
import org.incode.module.classification.fixture.scripts.scenarios.ClassifiedDemoObjectsFixture;
import org.incode.module.classification.integtests.ClassificationModuleIntegTest;

import static org.assertj.core.api.Assertions.assertThat;

public class Category_removeChild_IntegTest extends ClassificationModuleIntegTest {

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

    @Inject
    TransactionService transactionService;

    @Before
    public void setUpData() throws Exception {
        fixtureScripts.runFixtureScript(new ClassifiedDemoObjectsFixture(), null);
    }

    @Test
    public void happy_case() {
        // given
        Category large = categoryRepository.findByReference("LGE");
        Category largest = categoryRepository.findByReference("XXL");
        assertThat(large.getChildren()).contains(largest);

        // when
        wrap(large).removeChild(largest);
        transactionService.nextTransaction();

        // then
        assertThat(large.getChildren()).doesNotContain(largest);
    }

    @Test
    public void happy_case_cascading() {
        // given
        Category sizes = categoryRepository.findByReference("SIZES");
        Category large = categoryRepository.findByReference("LGE");
        assertThat(sizes.getChildren()).contains(large);
        assertThat(large.getChildren()).isNotEmpty();

        // when
        wrap(sizes).removeChild(large);
        transactionService.nextTransaction();

        // then
        assertThat(sizes.getChildren()).doesNotContain(large);
        assertThat(categoryRepository.findByReference("LGE")).isNull();
    }

    @Test
    public void cannot_remove_if_has_classification() {
        // given
        Category sizes = categoryRepository.findByReference("SIZES");
        Category medium = categoryRepository.findByReference("M");

        // then
        expectedException.expect(InvalidException.class);
        expectedException.expectMessage("Child 'Sizes/Medium' is classified by 'DemoObject{name=Demo foo (in Italy)}' and cannot be removed");

        // when
        wrap(sizes).removeChild(medium);
    }

    @Test
    public void cannot_remove_if_child_has_classification() {
        // given
        Category sizes = categoryRepository.findByReference("SIZES");
        Category small = categoryRepository.findByReference("SML");

        // then
        expectedException.expect(InvalidException.class);
        expectedException.expectMessage("Child 'Sizes/Small/Smaller' is classified by 'DemoObject{name=Demo bar (in France)}' and cannot be removed");

        // when
        wrap(sizes).removeChild(small);
    }

}