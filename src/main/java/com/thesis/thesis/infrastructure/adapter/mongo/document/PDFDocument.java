package com.thesis.thesis.infrastructure.adapter.mongo.document;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.PersistenceConstructor;
import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.io.Serializable;
import java.time.OffsetDateTime;

@Document(collection = "pdfDocuments")
@TypeAlias("pdf")
public class PDFDocument implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    public String id;

    @Field("ownerEmail")
    public String ownerEmail;

    @Field("privateAccess")
    public Boolean privateAccess;

    @Field("title")
    public String title;

    @Field("sourceCode")
    public String sourceCode;

    @Field("creationDate")
    public OffsetDateTime creationDate;

    @Field("generatedDocumentId")
    public String generatedDocumentId;

    public PDFDocument() {
    }

    @PersistenceConstructor
    public PDFDocument(String id, String ownerEmail, Boolean privateAccess, String title, String sourceCode, OffsetDateTime creationDate, String generatedDocumentId) {
        this.id = id;
        this.ownerEmail = ownerEmail;
        this.privateAccess = privateAccess;
        this.title = title;
        this.sourceCode = sourceCode;
        this.creationDate = creationDate;
        this.generatedDocumentId = generatedDocumentId;
    }
}
