package com.thesis.thesis.infrastructure.adapter.mongo;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "generatedDocuments")
@TypeAlias("pdd")
public class GeneratedPDF {

    @Id
    public String id;

    // WARN: causes InaccessibleObjectException
//    @Field("generatedDocument")
//    public PDDocument generatedDocument;
}
