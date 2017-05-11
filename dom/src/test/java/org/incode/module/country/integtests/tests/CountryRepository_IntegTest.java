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
package org.incode.module.country.integtests.tests;

import java.util.List;

import javax.inject.Inject;

import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Test;

import org.incode.module.country.dom.impl.Country;
import org.incode.module.country.dom.impl.CountryRepository;
import org.incode.module.country.fixture.CountriesRefData;
import org.incode.module.country.integtests.CountryModuleIntegTestAbstract;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class CountryRepository_IntegTest extends CountryModuleIntegTestAbstract {

    public static class FindCountry extends CountryRepository_IntegTest {

        @Before
        public void setupData() {
            runFixtureScript(new CountriesRefData());
        }


        @Test
        public void whenExists() throws Exception {
            final Country country = countryRepository.findCountry(CountriesRefData.NLD);
            assertThat(country.getReference(), is(CountriesRefData.NLD));
        }

    }

    public static class FindCountries extends CountryRepository_IntegTest {

        @Before
        public void setupData() {
            runFixtureScript(new CountriesRefData());
        }

        @Test
        public void wildcard_matching_all() throws Exception {
            final List<Country> countries = countryRepository.findCountries(".*");
            Assertions.assertThat(countries).hasSize(249);
        }

        @Test
        public void wild_card() throws Exception {
            final List<Country> countries = countryRepository.findCountries(".*X.*");
            Assertions.assertThat(countries).hasSize(4); // CXR (christmas island), LUX, MEX, SXM (Sint Maarten - dutch part)
        }

        @Test
        public void find_by_alpha2Code() throws Exception {
            final Country country = countryRepository.findCountryByAlpha2Code("NL");
            Assertions.assertThat(country.getReference()).isEqualTo(CountriesRefData.NLD);
        }
    }

    @Inject
    CountryRepository countryRepository;
}