package com.danhaywood.isis.domainservice.docx;

public class LoadInputException extends DocxServiceException {
    private static final long serialVersionUID = 1L;
    public LoadInputException(String message, Throwable cause) {
        super(message, cause);
    }
}