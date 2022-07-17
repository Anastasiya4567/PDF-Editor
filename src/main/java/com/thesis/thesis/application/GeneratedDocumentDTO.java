package com.thesis.thesis.application;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class GeneratedDocumentDTO {

    @JsonProperty("id")
    public String id;

    @JsonProperty("generatedPages")
    public List<String> generatedPages;

    public GeneratedDocumentDTO() {
    }
}
