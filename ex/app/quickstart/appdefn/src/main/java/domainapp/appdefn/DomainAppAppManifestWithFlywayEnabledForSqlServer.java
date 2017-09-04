package domainapp.appdefn;

import java.util.Map;

import com.google.common.base.Joiner;

public class DomainAppAppManifestWithFlywayEnabledForSqlServer extends DomainAppAppManifestWithSeedUsers {

    @Override
    protected void overrideConfigurationProperties(final Map<String, String> configurationProperties) {
        configureFlywayDb(configurationProperties);
    }

    protected void configureFlywayDb(final Map<String, String> configurationProperties) {
        configurationProperties.put(
                "isis.persistor.datanucleus.impl.datanucleus.schema.autoCreateAll", "false");
        configurationProperties.put(
                "isis.persistor.datanucleus.impl.datanucleus.schema.autoCreateConstraints", "true");
        configurationProperties.put(
                "isis.persistor.datanucleus.impl.datanucleus.schema.validateAll", "true");
        configurationProperties.put(
                "isis.persistor.datanucleus.impl.flyway.locations", "classpath:db/migration/sqlserver");
        configurationProperties.put(
                "isis.persistor.datanucleus.impl.flyway.schemas",
                Joiner.on(",").join(
                        "flyway",
                        "incodeAlias",
                        "incodeClassification",
                        "incodeCommChannel",
                        "IncodeCommunications",
                        "incodeCountry",
                        "incodeDocFragment",
                        "incodeDocuments",
                        "incodeNote",
                        "isissettings",
                        "isistags",
                        "isisaudit",
                        "isiscommand",
                        "isispublishmq",
                        "isissecurity",
                        "isissessionlogger",
                        "simple"
                ));

        // pick up sqlserver.orm files
        configurationProperties.put(
                "isis.persistor.datanucleus.impl.datanucleus.Mapping", "sqlserver");

        // JDBC config properties for sqlserver
        configurationProperties.put(
                "isis.persistor.datanucleus.impl.javax.jdo.option.ConnectionDriverName",
                "com.microsoft.sqlserver.jdbc.SQLServerDriver");

        /*
        // specify remaining as system properties or uncomment and hard-code:
        configurationProperties.put(
                "isis.persistor.datanucleus.impl.javax.jdo.option.ConnectionURL",
                 "jdbc:sqlserver://localhost:1433;instance=.;databaseName=myappdb");
        configurationProperties.put(
                "isis.persistor.datanucleus.impl.javax.jdo.option.ConnectionUserName", "myappdbo");
        configurationProperties.put(
                "isis.persistor.datanucleus.impl.javax.jdo.option.ConnectionPassword", "s3cr3t!");
        */

    }
}
