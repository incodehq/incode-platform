package org.incode.domainapp.extended.appdefn.services.homepage;

import java.util.List;

import javax.inject.Inject;
import javax.xml.bind.annotation.XmlTransient;

import org.wicketstuff.pdfjs.Scale;

import org.apache.isis.applib.annotation.DomainObject;
import org.apache.isis.applib.annotation.Nature;
import org.apache.isis.applib.annotation.Property;
import org.apache.isis.applib.annotation.Where;
import org.apache.isis.applib.services.factory.FactoryService;
import org.apache.isis.applib.services.repository.RepositoryService;
import org.apache.isis.applib.value.Blob;

import org.isisaddons.module.audit.dom.AuditEntry;
import org.isisaddons.module.audit.fixture.demoapp.demomodule.dom.audited.SomeAuditedObject;
import org.isisaddons.module.audit.fixture.demoapp.demomodule.dom.audited.SomeAuditedObjects;
import org.isisaddons.module.audit.fixture.demoapp.demomodule.dom.notaudited.SomeNotAuditedObject;
import org.isisaddons.module.audit.fixture.demoapp.demomodule.dom.notaudited.SomeNotAuditedObjects;
import org.isisaddons.module.command.dom.CommandJdo;
import org.isisaddons.module.command.dom.CommandServiceJdoRepository;
import org.isisaddons.module.command.fixture.demoapp.demomodule.dom.SomeCommandAnnotatedObject;
import org.isisaddons.module.command.fixture.demoapp.demomodule.dom.SomeCommandAnnotatedObjects;
import org.isisaddons.module.docx.fixture.dom.demoorder.DemoOrder;
import org.isisaddons.module.docx.fixture.dom.demoorder.DemoOrderMenu;
import org.isisaddons.module.excel.fixture.demoapp.demomodule.dom.bulkupdate.BulkUpdateMenuForDemoToDoItem;
import org.isisaddons.module.excel.fixture.demoapp.demomodule.dom.pivot.ExcelPivotByCategoryAndSubcategoryMenu;
import org.isisaddons.module.excel.fixture.demoapp.demomodule.fixturehandlers.excelupload.ExcelUploadServiceForDemoToDoItem;
import org.isisaddons.module.excel.fixture.demoapp.todomodule.dom.DemoToDoItem;
import org.isisaddons.module.excel.fixture.demoapp.todomodule.dom.DemoToDoItemMenu;
import org.isisaddons.module.fakedata.fixture.demoapp.demomodule.dom.DemoObjectWithAll;
import org.isisaddons.module.fakedata.fixture.demoapp.demomodule.dom.DemoObjectWithAllMenu;
import org.isisaddons.module.flywaydb.fixture.demomodule.dom.DemoObject;
import org.isisaddons.module.flywaydb.fixture.demomodule.dom.DemoObjectRepository;
import org.isisaddons.module.poly.fixture.demoapp.demomodule.dom.casemgmt.Case;
import org.isisaddons.module.poly.fixture.demoapp.demomodule.dom.casemgmt.Cases;
import org.isisaddons.module.poly.fixture.demoapp.demomodule.dom.commchannel.CommunicationChannel;
import org.isisaddons.module.poly.fixture.demoapp.demomodule.dom.commchannel.CommunicationChannels;
import org.isisaddons.module.poly.fixture.demoapp.demomodule.dom.fixedasset.FixedAsset;
import org.isisaddons.module.poly.fixture.demoapp.demomodule.dom.fixedasset.FixedAssets;
import org.isisaddons.module.poly.fixture.demoapp.demomodule.dom.party.Parties;
import org.isisaddons.module.poly.fixture.demoapp.demomodule.dom.party.Party;
import org.isisaddons.module.publishmq.dom.jdo.events.PublishedEvent;
import org.isisaddons.module.publishmq.dom.jdo.events.PublishedEventRepository;
import org.isisaddons.module.publishmq.dom.jdo.status.StatusMessage;
import org.isisaddons.module.publishmq.fixture.demoapp.demomodule.dom.PublishMqDemoObject;
import org.isisaddons.module.publishmq.fixture.demoapp.demomodule.dom.PublishMqDemoObjects;
import org.isisaddons.module.security.dom.permission.ApplicationPermission;
import org.isisaddons.module.security.dom.permission.ApplicationPermissionMenu;
import org.isisaddons.module.security.dom.role.ApplicationRole;
import org.isisaddons.module.security.dom.role.ApplicationRoleMenu;
import org.isisaddons.module.security.dom.tenancy.ApplicationTenancy;
import org.isisaddons.module.security.dom.tenancy.ApplicationTenancyMenu;
import org.isisaddons.module.security.dom.user.ApplicationUser;
import org.isisaddons.module.security.dom.user.ApplicationUserMenu;
import org.isisaddons.module.security.fixture.demoapp.demonontenantedmodule.dom.NonTenantedEntities;
import org.isisaddons.module.security.fixture.demoapp.demonontenantedmodule.dom.NonTenantedEntity;
import org.isisaddons.module.security.fixture.demoapp.demotenantedmodule.dom.TenantedEntities;
import org.isisaddons.module.security.fixture.demoapp.demotenantedmodule.dom.TenantedEntity;
import org.isisaddons.module.servletapi.fixture.demoapp.demomodule.dom.ServletApiDemoObject;
import org.isisaddons.module.sessionlogger.dom.SessionLogEntry;
import org.isisaddons.module.stringinterpolator.fixture.lib.stringinterpolator.demoapp.demomodule.dom.DemoReminder;
import org.isisaddons.module.stringinterpolator.fixture.lib.stringinterpolator.demoapp.demomodule.dom.DemoReminderMenu;
import org.isisaddons.wicket.pdfjs.cpt.applib.PdfJsViewer;
import org.isisaddons.wicket.pdfjs.fixture.demoapp.demomodule.dom.PdfJsDemoObjectWithBlob;
import org.isisaddons.wicket.pdfjs.fixture.demoapp.demomodule.dom.PdfJsDemoObjectWithBlobMenu;

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

    // shared.demo
    public List<DemoObject> getSharedDemoObjects() {
        return demoObjectRepository.listAll();
    }

    @javax.inject.Inject
    DemoObjectRepository demoObjectRepository;


    // shared.simple


    // shared.todo

    public List<DemoToDoItem> getSharedDemoToDoItems() {
        return demoToDoItemMenu.allInstances();
    }

    @javax.inject.Inject
    DemoToDoItemMenu demoToDoItemMenu;



    // lib.docx

    public List<DemoOrder> getLibDocxDemoOrders() {
        return demoOrderMenu.listAllDemoOrders();
    }
    @javax.inject.Inject
    DemoOrderMenu demoOrderMenu;


    // lib.excel
    @javax.inject.Inject
    BulkUpdateMenuForDemoToDoItem bulkUpdateMenuForDemoToDoItem;

    @javax.inject.Inject
    ExcelPivotByCategoryAndSubcategoryMenu excelPivotByCategoryAndSubcategoryMenu;

    @javax.inject.Inject
    ExcelUploadServiceForDemoToDoItem excelUploadServiceForDemoToDoItem;


    // lib.fakedata
    public List<DemoObjectWithAll> getLibFakeDataDemoObjectsWithAll() {
        return demoObjectWithAllMenu.listAllDemoObjectsWithAll();
    }

    @javax.inject.Inject
    DemoObjectWithAllMenu demoObjectWithAllMenu;





    // lib.poly
    public List<CommunicationChannel> getLibPolyCommunicationChannels() {
        return communicationChannelsMenu.listAllCommunicationChannels();
    }

    public List<Party> getLibPolyParties() {
        return parties.listAllParties();
    }

    public List<FixedAsset> getLibPolyFixedAssets() {
        return fixedAssets.listAllFixedAssets();
    }

    public List<Case> getLibPolyCases() {
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


    // lib.servletApi

    public List<ServletApiDemoObject> getLibServletApiDemoObjects() {
        return repositoryService.allInstances(ServletApiDemoObject.class);
    }


    // lib.stringinterpolator

    public List<DemoReminder> getDemoReminders() {
        return demoReminderMenu.listAllReminders();
    }

    @javax.inject.Inject
    DemoReminderMenu demoReminderMenu;






    // spi.audit

    public List<SomeAuditedObject> getSpiAuditSomeAuditedObjects() {
        return someAuditedObjects.listAllSomeAuditedObjects();
    }

    public List<SomeNotAuditedObject> getSpiAuditSomeNotAuditedObjects() {
        return someNotAuditedObjects.listAllSomeNotAuditedObjects();
    }

    public List<AuditEntry> getSpiAuditAuditEntries() {
        return repositoryService.allInstances(AuditEntry.class);
    }

    @Inject
    SomeAuditedObjects someAuditedObjects;

    @Inject
    SomeNotAuditedObjects someNotAuditedObjects;



    // spi.command

    public List<SomeCommandAnnotatedObject> getSpiCommandSomeCommandAnnotatedObjects() {
        return someCommandAnnotatedObjects.listAllSomeCommandAnnotatedObjects();
    }

    public List<CommandJdo> getSpiCommandCommands() {
        return repositoryService.allInstances(CommandJdo.class);
    }


    @Inject
    SomeCommandAnnotatedObjects someCommandAnnotatedObjects;

    @Inject
    CommandServiceJdoRepository commandServiceJdoRepository;



    // spi.publishmq

    public List<PublishMqDemoObject> getSpiPublishMqDemoObjects() {
        return publishMqDemoObjects.listAllPublishMqDemoObjects();
    }

    public List<PublishedEvent> getSpiPublishMqPublishedEvents() {
        return repositoryService.allInstances(PublishedEvent.class);
    }

    public List<StatusMessage> getSpiPublishMqStatusMessages() {
        return repositoryService.allInstances(StatusMessage.class);
    }

    @Inject
    PublishMqDemoObjects publishMqDemoObjects;

    @Inject
    PublishedEventRepository publishedEventRepository;


    // spi.security

    public List<ApplicationUser> getSpiSecurityApplicationUsers() {
        return applicationUserMenu.allUsers();
    }

    public List<ApplicationRole> getSpiSecurityApplicationRoles() {
        return applicationRoleMenu.allRoles();
    }

    public List<ApplicationPermission> getSpiSecurityApplicationPermissions() {
        return applicationPermissionMenu.allPermissions();
    }

    public List<ApplicationTenancy> getSpiSecurityApplicationTenancies() {
        return applicationTenancyMenu.allTenancies();
    }

    public List<TenantedEntity> getSpiSecurityTenantedEntities() {
        return tenantedEntities.listAllTenantedEntities();
    }

    public List<NonTenantedEntity> getSpiSecurityNonTenantedEntities() {
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



    // spi.sessionlogger

    public List<SessionLogEntry> getSpiSessionLoggerSessionLogEntries() {
        return repositoryService.allInstances(SessionLogEntry.class);
    }

    // wkt2.fullcalendar2

    public List<DemoToDoItem> getDemoToDoItemsNotYetComplete() {
        return demoToDoItemMenu.notYetCompleteNoUi();
    }




    // wkt.pdfjs

    public List<PdfJsDemoObjectWithBlob> getWktPdfjsDemoObjectsWithBlob() {
        return demoObjectWithBlobMenu.listAllDemoObjectsWithBlob();
    }
    @javax.inject.Inject
    PdfJsDemoObjectWithBlobMenu demoObjectWithBlobMenu;

    @PdfJsViewer(initialPageNum = 1, initialScale = Scale._1_00, initialHeight = 600)
    public Blob getBlob() {
        return getWktPdfjsSelected() != null ? getWktPdfjsSelected().getBlob() : null;
    }



    @Property(hidden = Where.EVERYWHERE)
    public int getWktPdfjsNumObjects() {
        return getWktPdfjsDemoObjectsWithBlob().size();
    }


    @Property
    @Getter @Setter
    private int idx;


    @XmlTransient
    public PdfJsDemoObjectWithBlob getWktPdfjsSelected() {
        final List<PdfJsDemoObjectWithBlob> demoObjectsWithBlob = getWktPdfjsDemoObjectsWithBlob();
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
        return getIdx() == getWktPdfjsNumObjects() - 1 ? "At end" : null;
    }


    @javax.inject.Inject
    FactoryService factoryService;


    @javax.inject.Inject
    CssHighlighter cssHighlighter;






    @Inject
    RepositoryService repositoryService;





}
