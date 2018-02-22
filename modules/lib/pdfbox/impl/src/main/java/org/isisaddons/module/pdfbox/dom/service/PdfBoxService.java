package org.isisaddons.module.pdfbox.dom.service;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Arrays;
import java.util.List;

import com.google.common.collect.Lists;

import org.apache.pdfbox.io.MemoryUsageSetting;
import org.apache.pdfbox.multipdf.PDFMergerUtility;

import org.apache.isis.applib.annotation.DomainService;
import org.apache.isis.applib.annotation.NatureOfService;
import org.apache.isis.applib.annotation.Programmatic;

@DomainService(nature = NatureOfService.DOMAIN)
public class PdfBoxService {

    @Programmatic
    public byte[] merge(final byte[]... pdfByteArrays) throws IOException {

        final List<InputStream> inputStreams = asByteArrays(pdfByteArrays);

        final ByteArrayOutputStream baos = new ByteArrayOutputStream();
        merge(baos, (InputStream[]) inputStreams.toArray(new InputStream[0]));

        return baos.toByteArray();
    }

    @Programmatic
    public void merge(final OutputStream outputStream, final InputStream... inputStreams) throws IOException {

        final PDFMergerUtility ut = new PDFMergerUtility();
        for (InputStream inputStream : inputStreams) {
            ut.addSource(inputStream);
        }
        ut.setDestinationStream(outputStream);

        ut.mergeDocuments(MemoryUsageSetting.setupTempFileOnly());

    }

    private static List<InputStream> asByteArrays(final byte[][] pdfByteArrays) {
        final List<InputStream> inputStreams = Lists.newArrayList();
        for (byte[] pdfByteArray : pdfByteArrays) {
            inputStreams.add(new ByteArrayInputStream(pdfByteArray));
        }
        return inputStreams;
    }


    @Programmatic
    public byte[] merge(final List<File> fileList) throws IOException {

        final ByteArrayOutputStream baos = new ByteArrayOutputStream();

        final PDFMergerUtility ut = new PDFMergerUtility();
        for (File file : fileList) {
            ut.addSource(file);
        }

        ut.setDestinationStream(baos);
        ut.mergeDocuments(MemoryUsageSetting.setupTempFileOnly());

        return baos.toByteArray();
    }

    @Programmatic
    public byte[] merge(final File... files) throws IOException {
        return merge(Arrays.asList(files));
    }

}
