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
package org.incode.module.docfragment.integtests.tests;

import java.sql.Timestamp;

import javax.inject.Inject;

import org.junit.Before;
import org.junit.Test;

import org.apache.isis.applib.fixturescripts.FixtureScripts;
import org.apache.isis.applib.services.title.TitleService;
import org.apache.isis.applib.services.wrapper.DisabledException;
import org.apache.isis.applib.services.wrapper.InvalidException;
import org.apache.isis.applib.services.xactn.TransactionService;
import org.apache.isis.core.metamodel.services.jdosupport.Persistable_datanucleusIdLong;
import org.apache.isis.core.metamodel.services.jdosupport.Persistable_datanucleusVersionTimestamp;

import org.incode.module.docfragment.dom.impl.DocFragmentObject;
import org.incode.module.docfragment.fixture.scenario.CreateDocFragmentObjects;
import org.incode.module.docfragment.fixture.teardown.DocFragmentModuleTearDown;
import org.incode.module.docfragment.integtests.DocFragmentModuleIntegTestAbstract;

import static org.assertj.core.api.Assertions.assertThat;

public class DocFragmentObject_IntegTest extends DocFragmentModuleIntegTestAbstract {

    @Inject
    FixtureScripts fixtureScripts;
    @Inject
    TransactionService transactionService;

    DocFragmentObject domainObject;

    @Before
    public void setUp() throws Exception {
        // given
        fixtureScripts.runFixtureScript(new DocFragmentModuleTearDown(), null);
        CreateDocFragmentObjects fs = new CreateDocFragmentObjects().setNumber(1);
        fixtureScripts.runFixtureScript(fs, null);
        transactionService.nextTransaction();

        domainObject = fs.getDocFragmentObjects().get(0);

        assertThat(domainObject).isNotNull();
    }

    public static class Name extends DocFragmentObject_IntegTest {

        @Test
        public void accessible() throws Exception {
            // when
            final String name = wrap(domainObject).getName();

            // then
            assertThat(name).isEqualTo(domainObject.getName());
        }

        @Test
        public void not_editable() throws Exception {
            // expect
            expectedExceptions.expect(DisabledException.class);

            // when
            wrap(domainObject).setName("new name");
        }

    }

    public static class UpdateName extends DocFragmentObject_IntegTest {

        @Test
        public void can_be_updated_directly() throws Exception {

            // when
            wrap(domainObject).updateName("new name");
            transactionService.nextTransaction();

            // then
            assertThat(wrap(domainObject).getName()).isEqualTo("new name");
        }

        @Test
        public void failsValidation() throws Exception {

            // expect
            expectedExceptions.expect(InvalidException.class);
            expectedExceptions.expectMessage("Exclamation mark is not allowed");

            // when
            wrap(domainObject).updateName("new name!");
        }
    }


    public static class Title extends DocFragmentObject_IntegTest {

        @Inject
        TitleService titleService;

        @Test
        public void interpolatesName() throws Exception {

            // given
            final String name = wrap(domainObject).getName();

            // when
            final String title = titleService.titleOf(domainObject);

            // then
            assertThat(title).isEqualTo("Object: " + name);
        }
    }

    public static class DataNucleusId extends DocFragmentObject_IntegTest {

        @Test
        public void should_be_populated() throws Exception {
            // when
            final Long id = mixin(Persistable_datanucleusIdLong.class, domainObject).$$();

            // then
            assertThat(id).isGreaterThanOrEqualTo(0);
        }
    }

    public static class DataNucleusVersionTimestamp extends DocFragmentObject_IntegTest {

        @Test
        public void should_be_populated() throws Exception {
            // when
            final Timestamp timestamp = mixin(Persistable_datanucleusVersionTimestamp.class, domainObject).$$();
            // then
            assertThat(timestamp).isNotNull();
        }
    }


}