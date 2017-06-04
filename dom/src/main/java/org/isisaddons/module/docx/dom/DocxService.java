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

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;
import java.util.Map;
import javax.annotation.PostConstruct;
import com.google.common.base.Objects;
import com.google.common.collect.Lists;
import org.apache.commons.io.IOUtils;
import org.docx4j.Docx4J;
import org.docx4j.XmlUtils;
import org.docx4j.convert.out.FOSettings;
import org.docx4j.fonts.IdentityPlusMapper;
import org.docx4j.fonts.Mapper;
import org.docx4j.openpackaging.exceptions.Docx4JException;
import org.docx4j.openpackaging.packages.WordprocessingMLPackage;
import org.docx4j.wml.Body;
import org.docx4j.wml.P;
import org.docx4j.wml.R;
import org.docx4j.wml.SdtElement;
import org.docx4j.wml.Tbl;
import org.docx4j.wml.Tc;
import org.docx4j.wml.Tr;
import org.isisaddons.module.docx.dom.traverse.AllMatches;
import org.isisaddons.module.docx.dom.traverse.FirstMatch;
import org.isisaddons.module.docx.dom.util.Docx;
import org.isisaddons.module.docx.dom.util.Jdom2;
import org.isisaddons.module.docx.dom.util.Types;
import org.jdom2.Content;
import org.jdom2.Element;
import org.jdom2.input.DOMBuilder;
import org.apache.isis.applib.annotation.DomainService;
import org.apache.isis.applib.annotation.NatureOfService;
import org.apache.isis.applib.annotation.Programmatic;

@DomainService(
        nature = NatureOfService.DOMAIN
)
public class DocxService {

    @Programmatic
    @PostConstruct
    public void init(final Map<String,String> properties) {
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
        private MatchingPolicy(final boolean allowUnmatchedInput, final boolean allowUnmatchedPlaceholders) {
            this.allowUnmatchedInput = allowUnmatchedInput;
            this.allowUnmatchedPlaceholders = allowUnmatchedPlaceholders;
        }

        public void unmatchedInputs(final List<String> unmatched) throws MergeException {
            if(!allowUnmatchedInput && !unmatched.isEmpty()) {
                throw new MergeException("Input elements " + unmatched + " were not matched to placeholders");
            }
        }
        public void unmatchedPlaceholders(final List<String> unmatched) throws MergeException {
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
     *  or {@link #merge(org.w3c.dom.Document, org.docx4j.openpackaging.packages.WordprocessingMLPackage, java.io.OutputStream, org.isisaddons.module.docx.dom.DocxService.MatchingPolicy, org.isisaddons.module.docx.dom.DocxService.OutputType)}
     */
    @Programmatic
    public WordprocessingMLPackage loadPackage(final InputStream docxTemplate) throws LoadTemplateException {
        final WordprocessingMLPackage docxPkg;
        try {
            docxPkg = WordprocessingMLPackage.load(docxTemplate);
        } catch (final Docx4JException ex) {
            throw new LoadTemplateException("Unable to load docx template from input stream", ex);
        }
        return docxPkg;
    }

    @Programmatic
    public void merge(final String html, final InputStream docxTemplate, final OutputStream docxTarget, final MatchingPolicy matchingPolicy) throws LoadInputException, LoadTemplateException, MergeException {
        final org.jdom2.Document htmlJdomDoc = Jdom2.loadInput(html);
        final WordprocessingMLPackage docxPkg = loadPackage(docxTemplate);
        merge(htmlJdomDoc, docxPkg, docxTarget, matchingPolicy, DefensiveCopy.NOT_REQUIRED, OutputType.DOCX);
    }
    
    @Programmatic
    public void merge(final String html, final WordprocessingMLPackage docxTemplate, final OutputStream docxTarget, final MatchingPolicy matchingPolicy) throws MergeException, LoadInputException {
        final org.jdom2.Document htmlJdomDoc = Jdom2.loadInput(html);
        merge(htmlJdomDoc, docxTemplate, docxTarget, matchingPolicy, DefensiveCopy.REQUIRED, OutputType.DOCX);
    }

    @Programmatic
    public void merge(final org.w3c.dom.Document htmlDoc, final InputStream docxTemplate, final OutputStream docxTarget, final MatchingPolicy matchingPolicy) throws MergeException, LoadTemplateException {
        final WordprocessingMLPackage docxPkg = loadPackage(docxTemplate);
        final org.jdom2.Document htmlJdomDoc = new DOMBuilder().build(htmlDoc);
        merge(htmlJdomDoc, docxPkg, docxTarget, matchingPolicy, DefensiveCopy.NOT_REQUIRED, OutputType.DOCX);
    }

    @Programmatic
    public void merge(final org.w3c.dom.Document htmlDoc, final WordprocessingMLPackage docxTemplate, final OutputStream docxTarget, final MatchingPolicy matchingPolicy) throws MergeException {
        final org.jdom2.Document htmlJdomDoc = new DOMBuilder().build(htmlDoc);
        merge(htmlJdomDoc, docxTemplate, docxTarget, matchingPolicy, DefensiveCopy.REQUIRED, OutputType.DOCX);
    }

    @Programmatic
    public void merge(final String html, final InputStream docxTemplate, final OutputStream docxTarget, final MatchingPolicy matchingPolicy, final OutputType outputType) throws MergeException, LoadInputException, LoadTemplateException {
        final org.jdom2.Document htmlJdomDoc = Jdom2.loadInput(html);
        final WordprocessingMLPackage docxPkg = loadPackage(docxTemplate);
        merge(htmlJdomDoc, docxPkg, docxTarget, matchingPolicy, DefensiveCopy.REQUIRED, outputType);
    }

    @Programmatic
    public void merge(final String html, final WordprocessingMLPackage docxTemplate, final OutputStream docxTarget, final MatchingPolicy matchingPolicy, final OutputType outputType) throws MergeException, LoadInputException {
        final org.jdom2.Document htmlJdomDoc = Jdom2.loadInput(html);
        merge(htmlJdomDoc, docxTemplate, docxTarget, matchingPolicy, DefensiveCopy.REQUIRED, outputType);
    }

    @Programmatic
    public void merge(final org.w3c.dom.Document htmlDoc, final InputStream docxTemplate, final OutputStream docxTarget, final MatchingPolicy matchingPolicy, final OutputType outputType) throws MergeException, LoadTemplateException {
        final org.jdom2.Document htmlJdomDoc = new DOMBuilder().build(htmlDoc);
        final WordprocessingMLPackage docxPkg = loadPackage(docxTemplate);
        merge(htmlJdomDoc, docxPkg, docxTarget, matchingPolicy, DefensiveCopy.REQUIRED, outputType);
    }

    @Programmatic
    public void merge(final org.w3c.dom.Document htmlDoc, final WordprocessingMLPackage docxTemplate, final OutputStream docxTarget, final MatchingPolicy matchingPolicy, final OutputType outputType) throws MergeException {
        final org.jdom2.Document htmlJdomDoc = new DOMBuilder().build(htmlDoc);
        merge(htmlJdomDoc, docxTemplate, docxTarget, matchingPolicy, DefensiveCopy.REQUIRED, outputType);
    }

    private enum DefensiveCopy {
        REQUIRED,
        NOT_REQUIRED
    }

    /**
     * The type of the file to generate
     */
    public enum OutputType {
        DOCX,
        /**
         * Support for PDF should be considered experimental.
         */
        PDF
    }
    
    private void merge(
            final org.jdom2.Document htmlDoc,
            final WordprocessingMLPackage docxTemplateInput,
            final OutputStream docxTarget,
            final MatchingPolicy matchingPolicy,
            final DefensiveCopy defensiveCopy,
            final OutputType outputType)
            throws MergeException {

        final WordprocessingMLPackage docxTemplate =
                defensiveCopy == DefensiveCopy.REQUIRED
                        ? Docx.clone(docxTemplateInput)
                        : docxTemplateInput;

        try {
            final Element bodyEl = Jdom2.htmlBodyFor(htmlDoc);
            final Body docXBody = Docx.docxBodyFor(docxTemplate);

            merge(bodyEl, docXBody, matchingPolicy);

            if (outputType == OutputType.PDF) {


                final FOSettings foSettings = Docx4J.createFOSettings();
                foSettings.setWmlPackage(docxTemplate);

                try {
                    final Mapper fontMapper = new IdentityPlusMapper();
                    docxTemplate.setFontMapper(fontMapper, true);
                } catch (final Exception e) {
                    throw new MergeException("unable to set font mapper for PDF generation", e);
                }

                // according to the documentation/examples the XSL transformation
                // is slower but more feature complete than Docx4J.FLAG_EXPORT_PREFER_NONXSL

                final int flags = Docx4J.FLAG_EXPORT_PREFER_XSL;

                Docx4J.toFO(foSettings, docxTarget, flags);

            } else {
                final File tempTargetFile = createTempFile();
                FileInputStream tempTargetFis = null;
                try {
                    docxTemplate.save(tempTargetFile);
                    tempTargetFis = new FileInputStream(tempTargetFile);
                    IOUtils.copy(tempTargetFis, docxTarget);
                } finally {
                    IOUtils.closeQuietly(tempTargetFis);
                    tempTargetFile.delete();
                }
            }
        } catch (final Docx4JException e) {
            throw new MergeException("unable to write to target file", e);
        } catch (final FileNotFoundException e) {
            throw new MergeException("unable to read back from target file", e);
        } catch (final IOException e) {
            throw new MergeException("unable to generate output stream from temporary file", e);
        }
    }

    private static void merge(final Element htmlBody, final Body docXBody, final MatchingPolicy matchingPolicy) throws MergeException {
        final List<String> matchedInputIds = Lists.newArrayList();
        final List<String> unmatchedInputIds = Lists.newArrayList();

        final List<Content> htmlBodyContents = htmlBody.getContent();
        for(final Content input: htmlBodyContents) {
            if(!(input instanceof Element)) {
                continue;
            }
            mergeInto((Element) input, docXBody, matchedInputIds, unmatchedInputIds);
        }

        final List<String> unmatchedPlaceHolders = unmatchedPlaceholders(docXBody, matchedInputIds);

        matchingPolicy.unmatchedInputs(unmatchedInputIds);
        matchingPolicy.unmatchedPlaceholders(unmatchedPlaceHolders);
    }

    private static void mergeInto(final Element input, final Body docXBody, final List<String> matchedInputs, final List<String> unmatchedInputs) throws MergeException {
        
        final String id = Jdom2.attrOf(input, "id");
        if(id == null) {
            throw new MergeException("Missing 'id' attribute for element within body of input HTML");
        }

        final MergeType mergeType = MergeType.lookup(input.getName(), Jdom2.attrOf(input, "class"));
        if(mergeType == null) {
            unmatchedInputs.add(id);
            return;
        }

        final SdtElement docxElement = FirstMatch.matching(docXBody, Docx.withTagVal(id));
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
            boolean merge(final Element htmlUl, final SdtElement sdtElement) {
                final List<Element> htmlLiList = htmlUl.getChildren("li"); // can be empty
                
                final List<P> docxPOrigList = AllMatches.<P>matching(sdtElement, Types.withType(P.class));
                if(docxPOrigList.isEmpty()) {
                    return false;
                }
                
                final List<P> docxPNewList = Lists.newArrayList();
                for (int htmlLiNum=0; htmlLiNum < htmlLiList.size(); htmlLiNum++) {
                    final Element htmlLi = htmlLiList.get(htmlLiNum);
                    
                    final List<Element> htmlPList = htmlLi.getChildren("p");
                    
                    for(int htmlPNum=0; htmlPNum<htmlPList.size(); htmlPNum++) {
                        final int numDocxPNum = docxPOrigList.size();
                        final int docxPNum = numDocxPNum == 1 || htmlPNum == 0? 0: 1;
                        final P docxP = XmlUtils.deepCopy(docxPOrigList.get(docxPNum));
                        docxPNewList.add(docxP);
                        final R docxR = FirstMatch.<R>matching(docxP, Types.withType(R.class));
                        final Element htmlP = htmlPList.get(htmlPNum);
                        Docx.setText(docxR, Jdom2.textValueOf(htmlP));
                    }
                }
                
                // remove original and replace with new
                final List<Object> content = sdtElement.getSdtContent().getContent();
                for (final P docxP : docxPOrigList) {
                    content.remove(docxP);
                }
                for (final P docxP : docxPNewList) {
                    content.add(docxP);
                }
                return true;
            }
        },
        TABLE("table") {
            @Override
            boolean merge(final Element htmlTable, final SdtElement sdtElement) {

                final List<Element> htmlTrOrigList = htmlTable.getChildren("tr"); // can be empty

                final List<Object> docxContents = sdtElement.getSdtContent().getContent();
                final Tbl docxTbl = FirstMatch.matching(docxContents, Types.withType(Tbl.class));
                if(docxTbl == null) {
                    return false;
                }
                final List<Tr> docxTrList = AllMatches.matching(docxTbl, Types.withType(Tr.class));
                if(docxTrList.size() < 2) {
                    // require a header row and one other
                    return false;
                }

                final List<Tr> docxTrNewList = Lists.newArrayList();
                for (int htmlRowNum=0; htmlRowNum < htmlTrOrigList.size(); htmlRowNum++) {
                    final Element htmlTr = htmlTrOrigList.get(htmlRowNum);
                    
                    final int numDocxBodyTr = docxTrList.size()-1;
                    final int docxTrNum = (htmlRowNum % numDocxBodyTr) + 1;
                    final Tr docxTr = XmlUtils.deepCopy(docxTrList.get(docxTrNum));
                    docxTrNewList.add(docxTr);
                    final List<Tc> docxTcList = AllMatches.matching(docxTr.getContent(), Types.withType(Tc.class));
                    final List<Element> htmlTdList = htmlTr.getChildren("td");
                    final List<String> htmlCellValues = Lists.transform(htmlTdList, Jdom2.textValue());
                    for (int cellNum=0; cellNum < docxTcList.size(); cellNum++) {
                        final Tc docxTc = docxTcList.get(cellNum);
                        final String value = cellNum < htmlCellValues.size()? htmlCellValues.get(cellNum): "";
                        final P docxP = FirstMatch.matching(docxTc.getContent(), Types.withType(P.class));
                        if(docxP == null) {
                            return false;
                        }
                        final R docxR = FirstMatch.matching(docxP, Types.withType(R.class));
                        if(docxR == null) {
                            return false;
                        }
                        Docx.setText(docxR, value);
                    }
                }
                docxReplaceRows(docxTbl, docxTrList, docxTrNewList);
                return true;
            }

            private void docxReplaceRows(final Tbl docxTbl, final List<Tr> docxTrList, final List<Tr> docxTrToAdd) {
                final List<Object> docxTblContent = docxTbl.getContent();
                boolean first = true;
                for (final Tr docxTr : docxTrList) {
                    if(first) {
                        // header, do NOT remove
                        first = false;
                    } else {
                        docxTblContent.remove(docxTr);
                    }
                }
                for (final Tr docxTr : docxTrToAdd) {
                    docxTblContent.add(docxTr);
                }
            }
        };

        boolean merge(final Element htmlElement, final SdtElement docxElement) {
            final String htmlTextValue = Jdom2.textValueOf(htmlElement);
            if(htmlTextValue == null) {
                return false;
            }

            final R docxR = FirstMatch.matching(docxElement, Types.withType(R.class));
            if(docxR == null) {
                return false;
            }
            return Docx.setText(docxR, htmlTextValue);
        }

        
        private final String type;
        private MergeType(final String type) {
            this.type = type;
        }

        public static MergeType lookup(final String name, final String clazz) {
            final String type = name + (clazz != null? "." + clazz: "");
            for (final MergeType mt : values()) {
                if(Objects.equal(mt.type, type)) {
                    return mt;
                }
            }
            return null;
        }
    }

    private static List<String> unmatchedPlaceholders(final Body docXBody, final List<String> matchedIds) {
        final List<SdtElement> taggedElements = AllMatches.matching(docXBody, Docx.withAnyTag());
        final List<String> unmatchedPlaceHolders = Lists.transform(taggedElements, Docx.tagToValue());
        unmatchedPlaceHolders.removeAll(matchedIds);
        return unmatchedPlaceHolders;
    }

    private static File createTempFile() throws MergeException {
        try {
            return File.createTempFile("docx", null);
        } catch (final IOException ex) {
            throw new MergeException("Unable to create temporary working file", ex);
        }
    }
}
