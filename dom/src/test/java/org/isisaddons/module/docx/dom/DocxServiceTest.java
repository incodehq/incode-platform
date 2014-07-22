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

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import org.docx4j.openpackaging.packages.WordprocessingMLPackage;
import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.isisaddons.module.docx.dom.DocxService.MatchingPolicy;
import org.junit.Test;

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

        // ... for manual inspection

        final File expectedFile = io.asFile(prefix + "-Output-Expected.docx");

        final File actualFile = io.asFileInSameDir(expectedFile,prefix +"-Output-Actual.docx");
        io.write(actual, actualFile);

        System.out.println("expected: " + expectedFile.getAbsolutePath());
        System.out.println("actual: " + actualFile.getAbsolutePath());


        // ... and automated
        // a simple binary comparison finds differences, even though a manual check using MS Word itself shows
        // no differences; for now just do a heuristic check on file size
        final byte[] expected = io.asBytes(expectedFile);

        assertThat(actual.length, isWithin(expected.length, 1));
    }

    private Matcher<Integer> isWithin(final Integer expected, final int tolerance) {
        return new TypeSafeMatcher<Integer>() {
            @Override
            protected boolean matchesSafely(Integer item) {
                final int length = expected;
                final int lower = length * (100 - tolerance) / 100;
                final int upper = length * (100 + tolerance) / 100;

                return item > lower && item < upper;
            }

            @Override
            public void describeTo(Description description) {
                description.appendText(" within " + tolerance + "% in size of array with " + expected + " bytes");
            }
        };
    }

}
