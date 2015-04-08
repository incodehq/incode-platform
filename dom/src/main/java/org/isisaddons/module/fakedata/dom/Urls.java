/*
 *  Copyright 2015 Dan Haywood
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
package org.isisaddons.module.fakedata.dom;

import java.net.MalformedURLException;
import java.net.URL;
import org.apache.isis.applib.annotation.Programmatic;

public class Urls extends AbstractRandomValueGenerator{

    public Urls(final FakeDataService fakeDataService) {
        super(fakeDataService);
    }

    @Programmatic
    public URL any() {
        try {
            final String protocol = fake.booleans().coinFlip() ? "http" : "https:";
            final String url = fake.comms().url();
            return new URL(String.format("%s://%s", protocol, url));
        } catch (MalformedURLException e) {
            // not expected
            throw new RuntimeException(e);
        }
    }
}
