package com.thesis.thesis.application.domain;

import com.thesis.thesis.infrastructure.adapter.mongo.document.Image;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

import static io.vavr.API.*;
import static org.apache.pdfbox.pdmodel.font.Standard14Fonts.FontName.HELVETICA;

public class DocumentParser {

    private static final float MARGIN = 72;

    private static final char SPACE = ' ';

    private static final String EMPTY_STRING = "";

    private static final int BEFORE_LINE_INDEX = -1;

    private List<Token> tokens;

    private final ImageRepository imageRepository;

    private float heightCursor;

    public DocumentParser(ImageRepository imageRepository) {
        this.imageRepository = imageRepository;
        setHeightCursorOnPageTop();
    }

    public void setTokens(List<Token> tokens) {
        this.tokens = tokens;
    }

    public PDDocument parse(String documentId) throws IOException {

        PDDocument document = new PDDocument();

        tokens.forEach(token -> Match(token.getKeyWord()).of(
                Case($(KeyWords.TITLE), () -> {
                    try {
                        return fillTitle(document, token.getValue());
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }),
                Case($(KeyWords.TEXT), () -> {
                    try {
                        return fillPages(document, token.getValue());
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }),
                Case($(KeyWords.IMG), () -> {
                    try {
                        return addImage(document, documentId, token.getValue());
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                })
        ));

        return document;
    }

    private Void fillTitle(PDDocument document, String text) throws IOException {

        PDRectangle mediaBox = new PDPage().getMediaBox();

        PDFont pdfFont = new PDType1Font(HELVETICA);
        float fontSize = 25;
        float height = calculateHeight(mediaBox);

        List<String> lines = parseIndividualLines(text, pdfFont, fontSize, calculateWidth(mediaBox));

        PDPage page = getAppropriatePage(document);

        // TODO

        return null;
    }

    private Void fillPages(PDDocument document, String text) throws IOException {

        PDRectangle mediaBox = new PDPage().getMediaBox();

        PDFont pdfFont = new PDType1Font(HELVETICA);
        float fontSize = 25;
        float height = calculateHeight(mediaBox);

        List<String> lines = parseIndividualLines(text, pdfFont, fontSize, calculateWidth(mediaBox));

        PDPage page = getAppropriatePage(document);

        int lineNumber = 0;

        float filledSpace = 0;
        int nextLineNumber = 0;

        while (nextLineNumber < lines.size()) {

            PDPageContentStream contentStream = new PDPageContentStream(document, page, PDPageContentStream.AppendMode.APPEND, true);

            contentStream.beginText();
            contentStream.setFont(pdfFont, fontSize);
            contentStream.newLineAtOffset(calculateStartX(mediaBox), heightCursor);

            for (int i = nextLineNumber; i < lines.size(); ++i) {
                if (calculateFreePageSpace(filledSpace) < 0) {
                    filledSpace = 0;
                    setHeightCursorOnPageTop();
                    break;
                } else {
                    lineNumber++;
                    contentStream.showText(lines.get(i));
                    contentStream.newLineAtOffset(0, -calculateLeading(fontSize));
                    filledSpace += calculateLeading(fontSize);
                }

            }
            nextLineNumber = lineNumber;

            if (filledSpace > 0) {
                heightCursor -= filledSpace;
            }

            contentStream.endText();
            contentStream.close();
            document.addPage(page);

            page = new PDPage();
        }
        return null;
    }

    public Void addImage(PDDocument document, String documentId, String text) throws IOException {

        PDRectangle mediaBox = new PDPage().getMediaBox();
        float horizontalCoordinateCenter = mediaBox.getWidth() / 2;

        PDPage page = getAppropriatePage(document);

        PDPageContentStream contentStream = new PDPageContentStream(document, page, PDPageContentStream.AppendMode.APPEND, true);
        Image image = imageRepository.load(documentId, text);

        byte[] byteArray = Base64.getDecoder().decode(image.data);

        PDImageXObject pdImage = PDImageXObject.createFromByteArray(document, byteArray, image.name);
        float imageWidth = 400;
        float imageHeight = 160;

        if (calculateFreePageSpace(imageHeight) < 0) {
            document.addPage(page);
            page = new PDPage();
            setHeightCursorOnPageTop();
        }
        contentStream.drawImage(pdImage,
                horizontalCoordinateCenter - imageWidth / 2, heightCursor - imageHeight,
                imageWidth, imageHeight);
        contentStream.close();
        document.addPage(page);
        heightCursor = heightCursor - imageHeight - calculateLeading(25);
        return null;
    }

    private float calculateFreePageSpace(float filledSpace) {
        return heightCursor - MARGIN - filledSpace;
    }

    private PDPage getAppropriatePage(PDDocument document) {
        if (document.getNumberOfPages() == 0) {
            return new PDPage();
        }
        PDPage page = document.getPage(document.getNumberOfPages() - 1);
        document.removePage(document.getNumberOfPages() - 1);
        return page;
    }

    private float calculateHeight(PDRectangle mediaBox) {
        return mediaBox.getHeight() - 2 * MARGIN;
    }

    private float calculateWidth(PDRectangle mediaBox) {
        return mediaBox.getWidth() - 2 * MARGIN;
    }

    private float calculateLeading(float fontSize) {
        return 1.5f * fontSize;
    }

    private List<String> parseIndividualLines(String text, PDFont pdfFont, float fontSize, float width) throws IOException {
//        text = text.replace("\n", " ").replace("\r", " ");
        List<String> lines = new ArrayList<>();
        int lastSpaceIndex = setBeforeLine();
        while (!isEndOf(text)) {
            int spaceIndex = text.indexOf(SPACE, lastSpaceIndex + 1);
            if (isLastWordOfText(spaceIndex))
                spaceIndex = setToEndOf(text);
            String subString = text.substring(0, spaceIndex);
            float size = calculateSubstringSize(pdfFont, fontSize, subString);
            if (size > width) {
                // next line
                if (lastSpaceIndex < 0)
                    lastSpaceIndex = spaceIndex;
                text = addLineAndCutOfIt(text, lastSpaceIndex, lines);
                lastSpaceIndex = setBeforeLine();
            } else if (isLastLine(text, spaceIndex)) {
                text = addLastLineAndEndText(text, lines);
            } else {
                // not end of line
                lastSpaceIndex = spaceIndex;
            }
        }
        return lines;
    }

    private String addLineAndCutOfIt(String text, int lastSpaceIndex, List<String> lines) {
        String subString = text.substring(0, lastSpaceIndex);
        lines.add(subString);
        return text.substring(lastSpaceIndex).trim();
    }

    private int setToEndOf(String text) {
        return text.length();
    }

    private float calculateSubstringSize(PDFont pdfFont, float fontSize, String subString) throws IOException {
        return fontSize * pdfFont.getStringWidth(subString) / 1000;
    }

    private int setBeforeLine() {
        return BEFORE_LINE_INDEX;
    }

    private boolean isEndOf(String text) {
        return text.length() == 0;
    }

    private boolean isLastWordOfText(int spaceIndex) {
        return spaceIndex < 0;
    }

    private String addLastLineAndEndText(String text, List<String> lines) {
        lines.add(text);
        return EMPTY_STRING;
    }

    private boolean isLastLine(String text, int spaceIndex) {
        return spaceIndex == text.length();
    }


    private void setHeightCursorOnPageTop() {
        heightCursor = new PDPage().getMediaBox().getUpperRightY() - MARGIN;
    }

    private float calculateStartX(PDRectangle mediaBox) {
        return mediaBox.getLowerLeftX() + MARGIN;
    }
}
