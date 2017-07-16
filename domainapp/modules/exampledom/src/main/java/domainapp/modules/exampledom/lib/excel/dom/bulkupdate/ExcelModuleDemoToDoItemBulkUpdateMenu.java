package domainapp.modules.exampledom.lib.excel.dom.bulkupdate;

import java.math.BigDecimal;

import javax.annotation.PostConstruct;

import org.joda.time.LocalDate;

import org.apache.isis.applib.DomainObjectContainer;
import org.apache.isis.applib.annotation.Action;
import org.apache.isis.applib.annotation.DomainService;
import org.apache.isis.applib.annotation.MemberOrder;
import org.apache.isis.applib.annotation.NatureOfService;
import org.apache.isis.applib.annotation.SemanticsOf;
import org.apache.isis.applib.services.bookmark.Bookmark;
import org.apache.isis.applib.services.bookmark.BookmarkService;
import org.apache.isis.applib.services.memento.MementoService;
import org.apache.isis.applib.services.memento.MementoService.Memento;

import org.isisaddons.module.excel.dom.ExcelService;
import domainapp.modules.exampledom.lib.excel.dom.demo.ExcelModuleDemoToDoItem;
import domainapp.modules.exampledom.lib.excel.dom.demo.ExcelModuleDemoToDoItem.Category;
import domainapp.modules.exampledom.lib.excel.dom.demo.ExcelModuleDemoToDoItem.Subcategory;

@DomainService(
        nature = NatureOfService.VIEW_MENU_ONLY,
        objectType = "org.isisaddons.module.excel.fixture.app.ExcelModuleDemoToDoItemBulkUpdateMenu"
)
public class ExcelModuleDemoToDoItemBulkUpdateMenu {

    public ExcelModuleDemoToDoItemBulkUpdateMenu() {
    }

    @PostConstruct
    public void init() {
        if(excelService == null) {
            throw new IllegalStateException("Require ExcelService to be configured");
        }
    }

    // //////////////////////////////////////
    // bulk update manager (action)
    // //////////////////////////////////////

    @Action(
            semantics = SemanticsOf.IDEMPOTENT
    )
    @MemberOrder(name="ToDos", sequence="90.1")
    public ExcelModuleDemoToDoItemBulkUpdateManager bulkUpdateManager() {
        ExcelModuleDemoToDoItemBulkUpdateManager template = new ExcelModuleDemoToDoItemBulkUpdateManager();
        template.setFileName("toDoItems.xlsx");
        template.setCategory(Category.Domestic);
        template.setSubcategory(Subcategory.Shopping);
        template.setComplete(false);
        return newBulkUpdateManager(template);
    }


    // //////////////////////////////////////
    // memento for manager
    // //////////////////////////////////////
    
    String mementoFor(final ExcelModuleDemoToDoItemBulkUpdateManager tdieim) {
        final Memento memento = mementoService.create();
        memento.set("fileName", tdieim.getFileName());
        memento.set("category", tdieim.getCategory());
        memento.set("subcategory", tdieim.getSubcategory());
        memento.set("completed", tdieim.isComplete());
        return memento.asString();
    }
    
    void initOf(final String mementoStr, final ExcelModuleDemoToDoItemBulkUpdateManager manager) {
        final Memento memento = mementoService.parse(mementoStr);
        manager.setFileName(memento.get("fileName", String.class));
        manager.setCategory(memento.get("category", Category.class));
        manager.setSubcategory(memento.get("subcategory", Subcategory.class));
        manager.setComplete(memento.get("completed", boolean.class));
    }

    ExcelModuleDemoToDoItemBulkUpdateManager newBulkUpdateManager(ExcelModuleDemoToDoItemBulkUpdateManager manager) {
        return container.newViewModelInstance(ExcelModuleDemoToDoItemBulkUpdateManager.class, mementoFor(manager));
    }
    
    // //////////////////////////////////////
    // memento for line item
    // //////////////////////////////////////
    
    String mementoFor(ExcelModuleDemoToDoItemBulkUpdateLineItem lineItem) {
        final Memento memento = mementoService.create();
        memento.set("toDoItem", bookmarkService.bookmarkFor(lineItem.getToDoItem()));
        memento.set("description", lineItem.getDescription());
        memento.set("category", lineItem.getCategory());
        memento.set("subcategory", lineItem.getSubcategory());
        memento.set("cost", lineItem.getCost());
        memento.set("complete", lineItem.isComplete());
        memento.set("dueBy", lineItem.getDueBy());
        memento.set("notes", lineItem.getNotes());
        memento.set("ownedBy", lineItem.getOwnedBy());
        return memento.asString();
    }

    void init(String mementoStr, ExcelModuleDemoToDoItemBulkUpdateLineItem lineItem) {
        final Memento memento = mementoService.parse(mementoStr);
        lineItem.setToDoItem(bookmarkService.lookup(memento.get("toDoItem", Bookmark.class), ExcelModuleDemoToDoItem.class));
        lineItem.setDescription(memento.get("description", String.class));
        lineItem.setCategory(memento.get("category", Category.class));
        lineItem.setSubcategory(memento.get("subcategory", Subcategory.class));
        lineItem.setCost(memento.get("cost", BigDecimal.class));
        lineItem.setComplete(memento.get("complete", boolean.class));
        lineItem.setDueBy(memento.get("dueBy", LocalDate.class));
        lineItem.setNotes(memento.get("notes", String.class));
        lineItem.setOwnedBy(memento.get("ownedBy", String.class));
    }
    
    ExcelModuleDemoToDoItemBulkUpdateLineItem newLineItem(ExcelModuleDemoToDoItemBulkUpdateLineItem lineItem) {
        return container.newViewModelInstance(ExcelModuleDemoToDoItemBulkUpdateLineItem.class, mementoFor(lineItem));
    }


    // //////////////////////////////////////
    // Injected Services
    // //////////////////////////////////////

    @javax.inject.Inject
    private DomainObjectContainer container;

    @javax.inject.Inject
    private ExcelService excelService;

    @javax.inject.Inject
    private BookmarkService bookmarkService;
    
    @javax.inject.Inject
    private MementoService mementoService;

}
