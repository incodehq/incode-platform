/*
 *  Copyright 2013~2014 Dan Haywood
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
package org.isisaddons.module.docx.fixture.dom.templates;

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
import org.isisaddons.module.docx.dom.DocxService;
import org.isisaddons.module.docx.dom.LoadTemplateException;
import org.isisaddons.module.docx.dom.MergeException;
import org.isisaddons.module.docx.fixture.dom.Order;
import org.isisaddons.module.docx.fixture.dom.OrderLine;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.output.DOMOutputter;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;
import org.apache.isis.applib.DomainObjectContainer;
import org.apache.isis.applib.annotation.*;
import org.apache.isis.applib.annotation.ActionSemantics.Of;
import org.apache.isis.applib.value.Blob;
import org.apache.isis.applib.value.Clob;

@DomainService
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

    @NotContributed(NotContributed.As.ASSOCIATION) // ie contributed as action
    @NotInServiceMenu
    @ActionSemantics(Of.SAFE)
    @MemberOrder(sequence = "10")
    public Blob downloadCustomerConfirmation(
            final Order order) throws IOException, JDOMException, MergeException {

        final org.w3c.dom.Document w3cDocument = asInputW3cDocument(order);

        final ByteArrayOutputStream docxTarget = new ByteArrayOutputStream();
        docxService.merge(w3cDocument, wordprocessingMLPackage, docxTarget, DocxService.MatchingPolicy.LAX);

        final String blobName = "customerConfirmation-" + order.getNumber() + ".docx";
        final String blobMimeType = "application/vnd.openxmlformats-officedocument.wordprocessingml.document";
        final byte[] blobBytes = docxTarget.toByteArray();

        return new Blob(blobName, blobMimeType, blobBytes);
    }

    @NotContributed(NotContributed.As.ASSOCIATION) // ie contributed as action
    @Prototype
    @NotInServiceMenu
    @ActionSemantics(Of.SAFE)
    @MemberOrder(sequence = "11")
    public Clob downloadCustomerConfirmationInputHtml(
            final Order order) throws IOException, JDOMException, MergeException {

        Document orderAsHtmlJdomDoc = asInputDocument(order);

        XMLOutputter xmlOutput = new XMLOutputter();
        xmlOutput.setFormat(Format.getPrettyFormat());

        final String html = xmlOutput.outputString(orderAsHtmlJdomDoc);

        final String clobName = "customerConfirmation-" + order.getNumber() + ".html";
        final String clobMimeType = "text/html";
        final String clobBytes = html;

        return new Clob(clobName, clobMimeType, clobBytes);
    }

    private static org.w3c.dom.Document asInputW3cDocument(Order order) throws JDOMException {
        Document orderAsHtmlJdomDoc = asInputDocument(order);

        DOMOutputter domOutputter = new DOMOutputter();
        return domOutputter.output(orderAsHtmlJdomDoc);
    }

    private static Document asInputDocument(Order order) {
        Element html = new Element("html");
        Document document = new Document(html);

        Element body = new Element("body");
        html.addContent(body);

        addPara(body, "OrderNum", "plain", order.getNumber());
        addPara(body, "OrderDate", "date", order.getDate().toString("dd-MMM-yyyy"));
        addPara(body, "CustomerName", "plain", order.getCustomerName());
        addPara(body, "Message", "plain", "Thank you for shopping with us!");

        Element table = addTable(body, "Products");
        for(OrderLine orderLine: order.getOrderLines()) {
            addTableRow(table, new String[]{orderLine.getDescription(), orderLine.getCost().toString(), ""+orderLine.getQuantity()});
        }

        Element ul = addList(body, "OrderPreferences");
        for(String preference: preferencesFor(order)) {
            addListItem(ul, preference);
        }
        return document;
    }

    //endregion (

    //region > helpers

    private static final Function<String, String> TRIM = new Function<String, String>() {
        @Override
        public String apply(String input) {
            return input.trim();
        }
    };

    private static Iterable<String> preferencesFor(Order order) {
        final String preferences = order.getPreferences();
        if(preferences == null) {
            return Collections.emptyList();
        }
        return Iterables.transform(Splitter.on(",").split(preferences), TRIM);
    }


    private static void addPara(Element body, String id, String clazz, String text) {
        Element p = new Element("p");
        body.addContent(p);
        p.setAttribute("id", id);
        p.setAttribute("class", clazz);
        p.setText(text);
    }

    private static Element addList(Element body, String id) {
        Element ul = new Element("ul");
        body.addContent(ul);
        ul.setAttribute("id", id);
        return ul;
    }

    private static Element addListItem(Element ul, String... paras) {
        Element li = new Element("li");
        ul.addContent(li);
        for (String para : paras) {
            addPara(li, para);
        }
        return ul;
    }

    private static void addPara(Element li, String text) {
        if(text == null) {
            return;
        }
        Element p = new Element("p");
        li.addContent(p);
        p.setText(text);
    }

    private static Element addTable(Element body, String id) {
        Element table = new Element("table");
        body.addContent(table);
        table.setAttribute("id", id);
        return table;
    }

    private static void addTableRow(Element table, String[] cells) {
        Element tr = new Element("tr");
        table.addContent(tr);
        for (String columnName : cells) {
            Element td = new Element("td");
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
