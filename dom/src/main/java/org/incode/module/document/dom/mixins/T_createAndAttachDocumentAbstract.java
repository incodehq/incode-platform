/*
 *
 *  Copyright 2012-2014 Eurocommercial Properties NV
 *
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
package org.incode.module.document.dom.mixins;

import java.io.IOException;
import java.util.List;

import javax.inject.Inject;

import org.apache.isis.applib.annotation.Action;
import org.apache.isis.applib.annotation.ActionLayout;
import org.apache.isis.applib.annotation.Contributed;
import org.apache.isis.applib.annotation.MemberOrder;
import org.apache.isis.applib.annotation.SemanticsOf;

import org.incode.module.document.dom.impl.applicability.Binder;
import org.incode.module.document.dom.impl.docs.Document;
import org.incode.module.document.dom.impl.docs.DocumentAbstract;
import org.incode.module.document.dom.impl.docs.DocumentTemplate;
import org.incode.module.document.dom.impl.paperclips.Paperclip;
import org.incode.module.document.dom.impl.paperclips.PaperclipRepository;
import org.incode.module.document.dom.impl.types.DocumentTypeRepository;
import org.incode.module.document.dom.services.ClassService;

/**
 * Not intended to be subclassed directly.
 */
public abstract class T_createAndAttachDocumentAbstract<T> {

    //region > constructor
    protected final T domainObject;

    public T_createAndAttachDocumentAbstract(final T domainObject) {
        this.domainObject = domainObject;
    }
    //endregion

    /**
     * Create a {@link Document} and attach using a {@link Paperclip}.
     */
    @Action(semantics = SemanticsOf.NON_IDEMPOTENT)
    @ActionLayout(contributed = Contributed.AS_ACTION)
    @MemberOrder(name = "documents", sequence = "2")
    public Object $$(final DocumentTemplate template) throws IOException {
        final String roleName = null;
        final String additionalTextIfAny = null;

        final Binder.Binding binding = template.newBinding(domainObject, additionalTextIfAny);
        final List<Object> attachTo = binding.getAttachTo();
        final boolean shouldPersist = !attachTo.isEmpty();

        final DocumentAbstract doc = doCreate(template, shouldPersist, additionalTextIfAny);

        for (Object o : attachTo) {
            if(paperclipRepository.canAttach(o)) {
                paperclipRepository.attach(doc, roleName, o);
            }
        }
        return doc;
    }


    public boolean hide$$() {
        return choices0$$().isEmpty();
    }

    /**
     * All templates which are applicable to the domain object's atPath, and which can be created and attached to at
     * least one domain object.
     */
    public List<DocumentTemplate> choices0$$() {
        return documentTemplateService.documentTemplatesForCreateAndAttach(domainObject);
    }


    /**
     * Mandatory hook method
     */
    protected abstract DocumentAbstract doCreate(
            final DocumentTemplate template,
            final boolean shouldPersist,
            final String additionalTextIfAny);

    //region > injected services

    @Inject
    DocumentTemplateForAtPathService documentTemplateService;

    @Inject
    PaperclipRepository paperclipRepository;

    @Inject
    DocumentTypeRepository documentTypeRepository;

    @Inject
    ClassService classService;

    //endregion

}
