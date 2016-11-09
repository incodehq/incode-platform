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

import javax.inject.Inject;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import org.incode.module.document.dom.impl.docs.Document;
import org.incode.module.document.dom.impl.docs.DocumentTemplate;
import org.incode.module.document.fixture.app.paperclips.PaperclipForDemoObject;
import org.incode.module.document.fixture.dom.demo.DemoObjectMenu;
import org.incode.module.document.fixture.scripts.teardown.DocumentDemoObjectsTearDownFixture;
import org.incode.module.document.fixture.seed.DocumentTypeAndTemplatesFixture;
import org.incode.module.document.integtests.DocumentModuleIntegTest;

import static org.assertj.core.api.Assertions.assertThat;

public class T_createDocument_IntegTest extends DocumentModuleIntegTest {

    @Inject
    DemoObjectMenu demoObjectMenu;

    Object demoObject;

    @Before
    public void setUpData() throws Exception {
        fixtureScripts.runFixtureScript(new DocumentDemoObjectsTearDownFixture(), null);
        fixtureScripts.runFixtureScript(new DocumentTypeAndTemplatesFixture(), null);

        demoObject = wrap(demoObjectMenu).create("Foo");
    }

    public static class ActionImplementationIntegTest extends T_createDocument_IntegTest {

        @Before
        public void setUp() throws Exception {
            assertThat(wrap(mixinDocuments(demoObject)).$$()).isEmpty();
        }

        @Ignore
        @Test
        public void can_create_document() throws Exception {

            // given
            final PaperclipForDemoObject._createAndAttachDocumentAndRender createDocument = mixinCreateDocument(demoObject);

            // when
            final List<DocumentTemplate> templates = createDocument.choices0$$();

            // then
            assertThat(templates).isNotEmpty();
            final DocumentTemplate firstTemplate = templates.get(0);

            // when
            final Object documentAsObj = createDocument.$$(firstTemplate);

            // then
            assertThat(documentAsObj).isInstanceOf(Document.class);
            Document document = (Document) documentAsObj;

        }

    }


}