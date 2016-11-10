/*
 *  Copyright 2014~2015 Dan Haywood
 *
 *  Licensed under the Apache License, Version 2.0 (the
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
package org.incode.module.document.fixture.scripts.data;

import java.util.List;

import com.google.common.collect.Lists;

import org.apache.isis.applib.fixturescripts.FixtureScript;

import org.isisaddons.module.fakedata.dom.FakeDataService;

import org.incode.module.document.fixture.dom.other.OtherObject;
import org.incode.module.document.fixture.dom.other.OtherObjectMenu;

import lombok.Getter;

public class OtherObjectsFixture extends FixtureScript {

    @javax.inject.Inject
    OtherObjectMenu otherObjectMenu;

    @javax.inject.Inject
    FakeDataService fakeDataService;

    @Getter
    private Integer number ;
    public OtherObjectsFixture setNumber(final Integer number) {
        this.number = number;
        return this;
    }

    @Getter
    private List<OtherObject> otherObjects = Lists.newArrayList();


    //region > constructor
    public OtherObjectsFixture() {
        withDiscoverability(Discoverability.DISCOVERABLE);
    }
    //endregion

    @Override
    protected void execute(final ExecutionContext ec) {

        defaultParam("number", ec, 3);
        if(getNumber() < 1 || getNumber() > 5) {
            // there are 5 sample PDFs
            throw new IllegalArgumentException("number of other objects to create must be within [1,5]");
        }

        for (int i = 0; i < getNumber(); i++) {
            final OtherObject otherObject = create(ec);
            getOtherObjects().add(otherObject);
        }
    }

    private OtherObject create(final ExecutionContext ec) {
        final String name = fakeDataService.name().firstName();

        final OtherObject otherObject = wrap(otherObjectMenu).create(name);

        return ec.addResult(this, otherObject);
    }

}
