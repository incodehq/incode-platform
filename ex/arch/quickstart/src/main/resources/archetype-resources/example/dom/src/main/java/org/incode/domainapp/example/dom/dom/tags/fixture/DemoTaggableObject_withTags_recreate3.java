#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package org.incode.domainapp.example.dom.dom.tags.fixture;

import org.apache.isis.applib.fixturescripts.FixtureScript;

import org.incode.domainapp.example.dom.dom.tags.dom.demo.DemoTaggableObjectMenu;

public class DemoTaggableObject_withTags_recreate3 extends FixtureScript {

    @javax.inject.Inject
    DemoTaggableObjectMenu demoTaggableObjects;


    @Override
    protected void execute(ExecutionContext executionContext) {

        executionContext.executeChild(this, new DemoTaggableObjects_tearDown());

        executionContext.executeChild(this, new DemoTaggableObject_withTags_create3());
    }

}
