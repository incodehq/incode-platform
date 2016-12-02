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

import java.util.Arrays;
import java.util.Map;

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
 * <li><p> implicitly: <code>isis.persistor.datanucleus.impl.datanucleus.schema.autoCreateAll=true</code> </p>
 * </li>
 *
 * <li><p> explicitly: <code>isis.persistor.datanucleus.impl.flywaydb.disable=true</code> </p>
 * </li>
 *
 * </ul>
 *
 */
public class FlywayJdoPersistenceManagerFactory extends JDOPersistenceManagerFactory {

    public FlywayJdoPersistenceManagerFactory() {
        super();
    }

    public FlywayJdoPersistenceManagerFactory(final PersistenceUnitMetaData pumd, final Map overrideProps) {
        super(pumd, overrideProps);
        final boolean disabled = isDisabled(overrideProps);
        if(!disabled) {
            migrateDatabase();
        }
    }

    public FlywayJdoPersistenceManagerFactory(final Map props) {
        super(props);
        final boolean disabled = isDisabled(props);
        if(!disabled) {
            migrateDatabase();
        }
    }

    private static boolean isDisabled(final Map props) {
        // if implicitly disabled
        if(     isSet(props, PropertyNames.PROPERTY_SCHEMA_AUTOCREATE_ALL) ||
                // as per DataNucleusApplicationComponents' handling of autocreation of schema
                !isSet(props, PropertyNames.PROPERTY_SCHEMA_AUTOCREATE_SCHEMA) &&
                 isSet(props, PropertyNames.PROPERTY_SCHEMA_AUTOCREATE_TABLES) &&
                 isSet(props, PropertyNames.PROPERTY_SCHEMA_AUTOCREATE_COLUMNS) &&
                 isSet(props, PropertyNames.PROPERTY_SCHEMA_AUTOCREATE_CONSTRAINTS)) {
            return true;
        }
        // explicitly disable (any of these keys will do)
        for (String key : Arrays.asList("flyway.disable", "flyway.disabled", "flywaydb.disable", "flywaydb.disabled")) {
            if(isSet(props, key)) {
                return true;
            }
        }
        return false;
    }

    private static boolean isSet(final Map props, final String key) {
        try {
            final String value = (String)props.get(key);
            return Boolean.parseBoolean(value);
        } catch (Exception e) {
            return false;
        }
    }

    private void migrateDatabase() {

        try {
            Flyway flyway = new Flyway();
            flyway.setDataSource(this.getConnectionURL(), this.getConnectionUserName(), this.getConnectionPassword());
            flyway.migrate();

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
