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
package domainapp.integtests.tests.modules.comms;

import domainapp.dom.modules.comms.CommunicationChannel;
import domainapp.dom.modules.comms.CommunicationChannels;
import domainapp.dom.modules.fixedasset.FixedAsset;
import domainapp.fixture.scenarios.RecreateFixedAssets;
import domainapp.integtests.tests.PolyAppIntegTest;

import java.util.List;
import javax.inject.Inject;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.apache.isis.applib.fixturescripts.FixtureScripts;
import org.apache.isis.applib.services.wrapper.DisabledException;
import org.apache.isis.applib.services.wrapper.InvalidException;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.assertThat;

public class FixedAssetIntegTest extends PolyAppIntegTest {

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Inject
    FixtureScripts fixtureScripts;
    @Inject
    CommunicationChannels communicationChannelsMenu;

    RecreateFixedAssets fs;
    FixedAsset fixedAsset;

    @Before
    public void setUp() throws Exception {

        fs = new RecreateFixedAssets();
        fixtureScripts.runFixtureScript(fs, null);
        nextTransaction();

        fixedAsset = fs.getFixedAssets().get(0);
    }

    public static class CreateCommunicationChannel extends FixedAssetIntegTest {


        @Test
        public void happyCase() throws Exception {

            // when
            wrap(fixedAsset).createCommunicationChannel("0207 123 4567");
            nextTransaction();

            // then
            final List<CommunicationChannel> all = communicationChannelsMenu.listAll();
            assertThat(all.size(), is(1));

            final CommunicationChannel communicationChannel = fixedAsset.getCommunicationChannel();
            assertThat(communicationChannel.getDetails(), is("0207 123 4567"));
        }

        @Test
        public void whenAlreadyExists() throws Exception {

            // given
            wrap(fixedAsset).createCommunicationChannel("0207 123 4567");
            nextTransaction();

            // then expect
            expectedException.expect(DisabledException.class);
            expectedException.expectMessage("Already owns a communication channel");

            // when
            wrap(fixedAsset).createCommunicationChannel("0207 123 4567");
            nextTransaction();
        }

        @Test
        public void whenFailsValidation() throws Exception {

            // then expect
            expectedException.expect(InvalidException.class);
            expectedException.expectMessage("No exclamation marks allowed in details");

            // when
            wrap(fixedAsset).createCommunicationChannel("&!%$");
        }
    }

    public static class DeleteCommunicationChannel extends FixedAssetIntegTest {

        @Test
        public void whenExists() throws Exception {

            // given
            wrap(fixedAsset).createCommunicationChannel("0207 123 4567");
            nextTransaction();

            // when
            wrap(fixedAsset).deleteCommunicationChannel();
            nextTransaction();

            // then
            assertThat(wrap(fixedAsset).getCommunicationChannel(), is(nullValue()));
            assertThat(wrap(communicationChannelsMenu).listAll().size(), is(0));
        }

        @Test
        public void whenDoesNotExist() throws Exception {

            // then expect
            expectedException.expect(DisabledException.class);
            expectedException.expectMessage("Does not own a communication channel");

            // when
            wrap(fixedAsset).deleteCommunicationChannel();
        }
    }

}