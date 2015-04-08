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

import org.apache.commons.lang.math.RandomUtils;
import org.apache.isis.applib.annotation.Programmatic;

public class Integers extends AbstractRandomValueGenerator{

    public Integers(final FakeDataService fakeDataService) {
        super(fakeDataService);
    }

    @Programmatic
    public int upTo(final int upTo) {
        return fake.randomService.nextInt(upTo);
    }

    @Programmatic
    public int between(final int min, final int max) {
        return min + fake.randomService.nextInt(max - min);
    }

    @Programmatic
    public int any() {
        return RandomUtils.nextInt();
    }
}
