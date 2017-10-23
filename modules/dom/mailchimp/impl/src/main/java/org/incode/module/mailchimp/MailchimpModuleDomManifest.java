package org.incode.module.mailchimp;

import org.apache.isis.applib.AppManifestAbstract;

import org.incode.module.mailchimp.dom.MailchimpModule;

/**
 * Used by <code>isis-maven-plugin</code> (build-time validation of the module) and also by module-level integration tests.
 */
public class MailchimpModuleDomManifest extends AppManifestAbstract {

    public static final Builder BUILDER = Builder.forModules(MailchimpModule.class);

    public MailchimpModuleDomManifest() {
        super(BUILDER);
    }


}
