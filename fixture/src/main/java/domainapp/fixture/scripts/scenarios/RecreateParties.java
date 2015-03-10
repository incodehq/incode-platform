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

package domainapp.fixture.scripts.scenarios;

import domainapp.fixture.dom.modules.party.Party;
import domainapp.fixture.scripts.modules.PolyAppTearDown;
import domainapp.fixture.scripts.modules.party.PartyCreate;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import com.google.common.collect.Lists;
import org.apache.isis.applib.fixturescripts.FixtureScript;

public class RecreateParties extends FixtureScript {

    public final List<String> PARTY_NAMES = Collections.unmodifiableList(Arrays.asList(
            "Mary", "Mungo", "Midge"));

    public RecreateParties() {
        withDiscoverability(Discoverability.DISCOVERABLE);
    }


    //region > teardown (input)

    private Boolean teardown;

    /**
     * Defaults to true
     */
    public Boolean getTeardown() {
        return teardown;
    }

    public RecreateParties setTeardown(final Boolean teardown) {
        this.teardown = teardown;
        return this;
    }
    //endregion

    //region > parties (output)
    private final List<Party> parties = Lists.newArrayList();

    /**
     * The {@link domainapp.fixture.dom.modules.party.Party}s created by this fixture (output).
     */
    public List<Party> getParties() {
        return parties;
    }
    //endregion

    @Override
    protected void execute(final ExecutionContext ec) {

        // defaults
        defaultParam("teardown", ec, true);

        //
        // execute
        //
        if(getTeardown()) {
            ec.executeChild(this, new PolyAppTearDown());
        }

        for (int i = 0; i < PARTY_NAMES.size(); i++) {
            final PartyCreate fs = new PartyCreate().setName(PARTY_NAMES.get(i));
            ec.executeChild(this, fs.getName(), fs);
            parties.add(fs.getParty());
        }

    }
}
