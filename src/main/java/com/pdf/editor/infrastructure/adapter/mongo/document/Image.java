package com.pdf.editor.infrastructure.adapter.mongo.document;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.io.Serializable;

@Document(collection = "images")
public class Image implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    public String id;

    @Field("documentId")
    public String documentId;

    @Field("name")
    public String name;

    @Field("data")
    public String data;

    @Field("extension")
    public String extension;

    public Image() {
    }
}
