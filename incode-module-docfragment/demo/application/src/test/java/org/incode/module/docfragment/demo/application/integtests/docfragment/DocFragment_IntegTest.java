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
package org.incode.module.docfragment.demo.application.integtests.docfragment;

import javax.inject.Inject;

import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Test;

import org.apache.isis.applib.fixturescripts.FixtureScripts;
import org.apache.isis.applib.services.title.TitleService;
import org.apache.isis.applib.services.wrapper.DisabledException;
import org.apache.isis.applib.services.wrapper.WrapperFactory;
import org.apache.isis.applib.services.xactn.TransactionService;
import org.apache.isis.core.metamodel.services.jdosupport.Persistable_datanucleusIdLong;
import org.apache.isis.core.metamodel.services.jdosupport.Persistable_datanucleusVersionLong;

import org.incode.module.docfragment.demo.application.integtests.DocFragmentModuleIntegTestAbstract;
import org.incode.module.docfragment.dom.impl.DocFragment;
import org.incode.module.docfragment.fixture.scenario.DocFragmentData;
import org.incode.module.docfragment.fixture.teardown.DocFragmentModuleTearDown;

import static org.assertj.core.api.Assertions.assertThat;

public class DocFragment_IntegTest extends DocFragmentModuleIntegTestAbstract {

    @Inject
    FixtureScripts fixtureScripts;
    @Inject
    TransactionService transactionService;

    DocFragment domainObject;

    @Before
    public void setUp() throws Exception {
        // given
        fixtureScripts.runFixtureScript(new DocFragmentModuleTearDown(), null);
        final DocFragmentData.PersistScript fs = new DocFragmentData.PersistScript().setNumber(1);
        fixtureScripts.runFixtureScript(fs, null);
        transactionService.nextTransaction();

        domainObject = fs.getObjects().get(0);

        assertThat(domainObject).isNotNull();
    }


    public static class ObjectType extends DocFragment_IntegTest {

        @Test
        public void accessible() throws Exception {
            // when
            final String objectType = wrap(domainObject).getObjectType();

            // then
            assertThat(objectType).isEqualTo(domainObject.getObjectType());
        }

        @Test
        public void not_editable() throws Exception {
            // expect
            expectedExceptions.expect(DisabledException.class);

            // when
            wrap(domainObject).setObjectType("new objectType");
        }
    }

    public static class Name extends DocFragment_IntegTest {

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


    public static class AtPath extends DocFragment_IntegTest {

        @Test
        public void accessible() throws Exception {
            // when
            final String atPath = wrap(domainObject).getAtPath();

            // then
            assertThat(atPath).isEqualTo(domainObject.getAtPath());
        }

        @Test
        public void not_editable() throws Exception {
            // expect
            expectedExceptions.expect(DisabledException.class);

            // when
            wrap(domainObject).setAtPath("new atPath");
        }

    }

    public static class TemplateText extends DocFragment_IntegTest {

        @Test
        public void accessible() throws Exception {
            // when
            final String templateText = wrap(domainObject).getTemplateText();

            // then
            assertThat(templateText).isEqualTo(domainObject.getTemplateText());
        }

        @Test
        public void is_editable() throws Exception {

            // given
            final String newText = "new templateText";

            // when
            wrap(domainObject).setTemplateText(newText);

            // then
            final String templateText = wrap(domainObject).getTemplateText();
            assertThat(templateText).isEqualTo(newText);

        }


    }

    public static class ChangeTemplateText extends DocFragment_IntegTest {

        @Test
        public void happyCase() throws Exception {
            // given
            final String originalText = domainObject.getTemplateText();

            // then
            Assertions.assertThat(domainObject.default0ChangeTemplateText()).isEqualTo(originalText);

            // when
            String updatedText = "CHANGED";
            wrapperFactory.wrap(domainObject).changeTemplateText(updatedText);

            // then
            Assertions.assertThat(domainObject.getTemplateText()).isEqualTo(updatedText);
        }
    }

    @Inject
    WrapperFactory wrapperFactory;






    public static class Title extends DocFragment_IntegTest {

        @Inject
        TitleService titleService;

        @Test
        public void interpolatesName() throws Exception {

            // given
            final String objectType = wrap(domainObject).getObjectType();
            final String name = wrap(domainObject).getName();
            final String atPath = wrap(domainObject).getAtPath();

            // when
            final String title = titleService.titleOf(domainObject);

            // then
            assertThat(title).isEqualTo(objectType + ": " + name + " @ " + atPath);
        }
    }

    public static class DataNucleusId extends DocFragment_IntegTest {

        @Test
        public void should_be_populated() throws Exception {
            // when
            final Long id = mixin(Persistable_datanucleusIdLong.class, domainObject).exec();

            // then
            assertThat(id).isGreaterThanOrEqualTo(0);
        }
    }

    public static class DataNucleusVersionLong extends DocFragment_IntegTest {

        @Test
        public void should_be_populated() throws Exception {
            // when
            final Long version = mixin(Persistable_datanucleusVersionLong.class, domainObject).exec();
            // then
            assertThat(version).isNotNull();
        }
    }


}