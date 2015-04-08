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

import java.util.List;
import org.apache.isis.applib.annotation.Programmatic;

public class Collections extends AbstractRandomValueGenerator{

    public Collections(final FakeDataService fakeDataService) {
        super(fakeDataService);
    }

    @Programmatic
    public <E extends Enum<E>> E randomEnum(final Class<E> enumType) {
        final E[] enumConstants = enumType.getEnumConstants();
        return enumConstants[fake.ints().upTo(enumConstants.length)];
    }

    @Programmatic
    public <T> T randomBounded(final Class<T> cls) {
        final List<T> list = fake.container.allInstances(cls);
        return anyOf(list);
    }

    @Programmatic
    public <T> T anyOf(final List<T> list) {
        final int randomIdx = fake.ints().upTo(list.size());
        return list.get(randomIdx);
    }

    @Programmatic
    public char anyOf(final char... elements) {
        final int randomIdx = fake.ints().upTo(elements.length);
        return elements[randomIdx];
    }

    @Programmatic
    public byte anyOf(final byte... elements) {
        final int randomIdx = fake.ints().upTo(elements.length);
        return elements[randomIdx];
    }

    @Programmatic
    public short anyOf(final short... elements) {
        final int randomIdx = fake.ints().upTo(elements.length);
        return elements[randomIdx];
    }

    @Programmatic
    public int anyOf(final int... elements) {
        final int randomIdx = fake.ints().upTo(elements.length);
        return elements[randomIdx];
    }

    @Programmatic
    public long anyOf(final long... elements) {
        final int randomIdx = fake.ints().upTo(elements.length);
        return elements[randomIdx];
    }

    @Programmatic
    public float anyOf(final float... elements) {
        final int randomIdx = fake.ints().upTo(elements.length);
        return elements[randomIdx];
    }

    @Programmatic
    public double anyOf(final double... elements) {
        final int randomIdx = fake.ints().upTo(elements.length);
        return elements[randomIdx];
    }

    @Programmatic
    public boolean anyOf(final boolean... elements) {
        final int randomIdx = fake.ints().upTo(elements.length);
        return elements[randomIdx];
    }

    @Programmatic
    public <T> T anyOf(final T... elements) {
        final int randomIdx = fake.ints().upTo(elements.length);
        return elements[randomIdx];
    }

}
