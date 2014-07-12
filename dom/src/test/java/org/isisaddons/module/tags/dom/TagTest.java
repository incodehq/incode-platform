/*
 *
 *  Copyright 2012-2014 Eurocommercial Properties NV
 *
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
package org.isisaddons.module.tags.dom;

import java.util.List;
import org.junit.Before;
import org.junit.Test;

import org.apache.isis.applib.util.ObjectContracts;
import org.apache.isis.core.unittestsupport.comparable.ComparableContractTest_compareTo;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class TagTest {

    public static class Properties extends TagTest {

        Tag tag;

        @Before
        public void setUp() throws Exception {
            tag = new Tag();
        }

        public static class Name extends Properties {

            @Test
            public void happyCase() {
                tag.setKey("foo");
                assertThat(tag.getKey(), is("foo"));
                tag.setKey("bar");
                assertThat(tag.getKey(), is("bar"));
            }
        }

        public static class Value extends Properties {

            @Test
            public void happyCase() {
                tag.setValue("foo");
                assertThat(tag.getValue(), is("foo"));
                tag.setValue("bar");
                assertThat(tag.getValue(), is("bar"));
            }
        }
    }

    public static class CompareTo extends ComparableContractTest_compareTo<Tag> {

        public static class SomeTaggedObject implements Comparable<SomeTaggedObject> {

            public SomeTaggedObject(String name) {
                setName(name);
            }

            private String name;
            public String getName() {
                return name;
            }
            public void setName(String name) {
                this.name = name;
            }

            public int compareTo(SomeTaggedObject other) {
                return new ObjectContracts().compare(this, other, "name");
            }
        }

        private Object taggable1;
        private Object taggable2;

        @Before
        public void setUp() throws Exception {
            taggable1 = new SomeTaggedObject("A");
            taggable2 = new SomeTaggedObject("B");
        }

        @SuppressWarnings("unchecked")
        @Override
        protected List<List<Tag>> orderedTuples() {
            return listOf(
                    listOf(
                            newTag(null, null),
                            newTag(taggable1, null),
                            newTag(taggable1, null),
                            newTag(taggable2, null)
                    ),
                    listOf(
                            newTag(taggable1, null),
                            newTag(taggable1, "Abc"),
                            newTag(taggable1, "Abc"),
                            newTag(taggable1, "Def")
                    )
            );
        }

        private Tag newTag(
                final Object taggedObject,
                final String name) {
            final Tag tag = new Tag();

            tag.setTaggedObject(taggedObject);
            tag.setKey(name);
            return tag;
        }

    }
}
