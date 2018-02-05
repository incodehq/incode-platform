package org.incode.example.country;

import org.apache.isis.applib.AppManifestAbstract;

import org.incode.example.country.dom.CountryExample;

/**
 * Used by <code>isis-maven-plugin</code> (build-time validation of the module) and also by module-level integration tests.
 */
public class CountryExampleDomManifest extends AppManifestAbstract {

    public static final Builder BUILDER = Builder.forModules(CountryExample.class);

    public CountryExampleDomManifest() {
        super(BUILDER);
    }


}
