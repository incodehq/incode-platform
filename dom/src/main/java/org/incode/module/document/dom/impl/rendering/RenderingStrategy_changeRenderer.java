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
package org.incode.module.document.dom.impl.rendering;

import java.util.List;

import javax.inject.Inject;

import org.apache.isis.applib.annotation.Action;
import org.apache.isis.applib.annotation.Mixin;
import org.apache.isis.applib.annotation.Parameter;
import org.apache.isis.applib.annotation.ParameterLayout;
import org.apache.isis.applib.annotation.SemanticsOf;
import org.apache.isis.applib.services.i18n.TranslatableString;

import org.incode.module.document.dom.DocumentModule;
import org.incode.module.document.dom.impl.renderers.Renderer;
import org.incode.module.document.dom.services.ClassNameViewModel;
import org.incode.module.document.dom.spi.RendererClassNameService;
import org.incode.module.document.dom.types.NameType;

@Mixin
public class RenderingStrategy_changeRenderer {

    //region > constructor
    private final RenderingStrategy renderingStrategy;

    public RenderingStrategy_changeRenderer(final RenderingStrategy renderingStrategy) {
        this.renderingStrategy = renderingStrategy;
    }
    //endregion


    public static class ActionDomainEvent extends DocumentModule.ActionDomainEvent<RenderingStrategy_changeRenderer>  { }

    @Action(
            semantics = SemanticsOf.IDEMPOTENT,
            domainEvent = ActionDomainEvent.class
    )
    public RenderingStrategy $$(
            @Parameter(maxLength = NameType.Meta.MAX_LEN, mustSatisfy = RenderingStrategy.RendererClassNameType.Meta.Specification.class)
            @ParameterLayout(named = "Renderer class name")
            final ClassNameViewModel classViewModel) {

        final Class<? extends Renderer> rendererClass =
                rendererClassNameService.asClass(classViewModel.getFullyQualifiedClassName());
        renderingStrategy.setRendererClassName(rendererClass.getName());
        return renderingStrategy;
    }

    public TranslatableString disable$$() {
        return rendererClassNameService == null
                ? TranslatableString.tr(
                "No RendererClassNameService registered to locate implementations of Renderer")
                : null;
    }

    public List<ClassNameViewModel> choices0$$() {
        return rendererClassNameService.renderClassNamesFor(renderingStrategy.getInputNature(), renderingStrategy.getOutputNature());
    }

    @Inject
    private RendererClassNameService rendererClassNameService;

}
