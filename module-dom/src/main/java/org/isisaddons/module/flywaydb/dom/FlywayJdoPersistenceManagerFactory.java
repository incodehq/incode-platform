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

import java.util.Map;
import java.util.Properties;

import org.datanucleus.PropertyNames;
import org.datanucleus.api.jdo.JDOPersistenceManagerFactory;
import org.datanucleus.metadata.PersistenceUnitMetaData;
import org.flywaydb.core.Flyway;

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

    public FlywayJdoPersistenceManagerFactory() {
        super();
    }

    public FlywayJdoPersistenceManagerFactory(final PersistenceUnitMetaData pumd, final Map overrideProps) {
        super(pumd, overrideProps);
        final boolean disabled = isDisabled(overrideProps);
        if(!disabled) {
            migrateDatabase(overrideProps);
        }
    }

    public FlywayJdoPersistenceManagerFactory(final Map props) {
        super(props);
        final boolean disabled = isDisabled(props);
        if(!disabled) {
            migrateDatabase(props);
        }
    }

    /**
     * implicitly disabled if either of these DN flags set
     *
     * Note though that we *don't* disable even if PropertyNames.PROPERTY_SCHEMA_AUTOCREATE_CONSTRAINTS is set.
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

    private void migrateDatabase(final Map map) {

        try {
            Flyway flyway = new Flyway();
            final Properties properties = asProperties(map);
            flyway.configure(properties);
            flyway.setDataSource(this.getConnectionURL(), this.getConnectionUserName(), this.getConnectionPassword());
            flyway.repair();
            flyway.migrate();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private static Properties asProperties(final Map map) {
        final Properties properties = new Properties();
        for (Object key : properties.keySet()) {
            if(key instanceof String) {
                final String keyStr = (String) key;
                if(((String) key).startsWith("flyway.")) {
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
}
