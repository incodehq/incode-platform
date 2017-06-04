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
package org.incode.module.classification.fixture.app.classification.demo;

import javax.jdo.annotations.Column;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.InheritanceStrategy;

import org.apache.isis.applib.annotation.DomainObject;
import org.apache.isis.applib.annotation.DomainService;
import org.apache.isis.applib.annotation.Editing;
import org.apache.isis.applib.annotation.Mixin;
import org.apache.isis.applib.annotation.NatureOfService;
import org.apache.isis.applib.annotation.Property;

import org.incode.module.classification.dom.impl.classification.Classification;
import org.incode.module.classification.dom.impl.classification.ClassificationRepository;
import org.incode.module.classification.dom.impl.classification.T_classifications;
import org.incode.module.classification.dom.impl.classification.T_classify;
import org.incode.module.classification.dom.impl.classification.T_unclassify;
import org.incode.module.classification.dom.spi.ApplicationTenancyService;
import org.incode.module.classification.fixture.dom.demo.first.DemoObject;

@javax.jdo.annotations.PersistenceCapable(identityType= IdentityType.DATASTORE, schema ="incodeClassificationDemo")
@javax.jdo.annotations.Inheritance(strategy = InheritanceStrategy.NEW_TABLE)
@DomainObject(
        objectType = "incodeClassificationDemo.ClassificationForDemoObject"
)
public class ClassificationForDemoObject extends Classification {

    //region > demoObject (property)
    private DemoObject demoObject;

    @Column(allowsNull = "false", name = "demoObjectId")
    @Property(editing = Editing.DISABLED)
    public DemoObject getDemoObject() {
        return demoObject;
    }

    public void setDemoObject(final DemoObject demoObject) {
        this.demoObject = demoObject;
    }
    //endregion

    //region > classified (hook, derived)
    @Override
    public Object getClassified() {
        return getDemoObject();
    }

    @Override
    protected void setClassified(final Object classified) {
        setDemoObject((DemoObject) classified);
    }
    //endregion

    //region > ApplicationTenancyService SPI implementation
    @DomainService(nature = NatureOfService.DOMAIN)
    public static class ApplicationTenancyServiceForDemoObject implements ApplicationTenancyService {
        @Override
        public String atPathFor(final Object domainObjectToClassify) {
            if(domainObjectToClassify instanceof DemoObject) {
                return ((DemoObject) domainObjectToClassify).getAtPath();
            }
            return null;
        }
    }
    //endregion

    //region > SubtypeProvider SPI implementation
    @DomainService(nature = NatureOfService.DOMAIN)
    public static class SubtypeProvider extends ClassificationRepository.SubtypeProviderAbstract {
        public SubtypeProvider() {
            super(DemoObject.class, ClassificationForDemoObject.class);
        }
    }
    //endregion

    //region > mixins

    @Mixin
    public static class _classifications extends T_classifications<DemoObject> {
        public _classifications(final DemoObject classified) {
            super(classified);
        }
    }

    @Mixin
    public static class _classify extends T_classify<DemoObject> {
        public _classify(final DemoObject classified) {
            super(classified);
        }
    }

    @Mixin
    public static class _unclassify extends T_unclassify<DemoObject> {
        public _unclassify(final DemoObject classified) {
            super(classified);
        }
    }

    //endregion

}
