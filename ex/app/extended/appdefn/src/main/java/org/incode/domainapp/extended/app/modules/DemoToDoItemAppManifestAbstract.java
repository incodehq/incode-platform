package org.incode.domainapp.extended.app.modules;

import java.util.List;

import javax.inject.Inject;

import org.apache.isis.applib.AppManifestAbstract;
import org.apache.isis.applib.annotation.CollectionLayout;
import org.apache.isis.applib.annotation.DomainObject;
import org.apache.isis.applib.annotation.HomePage;
import org.apache.isis.applib.annotation.Nature;

import org.isisaddons.module.security.dom.password.PasswordEncryptionServiceUsingJBcrypt;
import org.isisaddons.module.security.dom.permission.PermissionsEvaluationServiceAllowBeatsVeto;

import org.incode.domainapp.extended.module.fixtures.shared.todo.dom.DemoToDoItem;
import org.incode.domainapp.extended.module.fixtures.shared.todo.dom.DemoToDoItemMenu;
import org.incode.domainapp.extended.module.fixtures.shared.todo.fixture.DemoToDoItem_recreate5_forSven;

import org.incode.domainapp.extended.appdefn.DomainAppAppManifestAbstract;
import org.incode.domainapp.extended.appdefn.seed.security.SeedSuperAdministratorRoleAndSvenSuperUser;

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
