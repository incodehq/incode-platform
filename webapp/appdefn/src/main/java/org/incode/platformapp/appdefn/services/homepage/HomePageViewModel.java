package org.incode.platformapp.appdefn.services.homepage;

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
import org.isisaddons.module.docx.fixture.dom.demoorder.DocxDemoOrder;
import org.isisaddons.module.docx.fixture.dom.demoorder.DocxDemoOrderMenu;
import org.isisaddons.module.excel.fixture.demoapp.demomodule.dom.bulkupdate.BulkUpdateMenuForDemoToDoItem;
import org.isisaddons.module.excel.fixture.demoapp.demomodule.dom.pivot.ExcelPivotByCategoryAndSubcategoryMenu;
import org.isisaddons.module.excel.fixture.demoapp.demomodule.fixturehandlers.excelupload.ExcelUploadServiceForDemoToDoItem;
import org.isisaddons.module.excel.fixture.demoapp.todomodule.dom.ExcelDemoToDoItem;
import org.isisaddons.module.excel.fixture.demoapp.todomodule.dom.ExcelDemoToDoItemMenu;
import org.isisaddons.module.fakedata.fixture.demoapp.demomodule.dom.FakeDataDemoObjectWithAll;
import org.isisaddons.module.fakedata.fixture.demoapp.demomodule.dom.FakeDataDemoObjectWithAllMenu;
import org.isisaddons.module.flywaydb.fixture.demomodule.dom.FlywayDbDemoObject;
import org.isisaddons.module.flywaydb.fixture.demomodule.dom.FlywayDbDemoObjectRepository;
import org.isisaddons.module.poly.fixture.demoapp.demomodule.dom.casemgmt.PolyDemoCase;
import org.isisaddons.module.poly.fixture.demoapp.demomodule.dom.casemgmt.PolyDemoCases;
import org.isisaddons.module.poly.fixture.demoapp.demomodule.dom.commchannel.PolyDemoCommunicationChannel;
import org.isisaddons.module.poly.fixture.demoapp.demomodule.dom.commchannel.PolyDemoCommunicationChannels;
import org.isisaddons.module.poly.fixture.demoapp.demomodule.dom.fixedasset.PolyDemoFixedAsset;
import org.isisaddons.module.poly.fixture.demoapp.demomodule.dom.fixedasset.PolyDemoFixedAssets;
import org.isisaddons.module.poly.fixture.demoapp.demomodule.dom.party.PolyDemoParties;
import org.isisaddons.module.poly.fixture.demoapp.demomodule.dom.party.PolyDemoParty;
import org.isisaddons.module.publishmq.dom.jdo.events.PublishedEvent;
import org.isisaddons.module.publishmq.dom.jdo.events.PublishedEventRepository;
import org.isisaddons.module.publishmq.dom.status.impl.StatusMessage;
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
import org.isisaddons.module.stringinterpolator.fixture.lib.stringinterpolator.demoapp.demomodule.dom.OgnlDemoReminder;
import org.isisaddons.module.stringinterpolator.fixture.lib.stringinterpolator.demoapp.demomodule.dom.OgnlDemoReminderMenu;
import org.isisaddons.module.togglz.fixture.demoapp.demomodule.dom.TogglzDemoObject;
import org.isisaddons.module.togglz.fixture.demoapp.demomodule.dom.TogglzDemoObjectRepository;
import org.isisaddons.wicket.pdfjs.cpt.applib.PdfJsViewer;
import org.isisaddons.wicket.pdfjs.fixture.demoapp.demomodule.dom.PdfJsDemoObjectWithBlob;
import org.isisaddons.wicket.pdfjs.fixture.demoapp.demomodule.dom.PdfJsDemoObjectWithBlobMenu;

import lombok.Getter;
import lombok.Setter;

@DomainObject(
        nature = Nature.VIEW_MODEL,
        objectType = "platformapp.HomePageViewModel"
)
public class HomePageViewModel {

    public String title() {
        return "Home page";
    }

    // ext.flywayDb
    public List<FlywayDbDemoObject> getExtFlywayDbDemoObjects() {
        return demoObjectRepository.listAll();
    }

    @javax.inject.Inject
    FlywayDbDemoObjectRepository demoObjectRepository;


    // ext.togglz
    public List<TogglzDemoObject> getExtTogglzDemoObjects() {
        return togglzDemoObjectRepository.listAll();
    }

    @javax.inject.Inject
    TogglzDemoObjectRepository togglzDemoObjectRepository;


    // lib.docx

    public List<DocxDemoOrder> getLibDocxDemoOrders() {
        return demoOrderMenu.listAllDemoOrders();
    }
    @javax.inject.Inject
    DocxDemoOrderMenu demoOrderMenu;


    // lib.excel

    public List<ExcelDemoToDoItem> getLibExcelDemoToDoItems() {
        return demoToDoItemMenu.allInstances();
    }

    @javax.inject.Inject
    ExcelDemoToDoItemMenu demoToDoItemMenu;


    @javax.inject.Inject
    BulkUpdateMenuForDemoToDoItem bulkUpdateMenuForDemoToDoItem;

    @javax.inject.Inject
    ExcelPivotByCategoryAndSubcategoryMenu excelPivotByCategoryAndSubcategoryMenu;

    @javax.inject.Inject
    ExcelUploadServiceForDemoToDoItem excelUploadServiceForDemoToDoItem;


    // lib.fakedata
    public List<FakeDataDemoObjectWithAll> getLibFakeDataDemoObjectsWithAll() {
        return demoObjectWithAllMenu.listAllDemoObjectsWithAll();
    }

    @javax.inject.Inject
    FakeDataDemoObjectWithAllMenu demoObjectWithAllMenu;





    // lib.poly
    public List<PolyDemoCommunicationChannel> getLibPolyCommunicationChannels() {
        return communicationChannelsMenu.listAllCommunicationChannels();
    }

    public List<PolyDemoParty> getLibPolyParties() {
        return parties.listAllParties();
    }

    public List<PolyDemoFixedAsset> getLibPolyFixedAssets() {
        return fixedAssets.listAllFixedAssets();
    }

    public List<PolyDemoCase> getLibPolyCases() {
        return cases.listAllCases();
    }


    @javax.inject.Inject
    PolyDemoCommunicationChannels communicationChannelsMenu;

    @javax.inject.Inject
    PolyDemoParties parties;

    @javax.inject.Inject
    PolyDemoFixedAssets fixedAssets;

    @javax.inject.Inject
    PolyDemoCases cases;


    // lib.servletApi

    public List<ServletApiDemoObject> getLibServletApiDemoObjects() {
        return repositoryService.allInstances(ServletApiDemoObject.class);
    }


    // lib.stringinterpolator

    public List<OgnlDemoReminder> getLibStringInterpolatorDemoReminders() {
        return demoReminderMenu.listAllReminders();
    }

    @javax.inject.Inject
    OgnlDemoReminderMenu demoReminderMenu;






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

    public List<ExcelDemoToDoItem> getDemoToDoItemsNotYetComplete() {
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
