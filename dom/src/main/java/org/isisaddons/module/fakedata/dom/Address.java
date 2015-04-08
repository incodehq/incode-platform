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

public class Address extends AbstractRandomValueGenerator {

    com.github.javafaker.Address javaFakerAddress;

    Address(final FakeDataService fakeDataService) {
        super(fakeDataService);
        javaFakerAddress = new com.github.javafaker.Address(
                fakeDataService.name().javaFakerName,
                fakeDataService.fakeValuesService,
                fakeDataService.randomService);
    }

    @Programmatic
    public String streetName() {
        return javaFakerAddress.streetName();
    }

    @Programmatic
    public String streetAddressNumber() {
        return javaFakerAddress.streetAddressNumber();
    }

    @Programmatic
    public String streetAddress() {
        return javaFakerAddress.streetAddress(false);
    }

    @Programmatic
    public String streetAddressWithSecondary() {
        return javaFakerAddress.streetAddress(true);
    }

    @Programmatic
    public String usZipCode() {
        return javaFakerAddress.zipCode();
    }

    @Programmatic
    public String streetSuffix() {
        return javaFakerAddress.streetSuffix();
    }

    @Programmatic
    public String citySuffix() {
        return javaFakerAddress.citySuffix();
    }

    @Programmatic
    public String cityPrefix() {
        return javaFakerAddress.cityPrefix();
    }

    @Programmatic
    public String city() {
        return cityPrefix() + " " + fakeDataService.name().firstName() + " " + citySuffix();
    }

    @Programmatic
    public String usStateAbbr() {
        return javaFakerAddress.stateAbbr();
    }

    @Programmatic
    public String country() {
        return javaFakerAddress.country();
    }

    @Programmatic
    public String latitude() {
        return javaFakerAddress.latitude();
    }

    @Programmatic
    public String longitude() {
        return javaFakerAddress.longitude();
    }

}
