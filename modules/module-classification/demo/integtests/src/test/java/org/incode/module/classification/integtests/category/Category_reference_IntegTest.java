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
import domainapp.modules.exampledom.module.classification.dom.demo.DemoObjectMenu;
import domainapp.modules.exampledom.module.classification.fixture.ClassifiedDemoObjectsFixture;
import org.incode.module.classification.integtests.ClassificationModuleIntegTestAbstract;

import static org.assertj.core.api.Assertions.assertThat;

public class Category_reference_IntegTest extends ClassificationModuleIntegTestAbstract {

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
        fixtureScripts.runFixtureScript(new ClassifiedDemoObjectsFixture(), null);
    }

    @Test
    public void happy_case() {
        // given
        Category smallest = categoryRepository.findByReference("XXS");

        // when
        smallest.modifyReference("V.SMALL");

        // then
        assertThat(smallest.getReference()).isEqualTo("V.SMALL");
    }

    @Test
    public void cannot_rename_to_a_reference_already_in_use() {
        // given
        Category smallest = categoryRepository.findByReference("XXS");

        // then
        expectedException.expect(InvalidException.class);
        expectedException.expectMessage("A category with reference 'XS' already exists (under this parent)");

        // when
        wrap(smallest).setReference("XS");
    }

    @Test
    public void can_clear() {
        // given
        Category smallest = categoryRepository.findByReference("XXS");

        // when
        smallest.setReference(null);

        // then
        assertThat(smallest.getReference()).isNull();
    }

}