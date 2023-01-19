package com.pdf.editor.application;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.OffsetDateTime;

public class PDFDocumentDTO {

    @JsonProperty("id")
    public String id;

    @JsonProperty("title")
    public String title;

    @JsonProperty("ownerEmail")
    public String ownerEmail;

    @JsonProperty("privateAccess")
    public Boolean privateAccess;

    @JsonProperty("sourceCode")
    public String sourceCode;

    @JsonProperty("creationDate")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    public OffsetDateTime creationDate;

    public PDFDocumentDTO() {
    }

    public PDFDocumentDTO(String id, String title, String ownerEmail, Boolean privateAccess, String sourceCode, OffsetDateTime creationDate) {
        this.id = id;
        this.title = title;
        this.ownerEmail = ownerEmail;
        this.privateAccess = privateAccess;
        this.sourceCode = sourceCode;
        this.creationDate = creationDate;
    }

    @Override
    public String toString() {
        return "PDFDocumentDTO{" +
                "id='" + id + '\'' +
                ", title='" + title + '\'' +
                ", ownerEmail='" + ownerEmail + '\'' +
                ", privateAccess=" + privateAccess +
                ", sourceCode='" + sourceCode + '\'' +
                ", creationDate=" + creationDate +
                '}';
    }
}
