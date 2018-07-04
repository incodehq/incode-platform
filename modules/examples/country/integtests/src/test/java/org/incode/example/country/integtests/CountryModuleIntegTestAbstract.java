package org.incode.example.country.integtests;

import org.apache.isis.applib.Module;
import org.apache.isis.core.integtestsupport.IntegrationTestAbstract3;

public abstract class CountryModuleIntegTestAbstract extends IntegrationTestAbstract3 {

    protected CountryModuleIntegTestAbstract() {
        super(module());
    }

    public static Module module() {
        return new CountryModuleIntegTestModule();
    }

}
