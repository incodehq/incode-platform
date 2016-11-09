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

import java.util.List;
import java.util.stream.Collectors;

import javax.inject.Inject;

import com.google.common.collect.Lists;

import org.apache.isis.applib.annotation.DomainService;
import org.apache.isis.applib.annotation.NatureOfService;
import org.apache.isis.applib.annotation.Programmatic;

import org.incode.module.document.dom.impl.applicability.Binder;
import org.incode.module.document.dom.impl.docs.DocumentTemplate;
import org.incode.module.document.dom.impl.docs.DocumentTemplateRepository;
import org.incode.module.document.dom.impl.paperclips.PaperclipRepository;
import org.incode.module.document.dom.spi.ApplicationTenancyService;

@DomainService(nature = NatureOfService.DOMAIN)
public class DocumentTemplateForAtPathService {

    @Programmatic
    public List<DocumentTemplate> documentTemplatesForPreview(final Object domainObject) {
        final List<DocumentTemplate> templates = Lists.newArrayList();

        final String atPath = atPathFor(domainObject);
        if(atPath == null) {
            return templates;
        }

        final List<DocumentTemplate> templatesForPath =
                documentTemplateRepository.findByApplicableToAtPath(atPath);

        return Lists.newArrayList(
                templatesForPath.stream()
                                .filter(template -> canPreview(template))
                                .collect(Collectors.toList()));
    }

    @Programmatic
    public List<DocumentTemplate> documentTemplatesForCreateAndAttach(final Object domainObject) {
        final List<DocumentTemplate> templates = Lists.newArrayList();

        final String atPath = atPathFor(domainObject);
        if(atPath == null) {
            return templates;
        }

        final List<DocumentTemplate> templatesForPath =
                documentTemplateRepository.findByApplicableToAtPath(atPath);

        return Lists.newArrayList(
                templatesForPath.stream()
                        .filter(template -> {
                            final Binder binder = template.newBinder(domainObject);
                            if (binder == null) {
                                return false;
                            }
                            final Binder.Binding binding = binder.newBinding(template, domainObject, null);
                            final List<Object> attachTo = binding.getAttachTo();
                            return canCreate(template, attachTo);
                        })
                        .collect(Collectors.toList()));
    }

    private String atPathFor(final Object domainObject) {
        return applicationTenancyServices.stream()
                .map(x -> x.atPathFor(domainObject))
                .filter(x -> x != null)
                .findFirst()
                .orElse(null);
    }

    private boolean canPreview(final DocumentTemplate template) {
        return template.getContentRenderingStrategy().isPreviewsToUrl();
    }

    private boolean canCreate(final DocumentTemplate template, final List<Object> attachTo) {
        return !template.isPreviewOnly() &&
                attachTo.stream().filter(x -> paperclipRepository.canAttach(x)).findFirst().isPresent();
    }


    @Inject
    PaperclipRepository paperclipRepository;

    @Inject
    List<ApplicationTenancyService> applicationTenancyServices;

    @Inject
    DocumentTemplateRepository documentTemplateRepository;



}
