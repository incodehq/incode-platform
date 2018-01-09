package domainapp.modules.simple;

import org.apache.isis.applib.AppManifestAbstract;

/**
 * Used by <code>isis-maven-plugin</code> (build-time validation of the module) and also by module-level integration tests.
 */
public class SimpleModuleManifest extends AppManifestAbstract {

    public static final Builder BUILDER = Builder.forModules(
            SimpleModule.class
    );

    public SimpleModuleManifest() {
        super(BUILDER);
    }


}
