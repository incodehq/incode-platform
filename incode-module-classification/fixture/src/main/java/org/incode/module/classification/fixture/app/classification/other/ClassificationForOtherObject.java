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
package org.incode.module.classification.fixture.app.classification.other;

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
import org.incode.module.classification.fixture.dom.demo.other.OtherObject;

@javax.jdo.annotations.PersistenceCapable(
        identityType= IdentityType.DATASTORE,
        schema="incodeClassificationDemo")
@javax.jdo.annotations.Inheritance(strategy = InheritanceStrategy.NEW_TABLE)
@DomainObject(
        objectType = "incodeClassificationDemo.ClassificationForOtherObject"
)
public class ClassificationForOtherObject extends Classification {

    //region > otherObject (property)
    private OtherObject otherObject;

    @Column(allowsNull = "false", name = "otherObjectId")
    @Property(editing = Editing.DISABLED)
    public OtherObject getOtherObject() {
        return otherObject;
    }

    public void setOtherObject(final OtherObject otherObject) {
        this.otherObject = otherObject;
    }
    //endregion

    //region > classified (hook, derived)
    @Override
    public Object getClassified() {
        return getOtherObject();
    }

    @Override
    protected void setClassified(final Object classified) {
        setOtherObject((OtherObject) classified);
    }
    //endregion

    //region > ApplicationTenancyService SPI implementation
    @DomainService(nature = NatureOfService.DOMAIN)
    public static class ApplicationTenancyServiceForOtherObject implements ApplicationTenancyService {

        @Override
        public String atPathFor(final Object domainObjectToClassify) {
            if(domainObjectToClassify instanceof OtherObject) {
                return ((OtherObject) domainObjectToClassify).getAtPath();
            }
            return null;
        }
    }
    //endregion

    //region > SubtypeProvider SPI implementation
    @DomainService(nature = NatureOfService.DOMAIN)
    public static class SubtypeProvider extends ClassificationRepository.SubtypeProviderAbstract {
        public SubtypeProvider() {
            super(OtherObject.class, ClassificationForOtherObject.class);
        }
    }
    //endregion

    //region > mixins

    @Mixin
    public static class _classifications extends T_classifications<OtherObject> {
        public _classifications(final OtherObject classified) {
            super(classified);
        }
    }

    @Mixin
    public static class _classify extends T_classify<OtherObject> {
        public _classify(final OtherObject classified) {
            super(classified);
        }
    }

    @Mixin
    public static class _unclassify extends T_unclassify<OtherObject> {
        public _unclassify(final OtherObject classified) {
            super(classified);
        }
    }

    //endregion

}
