package domainapp.modules.exampledom.ext.flywaydb.dom;

import org.junit.Before;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class FlywayDemoObject_Test {

    FlywayDemoObject flywayDemoObject;

    @Before
    public void setUp() throws Exception {
        flywayDemoObject = new FlywayDemoObject("Foobar");
    }

    public static class Name extends FlywayDemoObject_Test {

        @Test
        public void happyCase() throws Exception {
            // given
            assertThat(flywayDemoObject.getName()).isEqualTo("Foobar");

            // when
            String name = "Foobar - updated";
            flywayDemoObject.setName(name);

            // then
            assertThat(flywayDemoObject.getName()).isEqualTo(name);
        }
    }

}
