package org.incode.domainapp.example.app;

import org.apache.isis.applib.AppManifestAbstract;

import org.incode.domainapp.example.dom.ExampleDomSubmodule;
import org.incode.domainapp.example.dom.demo.ExampleDomDemoDomSubmodule;

import domainapp.appdefn.DomainAppAppManifest;

public class DomainAppAppManifestWithExampleModules extends AppManifestAbstract {

    public static final Builder BUILDER = DomainAppAppManifest.BUILDER
            .withAdditionalModules(
                    ExampleAppSubmodule.class,
                    ExampleDomSubmodule.class,
                    ExampleDomDemoDomSubmodule.class
            );

    public DomainAppAppManifestWithExampleModules() {
        super(BUILDER);
    }

}
