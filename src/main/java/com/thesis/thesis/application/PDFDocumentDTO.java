package com.thesis.thesis.application;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.OffsetDateTime;

public class PDFDocumentDTO {

    @JsonProperty("id")
    public String id;

    @JsonProperty("title")
    public String title;

    @JsonProperty("sourceCode")
    public String sourceCode;

    @JsonProperty("creationDate")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    public OffsetDateTime creationDate;

    @JsonProperty("generatedDocumentId")
    public String generatedDocumentId;

    public PDFDocumentDTO() {
    }

}
