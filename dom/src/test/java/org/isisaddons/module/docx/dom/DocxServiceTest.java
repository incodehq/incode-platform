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
import org.docx4j.openpackaging.packages.WordprocessingMLPackage;
import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.greaterThan;

public class DocxServiceTest {

    final IoHelper io = new IoHelper(this);

    DocxService docxService;
    WordprocessingMLPackage docxTemplate;

    @org.junit.Before
    public void setUp() throws Exception {
        docxService = new DocxService();

        // given
        final ByteArrayInputStream docxInputTemplate = io.asBais("Template.docx");
        docxTemplate = docxService.loadPackage(docxInputTemplate);

    }

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    public static class Strict extends DocxServiceTest {

        private DocxService.MatchingPolicy matchingPolicy = DocxService.MatchingPolicy.STRICT;

        @Test
        public void exactMatch() throws Exception {

            // when
            final String html = io.asString("input-exact-match.html");
            final ByteArrayOutputStream baos = new ByteArrayOutputStream();

            docxService.merge(html, docxTemplate, baos, matchingPolicy);

            // then
            final byte[] actual = baos.toByteArray();

            // ... for manual inspection
            final File expectedFile = io.asFile("Output-Expected.docx");

            final File actualFile = io.asFileInSameDir(expectedFile, "Output-Actual.docx");
            io.write(actual, actualFile);

            System.out.println("expected: " + expectedFile.getAbsolutePath());
            System.out.println("actual: " + actualFile.getAbsolutePath());


            // ... and automated
            // a simple binary comparison finds differences, even though a manual check using MS Word itself shows
            // no differences; for now just do a heuristic check on file size
            final byte[] expected = io.asBytes(expectedFile);

            assertThat(actual.length, isWithin(expected.length, 1));
        }

        @Test
        public void whenSurplusInput() throws Exception {

            // then
            expectedException.expectMessage("Input elements [SURPLUS] were not matched to placeholders");

            // when
            final String html = io.asString("input-surplus.html");
            docxService.merge(html, docxTemplate, new ByteArrayOutputStream(), matchingPolicy);
        }

        @Test
        public void whenMissingInput() throws Exception {

            // then
            expectedException.expectMessage("Placeholders [Decision2] were not matched to input");

            // when
            final String html = io.asString("input-missing.html");
            docxService.merge(html, docxTemplate, new ByteArrayOutputStream(), matchingPolicy);
        }

    }

    public static class AllowUnmatchedInput extends DocxServiceTest {

        private DocxService.MatchingPolicy matchingPolicy = DocxService.MatchingPolicy.ALLOW_UNMATCHED_INPUT;


        @Test
        public void exactMatch() throws Exception {

            // when
            final String html = io.asString("input-exact-match.html");
            final ByteArrayOutputStream baos = new ByteArrayOutputStream();

            docxService.merge(html, docxTemplate, baos, matchingPolicy);

            // then
            final byte[] actual = baos.toByteArray();
            assertThat(actual.length, greaterThan(0));
        }

        @Test
        public void whenSurplusInput() throws Exception {

            // when
            final String html = io.asString("input-surplus.html");
            docxService.merge(html, docxTemplate, new ByteArrayOutputStream(), matchingPolicy);

            // then no exceptions
        }

        @Test
        public void whenMissingInput() throws Exception {

            // then
            expectedException.expectMessage("Placeholders [Decision2] were not matched to input");

            // when
            final String html = io.asString("input-missing.html");
            docxService.merge(html, docxTemplate, new ByteArrayOutputStream(), matchingPolicy);
        }

    }

    public static class AllowUnmatchedPlaceholders extends DocxServiceTest {

        private DocxService.MatchingPolicy matchingPolicy = DocxService.MatchingPolicy.ALLOW_UNMATCHED_PLACEHOLDERS;

        @Test
        public void exactMatch() throws Exception {

            // when
            final String html = io.asString("input-exact-match.html");
            final ByteArrayOutputStream baos = new ByteArrayOutputStream();

            docxService.merge(html, docxTemplate, baos, matchingPolicy);

            // then
            final byte[] actual = baos.toByteArray();
            assertThat(actual.length, greaterThan(0));
        }

        @Test
        public void whenSurplusInput() throws Exception {

            // then
            expectedException.expectMessage("Input elements [SURPLUS] were not matched to placeholders");

            // when
            final String html = io.asString("input-surplus.html");
            docxService.merge(html, docxTemplate, new ByteArrayOutputStream(), matchingPolicy);
        }

        @Test
        public void whenMissingInput() throws Exception {

            // when
            final String html = io.asString("input-missing.html");
            docxService.merge(html, docxTemplate, new ByteArrayOutputStream(), matchingPolicy);

            // then no exceptions

        }

    }

    public static class Lax extends DocxServiceTest {

        private DocxService.MatchingPolicy matchingPolicy = DocxService.MatchingPolicy.LAX;

        @Test
        public void exactMatch() throws Exception {

            // when
            final String html = io.asString("input-exact-match.html");
            final ByteArrayOutputStream baos = new ByteArrayOutputStream();

            docxService.merge(html, docxTemplate, baos, matchingPolicy);

            // then
            final byte[] actual = baos.toByteArray();
            assertThat(actual.length, greaterThan(0));
        }

        @Test
        public void whenSurplusInput() throws Exception {

            // when
            final String html = io.asString("input-surplus.html");
            docxService.merge(html, docxTemplate, new ByteArrayOutputStream(), matchingPolicy);

            // then no exceptions
        }

        @Test
        public void whenMissingInput() throws Exception {

            // when
            final String html = io.asString("input-missing.html");
            docxService.merge(html, docxTemplate, new ByteArrayOutputStream(), matchingPolicy);

            // then no exceptions
        }

    }


    public static class BadInput extends DocxServiceTest {

        @Test
        public void whenBadInput() throws Exception {

            // then
            expectedException.expect(LoadInputException.class);
            expectedException.expectMessage("Unable to parse input");

            // when
            final String html = io.asString("input-malformed.html");
            docxService.merge(html, docxTemplate, new ByteArrayOutputStream(), DocxService.MatchingPolicy.LAX);
        }
    }

    private static Matcher<Integer> isWithin(final Integer expected, final int tolerance) {
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
