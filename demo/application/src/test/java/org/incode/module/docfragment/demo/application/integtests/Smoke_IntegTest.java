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
package org.incode.module.docfragment.demo.application.integtests;

import java.util.List;

import javax.inject.Inject;

import org.junit.Test;

import org.apache.isis.applib.fixturescripts.FixtureScripts;
import org.apache.isis.applib.services.xactn.TransactionService;

import org.incode.module.docfragment.demo.application.fixture.teardown.DemoAppTearDown;
import org.incode.module.docfragment.dom.impl.DocFragment;
import org.incode.module.docfragment.dom.menu.DocFragmentMenu;
import org.incode.module.docfragment.fixture.scenario.DocFragmentData;

import static org.assertj.core.api.Assertions.assertThat;

public class Smoke_IntegTest extends DemoAppIntegTestAbstract {

    @Inject
    FixtureScripts fixtureScripts;
    @Inject
    TransactionService transactionService;
    @Inject
    DocFragmentMenu menu;

    @Test
    public void create() throws Exception {

        // given
        DemoAppTearDown fs = new DemoAppTearDown();
        fixtureScripts.runFixtureScript(fs, null);
        transactionService.nextTransaction();


        // when
        List<DocFragment> all = wrap(menu).listAll();

        // then
        assertThat(all).isEmpty();



        // when
        final DocFragment hello_global = DocFragmentData.Customer_hello_GLOBAL.createWith(wrap(menu));
        transactionService.flushTransaction();

        // then
        all = wrap(menu).listAll();
        assertThat(all).hasSize(1);
        assertThat(all).contains(hello_global);



        // when
        final DocFragment hello_fra = DocFragmentData.Customer_hello_FRA.createWith(wrap(menu));
        transactionService.flushTransaction();

        // then
        all = wrap(menu).listAll();
        assertThat(all).hasSize(2);
        assertThat(all).contains(hello_global, hello_fra);



        // when
        wrap(hello_global).delete();
        transactionService.flushTransaction();


        all = wrap(menu).listAll();
        assertThat(all).hasSize(1);

    }

}

