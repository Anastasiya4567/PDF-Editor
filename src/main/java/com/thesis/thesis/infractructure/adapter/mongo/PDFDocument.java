package com.thesis.thesis.infractructure.adapter.mongo;

import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "pdfDocuments")
@TypeAlias()
public class Document {
}
