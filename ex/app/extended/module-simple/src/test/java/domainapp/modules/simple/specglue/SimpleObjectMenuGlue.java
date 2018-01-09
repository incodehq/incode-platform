package domainapp.modules.simple.specglue;

import java.util.List;
import java.util.UUID;

import org.apache.isis.core.specsupport.specs.CukeGlueAbstract;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.When;
import domainapp.modules.simple.dom.SimpleObject;
import domainapp.modules.simple.dom.SimpleObjectMenu;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class SimpleObjectMenuGlue extends CukeGlueAbstract {

    @Given("^there are.* (\\d+) simple objects$")
    public void there_are_N_simple_objects(int n) throws Throwable {
        try {
            final List<SimpleObject> list = simpleObjectMenu().listAll();
            assertThat(list.size(), is(n));
            putVar("java.util.List", "simpleObjects", list);
        } finally {
            assertMocksSatisfied();
        }
    }
    
    @When("^.*create a .*simple object$")
    public void create_a_simple_object() throws Throwable {
        simpleObjectMenu().create(UUID.randomUUID().toString());
    }

    private SimpleObjectMenu simpleObjectMenu() {
        return service(SimpleObjectMenu.class);
    }

}
