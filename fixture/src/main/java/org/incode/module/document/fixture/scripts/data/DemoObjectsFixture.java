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

import org.apache.isis.applib.fixturescripts.DiscoverableFixtureScript;

import org.isisaddons.module.fakedata.dom.FakeDataService;

import org.incode.module.document.fixture.dom.demo.DemoObject;
import org.incode.module.document.fixture.dom.demo.DemoObjectMenu;
import org.incode.module.document.fixture.seed.DocumentTypeAndTemplatesApplicableForDemoObjectFixture;

import lombok.Getter;

public class DemoObjectsFixture extends DiscoverableFixtureScript {

    @javax.inject.Inject
    DemoObjectMenu demoObjectMenu;

    @javax.inject.Inject
    FakeDataService fakeDataService;

    @Getter
    private Integer number ;
    private DocumentTypeAndTemplatesApplicableForDemoObjectFixture documentTypeAndTemplatesApplicableForDemoObjectFixture;

    public DemoObjectsFixture setNumber(final Integer number) {
        this.number = number;
        return this;
    }

    @Getter
    private List<DemoObject> demoObjects = Lists.newArrayList();


    //region > constructor
    public DemoObjectsFixture() {
    }
    //endregion



    @Override
    protected void execute(final ExecutionContext ec) {

        defaultParam("number", ec, 3);
        if(getNumber() < 1 || getNumber() > 5) {
            // there are 5 sample PDFs
            throw new IllegalArgumentException("number of demo objects to create must be within [1,5]");
        }

        for (int i = 0; i < getNumber(); i++) {
            final DemoObject demoObject = create(i, ec);
            getDemoObjects().add(demoObject);
        }
    }

    private DemoObject create(final int n, final ExecutionContext ec) {
        final String name = fakeDataService.name().firstName();
        final String url = "http://www.pdfpdf.com/samples/Sample" + (n+1) + ".PDF";

        final DemoObject demoObject = wrap(demoObjectMenu).create(name);
        wrap(demoObject).setUrl(url);

        return ec.addResult(this, demoObject);
    }

}
