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

    public PDFDocumentDTO(String id, String title, String sourceCode, OffsetDateTime creationDate, String generatedDocumentId) {
        this.id = id;
        this.title = title;
        this.sourceCode = sourceCode;
        this.creationDate = creationDate;
        this.generatedDocumentId = generatedDocumentId;
    }

    @Override
    public String toString() {
        return "PDFDocumentDTO{" +
                "id='" + id + '\'' +
                ", title='" + title + '\'' +
                ", sourceCode='" + sourceCode + '\'' +
                ", creationDate=" + creationDate +
                ", generatedDocumentId='" + generatedDocumentId + '\'' +
                '}';
    }
}
