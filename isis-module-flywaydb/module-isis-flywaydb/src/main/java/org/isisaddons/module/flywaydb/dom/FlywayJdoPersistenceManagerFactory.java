/*
 *  Copyright 2016~date Dan Haywood
 *
 *  Licensed under the Apache License, Version 2.0 (the
 *  "License"); you may not use this file except in compliance
 *  with the License.  You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing,
 *  software distributed under the License is distributed on an
 *  "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 *  KIND, either express or implied.  See the License for the
 *  specific language governing permissions and limitations
 *  under the License.
 */
package org.isisaddons.module.flywaydb.dom;

import java.sql.Connection;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import javax.jdo.Extent;
import javax.jdo.PersistenceManager;

import com.google.common.base.Function;
import com.google.common.collect.FluentIterable;
import com.google.common.collect.Lists;

import org.datanucleus.PropertyNames;
import org.datanucleus.api.jdo.JDOPersistenceManager;
import org.datanucleus.api.jdo.JDOPersistenceManagerFactory;
import org.datanucleus.metadata.FileMetaData;
import org.datanucleus.metadata.MetaDataManager;
import org.datanucleus.metadata.PersistenceUnitMetaData;
import org.flywaydb.core.Flyway;
import org.flywaydb.core.api.callback.BaseFlywayCallback;

import org.apache.isis.applib.AppManifest;

/**
 * Can be disabled either in <code>persistor_datanucleus.properties</code> or by setting system property (-D).
 *
 * Two options, either:
 *
 * <ul>
 *
 * <li><p> <code>isis.persistor.datanucleus.impl.datanucleus.schema.autoCreateAll=true</code> </p>
 * </li>
 * <li><p> <code>isis.persistor.datanucleus.impl.datanucleus.schema.autoCreateTable=true</code> </p>
 * </li>
 *
 * </ul>
 *
 * Any properties in form <code>isis.persistor.datanucleus.impl.flyway.xxx</code> will be passed through as
 * configuration properties to Flyway (with the first four parts stripped off, ie <code>flyway.xxx</code> and so on).
 */
public class FlywayJdoPersistenceManagerFactory extends JDOPersistenceManagerFactory {

    /**
     * the PMF is instantiated twice by DataNucleusApplicationComponents; we only want to do the migration once.
     */
    private static ThreadLocal<Boolean> whetherMigrationRun = new ThreadLocal() { { set(false); } };

    public FlywayJdoPersistenceManagerFactory() {
        super();
    }

    public FlywayJdoPersistenceManagerFactory(final PersistenceUnitMetaData pumd, final Map overrideProps) {
        super(pumd, overrideProps);
        migrateIfRequired(overrideProps);
    }

    public FlywayJdoPersistenceManagerFactory(final Map props) {
        super(props);
        migrateIfRequired(props);
    }

    private void migrateIfRequired(final Map props) {
        // don't migrate if disabled
        final boolean disabled = isDisabled(props);
        if (disabled) {
            return;
        }

        // don't migrate if already run migration (PMF gets instantiated twice during Isis' bootstrapping in DNAC)
        if (whetherMigrationRun.get()) {
            return;
        }

        // ok, go ahead
        try {
            migrateDatabaseIfNotAlready(props);
        } finally {
            whetherMigrationRun.set(true);
        }
    }

    /**
     * implicitly disabled if either of these DN flags set
     *
     * Note though that we *don't* disable if PropertyNames.PROPERTY_SCHEMA_AUTOCREATE_CONSTRAINTS is set.
     * This enables the use case where the DBA prefers to drop all constraints using a 'beforeMigrate' callback,
     * and then relies on DN to automatically create them afterwards.
     */
    private static boolean isDisabled(final Map props) {
        return  isSet(props, PropertyNames.PROPERTY_SCHEMA_AUTOCREATE_ALL) ||
                isSet(props, PropertyNames.PROPERTY_SCHEMA_AUTOCREATE_TABLES);
    }

    private static boolean isSet(final Map props, final String key) {
        try {
            final String value = (String)props.get(key);
            return Boolean.parseBoolean(value);
        } catch (Exception e) {
            return false;
        }
    }

    private void migrateDatabaseIfNotAlready(final Map map) {
        try {
            Flyway flyway = new Flyway();

            final Properties properties = asProperties(map);
            flyway.configure(properties);
            flyway.setDataSource(this.getConnectionURL(), this.getConnectionUserName(), this.getConnectionPassword());

            flyway.setCallbacks(new TouchAllEntities());

            flyway.repair();
            flyway.migrate();

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private static Properties asProperties(final Map map) {
        final Properties properties = new Properties();
        for (Object key : map.keySet()) {
            if(key instanceof String) {
                final String keyStr = (String) key;
                if((keyStr).startsWith("flyway.")) {
                    final Object value = map.get(keyStr);
                    if(value instanceof String) {
                        final String valueStr = (String) value;
                        properties.put(keyStr, valueStr);
                    }
                }
            }
        }
        return properties;
    }

    /**
     * Ensures that all entities are visited and validated (and if autoCreateConstraints=true, then have their constraints created also).
     */
    private class TouchAllEntities extends BaseFlywayCallback {
        @Override
        public void afterMigrate(final Connection connection) {

            PersistenceManager persistenceManager = null;
            try {
                persistenceManager = getPersistenceManager();
                final Set<Class<?>> persistenceCapableTypes = AppManifest.Registry.instance().getPersistenceCapableTypes();

                // this doesn't seem to be enough to trigger validation
                // loadClasses(persistenceManager, persistenceCapableTypes);

                // but this does
                for (Class<?> persistenceCapableType : persistenceCapableTypes) {
                    final Extent<?> unused = persistenceManager.getExtent(persistenceCapableType);
                }
            } finally {
                closeQuietly(persistenceManager);
            }
        }

        private void loadClasses(
                final PersistenceManager persistenceManager,
                final Set<Class<?>> persistenceCapableTypes1) {
            JDOPersistenceManager jdoPm = (JDOPersistenceManager) persistenceManager;
            final MetaDataManager metaDataManager = jdoPm.getExecutionContext().getMetaDataManager();
            List<String> classNameList = Lists.newArrayList(FluentIterable.from(persistenceCapableTypes1).transform(
                    new Function<Class<?>, String>() {
                        @Override
                        public String apply(final Class<?> input) {
                            return input.getName();
                        }
                    }));
            final String[] classNameArray = classNameList.toArray(new String[] {});
            final Collection<String> classesWithMetaData = metaDataManager.getClassesWithMetaData();
            System.out.println(classesWithMetaData.toArray());
            final FileMetaData[] fileMetaDatas = metaDataManager.loadClasses(classNameArray, null);
        }

        private void closeQuietly(final PersistenceManager persistenceManager) {
            if(persistenceManager != null) {
                try {
                    persistenceManager.close();
                } catch(Exception ignore) {
                    // ignore
                }

            }
        }
    }
}
