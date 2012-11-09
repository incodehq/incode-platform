package com.danhaywood.ddd.domainservices.docx;

import java.io.ByteArrayInputStream;
import java.io.FileOutputStream;
import java.net.URL;
import java.nio.charset.Charset;

import org.docx4j.openpackaging.packages.WordprocessingMLPackage;

import com.danhaywood.ddd.domainservices.docx.DocxService.MatchingPolicy;
import com.google.common.io.Resources;

public class DocxServiceDemo {

    public static void main(String[] args) throws Exception {

        URL htmlUrl = Resources.getResource(DocxServiceDemo.class, "input.html");
        String html = Resources.toString(htmlUrl, Charset.forName("UTF-8"));
        
        URL templateUrl = Resources.getResource(DocxServiceDemo.class, "TypicalDocument.docx");
        ByteArrayInputStream docxTemplateIs = new ByteArrayInputStream(Resources.toByteArray(templateUrl));
        
        java.io.File targetFile = new java.io.File(System.getProperty("user.dir") + "/target/Generated.docx");
        FileOutputStream targetFos = new FileOutputStream(targetFile);

        DocxService docxService = new DocxService();
        WordprocessingMLPackage docxTemplate = docxService.loadPackage(docxTemplateIs);
        docxService.merge(html, docxTemplate, targetFos, MatchingPolicy.LAX);
    }

}
