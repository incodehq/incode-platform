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

import org.apache.isis.applib.annotation.Programmatic;

public class Name extends AbstractRandomValueGenerator {
    com.github.javafaker.Name javaFakerName;

    Name(final FakeDataService fakeDataService) {
        super(fakeDataService);
        javaFakerName = new com.github.javafaker.Name(fakeDataService.fakeValuesService);
    }

    @Programmatic
    public String fullName() {
        return javaFakerName.name();
    }

    @Programmatic
    public String firstName() {
        return javaFakerName.firstName();
    }

    @Programmatic
    public String lastName() {
        return javaFakerName.lastName();
    }

    @Programmatic
    public String prefix() {
        return javaFakerName.prefix();
    }

    @Programmatic
    public String suffix() {
        return javaFakerName.suffix();
    }
}
