package org.incode.example.country.fixtures.enums;

import org.apache.isis.applib.fixturescripts.PersonaWithBuilderScript;
import org.apache.isis.applib.fixturescripts.PersonaWithFinder;
import org.apache.isis.applib.services.registry.ServiceRegistry2;

import org.incode.example.country.dom.impl.Country;
import org.incode.example.country.dom.impl.CountryRepository;
import org.incode.example.country.fixtures.builders.CountryBuilder;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.Accessors;

@AllArgsConstructor
@Getter
@Accessors(chain = true)
public enum Country_enum implements PersonaWithBuilderScript<Country, CountryBuilder>, PersonaWithFinder<Country> {

    GBR("GBR", "GB", "United Kingdom"),
    NLD("NLD", "NL", "The Netherlands"),
    ITA("ITA", "IT", "Italy"),
    FRA("FRA", "FR", "France"),
    SWE("SWE", "SE", "Sweden");

    private final String ref3;
    private final String ref2;
    private final String name;

    @Override
    public Country findUsing(final ServiceRegistry2 serviceRegistry) {
        final CountryRepository countryRepository =
                serviceRegistry.lookupService(CountryRepository.class);
        return countryRepository.findCountry(this.ref3);
    }

    @Override
    public CountryBuilder builder() {
        return new CountryBuilder()
                    .setRef3(ref3)
                    .setRef2(ref2)
                    .setName(name);
    }

}
