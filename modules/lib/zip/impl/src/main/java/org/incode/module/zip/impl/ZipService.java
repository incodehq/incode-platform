package org.incode.module.zip.impl;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import org.apache.isis.applib.FatalException;
import org.apache.isis.applib.annotation.DomainService;
import org.apache.isis.applib.annotation.NatureOfService;
import org.apache.isis.applib.annotation.Programmatic;

import lombok.Data;

@DomainService(nature = NatureOfService.DOMAIN)
public class ZipService {

    @Data
    public static class FileAndName {
        private final String name;
        private final File file;
    }

    /**
     * Rather than use the name of the file (which might be temporary files, for example)
     * we explicitly provide the name to use (in the ZipEntry).
     *
     */
    @Programmatic
    public byte[] zip(final List<FileAndName> fileAndNameList) {

        final byte[] bytes;
        try {
            final ByteArrayOutputStream baos = new ByteArrayOutputStream();
            final ZipOutputStream zos = new ZipOutputStream(baos);

            for (FileAndName fan : fileAndNameList) {
                zos.putNextEntry(new ZipEntry(fan.getName()));
                zos.write(Files.readAllBytes(Paths.get(fan.getFile().toURI())));
                zos.closeEntry();
            }
            zos.close();
            bytes = baos.toByteArray();
        } catch (final IOException ex) {
            throw new FatalException("Unable to create zip", ex);
        }
        return bytes;
    }

    /**
     * As per {@link #zip(List)}, but using each file's name as the zip entry (rather than providing it).
     */
    @Programmatic
    public byte[] zipFiles(final List<File> fileList) {
        return zip(fileList.stream()
                           .map(file -> new FileAndName(file.getName(), file))
                           .collect(Collectors.toList())
                );
    }


    @Data
    public static class BytesAndName {
        private final String name;
        private final byte[] bytes;
    }

    /**
     * Similar to {@link #zip(List)}, but uses simple byte[] as the input, rather than files.
     *
     * @param bytesAndNameList
     * @return
     */
    @Programmatic
    public byte[] zip(final List<BytesAndName> bytesAndNameList) {

        final byte[] bytes;
        try {
            final ByteArrayOutputStream baos = new ByteArrayOutputStream();
            final ZipOutputStream zos = new ZipOutputStream(baos);

            for (BytesAndName ban : bytesAndNameList) {
                zos.putNextEntry(new ZipEntry(ban.getName()));
                zos.write(ban.getBytes());
                zos.closeEntry();
            }
            zos.close();
            bytes = baos.toByteArray();
        } catch (final IOException ex) {
            throw new FatalException("Unable to create zip", ex);
        }
        return bytes;
    }



}
