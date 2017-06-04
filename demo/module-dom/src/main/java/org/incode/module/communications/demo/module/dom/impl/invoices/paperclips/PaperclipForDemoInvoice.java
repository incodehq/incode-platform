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
package org.incode.module.communications.demo.module.dom.impl.invoices.paperclips;

import javax.jdo.annotations.Column;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.InheritanceStrategy;
import javax.jdo.annotations.NotPersistent;

import org.apache.isis.applib.annotation.DomainObject;
import org.apache.isis.applib.annotation.DomainService;
import org.apache.isis.applib.annotation.Editing;
import org.apache.isis.applib.annotation.Mixin;
import org.apache.isis.applib.annotation.NatureOfService;
import org.apache.isis.applib.annotation.Property;

import org.incode.module.communications.demo.module.dom.impl.invoices.DemoInvoice;
import org.incode.module.document.dom.impl.paperclips.Paperclip;
import org.incode.module.document.dom.impl.paperclips.PaperclipRepository;
import org.incode.module.document.dom.mixins.T_createAndAttachDocumentAndRender;
import org.incode.module.document.dom.mixins.T_createAndAttachDocumentAndScheduleRender;
import org.incode.module.document.dom.mixins.T_documents;
import org.incode.module.document.dom.mixins.T_preview;

@javax.jdo.annotations.PersistenceCapable(
        identityType= IdentityType.DATASTORE,
        schema="incodeCommunicationsDemo")
@javax.jdo.annotations.Inheritance(
        strategy = InheritanceStrategy.NEW_TABLE)
@DomainObject(
        // objectType inferred from schema
)
public class PaperclipForDemoInvoice extends Paperclip {

    //region > demoObject (property)
    private DemoInvoice demoInvoice;

    @Column(
            allowsNull = "false",
            name = "invoiceId"
    )
    @Property(
            editing = Editing.DISABLED
    )
    public DemoInvoice getDemoInvoice() {
        return demoInvoice;
    }

    public void setDemoInvoice(final DemoInvoice demoInvoice) {
        this.demoInvoice = demoInvoice;
    }
    //endregion


    //region > attachedTo (hook, derived)
    @NotPersistent
    @Override
    public Object getAttachedTo() {
        return getDemoInvoice();
    }

    @Override
    protected void setAttachedTo(final Object object) {
        setDemoInvoice((DemoInvoice) object);
    }
    //endregion


    //region > SubtypeProvider SPI implementation

    @DomainService(nature = NatureOfService.DOMAIN)
    public static class SubtypeProvider extends PaperclipRepository.SubtypeProviderAbstract {
        public SubtypeProvider() {
            super(DemoInvoice.class, PaperclipForDemoInvoice.class);
        }
    }
    //endregion

    //region > mixins

    @Mixin
    public static class _preview extends T_preview<DemoInvoice> {
        public _preview(final DemoInvoice demoInvoice) {
            super(demoInvoice);
        }
    }

    @Mixin
    public static class _documents extends T_documents<DemoInvoice> {
        public _documents(final DemoInvoice demoInvoice) {
            super(demoInvoice);
        }
    }

    @Mixin
    public static class _createAndAttachDocumentAndRender extends T_createAndAttachDocumentAndRender<DemoInvoice> {
        public _createAndAttachDocumentAndRender(final DemoInvoice demoInvoice) {
            super(demoInvoice);
        }
    }

    @Mixin
    public static class _createAndAttachDocumentAndScheduleRender extends T_createAndAttachDocumentAndScheduleRender<DemoInvoice> {
        public _createAndAttachDocumentAndScheduleRender(final DemoInvoice demoInvoice) {
            super(demoInvoice);
        }
    }

    //endregion

}
