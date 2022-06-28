package com.thesis.thesis.application;

import com.thesis.thesis.application.domain.KeyWords;
import com.thesis.thesis.application.domain.Token;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDDocumentInformation;
import org.apache.pdfbox.pdmodel.PDPage;

import java.io.IOException;
import java.util.List;

import static io.vavr.API.*;

//import static io.vavr.API.*;

public class DocumentParserFacade {

    private final List<Token> tokens;

    public DocumentParserFacade(List<Token> tokens) {
        this.tokens = tokens;
    }

    public PDDocument parse() throws IOException {

        PDDocument document = new PDDocument();

        PDPage blankPage = new PDPage();
        document.addPage(blankPage);
//
//        PDFont font = new PDType1Font(HELVETICA_BOLD);
//
        // document information
        PDDocumentInformation documentInformation = document.getDocumentInformation();

        tokens.forEach(token -> Match(token.getKeyWord()).of(
                Case($(KeyWords.TITLE), run(() -> documentInformation.setTitle(token.getValue()))),
                Case($(KeyWords.AUTHOR), run(() -> documentInformation.setAuthor(token.getValue())))
//                    Case($(KeyWords.DATE), documentInformation.setCreationDate(Calendar.getInstance()))
        ));
//
//        PDPageContentStream contentStream = new PDPageContentStream(document, blankPage);
//        contentStream.beginText();
//        contentStream.setFont(font, 12);
//        contentStream.endText();
//
//// Make sure that the content stream is closed:
//        contentStream.close();
//        // ...
        document.close();
        return document;
    }

}
