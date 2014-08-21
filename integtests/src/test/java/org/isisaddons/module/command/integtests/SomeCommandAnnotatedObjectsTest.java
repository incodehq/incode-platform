/*
 *  Licensed to the Apache Software Foundation (ASF) under one
 *  or more contributor license agreements.  See the NOTICE file
 *  distributed with this work for additional information
 *  regarding copyright ownership.  The ASF licenses this file
 *  to you under the Apache License, Version 2.0 (the
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
package org.isisaddons.module.command.integtests;

import java.util.List;
import javax.inject.Inject;
import org.isisaddons.module.command.fixture.dom.SomeCommandAnnotatedObject;
import org.isisaddons.module.command.fixture.dom.SomeCommandAnnotatedObjects;
import org.isisaddons.module.command.fixture.scripts.SomeCommandAnnotatedObjectsFixture;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.apache.isis.applib.services.bookmark.Bookmark;
import org.apache.isis.applib.services.bookmark.BookmarkService;
import org.apache.isis.objectstore.jdo.applib.service.background.BackgroundCommandServiceJdoRepository;
import org.apache.isis.objectstore.jdo.applib.service.command.CommandJdo;
import org.apache.isis.objectstore.jdo.applib.service.command.CommandServiceJdoRepository;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.not;
import static org.junit.Assert.assertThat;

public class SomeCommandAnnotatedObjectsTest extends CommandModuleIntegTest {


    @Before
    public void setUpData() throws Exception {
        scenarioExecution().install(new SomeCommandAnnotatedObjectsFixture());
    }

    @Inject
    private SomeCommandAnnotatedObjects someCommandAnnotatedObjects;

    @Inject
    CommandServiceJdoRepository commandServiceJdoRepository;

    @Inject
    BackgroundCommandServiceJdoRepository backgroundCommandServiceJdoRepository;

    @Inject
    BookmarkService bookmarkService;

    SomeCommandAnnotatedObject entity;
    Bookmark bookmark;

    @Before
    public void setUp() throws Exception {
        final List<SomeCommandAnnotatedObject> all = wrap(someCommandAnnotatedObjects).listAll();
        assertThat(all.size(), is(3));

        entity = wrap(all.get(0));
        assertThat(entity.getName(), is("Foo"));

        bookmark = bookmarkService.bookmarkFor(entity);

        final List<CommandJdo> commands = commandServiceJdoRepository.findByTargetAndFromAndTo(bookmark, null, null);
        assertThat(commands, is(empty()));
    }

    public static class ChangeName extends SomeCommandAnnotatedObjectsTest {

        @Ignore("currently not possible to test this using the wrapper, because the Command's executor is left as OTHER rather than USER")
        @Test
        public void happyCase() throws Exception {
            // when
            entity.changeName("Fizz");
            nextTransaction();

            // then
            final List<CommandJdo> commands = commandServiceJdoRepository.findByTargetAndFromAndTo(bookmark, null, null);
            assertThat(commands, is(not(empty())));
        }
    }

    public static class ChangeNameExplicitlyInBackground extends SomeCommandAnnotatedObjectsTest {

        @Test
        public void happyCase() throws Exception {
            // when
            entity.changeNameExplicitlyInBackground("Foo");
            nextTransaction();

            // then
            final List<CommandJdo> commands = backgroundCommandServiceJdoRepository.findBackgroundCommandsNotYetStarted();
            assertThat(commands, is(not(empty())));
        }
    }

}