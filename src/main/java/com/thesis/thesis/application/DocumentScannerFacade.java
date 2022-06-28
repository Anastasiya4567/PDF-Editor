package com.thesis.thesis.application;

import com.thesis.thesis.application.domain.KeyWords;
import com.thesis.thesis.application.domain.Token;

import java.util.ArrayList;
import java.util.List;

public class DocumentScannerFacade {

    private int currentIndex = 0;

    private char character;

    private final String sourceText;

    private List<Token> tokens = new ArrayList<>();

    public DocumentScannerFacade(String sourceText) {
        this.sourceText = sourceText;
    }

    public List<Token> scan() {
        while (character != '\0') {
            character = sourceText.charAt(currentIndex);
            identify();
        }

        // TODO
        return tokens;
    }

    private void identify() {
        if (character == '\\') {
            if (this.checkIfBegin(sourceText, ++currentIndex))
                return;
            if (this.checkIfTitle(sourceText, ++currentIndex))
                return;
            if (this.checkIfAuthor(sourceText, ++currentIndex))
                return;
            this.checkIfEnd(sourceText, ++currentIndex);
        }
    }

    private boolean checkIfBegin(String sourceText, int index) {
        if (sourceText.substring(index, index + 5).equals(KeyWords.BEGIN.name())) {
            currentIndex += 5;
            // read value
            tokens.add(new Token(KeyWords.BEGIN, ""));
            return true;
        }
        return false;
    }

    private boolean checkIfTitle(String sourceText, int index) {
        if (sourceText.substring(index, index + 5).equals(KeyWords.TITLE.name())) {
            currentIndex += 5;
            // read value
            tokens.add(new Token(KeyWords.TITLE, "Title"));
            return true;
        }
        return false;
    }

    private boolean checkIfAuthor(String sourceText, int index) {
        if (sourceText.substring(index, index + 6).equals(KeyWords.AUTHOR.name())) {
            currentIndex += 6;
            // read value
            tokens.add(new Token(KeyWords.AUTHOR, "Author"));
            return true;
        }
        return false;
    }

    private boolean checkIfEnd(String sourceText, int index) {
        if (sourceText.substring(index, index + 3).equals(KeyWords.END.name())) {
            currentIndex += 3;
            // read value
            tokens.add(new Token(KeyWords.END, ""));
            return true;
        }
        return false;
    }
}