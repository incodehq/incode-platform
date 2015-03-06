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
import domainapp.dom.modules.party.Party;
import domainapp.fixture.scenarios.RecreateParties;
import domainapp.integtests.tests.PolyAppIntegTest;

import java.sql.SQLIntegrityConstraintViolationException;
import java.util.List;
import javax.inject.Inject;
import com.google.common.base.Throwables;
import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.apache.isis.applib.fixturescripts.FixtureScripts;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class CommunicationChannelsIntegTest extends PolyAppIntegTest {

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Inject
    FixtureScripts fixtureScripts;
    @Inject
    CommunicationChannels communicationChannels;

    public static class Create extends CommunicationChannelsIntegTest {

        @Test
        public void happyCase() throws Exception {

            // given
            RecreateParties fs = new RecreateParties();
            fixtureScripts.runFixtureScript(fs, null);
            nextTransaction();

            final Party party = fs.getParties().get(0);

            // when
            wrap(communicationChannels).add("0207 123 4567", party);

            // then
            final List<CommunicationChannel> all = wrap(communicationChannels).listAll();
            assertThat(all.size(), is(1));
        }

        @Test
        public void whenAlreadyExists() throws Exception {

            // given
            RecreateParties fs = new RecreateParties();
            fixtureScripts.runFixtureScript(fs, null);
            nextTransaction();

            final Party party = fs.getParties().get(0);

            wrap(communicationChannels).add("0207 123 4567", party);
            nextTransaction();

            // then
            expectedException.expectCause(causalChainContains(SQLIntegrityConstraintViolationException.class));

            // when
            wrap(communicationChannels).add("0207 123 4567", party);
            nextTransaction();
        }

        private static Matcher<? extends Throwable> causalChainContains(final Class<?> cls) {
            return new TypeSafeMatcher<Throwable>() {
                @Override
                protected boolean matchesSafely(Throwable item) {
                    final List<Throwable> causalChain = Throwables.getCausalChain(item);
                    for (Throwable throwable : causalChain) {
                        if(cls.isAssignableFrom(throwable.getClass())){
                            return true;
                        }
                    }
                    return false;
                }

                @Override
                public void describeTo(Description description) {
                    description.appendText("exception with causal chain containing " + cls.getSimpleName());
                }
            };
        }
    }

}