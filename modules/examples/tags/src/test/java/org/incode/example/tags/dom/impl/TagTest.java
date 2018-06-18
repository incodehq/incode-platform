package org.incode.example.tags.dom.impl;

import java.util.List;
import org.jmock.Expectations;
import org.jmock.auto.Mock;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.apache.isis.applib.services.bookmark.Bookmark;
import org.apache.isis.applib.services.bookmark.BookmarkService;
import org.apache.isis.applib.util.ObjectContracts;
import org.apache.isis.core.unittestsupport.comparable.ComparableContractTest_compareTo;
import org.apache.isis.core.unittestsupport.jmocking.JUnitRuleMockery2;

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

        @Rule
        public JUnitRuleMockery2 context = JUnitRuleMockery2.createFor(JUnitRuleMockery2.Mode.INTERFACES_AND_CLASSES);

        @Mock
        private BookmarkService mockBookmarkService;

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
        private Object taggable3;

        private Bookmark bookmarkForTaggable1;
        private Bookmark bookmarkForTaggable2;
        private Bookmark bookmarkForTaggable3;

        @Before
        public void setUp() throws Exception {
            taggable1 = new SomeTaggedObject("A");
            taggable2 = new SomeTaggedObject("B");
            taggable3 = new SomeTaggedObject("B");

            bookmarkForTaggable1 = new Bookmark("SomeTaggedObject", "A");
            bookmarkForTaggable2 = new Bookmark("SomeTaggedObject", "B");
            bookmarkForTaggable3 = new Bookmark("SomeTaggedObject", "C");

            context.checking(new Expectations() {{
                allowing(mockBookmarkService).bookmarkFor(taggable1);
                will(returnValue(bookmarkForTaggable1));

                allowing(mockBookmarkService).bookmarkFor(taggable2);
                will(returnValue(bookmarkForTaggable2));

                allowing(mockBookmarkService).bookmarkFor(taggable3);
                will(returnValue(bookmarkForTaggable3));
            }});
        }

        @SuppressWarnings("unchecked")
        @Override
        protected List<List<Tag>> orderedTuples() {
            return listOf(
                    listOf(
                            newTag(taggable1, "Abc"),
                            newTag(taggable2, "Abc"),
                            newTag(taggable2, "Abc"),
                            newTag(taggable3, "Abc")
                    ),
                    listOf(
                            newTag(taggable1, "Abc"),
                            newTag(taggable1, "Def"),
                            newTag(taggable1, "Def"),
                            newTag(taggable1, "Ghi")
                    )
            );
        }

        private Tag newTag(
                final Object taggedObject,
                final String name) {
            final Tag tag = new Tag();
            tag.bookmarkService = mockBookmarkService;

            tag.setTaggedObject(taggedObject);
            tag.setKey(name);
            return tag;
        }

    }
}
