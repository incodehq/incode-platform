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
package org.incode.module.country.dom.impl;

import java.util.List;

import javax.inject.Inject;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import org.apache.isis.applib.annotation.DomainService;
import org.apache.isis.applib.annotation.NatureOfService;
import org.apache.isis.applib.annotation.Programmatic;
import org.apache.isis.applib.query.QueryDefault;
import org.apache.isis.applib.services.repository.RepositoryService;

@DomainService(nature = NatureOfService.DOMAIN, repositoryFor = Country.class)
public class CountryRepository  {

    @Programmatic
    public List<Country> newCountry(
            final String reference,
            final String alpha2Code,
            final String name) {
        createCountry(reference, alpha2Code, name);
        return allCountries();
    }


    @Programmatic
    public List<Country> allCountries() {
        return repositoryService.allInstances(Country.class);
    }


    @Programmatic
    public Country createCountry(
            final String reference,
            final String alpha2Code,
            final String name) {
        final Country country = new Country(reference, alpha2Code, name);
        repositoryService.persistAndFlush(country);
        return country;
    }

    @Programmatic
    public Country findOrCreateCountry(
            final String reference,
            final String alpha2Code,
            final String name) {
        Country country = findCountry(reference);
        return country == null ? createCountry(reference, alpha2Code, name) : country;
    }


    @Programmatic
    public Country findCountry(
            final String reference) {
        return repositoryService.uniqueMatch(
                new QueryDefault<>(
                    Country.class,
                    "findByReference",
                    "reference", reference));
    }

    @Programmatic
    public List<Country> countriesFor(final Iterable<String> countryCodes) {
        List<Country> available = Lists.newArrayList();
        final ImmutableMap<String, Country> countryByCode = Maps.uniqueIndex(allCountries(), input -> input.getName());
        for (String countryCodeForUser : countryCodes) {
            available.add(countryByCode.get(countryCodeForUser));
        }
        return available;
    }

    @Programmatic
    public List<Country> findCountries(final String regexReference) {
        return repositoryService.allMatches(
                new QueryDefault<>(Country.class,
                        "findLikeReference",
                        "reference", regexReference));
    }

    @Inject
    RepositoryService repositoryService;

}
