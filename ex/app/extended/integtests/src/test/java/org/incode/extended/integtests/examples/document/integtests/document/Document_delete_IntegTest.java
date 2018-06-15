package org.incode.extended.integtests.examples.document.integtests.document;

import javax.inject.Inject;

import org.junit.Before;
import org.junit.Test;

import org.incode.domainapp.extended.module.fixtures.per_cpt.examples.document.fixture.DemoModule_and_DocTypesAndTemplates_tearDown;
import org.incode.domainapp.extended.module.fixtures.per_cpt.examples.document.fixture.seed.DocumentTypeAndTemplatesApplicableForDemoObjectFixture;
import org.incode.domainapp.extended.module.fixtures.shared.demowithurl.dom.DemoObjectWithUrl;
import org.incode.domainapp.extended.module.fixtures.shared.demowithurl.fixture.DemoObjectWithUrl_createUpTo5_fakeData;
import org.incode.domainapp.extended.module.fixtures.shared.other.dom.OtherObject;
import org.incode.domainapp.extended.module.fixtures.shared.other.fixture.OtherObject_createUpTo5_fakeData;
import org.incode.example.document.dom.impl.docs.Document;
import org.incode.example.document.dom.impl.paperclips.PaperclipRepository;
import org.incode.extended.integtests.examples.document.DocumentModuleIntegTestAbstract;

import static org.assertj.core.api.Assertions.assertThat;

public class Document_delete_IntegTest extends DocumentModuleIntegTestAbstract {

    DemoObjectWithUrl demoObject;
    OtherObject otherObject;

    DocumentTypeAndTemplatesApplicableForDemoObjectFixture templateFixture;

    Document fmkDocument;
    Document xddDocument;

    @Before
    public void setUpData() throws Exception {

        fixtureScripts.runFixtureScript(new DemoModule_and_DocTypesAndTemplates_tearDown(), null);

        // types + templates
        templateFixture = new DocumentTypeAndTemplatesApplicableForDemoObjectFixture();
        fixtureScripts.runFixtureScript(templateFixture, null);

        // demo objects
        final DemoObjectWithUrl_createUpTo5_fakeData demoObjectWithUrlFixture = new DemoObjectWithUrl_createUpTo5_fakeData();
        fixtureScripts.runFixtureScript(demoObjectWithUrlFixture, null);
        demoObject = demoObjectWithUrlFixture.getDemoObjects().get(0);

        // other objects
        final OtherObject_createUpTo5_fakeData otherObjectsFixture = new OtherObject_createUpTo5_fakeData();
        fixtureScripts.runFixtureScript(otherObjectsFixture, null);
        otherObject = otherObjectsFixture.getOtherObjects().get(0);

        // some docs
        fmkDocument = (Document)_createAndAttachDocumentAndRender(demoObject).$$(templateFixture.getFmkTemplate());
        xddDocument = (Document)_createAndAttachDocumentAndRender(demoObject).$$(templateFixture.getXddTemplate());

        transactionService.flushTransaction();
    }

    public static class ActionImplementation_IntegTest extends Document_delete_IntegTest {

        @Test
        public void can_delete_when_attached_to_single_object() throws Exception {

            // given
            assertThat(wrap(_documents(demoObject)).$$()).hasSize(2); // fmk + xdd
            assertThat(wrap(_documents(otherObject)).$$()).hasSize(1); // xdd

            // when
            final Object result = _delete(fmkDocument).$$();
            transactionService.flushTransaction();

            // then
            assertThat(wrap(_documents(demoObject)).$$()).hasSize(1); // xdd
            assertThat(wrap(_documents(otherObject)).$$()).hasSize(1); // xdd

            assertThat(result).isSameAs(demoObject);
        }

        @Test
        public void can_delete_when_attached_to_multiple_objects() throws Exception {

            // given
            assertThat(wrap(_documents(demoObject)).$$()).hasSize(2); // fmk + xdd
            assertThat(wrap(_documents(otherObject)).$$()).hasSize(1); // xdd

            // when
            final Object result = _delete(xddDocument).$$();
            transactionService.flushTransaction();

            // then
            assertThat(wrap(_documents(demoObject)).$$()).hasSize(1); // xdd
            assertThat(wrap(_documents(otherObject)).$$()).hasSize(0); // xdd

            assertThat(result).isNull();
        }

    }

    @Inject
    PaperclipRepository paperclipRepository;

}