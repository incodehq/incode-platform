package org.incode.extended.integtests.mml.paraname8.app;

import org.apache.isis.applib.AppManifestAbstract;

import org.incode.domainapp.extended.module.fixtures.shared.FixturesModuleSharedSubmodule;

public class Paraname8MetaModelAppManifest extends AppManifestAbstract {

    public static final Builder BUILDER = Builder
            .forModules(
                    org.incode.extended.integtests.mml.paraname8.app.Paraname8AppModule.class,
                    FixturesModuleSharedSubmodule.class)
            .withConfigurationProperty(
                    "isis.reflector.facets.include",
                    org.isisaddons.metamodel.paraname8.NamedFacetOnParameterParaname8Factory.class.getName());

    public Paraname8MetaModelAppManifest() {
        super(BUILDER);
    }

}
