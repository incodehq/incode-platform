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

import java.util.Locale;
import java.util.Random;
import javax.annotation.PostConstruct;
import javax.inject.Inject;
import com.github.javafaker.Faker;
import com.github.javafaker.service.FakeValuesService;
import com.github.javafaker.service.RandomService;
import org.apache.commons.lang.math.RandomUtils;
import org.apache.isis.applib.DomainObjectContainer;
import org.apache.isis.applib.annotation.DomainService;
import org.apache.isis.applib.annotation.NatureOfService;
import org.apache.isis.applib.annotation.Programmatic;
import org.apache.isis.applib.services.clock.ClockService;

@DomainService(
        nature = NatureOfService.DOMAIN
)
public class FakeDataService {

    private Faker javaFaker;

    Random random;
    RandomService randomService;
    FakeValuesService fakeValuesService;

    @Programmatic
    @PostConstruct
    public void init() {

        random = RandomUtils.JVM_RANDOM;
        javaFaker = new Faker(random);

        randomService = new RandomService(random);
        fakeValuesService = new FakeValuesService(Locale.ENGLISH, randomService);

        // wrappers for the javafaker subclasses
        this.name = new Name(this);
        this.comms = new Comms(this);
        this.lorem = new Lorem(this);
        this.address = new Address(this);
        this.creditCard = new CreditCard(fakeValuesService);
        this.book = new Book(this);

        this.strings = new Strings(this);
        this.bytes = new Bytes(this);
        this.shorts = new Shorts(this);
        this.integers = new Integers(this);
        this.longs = new Longs(this);
        this.floats = new Floats(this);
        this.doubles = new Doubles(this);
        this.chars = new Chars(this);
        this.booleans = new Booleans(this);

        this.collections = new Collections(this);
        this.localDates = new LocalDates(this);
        this.periods = new Periods(this);
    }

    private Name name;
    private Comms comms;
    private Lorem lorem;
    private Address address;
    private CreditCard creditCard;
    private Book book;

    private Strings strings;
    private Bytes bytes;
    private Shorts shorts;
    private Integers integers;
    private Longs longs;
    private Floats floats;
    private Doubles doubles;
    private Chars chars;
    private Booleans booleans;
    private Collections collections;
    private LocalDates localDates;
    private Periods periods;


    /**
     * Access to the full API of the underlying javafaker library.
     */
    @Programmatic
    public Faker javaFaker() { return javaFaker; }

    // //////////////////////////////////////

    @Programmatic
    public Name name() {
        return name;
    }

    @Programmatic
    public Comms comms() {
        return comms;
    }

    @Programmatic
    public Lorem lorem() {
        return lorem;
    }

    @Programmatic
    public Address address() {
        return address;
    }

    @Programmatic
    public CreditCard creditCard() {
        return creditCard;
    }

    @Programmatic
    public Book book() {
        return book;
    }

    // //////////////////////////////////////

    @Programmatic
    public Bytes bytes() {
        return bytes;
    }

    @Programmatic
    public Shorts shorts() {
        return shorts;
    }

    @Programmatic
    public Integers ints() {
        return integers;
    }

    @Programmatic
    public Longs longs() {
        return longs;
    }

    @Programmatic
    public Floats floats() {
        return floats;
    }

    @Programmatic
    public Doubles doubles() {
        return doubles;
    }

    @Programmatic
    public Chars chars() {
        return chars;
    }

    @Programmatic
    public Booleans booleans() {
        return booleans;
    }

    @Programmatic
    public Strings strings() {
        return strings;
    }

    // //////////////////////////////////////

    @Programmatic
    public Collections collections() {
        return collections;
    }

    @Programmatic
    public LocalDates localDates() {
        return localDates;
    }

    @Programmatic
    public Periods periods() {
        return periods;
    }

    // //////////////////////////////////////

    public class CreditCard {
        com.github.javafaker.Business javaFakerBusiness;
        CreditCard(final FakeValuesService fakeValuesService) {
            javaFakerBusiness = new com.github.javafaker.Business(fakeValuesService);
        }

        @Programmatic
        public String number() {
            return fakeValuesService.fetchString("business.credit_card_numbers");
        }

        @Programmatic
        public String type() {
            return fakeValuesService.fetchString("business.credit_card_types");
        }

    }


    // //////////////////////////////////////

    @Inject
    ClockService clockService;

    @Inject
    DomainObjectContainer container;


}