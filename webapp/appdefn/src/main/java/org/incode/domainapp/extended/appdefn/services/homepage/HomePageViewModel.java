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

import org.incode.domainapp.extended.module.fixtures.per_cpt.lib.docx.dom.order.DemoOrder;
import org.incode.domainapp.extended.module.fixtures.per_cpt.lib.docx.dom.order.DemoOrderMenu;
import org.incode.domainapp.extended.module.fixtures.per_cpt.lib.excel.dom.bulkupdate.BulkUpdateMenuForDemoToDoItem;
import org.incode.domainapp.extended.module.fixtures.per_cpt.lib.excel.dom.pivot.ExcelPivotByCategoryAndSubcategoryMenu;
import org.incode.domainapp.extended.module.fixtures.per_cpt.lib.excel.dom.upload.ExcelUploadServiceForDemoToDoItem;
import org.incode.domainapp.extended.module.fixtures.per_cpt.lib.fakedata.dom.DemoObjectWithAll;
import org.incode.domainapp.extended.module.fixtures.per_cpt.lib.fakedata.dom.DemoObjectWithAllMenu;
import org.incode.domainapp.extended.module.fixtures.per_cpt.lib.poly.dom.democasemgmt.Case;
import org.incode.domainapp.extended.module.fixtures.per_cpt.lib.poly.dom.democasemgmt.Cases;
import org.incode.domainapp.extended.module.fixtures.per_cpt.lib.poly.dom.democommchannel.CommunicationChannel;
import org.incode.domainapp.extended.module.fixtures.per_cpt.lib.poly.dom.democommchannel.CommunicationChannels;
import org.incode.domainapp.extended.module.fixtures.per_cpt.lib.poly.dom.demofixedasset.FixedAsset;
import org.incode.domainapp.extended.module.fixtures.per_cpt.lib.poly.dom.demofixedasset.FixedAssets;
import org.incode.domainapp.extended.module.fixtures.per_cpt.lib.poly.dom.demoparty.Parties;
import org.incode.domainapp.extended.module.fixtures.per_cpt.lib.poly.dom.demoparty.Party;
import org.incode.domainapp.extended.module.fixtures.per_cpt.lib.servletapi.dom.demo.ServletApiDemoObject;
import org.incode.domainapp.extended.module.fixtures.per_cpt.lib.stringinterpolator.dom.DemoReminder;
import org.incode.domainapp.extended.module.fixtures.per_cpt.lib.stringinterpolator.dom.DemoReminderMenu;
import org.incode.domainapp.extended.module.fixtures.per_cpt.spi.audit.dom.demo.audited.SomeAuditedObject;
import org.incode.domainapp.extended.module.fixtures.per_cpt.spi.audit.dom.demo.audited.SomeAuditedObjects;
import org.incode.domainapp.extended.module.fixtures.per_cpt.spi.audit.dom.demo.notaudited.SomeNotAuditedObject;
import org.incode.domainapp.extended.module.fixtures.per_cpt.spi.audit.dom.demo.notaudited.SomeNotAuditedObjects;
import org.incode.domainapp.extended.module.fixtures.per_cpt.spi.command.dom.SomeCommandAnnotatedObject;
import org.incode.domainapp.extended.module.fixtures.per_cpt.spi.command.dom.SomeCommandAnnotatedObjects;
import org.incode.domainapp.extended.module.fixtures.per_cpt.spi.publishmq.dom.demo.PublishMqDemoObject;
import org.incode.domainapp.extended.module.fixtures.per_cpt.spi.publishmq.dom.demo.PublishMqDemoObjects;
import org.incode.domainapp.extended.module.fixtures.per_cpt.spi.security.dom.demo.nontenanted.NonTenantedEntities;
import org.incode.domainapp.extended.module.fixtures.per_cpt.spi.security.dom.demo.nontenanted.NonTenantedEntity;
import org.incode.domainapp.extended.module.fixtures.per_cpt.spi.security.dom.demo.tenanted.TenantedEntities;
import org.incode.domainapp.extended.module.fixtures.per_cpt.spi.security.dom.demo.tenanted.TenantedEntity;
import org.incode.domainapp.extended.module.fixtures.per_cpt.wkt.pdfjs.dom.DemoObjectWithBlob;
import org.incode.domainapp.extended.module.fixtures.per_cpt.wkt.pdfjs.dom.DemoObjectWithBlobMenu;
import org.incode.domainapp.extended.module.fixtures.shared.demo.dom.DemoObject;
import org.incode.domainapp.extended.module.fixtures.shared.demo.dom.DemoObjectRepository;
import org.incode.domainapp.extended.module.fixtures.shared.todo.dom.DemoToDoItem;
import org.incode.domainapp.extended.module.fixtures.shared.todo.dom.DemoToDoItemMenu;

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

    public List<DemoObjectWithBlob> getWktPdfjsDemoObjectsWithBlob() {
        return demoObjectWithBlobMenu.listAllDemoObjectsWithBlob();
    }
    @javax.inject.Inject
    DemoObjectWithBlobMenu demoObjectWithBlobMenu;

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
    public DemoObjectWithBlob getWktPdfjsSelected() {
        final List<DemoObjectWithBlob> demoObjectsWithBlob = getWktPdfjsDemoObjectsWithBlob();
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
