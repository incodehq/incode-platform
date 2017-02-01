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
package org.incode.module.docfragment.demo.module.dom.impl.customers;

import java.io.IOException;
import java.util.List;

import javax.inject.Inject;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.VersionStrategy;

import com.google.common.collect.Lists;

import org.apache.isis.applib.annotation.Action;
import org.apache.isis.applib.annotation.DomainObject;
import org.apache.isis.applib.annotation.Editing;
import org.apache.isis.applib.annotation.ParameterLayout;
import org.apache.isis.applib.annotation.Property;
import org.apache.isis.applib.annotation.SemanticsOf;
import org.apache.isis.applib.annotation.Title;
import org.apache.isis.applib.util.ObjectContracts;

import org.incode.module.docfragment.dom.api.DocFragmentService;
import org.incode.module.docfragment.dom.types.AtPathType;

import freemarker.template.TemplateException;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@javax.jdo.annotations.PersistenceCapable(
        identityType=IdentityType.DATASTORE,
        schema = "docfragmentdemo",
        table = "DemoCustomer"
)
@javax.jdo.annotations.DatastoreIdentity(
        strategy=javax.jdo.annotations.IdGeneratorStrategy.IDENTITY,
        column="id")
@javax.jdo.annotations.Version(
        strategy= VersionStrategy.VERSION_NUMBER,
        column="version")
@DomainObject(
        objectType = "docfragmentdemo.DemoCustomer")
public class DemoCustomer implements Comparable<DemoCustomer> {

    @Builder
    public DemoCustomer(final String title, final String firstName, final String lastName, final String atPath) {
        this.title = title;
        this.firstName = firstName;
        this.lastName = lastName;
        this.atPath = atPath;
    }

    @javax.jdo.annotations.Column(allowsNull = "false", length = 10)
    @Property(editing = Editing.ENABLED)
    @Getter @Setter
    @Title(sequence = "1", append = ". ")
    private String title;


    @javax.jdo.annotations.Column(allowsNull = "false", length = 40)
    @Property(editing = Editing.ENABLED)
    @Getter @Setter
    @Title(sequence = "2", append = " ")
    private String firstName;


    @javax.jdo.annotations.Column(allowsNull = "false", length = 40)
    @Property(editing = Editing.ENABLED)
    @Getter @Setter
    @Title(sequence = "3")
    private String lastName;


    @javax.jdo.annotations.Column(allowsNull = "false", length = AtPathType.Meta.MAX_LEN)
    @Property(editing = Editing.ENABLED)
    @Getter @Setter
    private String atPath;



    @Property(editing = Editing.DISABLED)
    @javax.jdo.annotations.Column(allowsNull = "true", length = 200)
    @Getter @Setter
    private String rendered;


    @Action(semantics = SemanticsOf.IDEMPOTENT)
    public DemoCustomer render(
            @ParameterLayout(named = "Fragment name")
            final String fragmentName) {
        final String rendered = doRender(fragmentName);
        setRendered(rendered);
        return this;
    }

    public List<String> choices0Render() {
        return Lists.newArrayList("hello", "goodbye");
    }

    private String doRender(final String name) {
        try {
            return docFragmentService.render(this, name);
        } catch (IOException | TemplateException e) {
            return "failed to render";
        }
    }


    //region > toString, compareTo, equals, hashCode
    private static final String[] PROPERTY_NAMES = {"title", "firstName", "lastName"};

    @Override
    public String toString() {
        return ObjectContracts.toString(this, PROPERTY_NAMES);
    }

    @Override
    public int compareTo(final DemoCustomer other) {
        return ObjectContracts.compare(this, other, PROPERTY_NAMES);
    }

    @Override
    public boolean equals(final Object o) {
        return ObjectContracts.equals(this, o, PROPERTY_NAMES);
    }

    @Override
    public int hashCode() {
        return ObjectContracts.hashCode(this, PROPERTY_NAMES);
    }

    //endregion

    @Inject
    DocFragmentService docFragmentService;

}