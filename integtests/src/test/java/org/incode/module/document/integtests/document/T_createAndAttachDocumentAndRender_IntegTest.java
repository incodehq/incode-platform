/*
 *  Copyright 2014~2015 Dan Haywood
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
package org.incode.module.document.integtests.document;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.inject.Inject;

import com.google.common.collect.Sets;

import org.junit.Before;
import org.junit.Test;

import org.incode.module.document.dom.impl.docs.Document;
import org.incode.module.document.dom.impl.docs.DocumentAbstract;
import org.incode.module.document.dom.impl.docs.DocumentTemplate;
import org.incode.module.document.dom.impl.paperclips.Paperclip;
import org.incode.module.document.dom.impl.paperclips.PaperclipRepository;
import org.incode.module.document.fixture.dom.demo.DemoObject;
import org.incode.module.document.fixture.dom.demo.DemoObjectMenu;
import org.incode.module.document.fixture.scripts.scenarios.DocumentDemoObjectsFixture;
import org.incode.module.document.integtests.DocumentModuleIntegTest;

import static org.assertj.core.api.Assertions.assertThat;

public class T_createAndAttachDocumentAndRender_IntegTest extends DocumentModuleIntegTest {

    @Inject
    DemoObjectMenu demoObjectMenu;

    DemoObject demoObject;

    @Before
    public void setUpData() throws Exception {
        final DocumentDemoObjectsFixture fs = new DocumentDemoObjectsFixture();
        fixtureScripts.runFixtureScript(fs, null);

        demoObject = fs.getDemoObjects().get(0);
    }

    public static class ActionImplementationIntegTest extends T_createAndAttachDocumentAndRender_IntegTest {

        @Before
        public void setUp() throws Exception {
            assertThat(wrap(_documents(demoObject)).$$()).isEmpty();
        }

        @Test
        public void can_create_document() throws Exception {

            // when
            final List<DocumentTemplate> templates = _createAndAttachDocumentAndRender(demoObject).choices0$$();

            // then
            assertThat(templates).hasSize(4);

            // when
            Set<Document> documents = Sets.newHashSet();
            for (DocumentTemplate template : templates) {

                final Object documentAsObj = _createAndAttachDocumentAndRender(demoObject).$$(template);

                // then
                assertThat(documentAsObj).isInstanceOf(Document.class);
                Document document = (Document) documentAsObj;
                documents.add(document);

                // when
                final List<Paperclip> paperclips = paperclipRepository.findByDocument(document);

                // then
                assertThat(paperclips).hasSize(1);
                for (Paperclip paperclip : paperclips) {

                    final DocumentAbstract paperclipDocument = paperclip.getDocument();
                    assertThat(paperclipDocument).isSameAs(document);

                    final Object paperclipAttachedTo = paperclip.getAttachedTo();
                    assertThat(paperclipAttachedTo).isSameAs(demoObject);
                }
            }

            // when
            final List<Paperclip> paperclips = paperclipRepository.findByAttachedTo(demoObject);
            assertThat(paperclips).hasSize(4);

            final Set<Document> attachedDocuments = paperclips.stream().map(x -> (Document) x.getDocument())
                    .collect(Collectors.toSet());

            // then
            assertContainSame(documents, attachedDocuments);
        }

        public void assertContainSame(final Set<Document> docSet1, final Set<Document> docSet2) {
            assertThat(docSet1).contains(docSet2.toArray(new Document[] {}));
            assertThat(docSet2).contains(docSet1.toArray(new Document[]{}));
        }

        @Inject
        PaperclipRepository paperclipRepository;



    }


}