package reporting;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.URL;
import java.util.SortedSet;

import com.danhaywood.isis.domainservice.docx.DocxService;
import com.danhaywood.isis.domainservice.docx.DocxService.MatchingPolicy;
import com.danhaywood.isis.domainservice.docx.LoadInputException;
import com.danhaywood.isis.domainservice.docx.LoadTemplateException;
import com.danhaywood.isis.domainservice.docx.MergeException;
import com.google.common.io.Resources;

import dom.todo.ToDoItem;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.Text;
import org.jdom2.output.XMLOutputter;
import org.joda.time.LocalDate;

import org.apache.isis.applib.annotation.NotContributed;
import org.apache.isis.applib.annotation.NotContributed.As;
import org.apache.isis.applib.annotation.NotInServiceMenu;
import org.apache.isis.applib.services.bookmark.BookmarkService;
import org.apache.isis.applib.value.Blob;

public class ToDoItemReportingService {

    private final static String MIME_TYPE_DOCX = "application/vnd.openxmlformats-officedocument.wordprocessingml.document";
    
    private final byte[] toDoItemTemplates;

    public ToDoItemReportingService() throws IOException {
        final URL templateUrl = Resources.getResource(ToDoItemReportingService.class, "ToDoItem.docx");
        toDoItemTemplates = Resources.toByteArray(templateUrl);
    }

    @NotContributed(As.ASSOCIATION) // ie contributed as action
    @NotInServiceMenu
    public Blob downloadAsDoc(ToDoItem toDoItem) throws LoadInputException, LoadTemplateException, MergeException {

        final String html = asInputHtml(toDoItem);
        final byte[] byteArray = mergeToDocx(html);
        
        final String outputFileName = "ToDoItem-" + bookmarkService.bookmarkFor(toDoItem).getIdentifier() + ".docx";
        return new Blob(outputFileName, MIME_TYPE_DOCX, byteArray);
    }

    private static String asInputHtml(ToDoItem toDoItem) {
        final Element htmlEl = new Element("html");
        Document doc = new Document();
        doc.setRootElement(htmlEl);

        final Element bodyEl = new Element("body");
        htmlEl.addContent(bodyEl);
        
        bodyEl.addContent(newP("Description", "plain", toDoItem.getDescription()));
        bodyEl.addContent(newP("Category", "plain", toDoItem.getCategory().name()));
        bodyEl.addContent(newP("Subcategory", "plain", toDoItem.getSubcategory().name()));
        bodyEl.addContent(newP("DueBy", "date", dueByOf(toDoItem)));
        
        final Element ulDependencies = new Element("ul");
        ulDependencies.setAttribute("id", "Dependencies");
        
        final SortedSet<ToDoItem> dependencies = toDoItem.getDependencies();
        for (final ToDoItem dependency : dependencies) {
            final Element liDependency = new Element("li");
            ulDependencies.addContent(liDependency);
            final Element pDependency = new Element("p");
            pDependency.setContent(new Text(dependency.getDescription()));
            liDependency.addContent(pDependency);
        }
        bodyEl.addContent(ulDependencies);
        
        final String html = new XMLOutputter().outputString(doc);
        return html;
    }

    private static String dueByOf(ToDoItem toDoItem) {
        LocalDate dueBy = toDoItem.getDueBy();
        return dueBy != null? dueBy.toString("dd/MM/yyyy"): "";
    }

    private static Element newP(String id, String cls, String text) {
        final Element pDescription = new Element("p");
        pDescription.setAttribute("id", id);
        pDescription.setAttribute("class", cls);
        pDescription.setContent(new Text(text));
        return pDescription;
    }

    private byte[] mergeToDocx(final String html) throws LoadInputException, LoadTemplateException, MergeException {
        final ByteArrayInputStream docxTemplateIs = new ByteArrayInputStream(toDoItemTemplates);

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        docxService.merge(html, docxTemplateIs, baos, MatchingPolicy.LAX);
        byte[] byteArray = baos.toByteArray();
        return byteArray;
    }


    // //////////////////////////////////////
    
    @javax.inject.Inject
    private DocxService docxService;
    
    @javax.inject.Inject
    private BookmarkService bookmarkService;
}

