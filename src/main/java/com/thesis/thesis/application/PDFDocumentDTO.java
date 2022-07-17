package com.thesis.thesis.application;

import com.fasterxml.jackson.annotation.JsonProperty;

public class PDFDocumentDTO {

    @JsonProperty("id")
    public String id;

    @JsonProperty("title")
    public String title;

    @JsonProperty("sourceCode")
    public String sourceCode;

    @JsonProperty("generatedDocumentId")
    public String generatedDocumentId;

    public PDFDocumentDTO() {
    }

}
