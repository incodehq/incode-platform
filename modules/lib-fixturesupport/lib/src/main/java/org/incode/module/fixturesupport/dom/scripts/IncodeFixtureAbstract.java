package org.incode.module.fixturesupport.dom.scripts;

import org.apache.isis.applib.fixturescripts.FixtureScript;

public abstract class IncodeFixtureAbstract extends FixtureScript {

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

}
