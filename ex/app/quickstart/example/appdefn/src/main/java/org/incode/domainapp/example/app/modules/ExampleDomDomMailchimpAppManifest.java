package org.incode.domainapp.example.app.modules;

import java.util.List;

import javax.inject.Inject;

import org.apache.isis.applib.AppManifestAbstract;
import org.apache.isis.applib.annotation.CollectionLayout;
import org.apache.isis.applib.annotation.DomainObject;
import org.apache.isis.applib.annotation.HomePage;
import org.apache.isis.applib.annotation.Nature;

import org.isisaddons.module.security.dom.password.PasswordEncryptionServiceUsingJBcrypt;
import org.isisaddons.module.security.dom.permission.PermissionsEvaluationServiceAllowBeatsVeto;

import org.incode.domainapp.example.dom.dom.mailchimp.ExampleDomModuleMailchimpModule;
import org.incode.domainapp.example.dom.dom.mailchimp.dom.MailChimpDemoMenu;
import org.incode.domainapp.example.dom.dom.mailchimp.dom.demoparty.DemoParty;
import org.incode.domainapp.example.dom.dom.mailchimp.fixture.DemoParties_forMailChimp_create;
import org.incode.module.mailchimp.dom.MailchimpModule;
import org.incode.module.mailchimp.dom.impl.MailChimpMember;

import domainapp.appdefn.DomainAppAppManifestAbstract;
import domainapp.appdefn.fixture.DomainAppFixtureScriptsSpecProvider;
import domainapp.appdefn.seed.security.SeedSuperAdministratorRoleAndSvenSuperUser;

public class ExampleDomDomMailchimpAppManifest extends AppManifestAbstract {

    public static final Builder BUILDER = DomainAppAppManifestAbstract.BUILDER.withAdditionalModules(

            DemoParty.class,

            ExampleDomModuleMailchimpModule.class,
            MailchimpModule.class
        )
        .withFixtureScripts(
                DemoParties_forMailChimp_create.class,
                SeedSuperAdministratorRoleAndSvenSuperUser.class
        )
        .withAdditionalServices(
                HomePageProvider.class,
                DomainAppFixtureScriptsSpecProvider.class,
                // necessary because of ISIS-1710
                PasswordEncryptionServiceUsingJBcrypt.class,
                PermissionsEvaluationServiceAllowBeatsVeto.class
        )
        // required by Maichimp Module
        .withConfigurationProperty("isis.service.mailchimp.base-url","https://us6.api.mailchimp.com/3.0")
        .withConfigurationProperty("isis.service.mailchimp.secret","e22a20f29430c3df0d2b7840af9921d6-us6")
        .withConfigurationProperty("isis.service.mailchimp.connect-timeout","2000")
        .withConfigurationProperty("isis.service.mailchimp.read-timeout","30000")
        .withConfigurationProperty("isis.service.mailchimp.company","MyCompany")
        .withConfigurationProperty("isis.service.mailchimp.address1","My Street 1")
        .withConfigurationProperty("isis.service.mailchimp.city","MyCity")
        .withConfigurationProperty("isis.service.mailchimp.state","MyState")
        .withConfigurationProperty("isis.service.mailchimp.zip","MyZip")
        .withConfigurationProperty("isis.service.mailchimp.country","MyCountry")
        .withConfigurationProperty("isis.service.mailchimp.from_name","MyCompany - mySlogan")
        .withConfigurationProperty("isis.service.mailchimp.from_email","my@email.com")
        .withConfigurationProperty("isis.service.mailchimp.language","en")
        .withConfigurationProperty("isis.service.mailchimp.permissionreminder","You are receiving this mail because .....")
        ;

    public ExampleDomDomMailchimpAppManifest() {
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
        public List<MailChimpMember> allMailChimpMembers() {
            return mailChimpDemoMenu.allMailChimpMembers();
        }

        @Inject
        MailChimpDemoMenu mailChimpDemoMenu;

    }

}
