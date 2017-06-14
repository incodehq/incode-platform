package org.isisaddons.module.docx.dom;

public class DocxServiceException extends Exception {
    public DocxServiceException(String message) {
        super(message);
    }
    
    public DocxServiceException(String message, Throwable cause) {
        super(message, cause);
    }

    private static final long serialVersionUID = 1L;
}