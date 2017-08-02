package org.isisaddons.metamodel.paraname8.app;

import org.apache.isis.applib.AppManifestAbstract;

import org.incode.domainapp.example.dom.mml.paraname8.ExampleDomMetaModelParaname8Module;

public class Paraname8MetaModelAppManifest extends AppManifestAbstract {

    public static final Builder BUILDER = Builder
            .forModules(
                    Paraname8AppModule.class,
                    ExampleDomMetaModelParaname8Module.class)
            .withConfigurationProperty(
                    "isis.reflector.facets.include",
                    org.isisaddons.metamodel.paraname8.NamedFacetOnParameterParaname8Factory.class.getName());

    public Paraname8MetaModelAppManifest() {
        super(BUILDER);
    }

}
