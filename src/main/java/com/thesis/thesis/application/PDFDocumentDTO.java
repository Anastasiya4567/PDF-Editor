package com.thesis.thesis.application;

import com.fasterxml.jackson.annotation.JsonProperty;

public class PDFDocumentDTO {

    @JsonProperty("id")
    private String id;

    @JsonProperty("title")
    private String title;

    public PDFDocumentDTO() {
    }

}
