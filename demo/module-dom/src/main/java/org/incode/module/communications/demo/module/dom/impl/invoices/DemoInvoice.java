/*
 *  Copyright 2014 Dan Haywood
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
package org.incode.module.communications.demo.module.dom.impl.invoices;

import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.VersionStrategy;

import org.apache.isis.applib.annotation.BookmarkPolicy;
import org.apache.isis.applib.annotation.DomainObject;
import org.apache.isis.applib.annotation.DomainObjectLayout;
import org.apache.isis.applib.annotation.Editing;
import org.apache.isis.applib.annotation.Property;
import org.apache.isis.applib.annotation.Title;
import org.apache.isis.applib.util.ObjectContracts;

import org.incode.module.communications.demo.module.dom.impl.customers.DemoCustomer;

import lombok.Getter;
import lombok.Setter;

@javax.jdo.annotations.PersistenceCapable(
        identityType=IdentityType.DATASTORE,
        schema="incodeCommunicationsDemo")
@javax.jdo.annotations.DatastoreIdentity(
        strategy=javax.jdo.annotations.IdGeneratorStrategy.IDENTITY,
         column="id")
@javax.jdo.annotations.Version(
        strategy=VersionStrategy.VERSION_NUMBER, 
        column="version")
@DomainObject(
        // objectType inferred from schema
)
@DomainObjectLayout(
        bookmarking = BookmarkPolicy.AS_ROOT
)
public class DemoInvoice implements Comparable<DemoInvoice> {

    @javax.jdo.annotations.Column(allowsNull="false")
    @Title(sequence="1", prepend = "Invoice #")
    @Property(editing = Editing.DISABLED)
    @Getter @Setter
    private String num;

    @javax.jdo.annotations.Column(
            allowsNull = "false",
            name = "customerId"
    )
    @Title(sequence="2", prepend = " for ")
    @Property
    @Getter @Setter
    private DemoCustomer customer;

    //region > toString, compareTo

    @Override
    public String toString() {
        return ObjectContracts.toString(this, "num", "customer");
    }

    @Override
    public int compareTo(final DemoInvoice other) {
        return ObjectContracts.compare(this, other, "num", "customer");
    }

    //endregion

    //region > injected services

}
