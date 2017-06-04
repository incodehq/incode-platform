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
package org.isisaddons.module.tags.dom;

import org.jmock.Expectations;
import org.jmock.auto.Mock;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.apache.isis.applib.DomainObjectContainer;
import org.apache.isis.applib.services.bookmark.Bookmark;
import org.apache.isis.applib.services.bookmark.BookmarkService;
import org.apache.isis.core.unittestsupport.jmocking.JUnitRuleMockery2;

import static org.hamcrest.CoreMatchers.*;

public class TagsTest {

    public static class Actions extends TagsTest {

        public static class TagFor extends TagsTest {

            @Rule
            public JUnitRuleMockery2 context = JUnitRuleMockery2.createFor(JUnitRuleMockery2.Mode.INTERFACES_AND_CLASSES);

            private Tag tag;
            private Object customer;
            @Mock
            private DomainObjectContainer mockContainer;
            @Mock
            private BookmarkService mockBookmarkService;
            private Bookmark bookmarkForCustomer;

            private Tags tags;

            @Before
            public void setup() {

                customer = new Object();
                bookmarkForCustomer = new Bookmark("Customer", "123");

                context.checking(new Expectations() {{
                    allowing(mockBookmarkService).bookmarkFor(customer);
                    will(returnValue(bookmarkForCustomer));

                    allowing(mockBookmarkService).lookup(bookmarkForCustomer);
                    will(returnValue(customer));
                }});

                tag = new Tag();
                tag.bookmarkService = mockBookmarkService;
                tag.setKey("theme");
                tag.setTaggedObject(customer);
                tag.setValue("lightTheme");

                tags = new Tags();
                tags.container = mockContainer;
                tags.bookmarkService = mockBookmarkService;
            }

            @Test
            public void whenTagNotNull_butTagValueIsNull_thenTagIsRemoved() {
                context.checking(new Expectations() {
                    {
                        oneOf(mockContainer).remove(tag);
                    }
                });

                tag = tags.tagFor(customer, tag, "someTag", null);
                Assert.assertThat(tag, is(nullValue()));
            }

            @Test
            public void whenTagNotNull_butTagValueIsEmptyString_thenTagIsRemoved() {
                context.checking(new Expectations() {
                    {
                        oneOf(mockContainer).remove(tag);
                    }
                });

                tag = tags.tagFor(customer, tag, "theme", "");
                Assert.assertThat(tag, is(nullValue()));
            }

            @Test
            public void whenTagNotNull_andTagValueIsNotNull_thenTagsValueIsUpdated() {
                tag = tags.tagFor(customer, tag, "theme", "darkTheme");
                Assert.assertThat(tag, is(not(nullValue())));
                Assert.assertThat(tag.getValue(), is("darkTheme"));
            }

            @Test
            public void whenTagIsNull_andTagValueIsNull_thenNothing() {
                tag = tags.tagFor(customer, null, "theme", null);
                Assert.assertThat(tag, is(nullValue()));
            }

            @Test
            public void whenTagIsNull_andTagValueIsEmptyString_thenNothing() {
                tag = tags.tagFor(customer, null, "theme", "");
                Assert.assertThat(tag, is(nullValue()));
            }

            @Test
            public void whenTagIsNull_andTagValueIsNotNull_thenTagCreatedAndSet() {
                final Tag newTag = new Tag();
                newTag.bookmarkService = mockBookmarkService;

                context.checking(new Expectations() {
                    {
                        oneOf(mockContainer).newTransientInstance(Tag.class);
                        will(returnValue(newTag));

                        oneOf(mockContainer).persist(newTag);
                    }
                });

                tag = tags.tagFor(customer, null, "theme", "darkTheme");
                Assert.assertThat(tag, is(not(nullValue())));
                Assert.assertThat(tag.getTaggedObject(), is(customer));
                Assert.assertThat(tag.getKey(), is("theme"));
                Assert.assertThat(tag.getValue(), is("darkTheme"));
            }
        }
    }
}
