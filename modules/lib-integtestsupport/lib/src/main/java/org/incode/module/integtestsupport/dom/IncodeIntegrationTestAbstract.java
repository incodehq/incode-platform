package org.incode.module.integtestsupport.dom;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.apache.isis.applib.fixturescripts.FixtureScript;
import org.apache.isis.applib.fixturescripts.FixtureScripts;
import org.apache.isis.applib.services.xactn.TransactionService;
import org.apache.isis.core.integtestsupport.IntegrationTestAbstract;

/**
 * Base class for integration tests.
 */
public abstract class IncodeIntegrationTestAbstract extends IntegrationTestAbstract {

    private static final Logger LOG = LoggerFactory.getLogger(IncodeIntegrationTestAbstract.class);


    /**
     * Replacement for the deprecated {@link #runScript(FixtureScript...)}.
     */
    protected void runFixtureScript(final FixtureScript... fixtureScriptList) {
        if(fixtureScriptList.length == 1) {
            fixtureScripts.runFixtureScript(fixtureScriptList[0], null);
        } else {
            fixtureScripts.runFixtureScript(new FixtureScript() {
                @Override
                protected void execute(final ExecutionContext executionContext) {
                    for (FixtureScript fixtureScript : fixtureScriptList) {
                        executionContext.executeChild(this, fixtureScript);
                    }
                }
            }, null);
        }
        nextTransaction();
    }

    @Inject
    protected TransactionService transactionService;

    @Inject
    protected FixtureScripts fixtureScripts;

}

