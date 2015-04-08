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

import com.github.javafaker.service.FakeValuesService;
import org.apache.isis.applib.annotation.Programmatic;

public class CreditCard extends AbstractRandomValueGenerator {

    final com.github.javafaker.Business javaFakerBusiness;

    CreditCard(final FakeDataService fakeDataService, final FakeValuesService fakeValuesService) {
        super(fakeDataService);
        javaFakerBusiness = new com.github.javafaker.Business(fakeValuesService);
    }

    @Programmatic
    public String number() {
        return fake.fakeValuesService.fetchString("business.credit_card_numbers");
    }

    @Programmatic
    public String type() {
        return fake.fakeValuesService.fetchString("business.credit_card_types");
    }

}
