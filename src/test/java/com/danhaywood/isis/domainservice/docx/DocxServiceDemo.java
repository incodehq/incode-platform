/*
 *  Copyright 2013 Dan Haywood
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
package com.danhaywood.isis.domainservice.docx;

import java.io.ByteArrayInputStream;
import java.io.FileOutputStream;
import java.net.URL;
import java.nio.charset.Charset;

import org.docx4j.openpackaging.packages.WordprocessingMLPackage;

import com.danhaywood.isis.domainservice.docx.DocxService;
import com.danhaywood.isis.domainservice.docx.DocxService.MatchingPolicy;
import com.google.common.io.Resources;

public class DocxServiceDemo {

    public static void main(String[] args) throws Exception {

        URL htmlUrl = Resources.getResource(DocxServiceDemo.class, "input.html");
        String html = Resources.toString(htmlUrl, Charset.forName("UTF-8"));
        
        URL templateUrl = Resources.getResource(DocxServiceDemo.class, "TypicalDocument.docx");
        ByteArrayInputStream docxTemplateIs = new ByteArrayInputStream(Resources.toByteArray(templateUrl));
        
        java.io.File targetFile = new java.io.File(System.getProperty("user.dir") + "/Generated.docx");
        FileOutputStream targetFos = new FileOutputStream(targetFile);

        DocxService docxService = new DocxService();
        WordprocessingMLPackage docxTemplate = docxService.loadPackage(docxTemplateIs);
        docxService.merge(html, docxTemplate, targetFos, MatchingPolicy.LAX);
    }

}
