/*
 *  Copyright 2016 incode.org
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
package org.incode.module.document.dom.impl.applicability;

import org.apache.isis.applib.annotation.Programmatic;

import org.incode.module.document.dom.impl.docs.DocumentTemplate;

/**
 * Implementation is responsible for creating the appropriate model object to feed into the
 * {@link DocumentTemplate#getContentRenderingStrategy() rendering}
 * {@link DocumentTemplate#getNameRenderingStrategy() strategies} of the supplied {@link DocumentTemplate}, obtaining
 * information from the supplied domainObject.
 *
 * <p>
 *     (Class name is) referenced by {@link Applicability#getRendererModelFactoryClassName()}.
 * </p>
 */
public interface RendererModelFactory {

    /**
     * @param documentTemplate - to which this implementation applies, as per {@link DocumentTemplate#getAppliesTo()} and {@link Applicability#getRendererModelFactoryClassName()}
     * @param domainObject - provides the input for the renderer model.
     */
    @Programmatic
    Object newRendererModel(
            final DocumentTemplate documentTemplate,
            final Object domainObject);

}
