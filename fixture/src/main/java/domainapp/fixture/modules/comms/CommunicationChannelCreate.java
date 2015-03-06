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

package domainapp.fixture.modules.comms;

import domainapp.dom.modules.comms.CommunicationChannel;
import domainapp.dom.modules.comms.CommunicationChannelOwner;
import domainapp.dom.modules.comms.CommunicationChannels;

import org.apache.isis.applib.annotation.MemberOrder;
import org.apache.isis.applib.fixturescripts.FixtureScript;

public class CommunicationChannelCreate extends FixtureScript {

    //region > details (input)
    private String details;
    public String getDetails() {
        return details;
    }

    public CommunicationChannelCreate setDetails(final String details) {
        this.details = details;
        return this;
    }
    //endregion

    //region > owner (input)
    private CommunicationChannelOwner owner;

    @MemberOrder(sequence = "1")
    public CommunicationChannelOwner getOwner() {
        return owner;
    }

    public void setOwner(final CommunicationChannelOwner owner) {
        this.owner = owner;
    }
    //endregion


    //region > communicationChannel (output)
    private CommunicationChannel communicationChannel;

    /**
     * The created communication channel (output).
     */
    public CommunicationChannel getCommunicationChannel() {
        return communicationChannel;
    }
    //endregion

    @Override
    protected void execute(final ExecutionContext ec) {

        final String details = checkParam("details", ec, String.class);
        final CommunicationChannelOwner owner = checkParam("owner", ec, CommunicationChannelOwner.class);

        this.communicationChannel = simplecommunicationChannels.create(details, owner);

        // also make available to UI
        ec.addResult(this, communicationChannel);
    }

    @javax.inject.Inject
    private CommunicationChannels simplecommunicationChannels;

}
