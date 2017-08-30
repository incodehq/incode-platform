#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package org.incode.domainapp.example.app;

import org.apache.isis.applib.AppManifestAbstract;

import org.incode.domainapp.example.dom.ExampleDomSubmodule;

import domainapp.appdefn.DomainAppAppManifest;

public class DomainAppAppManifestWithExampleModules extends AppManifestAbstract {

    public static final Builder BUILDER = DomainAppAppManifest.BUILDER
            .withAdditionalModules(
                    ExampleAppSubmodule.class,
                    ExampleDomSubmodule.class
            );

    public DomainAppAppManifestWithExampleModules() {
        super(BUILDER);
    }

}
