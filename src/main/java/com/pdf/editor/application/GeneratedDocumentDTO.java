package com.pdf.editor.application;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class GeneratedDocumentDTO {

    @JsonProperty("id")
    public String id;

    @JsonProperty("documentId")
    public String documentId;

    @JsonProperty("generatedPages")
    public List<String> generatedPages;

    public GeneratedDocumentDTO() {
    }
}
