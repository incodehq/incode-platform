/*
 *  Copyright 2015 Dan Haywood
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
package org.isisaddons.module.publishmq.dom.servicespi;

import org.apache.isis.applib.annotation.Programmatic;
import org.apache.isis.applib.services.changes.ChangedObjects;

public interface ChangedObjectsRepository {

    /**
     * @param changedObjects - the identity of the objects created, updated or deleted within a transaction, to be persisted.
     */
    @Programmatic
    void persist(final ChangedObjects changedObjects);

}
