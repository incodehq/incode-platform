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
package org.incode.module.docfragment.dom.api;

import java.io.IOException;

import javax.inject.Inject;

import org.apache.isis.applib.annotation.DomainService;
import org.apache.isis.applib.annotation.NatureOfService;
import org.apache.isis.applib.annotation.Programmatic;
import org.apache.isis.applib.services.metamodel.MetaModelService3;

import org.incode.module.docfragment.dom.impl.DocFragment;
import org.incode.module.docfragment.dom.impl.DocFragmentRepository;
import org.incode.module.docfragment.dom.spi.ApplicationTenancyService;

import freemarker.template.TemplateException;

@DomainService(nature = NatureOfService.DOMAIN)
public class DocFragmentService {

    @Programmatic
    public String render(final Object domainObject, String name) throws IOException, TemplateException {

        final String objectType = objectTypeFor(domainObject);
        String atPath = applicationTenancyService.atPathFor(domainObject);

        final DocFragment fragment = repo.findFragment(objectType, name, atPath);

        return fragment != null ? fragment.render(domainObject) : null;
    }


    private String objectTypeFor(final Object domainObject) {
        return metaModelService3.toObjectType(domainObject.getClass());
    }

    @Inject
    DocFragmentRepository repo;
    @Inject
    MetaModelService3 metaModelService3;
    @Inject
    ApplicationTenancyService applicationTenancyService;

}