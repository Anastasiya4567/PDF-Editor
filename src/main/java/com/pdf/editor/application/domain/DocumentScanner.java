package com.pdf.editor.application.domain;

import com.pdf.editor.application.domain.token.*;

import java.util.ArrayList;
import java.util.List;

public class DocumentScanner {

    private static final char END_OF_SOURCE_TEXT = '\0';

    private static final char END_OF_LINE = '\n';

    private int currentIndex = 0;

    private char character;

    private String sourceText;

    private List<Token> tokens = new ArrayList<>();

    public DocumentScanner() {

    }

    public void setSourceText(String sourceText) {
        this.sourceText = sourceText;
        this.character = sourceText.charAt(0);
    }

    public List<Token> scan() {
        while (character != END_OF_SOURCE_TEXT) {
            character = sourceText.charAt(currentIndex);
            identify();
        }

        return tokens;
    }

    private void identify() {
        if (character == '\\') {
            ++currentIndex;
            if (this.checkIfTitle(currentIndex))
                return;
            if (this.checkIfText(currentIndex))
                return;
            if (this.checkIfImage(currentIndex))
                return;
            if (this.checkIfComment(currentIndex))
                return;
            if (this.checkIfBegin(currentIndex))
                return;
            this.checkIfEnd(currentIndex);
        }
    }

    private boolean checkIfComment(int index) {
        if (sourceText.charAt(index) == '\\') {
            currentIndex++;
            while (sourceText.charAt(currentIndex) != END_OF_LINE) {
                if (sourceText.charAt(currentIndex) == END_OF_SOURCE_TEXT) {
                    return true;
                }
                currentIndex++;
            }
            currentIndex++;
            return true;
        }
        return false;
    }

    private boolean checkIfTitle(int index) {
        if (sourceText.substring(index, index + 5).equals(KeyWords.TITLE.name().toLowerCase())) {
            currentIndex += 5;
            tokens.add(new Token(KeyWords.TITLE, readTextAttributes(), readValue()));
            return true;
        }
        return false;
    }

    private List<Attribute> readTextAttributes() {
        if (sourceText.charAt(currentIndex) == '(') {
            currentIndex++;
            StringBuilder fontSize = new StringBuilder();
            while (sourceText.charAt(currentIndex) != ')') {
                fontSize.append(sourceText.charAt(currentIndex));
                currentIndex++;
            }
            currentIndex++;
            return List.of(
                    new FontSize(Float.parseFloat(fontSize.toString())));

        }
        return List.of();
    }

    private boolean checkIfImage(int index) {
        if (sourceText.substring(index, index + 3).equals(KeyWords.IMG.name().toLowerCase())) {
            currentIndex += 3;
            tokens.add(new Token(KeyWords.IMG, readImageAttributes(), readValue()));
            return true;
        }
        return false;
    }

    private List<Attribute> readImageAttributes() {
        if (sourceText.charAt(currentIndex) == '(') {
            currentIndex++;
            StringBuilder width = new StringBuilder();
            StringBuilder height = new StringBuilder();
            while (sourceText.charAt(currentIndex) != ',') {
                width.append(sourceText.charAt(currentIndex));
                currentIndex++;
            }
            currentIndex++;
            while (sourceText.charAt(currentIndex) != ')') {
                height.append(sourceText.charAt(currentIndex));
                currentIndex++;
            }
            currentIndex++;
            return List.of(
                    new ImageDimensions(
                            Float.parseFloat(width.toString()),
                            Float.parseFloat(height.toString())));

        }
        return List.of();
    }

    private boolean checkIfText(int index) {
        if (sourceText.substring(index, index + 4).equals(KeyWords.TEXT.name().toLowerCase())) {
            currentIndex += 4;
            tokens.add(new Token(KeyWords.TEXT, readTextAttributes(), readValue()));
            return true;
        }
        return false;
    }

    private String readValue() {
        currentIndex++;
        StringBuilder stringBuilder = new StringBuilder();
        while (sourceText.charAt(currentIndex) != END_OF_LINE) {
            if (sourceText.charAt(currentIndex) == END_OF_SOURCE_TEXT) {
                return stringBuilder.toString();
            }
            stringBuilder.append(sourceText.charAt(currentIndex));
            currentIndex++;
        }
        currentIndex++;
        return stringBuilder.toString();
    }

    private boolean checkIfBegin(int index) {
        if (sourceText.substring(index, index + 5).equals(KeyWords.BEGIN.name().toLowerCase())) {
            currentIndex += 5;
            tokens.add(new Token(KeyWords.BEGIN, ""));
            return true;
        }
        return false;
    }

    private boolean checkIfEnd(int index) {
        if (sourceText.substring(index, index + 3).equals(KeyWords.END.name().toLowerCase())) {
            currentIndex += 3;
            tokens.add(new Token(KeyWords.END, ""));
            return true;
        }
        return false;
    }
}
