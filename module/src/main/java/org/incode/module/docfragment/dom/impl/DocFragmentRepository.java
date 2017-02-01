/*
 *  Licensed to the Apache Software Foundation (ASF) under one
 *  or more contributor license agreements.  See the NOTICE file
 *  distributed with this work for additional information
 *  regarding copyright ownership.  The ASF licenses this file
 *  to you under the Apache License, Version 2.0 (the
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
package org.incode.module.docfragment.dom.impl;

import java.util.Collections;
import java.util.List;

import org.apache.isis.applib.annotation.DomainObject;
import org.apache.isis.applib.annotation.DomainService;
import org.apache.isis.applib.annotation.NatureOfService;
import org.apache.isis.applib.annotation.Programmatic;
import org.apache.isis.applib.query.QueryDefault;
import org.apache.isis.applib.services.registry.ServiceRegistry2;
import org.apache.isis.applib.services.repository.RepositoryService;

@DomainService(
        nature = NatureOfService.DOMAIN,
        repositoryFor = DocFragment.class
)
public class DocFragmentRepository {

    public List<DocFragment> listAll() {
        return repositoryService.allInstances(DocFragment.class);
    }

    /**
     * Returns the most applicable {@link DocFragment} by atPath
     *
     * <p>
     * for example, will match "/ITA/CAR" precedence over "/ITA" precedence over "/".
     * </p>
     *
     * @param objectType - the (as per {@link DomainObject#objectType() objectType} of the object to be used to interpolate
     * @param name
     * @param atPath
     */
    @Programmatic
    public DocFragment findByObjectTypeAndNameAndApplicableToAtPath(
            final String objectType,
            final String name,
            final String atPath) {

        // workaround, the ORDER BY atPath DESC doesn't seem to be honoured...
        final List<DocFragment> ts = repositoryService.allMatches(
                new QueryDefault<>(
                        DocFragment.class,
                        "findByObjectTypeAndNameAndApplicableToAtPath",
                        "objectType", objectType,
                        "name", name,
                        "atPath", atPath));
        Collections.sort(ts, (o1, o2) -> o2.getAtPath().length() - o1.getAtPath().length());

        return ts.isEmpty() ? null : ts.get(0);
    }

    @Programmatic
    public DocFragment create(final String objectType, final String name, final String atPath, final String templateText) {
        final DocFragment object = new DocFragment(objectType, name, atPath, templateText);
        serviceRegistry.injectServicesInto(object);
        repositoryService.persist(object);
        return object;
    }

    @javax.inject.Inject
    RepositoryService repositoryService;
    @javax.inject.Inject
    ServiceRegistry2 serviceRegistry;

}
