package org.incode.domainapp.example.app.modules;

import java.util.List;

import javax.inject.Inject;

import org.apache.isis.applib.AppManifestAbstract;
import org.apache.isis.applib.annotation.CollectionLayout;
import org.apache.isis.applib.annotation.DomainObject;
import org.apache.isis.applib.annotation.HomePage;
import org.apache.isis.applib.annotation.Nature;

import org.incode.example.country.dom.CountryExample;
import org.isisaddons.module.security.dom.password.PasswordEncryptionServiceUsingJBcrypt;
import org.isisaddons.module.security.dom.permission.PermissionsEvaluationServiceAllowBeatsVeto;

import org.incode.example.country.dom.impl.Country;
import org.incode.example.country.dom.impl.CountryRepository;
import org.incode.example.country.dom.impl.State;
import org.incode.example.country.dom.impl.StateRepository;
import org.incode.example.country.fixture.CountriesRefData;
import org.incode.example.country.fixture.StatesRefData;
import org.incode.example.country.fixture.teardown.CountryModule_tearDown;

import domainapp.appdefn.DomainAppAppManifestAbstract;
import domainapp.appdefn.fixture.DomainAppFixtureScriptsSpecProvider;
import domainapp.appdefn.seed.security.SeedSuperAdministratorRoleAndSvenSuperUser;

public class ExampleDomDomCountryAppManifest extends AppManifestAbstract {

    public static final Builder BUILDER = DomainAppAppManifestAbstract.BUILDER.withAdditionalModules(

            CountryExample.class
        )
        .withFixtureScripts(
                CountryModule_tearDown.class,
                CountriesRefData.class,
                StatesRefData.class,
                SeedSuperAdministratorRoleAndSvenSuperUser.class
        )
        .withAdditionalServices(
                HomePageProvider.class,
                DomainAppFixtureScriptsSpecProvider.class,
                // necessary because of ISIS-1710
                PasswordEncryptionServiceUsingJBcrypt.class,
                PermissionsEvaluationServiceAllowBeatsVeto.class
        );

    public ExampleDomDomCountryAppManifest() {
        super(BUILDER);
    }

    @DomainObject(
            objectType = "HomePageProvider" // bit of a hack; this is a (manually registered) service actually
    )
    public static class HomePageProvider {

        @HomePage
        public HomePageViewModel homePage() {
            return new HomePageViewModel();
        }
    }

    @DomainObject(
            nature = Nature.VIEW_MODEL,
            objectType = "HomePageViewModel"
    )
    public static class HomePageViewModel {

        public String title() { return "Home page"; }

        @CollectionLayout(defaultView = "table")
        public List<Country> getCountries() {
            return countryRepository.allCountries();
        }

        @CollectionLayout(defaultView = "table")
        public List<State> getStates() {
            return stateRepository.allStates();
        }

        @Inject
        CountryRepository countryRepository;

        @Inject
        StateRepository stateRepository;

    }

}
