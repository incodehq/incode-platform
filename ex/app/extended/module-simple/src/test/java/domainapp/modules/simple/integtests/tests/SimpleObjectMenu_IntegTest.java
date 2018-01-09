package domainapp.modules.simple.integtests.tests;

import java.sql.SQLIntegrityConstraintViolationException;
import java.util.List;

import javax.inject.Inject;

import com.google.common.base.Throwables;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.junit.Rule;
import org.junit.Test;
import org.togglz.junit.TogglzRule;

import org.apache.isis.applib.fixturescripts.FixtureScript;

import domainapp.modules.base.togglz.TogglzFeature;
import domainapp.modules.simple.dom.SimpleObject;
import domainapp.modules.simple.dom.SimpleObjectMenu;
import domainapp.modules.simple.fixture.scenario.SimpleObject_createUpTo10;
import domainapp.modules.simple.fixture.teardown.SimpleModule_tearDown;
import domainapp.modules.simple.integtests.SimpleModuleIntegTestAbstract;
import static org.assertj.core.api.Assertions.assertThat;

public class SimpleObjectMenu_IntegTest extends SimpleModuleIntegTestAbstract {

    @Inject
    SimpleObjectMenu menu;

    @Rule
    public TogglzRule togglzRule = TogglzRule.allEnabled(TogglzFeature.class);

    public static class ListAll extends SimpleObjectMenu_IntegTest {

        @Test
        public void happyCase() throws Exception {

            // given
            fixtureScripts.runFixtureScript(new SimpleModule_tearDown(), null);
            SimpleObject_createUpTo10 fs = new SimpleObject_createUpTo10();
            fixtureScripts.runFixtureScript(fs, null);
            transactionService.nextTransaction();

            // when
            final List<SimpleObject> all = wrap(menu).listAll();

            // then
            assertThat(all).hasSize(fs.getSimpleObjects().size());

            SimpleObject simpleObject = wrap(all.get(0));
            assertThat(simpleObject.getName()).isEqualTo(fs.getSimpleObjects().get(0).getName());
        }

        @Test
        public void whenNone() throws Exception {

            // given
            FixtureScript fs = new SimpleModule_tearDown();
            fixtureScripts.runFixtureScript(fs, null);
            transactionService.nextTransaction();

            // when
            final List<SimpleObject> all = wrap(menu).listAll();

            // then
            assertThat(all).hasSize(0);
        }
    }

    public static class Create extends SimpleObjectMenu_IntegTest {

        @Test
        public void happyCase() throws Exception {

            // given
            FixtureScript fs = new SimpleModule_tearDown();
            fixtureScripts.runFixtureScript(fs, null);
            transactionService.nextTransaction();

            // when
            wrap(menu).create("Faz");

            // then
            final List<SimpleObject> all = wrap(menu).listAll();
            assertThat(all).hasSize(1);
        }

        @Test
        public void whenAlreadyExists() throws Exception {

            // given
            FixtureScript fs = new SimpleModule_tearDown();
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