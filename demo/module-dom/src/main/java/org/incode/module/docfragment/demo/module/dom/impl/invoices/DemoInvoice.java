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
package org.incode.module.docfragment.demo.module.dom.impl.invoices;

import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.VersionStrategy;

import org.joda.time.LocalDate;

import org.apache.isis.applib.annotation.DomainObject;
import org.apache.isis.applib.annotation.Editing;
import org.apache.isis.applib.annotation.Property;
import org.apache.isis.applib.annotation.Title;
import org.apache.isis.applib.util.ObjectContracts;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@javax.jdo.annotations.PersistenceCapable(
        identityType=IdentityType.DATASTORE,
        schema = "docfragmentdemo",
        table = "DemoInvoice"
)
@javax.jdo.annotations.DatastoreIdentity(
        strategy=javax.jdo.annotations.IdGeneratorStrategy.IDENTITY,
        column="id")
@javax.jdo.annotations.Version(
        strategy= VersionStrategy.DATE_TIME,
        column="version")
@DomainObject(
        objectType = "docfragmentdemo.DemoInvoice"
)
public class DemoInvoice implements Comparable<DemoInvoice> {

    @Builder
    public DemoInvoice(final int num, final LocalDate dueBy, final int numDays) {
        this.num = num;
        this.dueBy = dueBy;
        this.numDays = numDays;
    }

    @javax.jdo.annotations.Column(allowsNull = "false")
    @Property(editing = Editing.DISABLED)
    @Getter @Setter
    @Title(sequence = "1", prepend = "Invoice #")
    private int num;


    @javax.jdo.annotations.Column(allowsNull = "false")
    @Property(editing = Editing.DISABLED)
    @Getter @Setter
    private LocalDate dueBy;


    @javax.jdo.annotations.Column(allowsNull = "false")
    @Property(editing = Editing.DISABLED)
    @Getter @Setter
    private int numDays;



    //region > toString, compareTo
    @Override
    public String toString() {
        return ObjectContracts.toString(this, "num");
    }

    @Override
    public int compareTo(final DemoInvoice other) {
        return ObjectContracts.compare(this, other, "num");
    }
    //endregion


}