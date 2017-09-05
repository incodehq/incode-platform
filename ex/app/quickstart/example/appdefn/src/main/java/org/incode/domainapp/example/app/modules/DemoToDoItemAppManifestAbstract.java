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

import org.incode.domainapp.example.dom.demo.dom.todo.DemoToDoItem;
import org.incode.domainapp.example.dom.demo.dom.todo.DemoToDoItemMenu;
import org.incode.domainapp.example.dom.demo.fixture.todoitems.DemoToDoItem_recreate5_forSven;

import domainapp.appdefn.DomainAppAppManifestAbstract;
import domainapp.appdefn.seed.security.SeedSuperAdministratorRoleAndSvenSuperUser;

public abstract class DemoToDoItemAppManifestAbstract extends AppManifestAbstract {

    public static final Builder BUILDER = DomainAppAppManifestAbstract.BUILDER.withAdditionalModules(

                DemoToDoItem.class
            )
            .withFixtureScripts(
                    DemoToDoItem_recreate5_forSven.class,
                    SeedSuperAdministratorRoleAndSvenSuperUser.class
            )
            .withAdditionalServices(
                    HomePageProvider.class,
                    // necessary because of ISIS-1710
                    PasswordEncryptionServiceUsingJBcrypt.class,
                    PermissionsEvaluationServiceAllowBeatsVeto.class
            );

    public DemoToDoItemAppManifestAbstract(final Builder builder) {
        super(builder);
    }

    @DomainObject(
            objectType = "HomePageProvider" // bit of a hack; this is a (manually registered) service actually
    )
    public static class HomePageProvider {

        @HomePage
        public HomePageViewModel homePage() {
            return new HomePageViewModel();
        }
        @Inject
        DemoToDoItemMenu menu;
    }

    @DomainObject(
            nature = Nature.VIEW_MODEL,
            objectType = "HomePageViewModel"
    )
    public static class HomePageViewModel {

        public String title() { return "Home page"; }

        @CollectionLayout(defaultView = "table")
        public List<DemoToDoItem> getToDoItems() {
            return menu.allInstances();
        }

        @Inject
        DemoToDoItemMenu menu;

    }

}
