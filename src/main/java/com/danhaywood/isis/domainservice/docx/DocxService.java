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

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import com.danhaywood.isis.domainservice.docx.traverse.AllMatches;
import com.danhaywood.isis.domainservice.docx.traverse.FirstMatch;
import com.danhaywood.isis.domainservice.docx.util.Docx;
import com.danhaywood.isis.domainservice.docx.util.Jdom2;
import com.danhaywood.isis.domainservice.docx.util.Types;
import com.google.common.base.Objects;
import com.google.common.collect.Lists;

import org.apache.commons.io.IOUtils;
import org.docx4j.XmlUtils;
import org.docx4j.openpackaging.exceptions.Docx4JException;
import org.docx4j.openpackaging.packages.WordprocessingMLPackage;
import org.docx4j.wml.Body;
import org.docx4j.wml.P;
import org.docx4j.wml.R;
import org.docx4j.wml.SdtElement;
import org.docx4j.wml.Tbl;
import org.docx4j.wml.Tc;
import org.docx4j.wml.Tr;
import org.jdom2.Content;
import org.jdom2.Element;
import org.jdom2.input.DOMBuilder;

public class DocxService {

    @PostConstruct
    public void init(Map<String,String> properties) {
    }
    
    public enum MatchingPolicy {
        STRICT(false,false),
        ALLOW_UNMATCHED_INPUT(true,false),
        ALLOW_UNMATCHED_PLACEHOLDERS(false,true),
        /**
         * Combination of both {@link #ALLOW_UNMATCHED_INPUT} and {@link #ALLOW_UNMATCHED_PLACEHOLDERS}.
         */
        LAX(true,true);
        private final boolean allowUnmatchedInput;
        private final boolean allowUnmatchedPlaceholders;
        private MatchingPolicy(boolean allowUnmatchedInput, boolean allowUnmatchedPlaceholders) {
            this.allowUnmatchedInput = allowUnmatchedInput;
            this.allowUnmatchedPlaceholders = allowUnmatchedPlaceholders;
        }

        public void unmatchedInputs(List<String> unmatched) throws MergeException {
            if(!allowUnmatchedInput) {
                throw new MergeException("Input elements " + unmatched + " were not matched to placeholders");
            }
        }
        public void unmatchedPlaceholders(List<String> unmatched) throws MergeException {
            if(!allowUnmatchedPlaceholders && !unmatched.isEmpty()) {
                throw new MergeException("Placeholders " + unmatched + " were not matched to input");
            }
        }
    }

    /**
     * Load and return an in-memory representation of a docx.
     * 
     * <p>
     * This is public API because building the in-memory structure can be
     * quite slow.  Thus, clients can use this method to cache the in-memory
     * structure, and pass in to either
     *  {@link #merge(String, WordprocessingMLPackage, OutputStream, MatchingPolicy)}
     *  or {@link #merge(org.w3c.dom.Document, WordprocessingMLPackage, OutputStream, MatchingPolicy, DefensiveCopy)}
     */
    public WordprocessingMLPackage loadPackage(InputStream docxTemplate) throws LoadTemplateException {
        WordprocessingMLPackage docxPkg;
        try {
            docxPkg = WordprocessingMLPackage.load(docxTemplate);
        } catch (Docx4JException ex) {
            throw new LoadTemplateException("Unable to load docx template from input stream", ex);
        }
        return docxPkg;
    }

    public void merge(String html, InputStream docxTemplate, OutputStream docxTarget, MatchingPolicy matchingPolicy) throws LoadInputException, LoadTemplateException, MergeException {
        org.jdom2.Document htmlJdomDoc = Jdom2.loadInput(html);
        WordprocessingMLPackage docxPkg = loadPackage(docxTemplate);
        merge(htmlJdomDoc, docxPkg, docxTarget, matchingPolicy, DefensiveCopy.NOT_REQUIRED);
    }
    
    public void merge(String html, WordprocessingMLPackage docxTemplate, OutputStream docxTarget, MatchingPolicy matchingPolicy) throws MergeException, LoadInputException {
        org.jdom2.Document htmlJdomDoc = Jdom2.loadInput(html);
        merge(htmlJdomDoc, docxTemplate, docxTarget, matchingPolicy, DefensiveCopy.REQUIRED);
    }

    public void merge(org.w3c.dom.Document htmlDoc, InputStream docxTemplate, OutputStream docxTarget, MatchingPolicy matchingPolicy) throws MergeException, LoadTemplateException {
        WordprocessingMLPackage docxPkg = loadPackage(docxTemplate);
        merge(htmlDoc, docxPkg, docxTarget, matchingPolicy, DefensiveCopy.NOT_REQUIRED);
    }

    public void merge(org.w3c.dom.Document htmlDoc, WordprocessingMLPackage docxTemplate, OutputStream docxTarget, MatchingPolicy matchingPolicy, DefensiveCopy defensiveCopy) throws MergeException {
        org.jdom2.Document htmlJdomDoc = new DOMBuilder().build(htmlDoc);
        merge(htmlJdomDoc, docxTemplate, docxTarget, matchingPolicy, DefensiveCopy.REQUIRED);
    }

    private enum DefensiveCopy {
        REQUIRED,
        NOT_REQUIRED
    }
    
    private void merge(org.jdom2.Document htmlDoc, WordprocessingMLPackage docxTemplate, OutputStream docxTarget, MatchingPolicy matchingPolicy, DefensiveCopy defensiveCopy) throws MergeException {
    
        if(defensiveCopy == DefensiveCopy.REQUIRED) {
            docxTemplate = Docx.clone(docxTemplate);
        }
        
        File tempTargetFile = createTempFile();
        FileInputStream tempTargetFis = null;
        try {
            Element bodyEl = Jdom2.htmlBodyFor(htmlDoc);
            Body docXBody = Docx.docxBodyFor(docxTemplate);

            merge(bodyEl, docXBody, matchingPolicy);

            docxTemplate.save(tempTargetFile);
            tempTargetFis = new FileInputStream(tempTargetFile);
            IOUtils.copy(tempTargetFis, docxTarget);
        } catch (Docx4JException e) {
            throw new MergeException("unable to write to target file", e);
        } catch (FileNotFoundException e) {
            throw new MergeException("unable to read back from target file", e);
        } catch (IOException e) {
            throw new MergeException("unable to generate output stream from temporary file", e);
        } finally {
            IOUtils.closeQuietly(tempTargetFis);
            tempTargetFile.delete();
        }
    }

    private static void merge(Element htmlBody, Body docXBody, MatchingPolicy matchingPolicy) throws MergeException {
        List<String> matchedInputIds = Lists.newArrayList();
        List<String> unmatchedInputIds = Lists.newArrayList();

        List<Content> htmlBodyContents = htmlBody.getContent();
        for(Content input: htmlBodyContents) {
            if(!(input instanceof Element)) {
                continue;
            }
            mergeInto((Element) input, docXBody, matchingPolicy, matchedInputIds, unmatchedInputIds);
        }

        List<String> unmatchedPlaceHolders = unmatchedPlaceholders(docXBody, matchedInputIds);

        matchingPolicy.unmatchedInputs(unmatchedInputIds);
        matchingPolicy.unmatchedPlaceholders(unmatchedPlaceHolders);
    }

    private static void mergeInto(Element input, Body docXBody, MatchingPolicy matchingPolicy, List<String> matchedInputs, List<String> unmatchedInputs) throws MergeException {
        
        String id = Jdom2.attrOf(input, "id");
        if(id == null) {
            throw new MergeException("Missing 'id' attribute for element within body of input HTML");
        }

        MergeType mergeType = MergeType.lookup(input.getName(), Jdom2.attrOf(input, "class"));
        if(mergeType == null) {
            unmatchedInputs.add(id);
            return;
        }

        SdtElement docxElement = FirstMatch.matching(docXBody, Docx.withTagVal(id));
        if(docxElement == null) {
            unmatchedInputs.add(id);
            return;
        }
        
        if(mergeType.merge(input, docxElement)) {
            matchedInputs.add(id);
        } else {
            unmatchedInputs.add(id);
        }
    }

    private enum MergeType {
        PLAIN ("p.plain"),
        RICH("p.rich"),
        DATE("p.date"),
        UL("ul") {
            @Override
            boolean merge(Element htmlUl, SdtElement sdtElement) {
                List<Element> htmlLiList = htmlUl.getChildren("li");
                if(htmlLiList.isEmpty()) {
                    return false;
                }
                
                List<P> docxPOrigList = AllMatches.<P>matching(sdtElement, Types.withType(P.class));
                if(docxPOrigList.isEmpty()) {
                    return false;
                }
                
                List<P> docxPNewList = Lists.newArrayList();
                for (int htmlLiNum=0; htmlLiNum < htmlLiList.size(); htmlLiNum++) {
                    Element htmlLi = htmlLiList.get(htmlLiNum);
                    
                    List<Element> htmlPList = htmlLi.getChildren("p");
                    
                    for(int htmlPNum=0; htmlPNum<htmlPList.size(); htmlPNum++) {
                        int numDocxPNum = docxPOrigList.size();
                        int docxPNum = numDocxPNum == 1 || htmlPNum == 0? 0: 1;
                        P docxP = XmlUtils.deepCopy(docxPOrigList.get(docxPNum));
                        docxPNewList.add(docxP);
                        R docxR = FirstMatch.<R>matching(docxP, Types.withType(R.class));
                        Element htmlP = htmlPList.get(htmlPNum);
                        Docx.setText(docxR, Jdom2.textValueOf(htmlP));
                    }
                }
                
                // remove original and replace with new
                List<Object> content = sdtElement.getSdtContent().getContent();
                for (P docxP : docxPOrigList) {
                    content.remove(docxP);
                }
                for (P docxP : docxPNewList) {
                    content.add(docxP);
                }
                return true;
            }
        },
        TABLE("table") {
            @Override
            boolean merge(Element htmlTable, SdtElement sdtElement) {

                List<Element> htmlTrOrigList = htmlTable.getChildren("tr");
                if(htmlTrOrigList.isEmpty()) {
                    return false;
                }

                List<Object> docxContents = sdtElement.getSdtContent().getContent();
                Tbl docxTbl = FirstMatch.matching(docxContents, Types.withType(Tbl.class));
                if(docxTbl == null) {
                    return false;
                }
                List<Tr> docxTrList = AllMatches.matching(docxTbl, Types.withType(Tr.class));
                if(docxTrList.size() < 2) {
                    // require a header row and one other
                    return false;
                }

                List<Tr> docxTrNewList = Lists.newArrayList();
                for (int htmlRowNum=0; htmlRowNum < htmlTrOrigList.size(); htmlRowNum++) {
                    Element htmlTr = htmlTrOrigList.get(htmlRowNum);
                    
                    int numDocxBodyTr = docxTrList.size()-1;
                    int docxTrNum = (htmlRowNum % numDocxBodyTr) + 1;
                    Tr docxTr = XmlUtils.deepCopy(docxTrList.get(docxTrNum));
                    docxTrNewList.add(docxTr);
                    List<Tc> docxTcList = AllMatches.matching(docxTr.getContent(), Types.withType(Tc.class));
                    List<Element> htmlTdList = htmlTr.getChildren("td");
                    List<String> htmlCellValues = Lists.transform(htmlTdList, Jdom2.textValue());
                    for (int cellNum=0; cellNum < docxTcList.size(); cellNum++) {
                        Tc docxTc = docxTcList.get(cellNum);
                        String value = cellNum < htmlCellValues.size()? htmlCellValues.get(cellNum): "";
                        P docxP = FirstMatch.matching(docxTc.getContent(), Types.withType(P.class));
                        if(docxP == null) {
                            return false;
                        }
                        R docxR = FirstMatch.matching(docxP, Types.withType(R.class));
                        if(docxR == null) {
                            return false;
                        }
                        Docx.setText(docxR, value);
                    }
                }
                docxReplaceRows(docxTbl, docxTrList, docxTrNewList);
                return true;
            }

            private void docxReplaceRows(Tbl docxTbl, List<Tr> docxTrList, List<Tr> docxTrToAdd) {
                List<Object> docxTblContent = docxTbl.getContent();
                boolean first = true;
                for (Tr docxTr : docxTrList) {
                    if(first) {
                        // header, do NOT remove
                        first = false;
                    } else {
                        docxTblContent.remove(docxTr);
                    }
                }
                for (Tr docxTr : docxTrToAdd) {
                    docxTblContent.add(docxTr);
                }
            }
        };

        boolean merge(Element htmlElement, SdtElement docxElement) {
            String htmlTextValue = Jdom2.textValueOf(htmlElement);
            if(htmlTextValue == null) {
                return false;
            }

            R docxR = FirstMatch.matching(docxElement, Types.withType(R.class));
            if(docxR == null) {
                return false;
            }
            return Docx.setText(docxR, htmlTextValue);
        }

        
        private final String type;
        private MergeType(String type) {
            this.type = type;
        }

        public static MergeType lookup(String name, String clazz) {
            String type = name + (clazz != null? "." + clazz: "");
            for (MergeType mt : values()) {
                if(Objects.equal(mt.type, type)) {
                    return mt;
                }
            }
            return null;
        }
    }

    private static List<String> unmatchedPlaceholders(Body docXBody, List<String> matchedIds) {
        List<SdtElement> taggedElements = AllMatches.matching(docXBody, Docx.withAnyTag());
        List<String> unmatchedPlaceHolders = Lists.transform(taggedElements, Docx.tagToValue());
        unmatchedPlaceHolders.removeAll(matchedIds);
        return unmatchedPlaceHolders;
    }

    private static File createTempFile() throws MergeException {
        try {
            return File.createTempFile("docx", null);
        } catch (IOException ex) {
            throw new MergeException("Unable to create temporary working file", ex);
        }
    }
}
