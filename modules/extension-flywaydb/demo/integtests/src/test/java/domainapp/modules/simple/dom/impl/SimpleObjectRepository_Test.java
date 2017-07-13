package domainapp.modules.simple.dom.impl;

import java.util.List;

import com.google.common.collect.Lists;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.jmock.Expectations;
import org.jmock.Sequence;
import org.jmock.auto.Mock;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import org.apache.isis.applib.services.registry.ServiceRegistry2;
import org.apache.isis.applib.services.repository.RepositoryService;
import org.apache.isis.core.unittestsupport.jmocking.JUnitRuleMockery2;
import org.apache.isis.core.unittestsupport.jmocking.JUnitRuleMockery2.Mode;

import static org.assertj.core.api.Assertions.assertThat;

public class SimpleObjectRepository_Test {

    @Rule
    public JUnitRuleMockery2 context = JUnitRuleMockery2.createFor(Mode.INTERFACES_AND_CLASSES);

    @Mock
    ServiceRegistry2 mockServiceRegistry;
    
    @Mock
    RepositoryService mockRepositoryService;

    FlywayDemoObjectRepository flywayDemoObjectRepository;

    @Before
    public void setUp() throws Exception {
        flywayDemoObjectRepository = new FlywayDemoObjectRepository();
        flywayDemoObjectRepository.repositoryService = mockRepositoryService;
        flywayDemoObjectRepository.serviceRegistry = mockServiceRegistry;
    }

    public static class Create extends SimpleObjectRepository_Test {

        @Test
        public void happyCase() throws Exception {

            final String someName = "Foobar";

            // given
            final Sequence seq = context.sequence("create");
            context.checking(new Expectations() {
                {
                    oneOf(mockServiceRegistry).injectServicesInto(with(any(FlywayDemoObject.class)));
                    inSequence(seq);

                    oneOf(mockRepositoryService).persist(with(nameOf(someName)));
                    inSequence(seq);
                }

            });

            // when
            final FlywayDemoObject obj = flywayDemoObjectRepository.create(someName);

            // then
            assertThat(obj).isNotNull();
            assertThat(obj.getName()).isEqualTo(someName);
        }

        private static Matcher<FlywayDemoObject> nameOf(final String name) {
            return new TypeSafeMatcher<FlywayDemoObject>() {
                @Override
                protected boolean matchesSafely(final FlywayDemoObject item) {
                    return name.equals(item.getName());
                }

                @Override
                public void describeTo(final Description description) {
                    description.appendText("has name of '" + name + "'");
                }
            };
        }
    }

    public static class ListAll extends SimpleObjectRepository_Test {

        @Test
        public void happyCase() throws Exception {

            // given
            final List<FlywayDemoObject> all = Lists.newArrayList();

            context.checking(new Expectations() {
                {
                    oneOf(mockRepositoryService).allInstances(FlywayDemoObject.class);
                    will(returnValue(all));
                }
            });

            // when
            final List<FlywayDemoObject> list = flywayDemoObjectRepository.listAll();

            // then
            assertThat(list).isEqualTo(all);
        }
    }
}
