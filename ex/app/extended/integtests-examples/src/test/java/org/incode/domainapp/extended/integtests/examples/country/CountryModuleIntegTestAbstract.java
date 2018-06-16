package org.incode.domainapp.extended.integtests.examples.country;

import org.apache.isis.core.integtestsupport.IntegrationTestAbstract3;

import org.incode.example.country.dom.CountryModule;

public abstract class CountryModuleIntegTestAbstract extends IntegrationTestAbstract3 {

    protected CountryModuleIntegTestAbstract() {
        super(module());
    }

    public static CountryModule module() {
        return new CountryModule();
    }

}
