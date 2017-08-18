package org.incode.domainapp.example.app.services.homepage;

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
import org.isisaddons.module.settings.dom.ApplicationSetting;
import org.isisaddons.module.settings.dom.ApplicationSettingsServiceRW;
import org.isisaddons.module.settings.dom.UserSetting;
import org.isisaddons.module.settings.dom.UserSettingsServiceRW;
import org.isisaddons.module.tags.dom.Tag;
import org.isisaddons.wicket.pdfjs.cpt.applib.PdfJsViewer;

import org.incode.domainapp.example.dom.demo.dom.customer.DemoCustomer;
import org.incode.domainapp.example.dom.demo.dom.customer.DemoCustomerRepository;
import org.incode.domainapp.example.dom.demo.dom.demo.DemoObject;
import org.incode.domainapp.example.dom.demo.dom.demo.DemoObjectRepository;
import org.incode.domainapp.example.dom.demo.dom.demowithall.DemoObjectWithAll;
import org.incode.domainapp.example.dom.demo.dom.demowithall.DemoObjectWithAllMenu;
import org.incode.domainapp.example.dom.demo.dom.demowithatpath.DemoObjectWithAtPath;
import org.incode.domainapp.example.dom.demo.dom.demowithatpath.DemoObjectWithAtPathMenu;
import org.incode.domainapp.example.dom.demo.dom.demowithblob.DemoObjectWithBlob;
import org.incode.domainapp.example.dom.demo.dom.demowithblob.DemoObjectWithBlobMenu;
import org.incode.domainapp.example.dom.demo.dom.demowithnotes.DemoObjectWithNotes;
import org.incode.domainapp.example.dom.demo.dom.demowithnotes.DemoObjectWithNotesRepository;
import org.incode.domainapp.example.dom.demo.dom.demowithurl.DemoObjectWithUrl;
import org.incode.domainapp.example.dom.demo.dom.demowithurl.DemoObjectWithUrlMenu;
import org.incode.domainapp.example.dom.demo.dom.invoice.DemoInvoice;
import org.incode.domainapp.example.dom.demo.dom.invoice.DemoInvoiceRepository;
import org.incode.domainapp.example.dom.demo.dom.invoicewithatpath.DemoInvoiceWithAtPath;
import org.incode.domainapp.example.dom.demo.dom.invoicewithatpath.DemoInvoiceWithAtPathRepository;
import org.incode.domainapp.example.dom.demo.dom.order.DemoOrder;
import org.incode.domainapp.example.dom.demo.dom.order.DemoOrderMenu;
import org.incode.domainapp.example.dom.demo.dom.other.OtherObject;
import org.incode.domainapp.example.dom.demo.dom.other.OtherObjectMenu;
import org.incode.domainapp.example.dom.demo.dom.otherwithatpath.OtherObjectWithAtPath;
import org.incode.domainapp.example.dom.demo.dom.otherwithatpath.OtherObjectWithAtPathMenu;
import org.incode.domainapp.example.dom.demo.dom.reminder.DemoReminder;
import org.incode.domainapp.example.dom.demo.dom.reminder.DemoReminderMenu;
import org.incode.domainapp.example.dom.demo.dom.todo.DemoToDoItem;
import org.incode.domainapp.example.dom.demo.dom.todo.DemoToDoItemMenu;
import org.incode.domainapp.example.dom.dom.classification.dom.menu.TaxonomyMenu;
import org.incode.domainapp.example.dom.dom.document.dom.menu.DocumentTypeMenu;
import org.incode.domainapp.example.dom.dom.tags.dom.demo.DemoTaggableObject;
import org.incode.domainapp.example.dom.dom.tags.dom.demo.DemoTaggableObjectMenu;
import org.incode.domainapp.example.dom.lib.poly.dom.democasemgmt.Case;
import org.incode.domainapp.example.dom.lib.poly.dom.democasemgmt.Cases;
import org.incode.domainapp.example.dom.lib.poly.dom.democommchannel.CommunicationChannel;
import org.incode.domainapp.example.dom.lib.poly.dom.democommchannel.CommunicationChannels;
import org.incode.domainapp.example.dom.lib.poly.dom.demofixedasset.FixedAsset;
import org.incode.domainapp.example.dom.lib.poly.dom.demofixedasset.FixedAssets;
import org.incode.domainapp.example.dom.lib.poly.dom.demoparty.Parties;
import org.incode.domainapp.example.dom.lib.poly.dom.demoparty.Party;
import org.incode.domainapp.example.dom.lib.servletapi.dom.demo.ServletApiDemoObject;
import org.incode.domainapp.example.dom.spi.audit.dom.demo.audited.SomeAuditedObject;
import org.incode.domainapp.example.dom.spi.audit.dom.demo.audited.SomeAuditedObjects;
import org.incode.domainapp.example.dom.spi.audit.dom.demo.notaudited.SomeNotAuditedObject;
import org.incode.domainapp.example.dom.spi.audit.dom.demo.notaudited.SomeNotAuditedObjects;
import org.incode.domainapp.example.dom.spi.command.dom.demo.SomeCommandAnnotatedObject;
import org.incode.domainapp.example.dom.spi.command.dom.demo.SomeCommandAnnotatedObjects;
import org.incode.domainapp.example.dom.spi.publishmq.dom.demo.PublishMqDemoObject;
import org.incode.domainapp.example.dom.spi.publishmq.dom.demo.PublishMqDemoObjects;
import org.incode.domainapp.example.dom.spi.security.dom.demo.nontenanted.NonTenantedEntities;
import org.incode.domainapp.example.dom.spi.security.dom.demo.nontenanted.NonTenantedEntity;
import org.incode.domainapp.example.dom.spi.security.dom.demo.tenanted.TenantedEntities;
import org.incode.domainapp.example.dom.spi.security.dom.demo.tenanted.TenantedEntity;
import org.incode.module.alias.dom.impl.Alias;
import org.incode.module.alias.dom.impl.AliasRepository;
import org.incode.module.alias.dom.spi.AliasTypeRepository;
import org.incode.module.classification.dom.impl.applicability.Applicability;
import org.incode.module.classification.dom.impl.category.Category;
import org.incode.module.classification.dom.impl.classification.Classification;
import org.incode.module.commchannel.dom.impl.emailaddress.EmailAddress;
import org.incode.module.commchannel.dom.impl.phoneorfax.PhoneOrFaxNumber;
import org.incode.module.commchannel.dom.impl.postaladdress.PostalAddress;
import org.incode.module.communications.dom.impl.comms.CommChannelRole;
import org.incode.module.communications.dom.impl.comms.Communication;
import org.incode.module.communications.dom.impl.paperclips.PaperclipForCommunication;
import org.incode.module.country.dom.impl.Country;
import org.incode.module.country.dom.impl.State;
import org.incode.module.docfragment.dom.impl.DocFragment;
import org.incode.module.docfragment.dom.impl.DocFragmentRepository;
import org.incode.module.document.dom.impl.docs.DocumentAbstract;
import org.incode.module.document.dom.impl.docs.DocumentRepository;
import org.incode.module.document.dom.impl.docs.DocumentTemplate;
import org.incode.module.document.dom.impl.docs.DocumentTemplateRepository;
import org.incode.module.document.dom.impl.rendering.RenderingStrategy;
import org.incode.module.document.dom.impl.rendering.RenderingStrategyRepository;
import org.incode.module.document.dom.impl.types.DocumentType;
import org.incode.module.note.dom.impl.note.Note;
import org.incode.module.note.dom.impl.note.NoteRepository;

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

    public List<org.incode.module.communications.dom.impl.commchannel.PostalAddress> getPostalAddresses() {
        return repositoryService.allInstances(org.incode.module.communications.dom.impl.commchannel.PostalAddress.class);
    }

    public List<org.incode.module.communications.dom.impl.commchannel.EmailAddress> getEmailAddresses() {
        return repositoryService.allInstances(org.incode.module.communications.dom.impl.commchannel.EmailAddress.class);
    }

    public List<org.incode.module.communications.dom.impl.commchannel.PhoneOrFaxNumber> getPhoneOrFaxNumbers() {
        return repositoryService.allInstances(org.incode.module.communications.dom.impl.commchannel.PhoneOrFaxNumber.class);
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

    public List<org.incode.module.document.dom.impl.applicability.Applicability> getDocumentApplicabilities() {
        return repositoryService.allInstances(org.incode.module.document.dom.impl.applicability.Applicability.class);
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
