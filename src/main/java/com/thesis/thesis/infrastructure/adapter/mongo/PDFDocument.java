package com.thesis.thesis.infrastructure.adapter.mongo;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.PersistenceConstructor;
import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Document(collection = "pdfDocuments")
@TypeAlias("pdf")
public class PDFDocument {

    @Id
    public String id;

    @Field("title")
    public String title;

    @Field("sourceCode")
    public String sourceCode;

    public PDFDocument() {
    }

    @PersistenceConstructor
    public PDFDocument(String id, String title) {
        this.id = id;
        this.title = title;
    }
}
