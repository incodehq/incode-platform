package org.isisaddons.module.excel.dom;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import org.apache.poi.util.IOUtils;
import org.apache.isis.applib.value.Blob;

class ExcelFileBlobConverter {

    private final String xslxMimeType = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";

    // //////////////////////////////////////
    
    Blob toBlob(final String name, final File file) {
        FileInputStream fis = null;
        ByteArrayOutputStream baos = null;
        try {
            fis = new FileInputStream(file);
            baos = new ByteArrayOutputStream();
            IOUtils.copy(fis, baos);
            return new Blob(name, xslxMimeType, baos.toByteArray());
        } catch (IOException ex) {
            throw new ExcelService.Exception(ex);
        } finally {
            IOUtils.closeQuietly(fis);
            IOUtils.closeQuietly(baos);
        }
    }

}
