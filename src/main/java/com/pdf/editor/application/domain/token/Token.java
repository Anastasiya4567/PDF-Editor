package com.pdf.editor.application.domain.token;

import java.util.List;

public class Token {
    private KeyWords keyWord;

    private List<Attribute> attributes;
    private String value;

    public Token(KeyWords keyWord, List<Attribute> attributes, String value) {
        this.keyWord = keyWord;
        this.attributes = attributes;
        this.value = value;
    }

    public Token(KeyWords keyWord, String value) {
        this(keyWord, List.of(), value);
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

    public List<Attribute> getAttributes() {
        return attributes;
    }

    public void setAttributes(List<Attribute> attributes) {
        this.attributes = attributes;
    }
}
