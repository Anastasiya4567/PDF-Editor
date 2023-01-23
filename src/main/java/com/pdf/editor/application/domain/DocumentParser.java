package com.pdf.editor.application.domain;

import com.pdf.editor.application.domain.token.*;
import com.pdf.editor.infrastructure.adapter.mongo.document.Image;
import io.vavr.control.Try;
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

    private static final float DEFAULT_FONT_SIZE = 12;

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

        if (tokens.isEmpty()) {
            document.addPage(new PDPage());
        }

        tokens.forEach(token -> Match(token.getKeyWord()).of(
                Case($(KeyWords.TITLE), () -> tryAppendTitle(document, token)),
                Case($(KeyWords.TEXT), () -> tryAppendText(document, token)),
                Case($(KeyWords.IMG), () -> tryAppendImage(documentId, document, token))
        ));

        return document;
    }

    private Void tryAppendImage(String documentId, PDDocument document, Token token) {
        return Try.of(() -> appendImage(document, documentId, token))
                .getOrElseThrow((e) -> new RuntimeException("Can't append image: " + e));
    }

    private Void tryAppendText(PDDocument document, Token token) {
        return Try.of(() -> appendText(document, token))
                .getOrElseThrow((e) -> new RuntimeException("Can't append text: " + e));
    }

    private Void tryAppendTitle(PDDocument document, Token token) {
        return Try.of(() -> appendTitle(document, token))
                .getOrElseThrow((e) -> new RuntimeException("Can't append title: " + e));
    }

    private Void appendTitle(PDDocument document, Token token) throws IOException {

        float fontSize = setFontSize(token.getAttributes());

        PDFont pdfFont = new PDType1Font(HELVETICA);

        List<String> lines = parseIndividualLines(token.getValue(), pdfFont, fontSize, calculateWidthForTitle());

        PDPage page = getAppropriatePage(document);

        int lineNumber = 0;
        float filledSpace = 0;
        int nextLineNumber = 0;

        while (nextLineNumber < lines.size()) {

            PDPageContentStream contentStream = new PDPageContentStream(document, page, PDPageContentStream.AppendMode.APPEND, true);

            contentStream.beginText();
            contentStream.setFont(pdfFont, fontSize);
            if (lines.size() == 1) {
                float indentation = getPageWidth() / 2 - calculateStartTitleX(getMediaBox()) - calculateSubstringSize(pdfFont, fontSize, lines.get(0)) / 2;
                contentStream.newLineAtOffset(calculateStartTitleX(getMediaBox()) + indentation, heightCursor);
            } else {
                contentStream.newLineAtOffset(calculateStartTitleX(getMediaBox()), heightCursor);
            }

            for (int i = nextLineNumber; i < lines.size(); ++i) {
                if (calculateFreePageSpace(filledSpace) < 0) {
                    filledSpace = 0;
                    setHeightCursorOnPageTop();
                    break;
                } else {
                    lineNumber++;
                    if (i == lines.size() - 2) {
                        float indentation = getPageWidth() / 2 - calculateStartTitleX(getMediaBox()) - calculateSubstringSize(pdfFont, fontSize, lines.get(i + 1)) / 2;
                        contentStream.showText(lines.get(i));
                        contentStream.newLineAtOffset(indentation, -calculateLeading(fontSize));
                    } else {
                        contentStream.showText(lines.get(i));
                        contentStream.newLineAtOffset(0, -calculateLeading(fontSize));
                    }
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

            page = createEmptyPage();
        }

        return null;
    }

    private float setFontSize(List<Attribute> attributes) {
        if (!attributes.isEmpty()) {
            for (var attribute : attributes) {
                if (attribute instanceof FontSize) {
                    return ((FontSize) attribute).getFontSize();
                }
            }
        }
        return DEFAULT_FONT_SIZE;
    }

    private Void appendText(PDDocument document, Token token) throws IOException {

        float fontSize = setFontSize(token.getAttributes());

        PDFont pdfFont = new PDType1Font(HELVETICA);

        List<String> lines = parseIndividualLines(token.getValue(), pdfFont, fontSize, calculateWidth());

        PDPage page = getAppropriatePage(document);

        int lineNumber = 0;

        float filledSpace = 0;
        int nextLineNumber = 0;

        while (nextLineNumber < lines.size()) {

            PDPageContentStream contentStream = new PDPageContentStream(document, page, PDPageContentStream.AppendMode.APPEND, true);

            contentStream.beginText();
            contentStream.setFont(pdfFont, fontSize);
            contentStream.newLineAtOffset(calculateStartX(getMediaBox()) + 30, heightCursor);

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

            page = createEmptyPage();
        }
        return null;
    }

    public Void appendImage(PDDocument document, String documentId, Token token) throws IOException {

        float imageWidth = 200;
        float imageHeight = 150;

        if (!token.getAttributes().isEmpty()) {
            for (var attribute : token.getAttributes()) {
                if (attribute instanceof ImageDimensions) {
                    ImageDimensions imageDimensions = (ImageDimensions) attribute;
                    imageWidth = imageDimensions.getWidth();
                    imageHeight = imageDimensions.getHeight();
                }
            }
        }

        PDPage page = getAppropriatePage(document);

        PDPageContentStream contentStream = new PDPageContentStream(document, page, PDPageContentStream.AppendMode.APPEND, true);
        PDImageXObject pdImage = findAndConvertImage(document, documentId, token.getValue());

        if (calculateFreePageSpace(imageHeight) < 0) {
            document.addPage(page);
            page = createEmptyPage();
            setHeightCursorOnPageTop();
        }
        contentStream.drawImage(pdImage, calculateStartImageX(imageWidth), heightCursor - imageHeight, imageWidth, imageHeight);
        contentStream.close();
        document.addPage(page);
        heightCursor = heightCursor - imageHeight - calculateLeading(25);
        return null;
    }

    private float calculateStartImageX(float imageWidth) {
        return (getPageWidth() - imageWidth) / 2;
    }

    private float getPageWidth() {
        return getMediaBox().getWidth();
    }

    private PDRectangle getMediaBox() {
        return new PDPage().getMediaBox();
    }

    private PDImageXObject findAndConvertImage(PDDocument document, String documentId, String name) throws IOException {
        Image image = imageRepository.load(documentId, name);
        byte[] byteArray = Base64.getDecoder().decode(image.data);
        return PDImageXObject.createFromByteArray(document, byteArray, image.name);
    }

    private float calculateFreePageSpace(float filledSpace) {
        return heightCursor - MARGIN - filledSpace;
    }

    private PDPage getAppropriatePage(PDDocument document) {
        if (document.getNumberOfPages() == 0) {
            return createEmptyPage();
        }
        PDPage page = document.getPage(document.getNumberOfPages() - 1);
        document.removePage(document.getNumberOfPages() - 1);
        return page;
    }

    private PDPage createEmptyPage() {
        return new PDPage();
    }

    private float calculateWidthForTitle() {
        return getPageWidth() - 3 * MARGIN;
    }

    private float calculateHeight(PDRectangle mediaBox) {
        return mediaBox.getHeight() - 2 * MARGIN;
    }

    private float calculateWidth() {
        return getPageWidth() - 2 * MARGIN;
    }

    private float calculateLeading(float fontSize) {
        return 1.5f * fontSize;
    }

    private List<String> parseIndividualLines(String text, PDFont pdfFont, float fontSize, float width) throws IOException {
        text = text.replace("\n", " ").replace("\r", " ");
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
        heightCursor = getMediaBox().getUpperRightY() - MARGIN;
    }

    private float calculateStartTitleX(PDRectangle mediaBox) {
        return mediaBox.getLowerLeftX() + 1.5f * MARGIN;
    }

    private float calculateStartX(PDRectangle mediaBox) {
        return mediaBox.getLowerLeftX() + MARGIN;
    }
}
