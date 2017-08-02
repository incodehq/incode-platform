package org.incode.platform.dom.document.integtests.tests.document;

import javax.inject.Inject;

import org.junit.Before;
import org.junit.Test;

import org.incode.module.document.dom.impl.docs.Document;
import org.incode.module.document.dom.impl.paperclips.PaperclipRepository;
import org.incode.domainapp.example.dom.dom.document.dom.demo.DemoObject;
import org.incode.domainapp.example.dom.dom.document.dom.demo2.OtherObject;
import org.incode.domainapp.example.dom.dom.document.fixture.data.DemoObjectsFixture;
import org.incode.domainapp.example.dom.dom.document.fixture.data.OtherObjectsFixture;
import org.incode.domainapp.example.dom.dom.document.fixture.DocumentDemoAppTearDownFixture;
import org.incode.domainapp.example.dom.dom.document.fixture.seed.DocumentTypeAndTemplatesApplicableForDemoObjectFixture;
import org.incode.platform.dom.document.integtests.DocumentModuleIntegTestAbstract;

import static org.assertj.core.api.Assertions.assertThat;

public class Document_delete_IntegTest extends DocumentModuleIntegTestAbstract {

    DemoObject demoObject;
    OtherObject otherObject;

    DocumentTypeAndTemplatesApplicableForDemoObjectFixture templateFixture;

    Document fmkDocument;
    Document xddDocument;

    @Before
    public void setUpData() throws Exception {

        fixtureScripts.runFixtureScript(new DocumentDemoAppTearDownFixture(), null);

        // types + templates
        templateFixture = new DocumentTypeAndTemplatesApplicableForDemoObjectFixture();
        fixtureScripts.runFixtureScript(templateFixture, null);

        // demo objects
        final DemoObjectsFixture demoObjectsFixture = new DemoObjectsFixture();
        fixtureScripts.runFixtureScript(demoObjectsFixture, null);
        demoObject = demoObjectsFixture.getDemoObjects().get(0);

        // other objects
        final OtherObjectsFixture otherObjectsFixture = new OtherObjectsFixture();
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