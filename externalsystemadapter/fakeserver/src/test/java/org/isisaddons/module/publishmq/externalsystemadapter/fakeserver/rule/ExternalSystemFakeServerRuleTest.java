/**
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements. See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership. The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package org.isisaddons.module.publishmq.externalsystemadapter.fakeserver.rule;

import java.util.List;
import java.util.UUID;

import javax.xml.ws.BindingProvider;

import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runners.MethodSorters;

import org.isisaddons.module.publishmq.externalsystemadapter.demoobject.DemoObject;
import org.isisaddons.module.publishmq.externalsystemadapter.demoobject.DemoObjectService;
import org.isisaddons.module.publishmq.externalsystemadapter.demoobject.PostResponse;
import org.isisaddons.module.publishmq.externalsystemadapter.demoobject.Update;
import org.isisaddons.module.publishmq.externalsystemadapter.fakeserver.ExternalSystemFakeServer;
import org.isisaddons.module.publishmq.externalsystemadapter.wsdl.ExternalSystemWsdl;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ExternalSystemFakeServerRuleTest {

    @Rule
    public ExternalSystemFakeServerRule serverRule = new ExternalSystemFakeServerRule();

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    private ExternalSystemFakeServer externalSystemFakeServer;
    private DemoObject externalSystemContract;

    @Before
    public void setUp() throws Exception {

        final DemoObjectService externalSystemService =
                new DemoObjectService(ExternalSystemWsdl.getWsdl());

        externalSystemContract = externalSystemService.getDemoObjectOverSOAP();

        BindingProvider provider = (BindingProvider) externalSystemContract;
        provider.getRequestContext().put(
                BindingProvider.ENDPOINT_ADDRESS_PROPERTY,
                serverRule.getEndpointAddress()
        );

        externalSystemFakeServer = serverRule.getPublishedEndpoint();
    }

    @Test
    public void t01_posts() throws Exception {

        // given
        final Update update = new Update();
        update.setMessageId(UUID.randomUUID().toString());
        update.setName("Fred");
        update.setDescription("Freddie Mercury");

        // when
        PostResponse response = externalSystemContract.post(update);

        // then
        final List<Update> updates = externalSystemFakeServer.control().getUpdates();
        assertThat(updates.size(), is(1));

        assertThat(updates.get(0).getName(), is(update.getName()));
        assertThat(updates.get(0).getDescription(), is(update.getDescription()));

    }

    @Test
    public void t02_server_is_reused() throws Exception {

        // given
        final Update update = new Update();
        update.setMessageId(UUID.randomUUID().toString());
        update.setName("Brian");
        update.setDescription("Brian May");

        // when
        PostResponse response = externalSystemContract.post(update);

        // then
        final List<Update> updates = externalSystemFakeServer.control().getUpdates();
        assertThat(updates.size(), is(2));

        assertThat(updates.get(1).getName(), is(update.getName()));
        assertThat(updates.get(1).getDescription(), is(update.getDescription()));
    }

    @Test
    public void t03_can_clear() throws Exception {

        // given
        assertThat(externalSystemFakeServer.control().getUpdates().size(), is(2));

        // when
        externalSystemFakeServer.control().clear();

        // then
        assertThat(externalSystemFakeServer.control().getUpdates().size(), is(0));
    }

}
