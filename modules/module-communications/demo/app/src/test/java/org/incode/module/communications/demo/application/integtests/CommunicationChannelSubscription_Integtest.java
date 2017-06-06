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
package org.incode.module.communications.demo.application.integtests;

import javax.inject.Inject;

import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Test;

import org.apache.isis.applib.fixturescripts.FixtureScripts;
import org.apache.isis.applib.services.wrapper.WrapperFactory;
import org.apache.isis.applib.services.xactn.TransactionService;

import org.incode.module.communications.demo.module.dom.impl.customers.DemoCustomer;
import org.incode.module.communications.demo.module.dom.impl.customers.DemoCustomerMenu;
import org.incode.module.communications.demo.module.fixture.demodata.DemoCustomersFixture;
import org.incode.module.communications.demo.module.fixture.scenario.DemoModuleFixture;
import org.incode.module.communications.dom.impl.commchannel.CommunicationChannelOwnerLinkRepository;
import org.incode.module.communications.dom.impl.commchannel.CommunicationChannelRepository;
import org.incode.module.communications.dom.impl.commchannel.CommunicationChannelType;
import org.incode.module.communications.dom.impl.commchannel.EmailAddress;
import org.incode.module.communications.dom.impl.commchannel.PostalAddress;
import org.incode.module.communications.dom.impl.comms.Communication;
import org.incode.module.communications.dom.impl.comms.CommunicationRepository;

public class CommunicationChannelSubscription_Integtest extends DemoAppIntegTestAbstract {

    @Inject CommunicationChannelRepository communicationChannelRepository;

    @Inject CommunicationRepository communicationRepository;

    @Inject
    FixtureScripts fixtureScripts;

    @Inject
    TransactionService transactionService;

    @Inject
    DemoCustomerMenu customerMenu;

    @Inject
    CommunicationChannelOwnerLinkRepository linkRepository;

    @Inject WrapperFactory wrapperFactory;

    @Before
    public void setUp() throws Exception {

        // given
        fixtureScripts.runFixtureScript(new DemoModuleFixture(), null);
        transactionService.nextTransaction();

        // and so given customer with an email
        final DemoCustomer fred = customerMenu.findByName(DemoCustomersFixture.FRED_HAS_EMAIL_AND_PHONE).get(0);

        final EmailAddress fredEmail = (EmailAddress) linkRepository
                .findByOwnerAndCommunicationChannelType(fred, CommunicationChannelType.EMAIL_ADDRESS)
                .get(0)
                .getCommunicationChannel();

    }

    @Test
    public void replacement_provided() throws Exception {
        // Given
        final DemoCustomer owner = customerMenu.findByName(DemoCustomersFixture.FRED_HAS_EMAIL_AND_PHONE).get(0);
        final PostalAddress postalAddress = communicationChannelRepository.newPostal(
                owner,
                CommunicationChannelType.POSTAL_ADDRESS,
                "Some address",
                null,
                null,
                null,
                null,
                null,
                null);

        final PostalAddress replaceWith = communicationChannelRepository.newPostal(
                owner,
                CommunicationChannelType.POSTAL_ADDRESS,
                "Replacement Address",
                null,
                null,
                null,
                null,
                null,
                null);

        final Communication communication = communicationRepository.createPostal("Subject", "/", postalAddress);
        final int size = communicationChannelRepository.findByOwner(owner).size();

        // When
        wrapperFactory.wrap(postalAddress).remove(replaceWith);

        //Then
        Assertions.assertThat(communicationChannelRepository.findByOwner(owner).size()).isEqualTo(size-1);

    }

    @Test
    public void validate_fails_when_no_replacement_is_provided() throws Exception {
        // Given
        final DemoCustomer owner = customerMenu.findByName(DemoCustomersFixture.FRED_HAS_EMAIL_AND_PHONE).get(0);
        final PostalAddress postalAddress = communicationChannelRepository.newPostal(
                owner,
                CommunicationChannelType.POSTAL_ADDRESS,
                "Some address",
                null,
                null,
                null,
                null,
                null,
                null);


        final Communication communication = communicationRepository.createPostal("Subject", "/", postalAddress);

        // Expect
        expectedExceptions.expectMessage("Communication channel is being used");

        // When
        wrap(postalAddress).remove(null);

    }

}
