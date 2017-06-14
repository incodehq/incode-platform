package org.incode.module.fixturesupport.dom.scripts;

import javax.inject.Inject;
import javax.jdo.metadata.TypeMetadata;

import com.google.common.base.Strings;

import org.apache.isis.applib.services.jdosupport.IsisJdoSupport;

public abstract class TeardownFixtureAbstract extends IncodeFixtureAbstract {

    protected void deleteFrom(final Class cls) {
        preDeleteFrom(cls);
        final TypeMetadata metadata = isisJdoSupport.getJdoPersistenceManager().getPersistenceManagerFactory()
                .getMetadata(cls.getName());
        if(metadata == null) {
            // fall-back
            deleteFrom(cls.getSimpleName());
        } else {
            final String schema = metadata.getSchema();
            String table = metadata.getTable();
            if(Strings.isNullOrEmpty(table)) {
                table = cls.getSimpleName();
            }
            if(Strings.isNullOrEmpty(schema)) {
                deleteFrom(table);
            } else {
                deleteFrom(schema, table);
            }
        }
        postDeleteFrom(cls);
    }

    protected Integer deleteFrom(final String schema, final String table) {
        return isisJdoSupport.executeUpdate(String.format("DELETE FROM \"%s\".\"%s\"", schema, table));
    }

    protected void deleteFrom(final String table) {
        isisJdoSupport.executeUpdate(String.format("DELETE FROM \"%s\"", table));
    }

    protected void preDeleteFrom(final Class cls) {}

    protected void postDeleteFrom(final Class cls) {}

    @Inject
    private IsisJdoSupport isisJdoSupport;

}
