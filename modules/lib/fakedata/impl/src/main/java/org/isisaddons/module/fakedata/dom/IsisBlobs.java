package org.isisaddons.module.fakedata.dom;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.isis.applib.annotation.Programmatic;
import org.apache.isis.applib.value.Blob;
import org.apache.isis.commons.internal.resources._Resources;

public class IsisBlobs extends AbstractRandomValueGenerator{

    public IsisBlobs(final FakeDataService fakeDataService) {
        super(fakeDataService);
    }

    private final static List<String> fileNames = Arrays.asList(
            "image01-150x150.jpg",
            "image01-240x180.jpg",
            "image01-640x480.jpg",
            "image01-2048x1536.jpg",
            "image01-4000x3000.jpg",
            "image02-150x150.jpg",
            "image02-240x180.jpg",
            "image02-640x480.jpg",
            "image02-2048x1536.jpg",
            "image02-4000x3000.jpg",
            "Pawson-Naked-Objects-thesis.pdf",
            "rick-mugridge-paper.pdf");

    @Programmatic
    public Blob any() {
        final List<String> fileNames = IsisBlobs.fileNames;
        return asBlob(fileNames);
    }

    @Programmatic
    public Blob anyJpg() {
        return asBlob(fileNamesEndingWith(".jpg"));
    }

    @Programmatic
    public Blob anyPdf() {
        return asBlob(fileNamesEndingWith(".pdf"));
    }

    private static List<String> fileNamesEndingWith(final String suffix) {
        return IsisBlobs.fileNames.stream()
                .filter(input -> input.endsWith(suffix))
                .collect(Collectors.toList());
    }

    private Blob asBlob(final List<String> fileNames) {
        final int randomIdx = fake.ints().upTo(fileNames.size());
        final String randomFileName = fileNames.get(randomIdx);
        return asBlob(randomFileName);
    }

    private static Blob asBlob(final String fileName) {
        //final URL resource = _Resources.getResourceUrl(IsisBlobs.class, "blobs/" + fileName);
        final InputStream inputStream = _Resources.load(IsisBlobs.class, "blobs/" + fileName);
        //final ByteSource byteSource = _Resources.asByteSource(resource);
        try {
            final byte[] bytes = toByteArray(inputStream);
            return new Blob(fileName, mimeTypeFor(fileName), bytes);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static byte[] toByteArray(InputStream in) throws IOException {
        final ByteArrayOutputStream os = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int len;
        while ((len = in.read(buffer)) != -1) {
            os.write(buffer, 0, len);
        }
        return os.toByteArray();
    }

    private static String mimeTypeFor(final String fileName) {
        if(fileName.endsWith("jpg")) {
            return "image/jpeg";
        }
        return "application/pdf";
    }

}
