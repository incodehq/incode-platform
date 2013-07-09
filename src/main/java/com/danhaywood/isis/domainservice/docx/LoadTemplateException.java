package com.danhaywood.isis.domainservice.docx;

public class LoadTemplateException extends DocxServiceException {
    private static final long serialVersionUID = 1L;
    public LoadTemplateException(String message, Throwable cause) {
        super(message, cause);
    }
}