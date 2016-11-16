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

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.VersionStrategy;

import com.google.common.eventbus.Subscribe;

import org.axonframework.eventhandling.annotation.EventHandler;

import org.apache.isis.applib.AbstractSubscriber;
import org.apache.isis.applib.annotation.DomainObject;
import org.apache.isis.applib.annotation.DomainObjectLayout;
import org.apache.isis.applib.annotation.DomainService;
import org.apache.isis.applib.annotation.Editing;
import org.apache.isis.applib.annotation.NatureOfService;
import org.apache.isis.applib.annotation.Property;
import org.apache.isis.applib.util.ObjectContracts;
import org.apache.isis.applib.util.TitleBuffer;

import org.incode.module.document.dom.DocumentModule;
import org.incode.module.document.dom.impl.docs.Document;
import org.incode.module.document.dom.impl.docs.DocumentTemplate;
import org.incode.module.document.dom.types.FqcnType;

import lombok.Getter;
import lombok.Setter;

/**
 * Indicates whether a domain object('s type) is applicable to a particular {@link DocumentTemplate}, providing the
 * (name of) the {@link RendererModelFactory} to use to create the renderer model to feed into that template, and the
 * (name of) the {@link AttachmentAdvisor} to use to specify which domain objects the resultant {@link Document}
 * should be attached to once created.
 */
@javax.jdo.annotations.PersistenceCapable(
        schema = "incodeDocuments",
        identityType = IdentityType.DATASTORE)
@javax.jdo.annotations.DatastoreIdentity(
        strategy = IdGeneratorStrategy.NATIVE,
        column = "id")
@javax.jdo.annotations.Version(
        strategy = VersionStrategy.VERSION_NUMBER,
        column = "version")
@javax.jdo.annotations.Queries({
    // none currently
})
@javax.jdo.annotations.Uniques({
        @javax.jdo.annotations.Unique(
                name = "Applicability_documentTemplate_domainClassName_UNQ",
                members = { "documentTemplate", "domainClassName" } )
})
@DomainObject(
        editing = Editing.DISABLED
)
@DomainObjectLayout(
        titleUiEvent = Applicability.TitleUiEvent.class,
        iconUiEvent = Applicability.IconUiEvent.class,
        cssClassUiEvent = Applicability.CssClassUiEvent.class
)
public class Applicability implements Comparable<Applicability> {

    //region > ui event classes
    public static class TitleUiEvent extends DocumentModule.TitleUiEvent<Applicability>{}
    public static class IconUiEvent extends DocumentModule.IconUiEvent<Applicability>{}
    public static class CssClassUiEvent extends DocumentModule.CssClassUiEvent<Applicability>{}
    //endregion

    //region > event classes
    public static abstract class PropertyDomainEvent<T> extends DocumentModule.PropertyDomainEvent<Applicability, T> { }
    public static abstract class CollectionDomainEvent<T> extends DocumentModule.CollectionDomainEvent<Applicability, T> { }
    public static abstract class ActionDomainEvent extends DocumentModule.ActionDomainEvent<Applicability> { }
    //endregion

    //region > title, icon, cssClass
    /**
     * Implemented as a subscriber so can be overridden by consuming application if required.
     */
    @DomainService(nature = NatureOfService.DOMAIN)
    public static class TitleSubscriber extends AbstractSubscriber {

        public String getId() {
            return "incodeDocuments.Applicability$TitleSubscriber";
        }

        @EventHandler
        @Subscribe
        public void on(Applicability.TitleUiEvent ev) {
            if(ev.getTitle() != null) {
                return;
            }
            ev.setTitle(titleOf(ev.getSource()));
        }
        private String titleOf(final Applicability applicability) {
            final TitleBuffer buf = new TitleBuffer();
            buf.append(simpleNameOf(applicability.getDomainClassName()));
            // can't use titleService.titleOf(...) if using guava, can't call events within events...
            buf.append(applicability.getDocumentTemplate().getName());
            return buf.toString();
        }
        private static String simpleNameOf(final String domainType) {
            int lastDot = domainType.lastIndexOf(".");
            return domainType.substring(lastDot+1);
        }
    }

    @DomainService(nature = NatureOfService.DOMAIN)
    public static class IconSubscriber extends AbstractSubscriber {

        public String getId() {
            return "incodeDocuments.Applicability$IconSubscriber";
        }

        @EventHandler
        @Subscribe
        public void on(Applicability.IconUiEvent ev) {
            if(ev.getIconName() != null) {
                return;
            }
            ev.setIconName("");
        }
    }

    /**
     * Implemented as a subscriber so can be overridden by consuming application if required.
     */
    @DomainService(nature = NatureOfService.DOMAIN)
    public static class CssClassSubscriber extends AbstractSubscriber {

        public String getId() {
            return "incodeDocuments.Applicability$CssClassSubscriber";
        }

        @EventHandler
        @Subscribe
        public void on(Applicability.CssClassUiEvent ev) {
            if(ev.getCssClass() != null) {
                return;
            }
            ev.setCssClass("");
        }
    }
    //endregion

    //region > constructor
    Applicability() {
        // for testing only
    }
    public Applicability(
            final DocumentTemplate documentTemplate,
            final Class<?> domainClass,
            final Class<? extends RendererModelFactory> rendererModelFactoryClass,
            final Class<? extends AttachmentAdvisor> attachmentAdvisorClass
            ) {
        this(documentTemplate, domainClass.getName(), rendererModelFactoryClass.getName(), attachmentAdvisorClass.getName());
    }

    public Applicability(
            final DocumentTemplate documentTemplate,
            final String domainClassName,
            final String rendererModelFactoryClassName,
            final String attachmentAdvisorClassName) {
        setDocumentTemplate(documentTemplate);
        setDomainClassName(domainClassName);
        setRendererModelFactoryClassName(rendererModelFactoryClassName);
        setAttachmentAdvisorClassName(attachmentAdvisorClassName);
    }
    //endregion


    //region > documentTemplate (property)
    public static class DocumentTemplateDomainEvent extends PropertyDomainEvent<DocumentTemplate> { }
    @Getter @Setter
    @javax.jdo.annotations.Column(allowsNull = "false", name = "documentTemplateId")
    @Property(
            domainEvent = DocumentTemplateDomainEvent.class,
            editing = Editing.DISABLED
    )
    private DocumentTemplate documentTemplate;
    // endregion

    //region > domainClassName (property)
    public static class DomainClassNameDomainEvent extends PropertyDomainEvent<String> { }

    /**
     * The class used as the input for the document
     */
    @Getter @Setter
    @javax.jdo.annotations.Column(allowsNull = "false", length = FqcnType.Meta.MAX_LEN)
    @Property(
            domainEvent = DomainClassNameDomainEvent.class
    )
    private String domainClassName;

    // endregion

    //region > rendererModelFactoryClassName (property)
    public static class RendererModelFactoryClassNameDomainEvent extends PropertyDomainEvent<String> { }
    @Getter @Setter
    @javax.jdo.annotations.Column(allowsNull = "false", length = FqcnType.Meta.MAX_LEN)
    @Property(
            domainEvent = RendererModelFactoryClassNameDomainEvent.class
    )
    private String rendererModelFactoryClassName;
    // endregion

    //region > attachmentAdvisorProviderClassName (property)
    public static class AttachmentAdvisorClassNameDomainEvent extends PropertyDomainEvent<String> { }
    @Getter @Setter
    @javax.jdo.annotations.Column(allowsNull = "false", length = FqcnType.Meta.MAX_LEN)
    @Property(
            domainEvent = AttachmentAdvisorClassNameDomainEvent.class
    )
    private String attachmentAdvisorClassName;
    // endregion


    //region > toString, compareTo
    @Override
    public String toString() {
        return ObjectContracts.toString(this, "documentTemplate", "domainClassName", "rendererModelFactoryClassName", "attachmentAdvisorClassName");
    }

    @Override
    public int compareTo(final Applicability other) {
        return ObjectContracts.compare(this, other, "documentTemplate", "domainClassName", "rendererModelFactoryClassName", "attachmentAdvisorClassName");
    }

    //endregion

}
