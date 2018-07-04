package org.incode.example.commchannel.demo.shared.fixture;

import org.apache.isis.applib.fixturescripts.FixtureScript;

import org.incode.example.commchannel.demo.shared.dom.CommChannelCustomer;
import org.incode.example.commchannel.demo.shared.dom.CommChannelCustomerMenu;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Accessors(chain = true)
public class CommChannelCustomer_create extends FixtureScript {

    @Getter @Setter
    private String name;

    /**
     * The created simple object (output).
     */
    @Getter
    private CommChannelCustomer commChannelCustomer;


    @Override
    protected void execute(final ExecutionContext ec) {

        String name = checkParam("name", ec, String.class);

        this.commChannelCustomer = wrap(demoObjectMenu).createDemoObject(name);
        ec.addResult(this, commChannelCustomer);
    }

    @javax.inject.Inject
    CommChannelCustomerMenu demoObjectMenu;

}
