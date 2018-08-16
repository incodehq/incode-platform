package org.incode.example.commchannel.demo.shared.fixture;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import com.google.common.collect.Lists;

import org.apache.isis.applib.fixturescripts.FixtureScript;

import org.incode.example.commchannel.demo.shared.dom.CommChannelCustomer;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Accessors(chain = true)
public class CommChannelCustomer_createUpTo10_hardcodedData extends FixtureScript {

    public final List<String> NAMES = Collections.unmodifiableList(Arrays.asList(
            "Foo", "Bar", "Baz", "Frodo", "Froyo", "Fizz", "Bip", "Bop", "Bang", "Boo"));

    @Getter @Setter
    private Integer number;

    @Getter
    private final List<CommChannelCustomer> commChannelCustomers = Lists.newArrayList();

    @Override
    protected void execute(final ExecutionContext ec) {

        // defaults
        final int number = defaultParam("number", ec, 5);

        // validate
        if(number < 0 || number > NAMES.size()) {
            throw new IllegalArgumentException(String.format("number must be in range [0,%d)", NAMES.size()));
        }

        // execute
        for (int i = 0; i < number; i++) {
            final String name = NAMES.get(i);
            final CommChannelCustomer_create fs = new CommChannelCustomer_create().setName(name);
            ec.executeChild(this, fs.getName(), fs);
            commChannelCustomers.add(fs.getCommChannelCustomer());
        }
    }

}
