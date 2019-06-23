package org.isisaddons.module.freemarker.dom.service;

import java.util.Map;

import com.google.common.collect.ImmutableMap;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import org.apache.isis.core.unittestsupport.jmocking.JUnitRuleMockery2;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class FreeMarkerServiceTest {

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Rule
    public JUnitRuleMockery2 context = JUnitRuleMockery2.createFor(JUnitRuleMockery2.Mode.INTERFACES_AND_CLASSES);

    @JUnitRuleMockery2.Ignoring

    FreeMarkerService service;

    @Before
    public void setUp() throws Exception {
        service = new FreeMarkerService();

        service.init(ImmutableMap.of(FreeMarkerService.JODA_SUPPORT_KEY, "true"));
    }

    @Test
    public void using_properties() throws Exception {

        // given
        Map<String, String> properties = ImmutableMap.of("user", "John Doe");

        // when
        String merged = service.render("WelcomeUserTemplate:/GBR:", "<h1>Welcome ${user}!</h1>", properties);

        // then
        assertThat(merged, is("<h1>Welcome John Doe!</h1>"));
    }

    public static class UserDataModel {
        private String user;

        public String getUser() {
            return user;
        }

        public void setUser(final String user) {
            this.user = user;
        }
    }

    @Test
    public void using_data_model() throws Exception {

        // given
        final UserDataModel userDataModel = new UserDataModel();
        userDataModel.setUser("John Doe");

        // when

        String merged = service.render("WelcomeUserTemplate:/GBR:", "<h1>Welcome ${user}!</h1>", userDataModel);

        // then
        assertThat(merged, is("<h1>Welcome John Doe!</h1>"));
    }

    @Test
    public void caching() throws Exception {

        // given
        final UserDataModel userDataModel = new UserDataModel();
        userDataModel.setUser("John Doe");

        // when
        String merged = service.render("WelcomeUserTemplate:/GBR:", "<h1>Welcome ${user}!</h1>", userDataModel);

        // when (don't pass in any template cache)
        String merged2 = service.render("WelcomeUserTemplate:/GBR:", "", userDataModel);

        // then
        assertThat(merged, is("<h1>Welcome John Doe!</h1>"));
        assertThat(merged2, is("<h1>Welcome John Doe!</h1>"));
    }

    @Test
    public void different_templates() throws Exception {

        // given
        final UserDataModel userDataModel = new UserDataModel();
        userDataModel.setUser("John Doe");

        // when
        String merged = service.render("WelcomeUserTemplate:/GBR:", "<h1>Welcome ${user}!</h1>", userDataModel);

        // when (don't pass in any template cache)
        String merged2 = service.render("WelcomeUserTemplate:/FRA:", "<h1>Bonjour, ${user}</h1>", userDataModel);

        // then
        assertThat(merged, is("<h1>Welcome John Doe!</h1>"));
        assertThat(merged2, is("<h1>Bonjour, John Doe</h1>"));
    }

    @Test
    public void different_templates_per_versioning() throws Exception {

        // given
        final UserDataModel userDataModel = new UserDataModel();
        userDataModel.setUser("John Doe");

        // when
        String merged = service.render("WelcomeUserTemplate:/GBR:1:", "<h1>Welcome ${user}!</h1>", userDataModel);

        // when (bump the version)
        String merged2 = service.render("WelcomeUserTemplate:/GBR:2:", "<h1>Hi there, ${user} !!!</h1>", userDataModel);

        // then
        assertThat(merged, is("<h1>Welcome John Doe!</h1>"));
        assertThat(merged2, is("<h1>Hi there, John Doe !!!</h1>"));
    }

}
