package domainapp.application.bdd.specglue;

import java.util.List;
import java.util.UUID;

import org.apache.isis.core.specsupport.specs.CukeGlueAbstract;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.When;
import domainapp.modules.simple.dom.impl.FlywayDemoObject;
import domainapp.modules.simple.dom.impl.FlywayDemoObjectMenu;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class FlywayDemoObjectGlue extends CukeGlueAbstract {

    @Given("^there are.* (\\d+) demo objects$")
    public void there_are_N_demo_objects(int n) throws Throwable {
        try {
            final List<FlywayDemoObject> findAll = service(FlywayDemoObjectMenu.class).listAll();
            assertThat(findAll.size(), is(n));
            putVar("list", "all", findAll);
            
        } finally {
            assertMocksSatisfied();
        }
    }
    
    @When("^I create a new demo object$")
    public void I_create_a_new_demo_object() throws Throwable {
        service(FlywayDemoObjectMenu.class).create(UUID.randomUUID().toString());
    }
    
}
