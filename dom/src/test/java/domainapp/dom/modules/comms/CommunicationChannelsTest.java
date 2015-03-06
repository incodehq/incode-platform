/**
 *  Licensed to the Apache Software Foundation (ASF) under one or more
 *  contributor license agreements.  See the NOTICE file distributed with
 *  this work for additional information regarding copyright ownership.
 *  The ASF licenses this file to You under the Apache License, Version 2.0
 *  (the "License"); you may not use this file except in compliance with
 *  the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package domainapp.dom.modules.comms;

import java.util.List;
import com.google.common.collect.Lists;
import org.jmock.Expectations;
import org.jmock.Sequence;
import org.jmock.auto.Mock;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.apache.isis.applib.DomainObjectContainer;
import org.apache.isis.core.unittestsupport.jmocking.JUnitRuleMockery2;
import org.apache.isis.core.unittestsupport.jmocking.JUnitRuleMockery2.Mode;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class CommunicationChannelsTest {

    @Rule
    public JUnitRuleMockery2 context = JUnitRuleMockery2.createFor(Mode.INTERFACES_AND_CLASSES);

    @Mock
    DomainObjectContainer mockContainer;

    @Mock
    CommunicationChannelOwner mockCommunicationChannelOwner;

    CommunicationChannels communicationChannels;

    @Before
    public void setUp() throws Exception {
        communicationChannels = new CommunicationChannels();
        communicationChannels.container = mockContainer;
    }

    public static class Create extends CommunicationChannelsTest {

        @Test
        public void happyCase() throws Exception {

            // given
            final CommunicationChannel communicationChannel = new CommunicationChannel();

            final Sequence seq = context.sequence("create");
            context.checking(new Expectations() {
                {
                    oneOf(mockContainer).newTransientInstance(CommunicationChannel.class);
                    inSequence(seq);
                    will(returnValue(communicationChannel));

                    oneOf(mockContainer).persistIfNotAlready(communicationChannel);
                    inSequence(seq);
                }
            });

            // when
            final CommunicationChannel obj = communicationChannels.create("Foobar", mockCommunicationChannelOwner);

            // then
            assertThat(obj, is(communicationChannel));
            assertThat(obj.getDetails(), is("Foobar"));
        }

    }

    public static class ListAll extends CommunicationChannelsTest {

        @Test
        public void happyCase() throws Exception {

            // given
            final List<CommunicationChannel> all = Lists.newArrayList();

            context.checking(new Expectations() {
                {
                    oneOf(mockContainer).allInstances(CommunicationChannel.class);
                    will(returnValue(all));
                }
            });

            // when
            final List<CommunicationChannel> list = communicationChannels.listAll();

            // then
            assertThat(list, is(all));
        }
    }
}
