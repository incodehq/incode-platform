package org.incode.platform.dom.mailchimp.integtests;

import org.junit.Before;
import org.junit.BeforeClass;

import org.apache.isis.core.integtestsupport.IntegrationTestAbstract2;

import org.incode.module.mailchimp.MailchimpModuleDomManifest;

public abstract class MailchimpModuleIntegTestAbstract extends IntegrationTestAbstract2 {

    @BeforeClass
    public static void initSystem() {
        bootstrapUsing(MailchimpModuleDomManifest.BUILDER
                        // TODO: suspect this is not needed, so commenting it out to see...
                        //.withAdditionalServices(ModuleFixtureScriptsSpecificationProvider.class)
                        .build());
    }

    @Before
    public void cleanUpFromPreviousTest() {
        // TODO ....
    }

}
