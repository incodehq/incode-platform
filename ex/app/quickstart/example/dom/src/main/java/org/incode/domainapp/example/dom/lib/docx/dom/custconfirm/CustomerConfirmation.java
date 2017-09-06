package org.incode.domainapp.example.dom.lib.docx.dom.custconfirm;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Collections;

import javax.annotation.PostConstruct;

import com.google.common.base.Function;
import com.google.common.base.Splitter;
import com.google.common.collect.Iterables;
import com.google.common.io.Resources;

import org.docx4j.openpackaging.packages.WordprocessingMLPackage;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.output.DOMOutputter;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;

import org.apache.isis.applib.DomainObjectContainer;
import org.apache.isis.applib.annotation.Action;
import org.apache.isis.applib.annotation.ActionLayout;
import org.apache.isis.applib.annotation.Contributed;
import org.apache.isis.applib.annotation.DomainService;
import org.apache.isis.applib.annotation.MemberOrder;
import org.apache.isis.applib.annotation.NatureOfService;
import org.apache.isis.applib.annotation.RestrictTo;
import org.apache.isis.applib.annotation.SemanticsOf;
import org.apache.isis.applib.value.Blob;
import org.apache.isis.applib.value.Clob;

import org.isisaddons.module.docx.dom.DocxService;
import org.isisaddons.module.docx.dom.LoadTemplateException;
import org.isisaddons.module.docx.dom.MergeException;

import org.incode.domainapp.example.dom.demo.dom.order.DemoOrder;
import org.incode.domainapp.example.dom.demo.dom.order.DemoOrderLine;

@DomainService(
        nature = NatureOfService.VIEW_CONTRIBUTIONS_ONLY
)
public class CustomerConfirmation {

    //region > init

    private WordprocessingMLPackage wordprocessingMLPackage;

    @PostConstruct
    public void init() throws IOException, LoadTemplateException {
        final byte[] bytes = Resources.toByteArray(Resources.getResource(this.getClass(), "CustomerConfirmation.docx"));
        wordprocessingMLPackage = docxService.loadPackage(new ByteArrayInputStream(bytes));
    }
    //endregion

    //region > downloadCustomerConfirmation (action)

    @Action(
            semantics = SemanticsOf.SAFE
    )
    @ActionLayout(
            contributed = Contributed.AS_ACTION
    )
    @MemberOrder(sequence = "10")
    public Blob downloadCustomerConfirmation(
            final DemoOrder order) throws IOException, JDOMException, MergeException {

        final org.w3c.dom.Document w3cDocument = asInputW3cDocument(order);

        final ByteArrayOutputStream docxTarget = new ByteArrayOutputStream();
        docxService.merge(w3cDocument, wordprocessingMLPackage, docxTarget, DocxService.MatchingPolicy.LAX);

        final String blobName = "customerConfirmation-" + order.getNumber() + ".docx";
        final String blobMimeType = "application/vnd.openxmlformats-officedocument.wordprocessingml.document";
        final byte[] blobBytes = docxTarget.toByteArray();

        return new Blob(blobName, blobMimeType, blobBytes);
    }

    @Action(
            semantics = SemanticsOf.SAFE,
            restrictTo = RestrictTo.PROTOTYPING
    )
    @ActionLayout(
            contributed = Contributed.AS_ACTION
    )
    @MemberOrder(sequence = "11")
    public Clob downloadCustomerConfirmationInputHtml(
            final DemoOrder order) throws IOException, JDOMException, MergeException {

        final Document orderAsHtmlJdomDoc = asInputDocument(order);

        final XMLOutputter xmlOutput = new XMLOutputter();
        xmlOutput.setFormat(Format.getPrettyFormat());

        final String html = xmlOutput.outputString(orderAsHtmlJdomDoc);

        final String clobName = "customerConfirmation-" + order.getNumber() + ".html";
        final String clobMimeType = "text/html";
        final String clobBytes = html;

        return new Clob(clobName, clobMimeType, clobBytes);
    }

    //region > downloadCustomerConfirmationAsPdf (action)

    @Action(
            semantics = SemanticsOf.SAFE
    )
    @ActionLayout(
            contributed = Contributed.AS_ACTION
    )
    @MemberOrder(sequence = "10")
    public Blob downloadCustomerConfirmationAsPdf(
            final DemoOrder order) throws IOException, JDOMException, MergeException {

        final org.w3c.dom.Document w3cDocument = asInputW3cDocument(order);

        final ByteArrayOutputStream docxTarget = new ByteArrayOutputStream();
        docxService.merge(w3cDocument, wordprocessingMLPackage, docxTarget, DocxService.MatchingPolicy.LAX, DocxService.OutputType.PDF);

        final String blobName = "customerConfirmation-" + order.getNumber() + ".pdf";
        final String blobMimeType = "application/pdf";
        final byte[] blobBytes = docxTarget.toByteArray();

        return new Blob(blobName, blobMimeType, blobBytes);
    }

    private static org.w3c.dom.Document asInputW3cDocument(final DemoOrder order) throws JDOMException {
        final Document orderAsHtmlJdomDoc = asInputDocument(order);

        final DOMOutputter domOutputter = new DOMOutputter();
        return domOutputter.output(orderAsHtmlJdomDoc);
    }

    private static Document asInputDocument(final DemoOrder order) {
        final Element html = new Element("html");
        final Document document = new Document(html);

        final Element body = new Element("body");
        html.addContent(body);

        addPara(body, "OrderNum", "plain", order.getNumber());
        addPara(body, "OrderDate", "date", order.getDate().toString("dd-MMM-yyyy"));
        addPara(body, "CustomerName", "plain", order.getCustomerName());
        addPara(body, "Message", "plain", "Thank you for shopping with us!");

        final Element table = addTable(body, "Products");
        for(final DemoOrderLine orderLine: order.getOrderLines()) {
            addTableRow(table, new String[]{orderLine.getDescription(), orderLine.getCost().toString(), ""+orderLine.getQuantity()});
        }

        final Element ul = addList(body, "OrderPreferences");
        for(final String preference: preferencesFor(order)) {
            addListItem(ul, preference);
        }
        return document;
    }

    //endregion (

    //region > helpers

    private static final Function<String, String> TRIM = input -> input.trim();

    private static Iterable<String> preferencesFor(final DemoOrder order) {
        final String preferences = order.getPreferences();
        if(preferences == null) {
            return Collections.emptyList();
        }
        return Iterables.transform(Splitter.on(",").split(preferences), TRIM);
    }


    private static void addPara(final Element body, final String id, final String clazz, final String text) {
        final Element p = new Element("p");
        body.addContent(p);
        p.setAttribute("id", id);
        p.setAttribute("class", clazz);
        p.setText(text);
    }

    private static Element addList(final Element body, final String id) {
        final Element ul = new Element("ul");
        body.addContent(ul);
        ul.setAttribute("id", id);
        return ul;
    }

    private static Element addListItem(final Element ul, final String... paras) {
        final Element li = new Element("li");
        ul.addContent(li);
        for (final String para : paras) {
            addPara(li, para);
        }
        return ul;
    }

    private static void addPara(final Element li, final String text) {
        if(text == null) {
            return;
        }
        final Element p = new Element("p");
        li.addContent(p);
        p.setText(text);
    }

    private static Element addTable(final Element body, final String id) {
        final Element table = new Element("table");
        body.addContent(table);
        table.setAttribute("id", id);
        return table;
    }

    private static void addTableRow(final Element table, final String[] cells) {
        final Element tr = new Element("tr");
        table.addContent(tr);
        for (final String columnName : cells) {
            final Element td = new Element("td");
            tr.addContent(td);
            td.setText(columnName);
        }
    }
    //endregion

    //region > injected services

    @javax.inject.Inject
    DomainObjectContainer container;

    @javax.inject.Inject
    private DocxService docxService;

    //endregion

}
