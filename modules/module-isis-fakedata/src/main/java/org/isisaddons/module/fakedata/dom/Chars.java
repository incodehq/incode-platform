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

public class Chars extends AbstractRandomValueGenerator{

    public Chars(final FakeDataService fakeDataService) {
        super(fakeDataService);
    }

    @Programmatic
    public char upper() {
        return anyOf("ABCDEFGHIJKLMNOPQRSTUVWXYZ");
    }

    @Programmatic
    public char lower() {
        return anyOf("abcdefghijklmonpqrstuvwxyz");
    }

    @Programmatic
    public char any() {
        final int any = fake.shorts().any();
        final int i = any - Short.MIN_VALUE;
        return (char) i;
    }

    @Programmatic
    public char digit() {
        return anyOf("0123456789");
    }


    private char anyOf(final String s) {
        final char[] chars = s.toCharArray();
        return fake.collections().anyOf(chars);
    }

}
