package com.thesis.thesis.infrastructure.adapter.mongo.document;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.PersistenceConstructor;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.List;

@Document(collection = "generatedDocuments")
public class GeneratedPDF {

    @Id
    public String id;

    @Field("generatedPages")
    public List<String> generatedPages;

    public GeneratedPDF() {
    }

    @PersistenceConstructor
    public GeneratedPDF(String id, List<String> generatedPages) {
        this.id = id;
        this.generatedPages = generatedPages;
    }

// WARN: causes InaccessibleObjectException
//    @Field("generatedDocument")
//    public PDDocument generatedDocument;
}
