package com.thesis.thesis.application.domain;

public class Token {
    private KeyWords keyWord;
    private String value;

    public Token(KeyWords keyWord, String value) {
        this.keyWord = keyWord;
        this.value = value;
    }

    public KeyWords getKeyWord() {
        return keyWord;
    }

    public void setKeyWord(KeyWords keyWord) {
        this.keyWord = keyWord;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
