package org.incode.domainapp.example.dom.dom.mailchimp.fixture;

import javax.inject.Inject;

import org.apache.isis.applib.fixturescripts.FixtureScript;

import org.incode.domainapp.example.dom.dom.mailchimp.dom.MailChimpDemoMenu;
import org.incode.domainapp.example.dom.dom.mailchimp.dom.demoparty.DemoParty;

public class DemoParties_forMailChimp_create extends FixtureScript {


    @Override
    protected void execute(final ExecutionContext executionContext) {

        final DemoParty party1 = create("Party One", "party1@somethi.ng", true, executionContext);
        final DemoParty party2 = create("Party2", "party2@somethi.ng", true, executionContext);
        final DemoParty party3 = create("Party Number Three", "party3@somethi.ng", false, executionContext);

    }

    private DemoParty create(
            final String name,
            final String email,
            final boolean sendMail,
            final ExecutionContext executionContext) {

        return executionContext.addResult(this, wrap(mailChimpDemoMenu).findOrCreateParty(name, email, sendMail));
    }


    @Inject
    MailChimpDemoMenu mailChimpDemoMenu;

}
