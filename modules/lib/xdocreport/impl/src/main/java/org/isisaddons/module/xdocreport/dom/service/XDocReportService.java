package org.isisaddons.module.xdocreport.dom.service;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.apache.poi.xwpf.converter.pdf.PdfConverter;
import org.apache.poi.xwpf.converter.pdf.PdfOptions;
import org.apache.poi.xwpf.usermodel.XWPFDocument;

import org.apache.isis.applib.annotation.DomainService;
import org.apache.isis.applib.annotation.NatureOfService;
import org.apache.isis.applib.annotation.Programmatic;

import fr.opensagres.xdocreport.core.XDocReportException;
import fr.opensagres.xdocreport.core.io.internal.ByteArrayOutputStream;
import fr.opensagres.xdocreport.document.IXDocReport;
import fr.opensagres.xdocreport.document.registry.XDocReportRegistry;
import fr.opensagres.xdocreport.template.IContext;
import fr.opensagres.xdocreport.template.TemplateEngineKind;
import fr.opensagres.xdocreport.template.formatter.FieldsMetadata;

@DomainService(nature = NatureOfService.DOMAIN)
public class XDocReportService {

    PdfOptions pdfOptions;

    @PostConstruct
    public void init() {
        pdfOptions = PdfOptions.create();
    }

    @Programmatic
    public byte[] render(
            final byte[] templateBytes,
            final XDocReportModel dataModel,
            final OutputType outputType) throws IOException {
        try {
            final byte[] docxBytes = toDocx(templateBytes, dataModel);

            switch (outputType) {
            case PDF:
                return toPdf(docxBytes);
            default: // ie DOCX
                return docxBytes;
            }

        } catch (XDocReportException e) {
            throw new IOException(e);
        }
    }

    private byte[] toDocx(final byte[] bytes, final XDocReportModel dataModel) throws IOException, XDocReportException {
        IXDocReport report = XDocReportRegistry
                .getRegistry().loadReport(new ByteArrayInputStream(bytes), TemplateEngineKind.Freemarker);

        IContext context = report.createContext();

        FieldsMetadata fieldsMetadata = report.createFieldsMetadata();

        final Map<String, XDocReportModel.Data> contextObjects = dataModel.getContextData();
        for (Map.Entry<String, XDocReportModel.Data> entry : contextObjects.entrySet()) {
            final XDocReportModel.Data data = entry.getValue();
            final String key = entry.getKey();
            fieldsMetadata.load(key, data.getCls(), data.isList());
            context.put(key, data.getObj());
        }

        final ByteArrayOutputStream baos = new ByteArrayOutputStream();
        report.process(context, baos);

        return baos.toByteArray();
    }

    private byte[] toPdf(final byte[] docxBytes) throws IOException {

        XWPFDocument document = new XWPFDocument(new ByteArrayInputStream(docxBytes));

        final ByteArrayOutputStream baos = new ByteArrayOutputStream();
        PdfConverter.getInstance().convert(document, baos, pdfOptions);

        return baos.toByteArray();
    }

}



