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
package org.isisaddons.module.docx.dom;

import java.io.ByteArrayInputStream;
import java.io.FileOutputStream;
import java.net.URL;
import java.nio.charset.Charset;
import com.google.common.io.Resources;
import org.docx4j.openpackaging.packages.WordprocessingMLPackage;
import org.isisaddons.module.docx.dom.DocxService.MatchingPolicy;

public class DocxServiceDemo {

    public static void main(String[] args) throws Exception {

//        final String inputHtml = "input.html";
//        final String templateDocx = "TypicalDocument.docx";
        
        final String inputHtml = "ToDoItem-input.html";
        final String templateDocx = "ToDoItem.docx";
        
        final URL htmlUrl = Resources.getResource(DocxServiceDemo.class, inputHtml);
        final String html = Resources.toString(htmlUrl, Charset.forName("UTF-8"));
        
        final URL templateUrl = Resources.getResource(DocxServiceDemo.class, templateDocx);
        final ByteArrayInputStream docxTemplateIs = new ByteArrayInputStream(Resources.toByteArray(templateUrl));
        
        final java.io.File targetFile = new java.io.File(System.getProperty("user.dir") + "/Generated.docx");
        final FileOutputStream targetFos = new FileOutputStream(targetFile);

        final DocxService docxService = new DocxService();
        final WordprocessingMLPackage docxTemplate = docxService.loadPackage(docxTemplateIs);
        docxService.merge(html, docxTemplate, targetFos, MatchingPolicy.LAX);
        
        System.out.println(targetFile.getAbsolutePath());
    }

}
