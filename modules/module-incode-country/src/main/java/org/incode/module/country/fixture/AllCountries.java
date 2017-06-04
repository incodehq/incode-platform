/*
 *
 *  Copyright 2012-2014 Eurocommercial Properties NV
 *
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
package org.incode.module.country.fixture;

import java.io.IOException;
import java.net.URL;

import javax.inject.Inject;

import com.google.common.base.Charsets;
import com.google.common.io.Resources;

import org.apache.isis.applib.fixturescripts.FixtureScript;

import org.incode.module.country.dom.impl.Country;
import org.incode.module.country.dom.impl.CountryRepository;
import org.incode.module.country.dom.impl.StateRepository;

public class AllCountries extends FixtureScript {

    @Override
    protected void execute(ExecutionContext executionContext) {

        URL url = Resources.getResource(getClass(), "country_codes.csv");
        String cvsSplitBy = ";";

        try {
            for (String line : Resources.readLines(url, Charsets.UTF_8)) {

                String[] country = line.split(cvsSplitBy);
                createCountry(country[2], country[1], country[0], executionContext);

            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private Country createCountry(final String reference, String alpha2Code, String name, ExecutionContext executionContext) {
        final Country country = countryRepository.findOrCreateCountry(reference, alpha2Code, name);
        return executionContext.addResult(this, country.getAlpha2Code(), country);
    }

    // //////////////////////////////////////

    @Inject
    private StateRepository stateRepository;

    @Inject
    private CountryRepository countryRepository;

}
