/*
 *  Copyright 2013~2014 Dan Haywood
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
package org.isisaddons.module.docx.dom;

import java.io.*;
import org.docx4j.openpackaging.packages.WordprocessingMLPackage;
import org.isisaddons.module.docx.dom.DocxService.MatchingPolicy;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class DocxServiceTest {

    private final IoHelper io = new IoHelper(this);

    private DocxService docxService;

    @org.junit.Before
    public void setUp() throws Exception {
        docxService = new DocxService();
    }


    @Test
    public void forAll() throws Exception {
        assertFor("All");
    }

    @Test
    public void forToDoItem() throws Exception {
        assertFor("ToDoItem");
    }

    private void assertFor(String prefix) throws IOException, LoadTemplateException, MergeException, LoadInputException {

        // given
        final ByteArrayInputStream docxInputTemplate = io.asBais(prefix + "-Input.docx");
        final WordprocessingMLPackage docxTemplate = docxService.loadPackage(docxInputTemplate);

        // when
        final String html = io.asString(prefix + "-input.html");
        final ByteArrayOutputStream baos = new ByteArrayOutputStream();

        docxService.merge(html, docxTemplate, baos, MatchingPolicy.LAX);

        // then
        final byte[] actual = baos.toByteArray();
        final byte[] expected = io.asBytes(prefix + "-Output-Expected.docx");

        assertThat(actual, is(expected));

        // then (for manual inspection)
        final File targetFile = io.asFileInSameDir(
                prefix + "-Input.docx",
                prefix +"-Output-Actual.docx");
        final FileOutputStream targetFos = new FileOutputStream(targetFile);

        //ByteStreams.copy(new ByteArrayInputStream(actual), targetFos);

        //System.out.println(targetFile.getAbsolutePath());
    }

}
