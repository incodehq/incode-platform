
package domainapp.modules.exampledom.ext.flywaydb.integtests.tests;

import java.sql.SQLIntegrityConstraintViolationException;
import java.util.List;

import javax.inject.Inject;

import com.google.common.base.Throwables;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.junit.Test;

import org.apache.isis.applib.fixturescripts.FixtureScript;
import org.apache.isis.applib.fixturescripts.FixtureScripts;
import org.apache.isis.applib.services.repository.RepositoryService;
import org.apache.isis.applib.services.xactn.TransactionService;

import domainapp.modules.exampledom.ext.flywaydb.dom.FlywayDemoObject;
import domainapp.modules.exampledom.ext.flywaydb.dom.FlywayDemoObjectMenu;
import domainapp.modules.exampledom.ext.flywaydb.fixture.scenario.RecreateFlywayDemoObjects;
import domainapp.modules.exampledom.ext.flywaydb.fixture.teardown.FlywayDemoModuleTearDown;
import domainapp.modules.exampledom.ext.flywaydb.integtests.FlywayDbModuleIntegTestAbstract;
import static org.assertj.core.api.Assertions.assertThat;

public class FlywayDemoObjectMenu_IntegTest extends FlywayDbModuleIntegTestAbstract {

    @Inject
    FixtureScripts fixtureScripts;
    @Inject
    TransactionService transactionService;
    @Inject
    FlywayDemoObjectMenu menu;
    @Inject
    RepositoryService repositoryService;

    public static class ListAll extends FlywayDemoObjectMenu_IntegTest {

        @Test
        public void happyCase() throws Exception {

            // given
            RecreateFlywayDemoObjects fs = new RecreateFlywayDemoObjects();
            fixtureScripts.runFixtureScript(fs, null);
            transactionService.nextTransaction();

            // when
            final List<FlywayDemoObject> all = wrap(menu).listAll();

            // then
            assertThat(all).hasSize(fs.getFlywayDemoObjects().size());

            FlywayDemoObject flywayDemoObject = wrap(all.get(0));
            assertThat(flywayDemoObject.getName()).isEqualTo(fs.getFlywayDemoObjects().get(0).getName());
        }

        @Test
        public void whenNone() throws Exception {

            // given
            FixtureScript fs = new FlywayDemoModuleTearDown();
            fixtureScripts.runFixtureScript(fs, null);
            transactionService.nextTransaction();

            // when
            final List<FlywayDemoObject> all = wrap(menu).listAll();

            // then
            assertThat(all).hasSize(0);
        }
    }

    public static class Create extends FlywayDemoObjectMenu_IntegTest {

        @Test
        public void happyCase() throws Exception {

            // given
            FixtureScript fs = new FlywayDemoModuleTearDown();
            fixtureScripts.runFixtureScript(fs, null);
            transactionService.nextTransaction();

            // when
            wrap(menu).create("Faz");

            // then
            final List<FlywayDemoObject> all = wrap(menu).listAll();
            assertThat(all).hasSize(1);
        }

        @Test
        public void whenAlreadyExists() throws Exception {

            // given
            FixtureScript fs = new FlywayDemoModuleTearDown();
            fixtureScripts.runFixtureScript(fs, null);
            transactionService.nextTransaction();
            wrap(menu).create("Faz");
            transactionService.nextTransaction();

            // then
            expectedExceptions.expectCause(causalChainContains(SQLIntegrityConstraintViolationException.class));

            // when
            wrap(menu).create("Faz");
            transactionService.nextTransaction();
        }

        private static Matcher<? extends Throwable> causalChainContains(final Class<?> cls) {
            return new TypeSafeMatcher<Throwable>() {
                @Override
                protected boolean matchesSafely(Throwable item) {
                    final List<Throwable> causalChain = Throwables.getCausalChain(item);
                    for (Throwable throwable : causalChain) {
                        if(cls.isAssignableFrom(throwable.getClass())){
                            return true;
                        }
                    }
                    return false;
                }

                @Override
                public void describeTo(Description description) {
                    description.appendText("exception with causal chain containing " + cls.getSimpleName());
                }
            };
        }
    }

}