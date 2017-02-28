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

import org.apache.isis.applib.fixturescripts.FixtureScripts;
import org.apache.isis.applib.services.xactn.TransactionService;

//import org.incode.module.communications.dom.impl.CommunicationsObject;
//import org.incode.module.communications.dom.impl.CommunicationsObjectMenu;

public class Smoke_IntegTest extends DemoAppIntegTestAbstract {

    @Inject
    FixtureScripts fixtureScripts;
    @Inject
    TransactionService transactionService;
//    @Inject
//    CommunicationsObjectMenu menu;

//    @Test
//    public void create() throws Exception {
//
//        // given
//        DemoAppTearDown fs = new DemoAppTearDown();
//        fixtureScripts.runFixtureScript(fs, null);
//        transactionService.nextTransaction();
//
//
//        // when
//        List<CommunicationsObject> all = wrap(menu).listAll();
//
//        // then
//        assertThat(all).isEmpty();
//
//
//
//        // when
//        final CommunicationsObject fred = wrap(menu).create("Fred");
//        transactionService.flushTransaction();
//
//        // then
//        all = wrap(menu).listAll();
//        assertThat(all).hasSize(1);
//        assertThat(all).contains(fred);
//
//
//
//        // when
//        final CommunicationsObject bill = wrap(menu).create("Bill");
//        transactionService.flushTransaction();
//
//        // then
//        all = wrap(menu).listAll();
//        assertThat(all).hasSize(2);
//        assertThat(all).contains(fred, bill);
//
//
//
//        // when
//        wrap(fred).updateName("Freddy");
//        transactionService.flushTransaction();
//
//        // then
//        assertThat(wrap(fred).getName()).isEqualTo("Freddy");
//
//
//
//        // when
//        wrap(fred).setNotes("These are some notes");
//
//        // then
//        assertThat(wrap(fred).getNotes()).isEqualTo("These are some notes");
//
//
//        // when
//        wrap(fred).delete();
//        transactionService.flushTransaction();
//
//
//        all = wrap(menu).listAll();
//        assertThat(all).hasSize(1);
//
//    }

}

