package org.incode.domainapp.extended.app.services.homepage;

import java.util.List;

import javax.inject.Inject;
import javax.xml.bind.annotation.XmlTransient;

import com.google.common.eventbus.Subscribe;

import org.axonframework.eventhandling.annotation.EventHandler;
import org.wicketstuff.pdfjs.Scale;

import org.apache.isis.applib.AbstractSubscriber;
import org.apache.isis.applib.annotation.DomainObject;
import org.apache.isis.applib.annotation.DomainService;
import org.apache.isis.applib.annotation.Nature;
import org.apache.isis.applib.annotation.NatureOfService;
import org.apache.isis.applib.annotation.Property;
import org.apache.isis.applib.annotation.Where;
import org.apache.isis.applib.services.factory.FactoryService;
import org.apache.isis.applib.services.repository.RepositoryService;
import org.apache.isis.applib.services.scratchpad.Scratchpad;
import org.apache.isis.applib.value.Blob;

import org.isisaddons.module.audit.dom.AuditEntry;
import org.isisaddons.module.command.dom.CommandJdo;
import org.isisaddons.module.command.dom.CommandServiceJdoRepository;
import org.isisaddons.module.publishmq.dom.jdo.events.PublishedEvent;
import org.isisaddons.module.publishmq.dom.jdo.events.PublishedEventRepository;
import org.isisaddons.module.publishmq.dom.jdo.status.StatusMessage;
import org.isisaddons.module.security.dom.permission.ApplicationPermission;
import org.isisaddons.module.security.dom.permission.ApplicationPermissionMenu;
import org.isisaddons.module.security.dom.role.ApplicationRole;
import org.isisaddons.module.security.dom.role.ApplicationRoleMenu;
import org.isisaddons.module.security.dom.tenancy.ApplicationTenancy;
import org.isisaddons.module.security.dom.tenancy.ApplicationTenancyMenu;
import org.isisaddons.module.security.dom.user.ApplicationUser;
import org.isisaddons.module.security.dom.user.ApplicationUserMenu;
import org.isisaddons.module.sessionlogger.dom.SessionLogEntry;
import org.isisaddons.wicket.pdfjs.cpt.applib.PdfJsViewer;

import org.incode.domainapp.extended.module.fixtures.per_cpt.examples.classification.dom.menu.TaxonomyMenu;
import org.incode.domainapp.extended.module.fixtures.per_cpt.examples.document.dom.menu.DocumentTypeMenu;
import org.incode.domainapp.extended.module.fixtures.per_cpt.examples.tags.dom.demo.DemoTaggableObject;
import org.incode.domainapp.extended.module.fixtures.per_cpt.examples.tags.dom.demo.DemoTaggableObjectMenu;
import org.incode.domainapp.extended.module.fixtures.per_cpt.lib.poly.dom.democasemgmt.Case;
import org.incode.domainapp.extended.module.fixtures.per_cpt.lib.poly.dom.democasemgmt.Cases;
import org.incode.domainapp.extended.module.fixtures.per_cpt.lib.poly.dom.democommchannel.CommunicationChannel;
import org.incode.domainapp.extended.module.fixtures.per_cpt.lib.poly.dom.democommchannel.CommunicationChannels;
import org.incode.domainapp.extended.module.fixtures.per_cpt.lib.poly.dom.demofixedasset.FixedAsset;
import org.incode.domainapp.extended.module.fixtures.per_cpt.lib.poly.dom.demofixedasset.FixedAssets;
import org.incode.domainapp.extended.module.fixtures.per_cpt.lib.poly.dom.demoparty.Parties;
import org.incode.domainapp.extended.module.fixtures.per_cpt.lib.poly.dom.demoparty.Party;
import org.incode.domainapp.extended.module.fixtures.per_cpt.lib.servletapi.dom.demo.ServletApiDemoObject;
import org.incode.domainapp.extended.module.fixtures.per_cpt.spi.audit.dom.demo.audited.SomeAuditedObject;
import org.incode.domainapp.extended.module.fixtures.per_cpt.spi.audit.dom.demo.audited.SomeAuditedObjects;
import org.incode.domainapp.extended.module.fixtures.per_cpt.spi.audit.dom.demo.notaudited.SomeNotAuditedObject;
import org.incode.domainapp.extended.module.fixtures.per_cpt.spi.audit.dom.demo.notaudited.SomeNotAuditedObjects;
import org.incode.domainapp.extended.module.fixtures.per_cpt.spi.command.dom.demo.SomeCommandAnnotatedObject;
import org.incode.domainapp.extended.module.fixtures.per_cpt.spi.command.dom.demo.SomeCommandAnnotatedObjects;
import org.incode.domainapp.extended.module.fixtures.per_cpt.spi.publishmq.dom.demo.PublishMqDemoObject;
import org.incode.domainapp.extended.module.fixtures.per_cpt.spi.publishmq.dom.demo.PublishMqDemoObjects;
import org.incode.domainapp.extended.module.fixtures.per_cpt.spi.security.dom.demo.nontenanted.NonTenantedEntities;
import org.incode.domainapp.extended.module.fixtures.per_cpt.spi.security.dom.demo.nontenanted.NonTenantedEntity;
import org.incode.domainapp.extended.module.fixtures.per_cpt.spi.security.dom.demo.tenanted.TenantedEntities;
import org.incode.domainapp.extended.module.fixtures.per_cpt.spi.security.dom.demo.tenanted.TenantedEntity;
import org.incode.domainapp.extended.module.fixtures.shared.customer.dom.DemoCustomer;
import org.incode.domainapp.extended.module.fixtures.shared.customer.dom.DemoCustomerRepository;
import org.incode.domainapp.extended.module.fixtures.shared.demo.dom.DemoObject;
import org.incode.domainapp.extended.module.fixtures.shared.demo.dom.DemoObjectRepository;
import org.incode.domainapp.extended.module.fixtures.shared.demowithall.dom.DemoObjectWithAll;
import org.incode.domainapp.extended.module.fixtures.shared.demowithall.dom.DemoObjectWithAllMenu;
import org.incode.domainapp.extended.module.fixtures.shared.demowithatpath.dom.DemoObjectWithAtPath;
import org.incode.domainapp.extended.module.fixtures.shared.demowithatpath.dom.DemoObjectWithAtPathMenu;
import org.incode.domainapp.extended.module.fixtures.shared.demowithblob.dom.DemoObjectWithBlob;
import org.incode.domainapp.extended.module.fixtures.shared.demowithblob.dom.DemoObjectWithBlobMenu;
import org.incode.domainapp.extended.module.fixtures.shared.demowithnotes.dom.DemoObjectWithNotes;
import org.incode.domainapp.extended.module.fixtures.shared.demowithnotes.dom.DemoObjectWithNotesRepository;
import org.incode.domainapp.extended.module.fixtures.shared.demowithurl.dom.DemoObjectWithUrl;
import org.incode.domainapp.extended.module.fixtures.shared.demowithurl.dom.DemoObjectWithUrlMenu;
import org.incode.domainapp.extended.module.fixtures.shared.demowithnotes.dom.DemoInvoice;
import org.incode.domainapp.extended.module.fixtures.shared.invoice.dom.DemoInvoiceRepository;
import org.incode.domainapp.extended.module.fixtures.shared.invoicewithatpath.dom.DemoInvoiceWithAtPath;
import org.incode.domainapp.extended.module.fixtures.shared.invoicewithatpath.dom.DemoInvoiceWithAtPathRepository;
import org.incode.domainapp.extended.module.fixtures.shared.order.dom.DemoOrder;
import org.incode.domainapp.extended.module.fixtures.shared.order.dom.DemoOrderMenu;
import org.incode.domainapp.extended.module.fixtures.shared.other.dom.OtherObject;
import org.incode.domainapp.extended.module.fixtures.shared.other.dom.OtherObjectMenu;
import org.incode.domainapp.extended.module.fixtures.shared.otherwithatpath.dom.OtherObjectWithAtPath;
import org.incode.domainapp.extended.module.fixtures.shared.otherwithatpath.dom.OtherObjectWithAtPathMenu;
import org.incode.domainapp.extended.module.fixtures.shared.reminder.dom.DemoReminder;
import org.incode.domainapp.extended.module.fixtures.shared.reminder.dom.DemoReminderMenu;
import org.incode.domainapp.extended.module.fixtures.shared.todo.dom.DemoToDoItem;
import org.incode.domainapp.extended.module.fixtures.shared.todo.dom.DemoToDoItemMenu;
import org.incode.example.alias.dom.impl.Alias;
import org.incode.example.alias.dom.impl.AliasRepository;
import org.incode.example.alias.dom.spi.AliasTypeRepository;
import org.incode.example.classification.dom.impl.applicability.Applicability;
import org.incode.example.classification.dom.impl.category.Category;
import org.incode.example.classification.dom.impl.classification.Classification;
import org.incode.example.commchannel.dom.impl.emailaddress.EmailAddress;
import org.incode.example.commchannel.dom.impl.phoneorfax.PhoneOrFaxNumber;
import org.incode.example.commchannel.dom.impl.postaladdress.PostalAddress;
import org.incode.example.communications.dom.impl.comms.CommChannelRole;
import org.incode.example.communications.dom.impl.comms.Communication;
import org.incode.example.communications.dom.impl.paperclips.PaperclipForCommunication;
import org.incode.example.country.dom.impl.Country;
import org.incode.example.country.dom.impl.State;
import org.incode.example.docfragment.dom.impl.DocFragment;
import org.incode.example.docfragment.dom.impl.DocFragmentRepository;
import org.incode.example.document.dom.impl.docs.DocumentAbstract;
import org.incode.example.document.dom.impl.docs.DocumentRepository;
import org.incode.example.document.dom.impl.docs.DocumentTemplate;
import org.incode.example.document.dom.impl.docs.DocumentTemplateRepository;
import org.incode.example.document.dom.impl.rendering.RenderingStrategy;
import org.incode.example.document.dom.impl.rendering.RenderingStrategyRepository;
import org.incode.example.document.dom.impl.types.DocumentType;
import org.incode.example.note.dom.impl.note.Note;
import org.incode.example.note.dom.impl.note.NoteRepository;
import org.incode.example.settings.dom.ApplicationSetting;
import org.incode.example.settings.dom.ApplicationSettingsServiceRW;
import org.incode.example.settings.dom.UserSetting;
import org.incode.example.settings.dom.UserSettingsServiceRW;
import org.incode.example.tags.dom.Tag;

import lombok.Getter;
import lombok.Setter;

@DomainObject(
        nature = Nature.VIEW_MODEL,
        objectType = "org.incode.domainapp.example.app.HomePageViewModel"
)
public class HomePageViewModel {

    public String title() {
        return "Home page";
    }

    // demo

    public List<DemoObject> getDemoObjects() {
        return demoObjectRepository.listAll();
    }

    public List<DemoObjectWithAll> getDemoObjectsWithAll() {
        return demoObjectWithAllMenu.listAllDemoObjectsWithAll();
    }

    public List<DemoObjectWithAtPath> getDemoObjectsWithAtPath() {
        return demoObjectWithAtPathMenu.listAllDemoObjectsWithAtPath();
    }

    public List<DemoObjectWithBlob> getDemoObjectsWithBlob() {
        return demoObjectWithBlobMenu.listAllDemoObjectsWithBlob();
    }

    public List<DemoObjectWithNotes> getDemoObjectWithNotes() {
        return demoObjectWithNotesRepository.listAll();
    }

    public List<DemoObjectWithUrl> getDemoObjectsWithUrl() {
        return demoObjectWithUrlMenu.listAllDemoObjectsWithUrl();
    }

    @javax.inject.Inject
    DemoObjectWithUrlMenu demoObjectWithUrlMenu;


    @javax.inject.Inject
    DemoObjectRepository demoObjectRepository;

    @javax.inject.Inject
    DemoObjectWithAllMenu demoObjectWithAllMenu;

    @javax.inject.Inject
    DemoObjectWithAtPathMenu demoObjectWithAtPathMenu;

    @javax.inject.Inject
    DemoObjectWithBlobMenu demoObjectWithBlobMenu;

    @javax.inject.Inject
    DemoObjectWithNotesRepository demoObjectWithNotesRepository;



    // demo (other)

    public List<OtherObject> getOtherObjects() {
        return otherObjectMenu.listAllOtherObjects();
    }


    public List<OtherObjectWithAtPath> getOtherObjectsWithAtPath() {
        return otherObjectWithAtPathMenu.listAllOtherObjectsWithAtPath();
    }

    @javax.inject.Inject
    OtherObjectMenu otherObjectMenu;

    @javax.inject.Inject
    OtherObjectWithAtPathMenu otherObjectWithAtPathMenu;




    // more demo

    public List<DemoCustomer> getDemoCustomers() {
        return demoCustomerRepository.listAll();
    }

    public List<DemoInvoice> getDemoInvoices() {
        return demoInvoiceRepository.listAll();
    }

    public List<DemoInvoiceWithAtPath> getDemoInvoicesWithAtPath() {
        return demoInvoiceWithAtPathRepository.listAll();
    }

    public List<DemoOrder> getDemoOrders() {
        return demoOrderMenu.listAllDemoOrders();
    }

    public List<DemoReminder> getDemoReminders() {
        return demoReminderMenu.listAllReminders();
    }

    public List<DemoToDoItem> getDemoToDoItems() {
        return demoToDoItemMenu.allInstances();
    }

    @javax.inject.Inject
    DemoCustomerRepository demoCustomerRepository;

    @javax.inject.Inject
    DemoInvoiceRepository demoInvoiceRepository;

    @javax.inject.Inject
    DemoInvoiceWithAtPathRepository demoInvoiceWithAtPathRepository;

    @javax.inject.Inject
    DemoOrderMenu demoOrderMenu;

    @javax.inject.Inject
    DemoReminderMenu demoReminderMenu;

    @javax.inject.Inject
    DemoToDoItemMenu demoToDoItemMenu;



    // alias

    public List<Alias> getAliases() {
        return repositoryService.allInstances(Alias.class);
    }

    @Inject
    AliasTypeRepository aliasTypeRepository;

    @Inject
    AliasRepository aliasRepository;


    // classification

    public List<Category> getTaxonomies() {
        return taxonomyMenu.listAllTaxonomies();
    }

    public List<Category> getCategories() {
        return repositoryService.allInstances(Category.class);
    }

    public List<Applicability> getClassificationApplicabilities() {
        return repositoryService.allInstances(Applicability.class);
    }

    public List<Classification> getClassifications() {
        return repositoryService.allInstances(Classification.class);
    }

    @Inject
    TaxonomyMenu taxonomyMenu;



    // comm channels

    public List<PostalAddress> getCcPostalAddresses() {
        return repositoryService.allInstances(PostalAddress.class);
    }

    public List<EmailAddress> getCcEmailAddresses() {
        return repositoryService.allInstances(EmailAddress.class);
    }

    public List<PhoneOrFaxNumber> getCcPhoneOrFaxNumbers() {
        return repositoryService.allInstances(PhoneOrFaxNumber.class);
    }

    // communications

    public List<CommChannelRole> getCommChannelRoles() {
        return repositoryService.allInstances(CommChannelRole.class);
    }

    public List<org.incode.example.communications.dom.impl.commchannel.PostalAddress> getPostalAddresses() {
        return repositoryService.allInstances(org.incode.example.communications.dom.impl.commchannel.PostalAddress.class);
    }

    public List<org.incode.example.communications.dom.impl.commchannel.EmailAddress> getEmailAddresses() {
        return repositoryService.allInstances(org.incode.example.communications.dom.impl.commchannel.EmailAddress.class);
    }

    public List<org.incode.example.communications.dom.impl.commchannel.PhoneOrFaxNumber> getPhoneOrFaxNumbers() {
        return repositoryService.allInstances(org.incode.example.communications.dom.impl.commchannel.PhoneOrFaxNumber.class);
    }

    public List<PaperclipForCommunication> getPaperclipForCommunications() {
        return repositoryService.allInstances(PaperclipForCommunication.class);
    }

    public List<Communication> getCommunications() {
        return repositoryService.allInstances(Communication.class);
    }



    // country

    public List<Country> getCountries() {
        return repositoryService.allInstances(Country.class);
    }

    public List<State> getStates() {
        return repositoryService.allInstances(State.class);
    }



    // docfragment

    public List<DocFragment> getDocFragments() {
        return docfragmentRepository.listAll();
    }

    @javax.inject.Inject
    DocFragmentRepository docfragmentRepository;




    // document

    public List<DocumentType> getDocumentTypes() {
        return documentTypeMenu.allDocumentTypes();
    }

    public List<RenderingStrategy> getRenderingStrategies() {
        return renderingStrategyRepository.allStrategies();
    }

    public List<DocumentTemplate> getDocumentTemplates() {
        return documentTemplateRepository.allTemplates();
    }

    public List<org.incode.example.document.dom.impl.applicability.Applicability> getDocumentApplicabilities() {
        return repositoryService.allInstances(org.incode.example.document.dom.impl.applicability.Applicability.class);
    }


    public List<DocumentAbstract> getDocuments() {
        return documentRepository.allDocuments();
    }

    @javax.inject.Inject
    DocumentTypeMenu documentTypeMenu;

    @javax.inject.Inject
    RenderingStrategyRepository renderingStrategyRepository;

    @javax.inject.Inject
    DocumentTemplateRepository documentTemplateRepository;

    @javax.inject.Inject
    DocumentRepository documentRepository;


    // note

    public List<Note> getNotes() {
        return noteRepository.allNotes();
    }

    @Inject
    NoteRepository noteRepository;

    // settings

    public List<ApplicationSetting> getApplicationSettings() {
        return applicationSettingsServiceRW.listAll();
    }

    @Inject
    ApplicationSettingsServiceRW applicationSettingsServiceRW;

    public List<UserSetting> getUserSettings() {
        return userSettingsServiceRW.listAll();
    }

    @Inject
    UserSettingsServiceRW userSettingsServiceRW;

    // tags

    public List<DemoTaggableObject> getDemoTaggableObjects() {
        return taggableObjectMenu.listAllTaggableObjects();
    }

    public List<Tag> getTags() {
        return repositoryService.allInstances(Tag.class);
    }

    @Inject
    DemoTaggableObjectMenu taggableObjectMenu;

    @Inject RepositoryService repositoryService;




    // poly
    public List<CommunicationChannel> getCommunicationChannels() {
        return communicationChannelsMenu.listAllCommunicationChannels();
    }

    public List<Party> getParties() {
        return parties.listAllParties();
    }

    public List<FixedAsset> getFixedAssets() {
        return fixedAssets.listAllFixedAssets();
    }

    public List<Case> getCases() {
        return cases.listAllCases();
    }

    @javax.inject.Inject
    CommunicationChannels communicationChannelsMenu;

    @javax.inject.Inject
    Parties parties;

    @javax.inject.Inject
    FixedAssets fixedAssets;

    @javax.inject.Inject
    Cases cases;




    // servlet api

    public List<ServletApiDemoObject> getServletApiDemoObjects() {
        return repositoryService.allInstances(ServletApiDemoObject.class);
    }



    // audit

    public List<SomeAuditedObject> getSomeAuditedObjects() {
        return someAuditedObjects.listAllSomeAuditedObjects();
    }

    public List<SomeNotAuditedObject> getSomeNotAuditedObjects() {
        return someNotAuditedObjects.listAllSomeNotAuditedObjects();
    }

    public List<AuditEntry> getAuditEntries() {
        return repositoryService.allInstances(AuditEntry.class);
    }

    @Inject
    SomeAuditedObjects someAuditedObjects;

    @Inject
    SomeNotAuditedObjects someNotAuditedObjects;



    // command

    public List<SomeCommandAnnotatedObject> getSomeCommandAnnotatedObjects() {
        return someCommandAnnotatedObjects.listAllSomeCommandAnnotatedObjects();
    }

    public List<CommandJdo> getCommands() {
        return repositoryService.allInstances(CommandJdo.class);
    }


    @Inject
    SomeCommandAnnotatedObjects someCommandAnnotatedObjects;

    @Inject
    CommandServiceJdoRepository commandServiceJdoRepository;


    // publishmq

    public List<PublishMqDemoObject> getPublishMqDemoObjects() {
        return publishMqDemoObjects.listAllPublishMqDemoObjects();
    }

    public List<PublishedEvent> getPublishedEvents() {
        return repositoryService.allInstances(PublishedEvent.class);
    }

    public List<StatusMessage> getStatusMessages() {
        return repositoryService.allInstances(StatusMessage.class);
    }



    @Inject
    PublishMqDemoObjects publishMqDemoObjects;

    @Inject
    PublishedEventRepository publishedEventRepository;

    // security

    public List<ApplicationUser> getApplicationUsers() {
        return applicationUserMenu.allUsers();
    }

    public List<ApplicationRole> getApplicationRoles() {
        return applicationRoleMenu.allRoles();
    }

    public List<ApplicationPermission> getApplicationPermissions() {
        return applicationPermissionMenu.allPermissions();
    }

    public List<ApplicationTenancy> getApplicationTenancies() {
        return applicationTenancyMenu.allTenancies();
    }

    public List<TenantedEntity> getTenantedEntities() {
        return tenantedEntities.listAllTenantedEntities();
    }

    public List<NonTenantedEntity> getNonTenantedEntities() {
        return nonTenantedEntities.listAllNonTenantedEntities();
    }

    @Inject
    ApplicationUserMenu applicationUserMenu;

    @Inject
    ApplicationRoleMenu applicationRoleMenu;

    @Inject
    ApplicationPermissionMenu applicationPermissionMenu;

    @Inject
    ApplicationTenancyMenu applicationTenancyMenu;

    @Inject
    TenantedEntities tenantedEntities;

    @Inject
    NonTenantedEntities nonTenantedEntities;

    // sessionlogger

    public List<SessionLogEntry> getSessionLogEntries() {
        return repositoryService.allInstances(SessionLogEntry.class);
    }


    // fullcalendar2

    public List<DemoToDoItem> getDemoToDoItemsNotYetComplete() {
        return demoToDoItemMenu.notYetCompleteNoUi();
    }




    // pdf.js

    @DomainService(nature = NatureOfService.DOMAIN)
    public static class CssHighlighter extends AbstractSubscriber {

        @EventHandler
        @Subscribe
        public void on(DemoObjectWithBlob.CssClassUiEvent ev) {
            if(getContext() == null) {
                return;
            }
            DemoObjectWithBlob selectedDemoObject = getContext().getSelected();
            if(ev.getSource() == selectedDemoObject) {
                ev.setCssClass("selected");
            }
        }

        private HomePageViewModel getContext() {
            return (HomePageViewModel) scratchpad.get("context");
        }
        void setContext(final HomePageViewModel homePageViewModel) {
            scratchpad.put("context", homePageViewModel);
        }

        @Inject
        Scratchpad scratchpad;
    }



    @PdfJsViewer(initialPageNum = 1, initialScale = Scale._1_00, initialHeight = 600)
    public Blob getBlob() {
        return getSelected() != null ? getSelected().getBlob() : null;
    }



    @Property(hidden = Where.EVERYWHERE)
    public int getNumObjects() {
        return getDemoObjectsWithBlob().size();
    }


    @Property
    @Getter @Setter
    private int idx;


    @XmlTransient
    public DemoObjectWithBlob getSelected() {
        final List<DemoObjectWithBlob> demoObjectsWithBlob = getDemoObjectsWithBlob();
        return demoObjectsWithBlob.isEmpty() ? null : demoObjectsWithBlob.get(getIdx());
    }


    public HomePageViewModel previous() {
        final HomePageViewModel viewModel = factoryService.instantiate(HomePageViewModel.class);
        viewModel.setIdx(getIdx()-1);
        return viewModel;
    }
    public String disablePrevious() {
        return getIdx() == 0 ? "At start" : null;
    }


    public HomePageViewModel next() {
        final HomePageViewModel viewModel = factoryService.instantiate(HomePageViewModel.class);
        viewModel.setIdx(getIdx()+1);
        return viewModel;
    }
    public String disableNext() {
        return getIdx() == getNumObjects() - 1 ? "At end" : null;
    }


    @javax.inject.Inject
    FactoryService factoryService;


    @javax.inject.Inject
    CssHighlighter cssHighlighter;



}
