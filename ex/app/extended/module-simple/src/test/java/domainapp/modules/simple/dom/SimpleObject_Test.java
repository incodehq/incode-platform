package domainapp.modules.simple.dom;

import org.junit.Before;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class SimpleObject_Test {

    SimpleObject simpleObject;

    @Before
    public void setUp() throws Exception {
        simpleObject = new SimpleObject("Foobar");
    }

    public static class Name extends SimpleObject_Test {

        @Test
        public void happyCase() throws Exception {
            // given
            assertThat(simpleObject.getName()).isEqualTo("Foobar");

            // when
            String name = "Foobar - updated";
            simpleObject.setName(name);

            // then
            assertThat(simpleObject.getName()).isEqualTo(name);
        }
    }

}
