package com.pdf.editor.interfaces.rest.requests;

public class GenerateDocumentRequest {

    private String id;
    private String sourceCode;

    public GenerateDocumentRequest() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSourceCode() {
        return sourceCode;
    }

    public void setSourceCode(String sourceCode) {
        this.sourceCode = sourceCode;
    }
}
