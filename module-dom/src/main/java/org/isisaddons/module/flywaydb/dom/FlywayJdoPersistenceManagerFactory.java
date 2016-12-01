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

import org.datanucleus.api.jdo.JDOPersistenceManagerFactory;
import org.datanucleus.metadata.PersistenceUnitMetaData;
import org.flywaydb.core.Flyway;

public class FlywayJdoPersistenceManagerFactory extends JDOPersistenceManagerFactory {

    public FlywayJdoPersistenceManagerFactory() {
        super();
    }

    public FlywayJdoPersistenceManagerFactory(final PersistenceUnitMetaData pumd, final Map overrideProps) {
        super(pumd, overrideProps);
        migrateDatabase();
    }

    public FlywayJdoPersistenceManagerFactory(final Map props) {
        super(props);
        migrateDatabase();
    }

    private void migrateDatabase() {

        //String driverName = (String)this.getProperties().get("javax.jdo.option.ConnectionDriverName"); Flyway uses auto detection...
        //        String url = (String)this.getProperties().get("javax.jdo.option.ConnectionURL"); don't use, as the properties names are changed during initialization of superclass
        //        String userName = (String)this.getProperties().get("javax.jdo.option.ConnectionUserName");
        //        String password = (String)this.getProperties().get("javax.jdo.option.ConnectionPassword");

        try {
            Flyway flyway = new Flyway();
            flyway.setDataSource(this.getConnectionURL(), this.getConnectionUserName(), this.getConnectionPassword());
            flyway.migrate();

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
