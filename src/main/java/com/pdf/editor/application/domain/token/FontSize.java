package com.pdf.editor.application.domain.token;

public class FontSize extends Attribute {

    private final float fontSize;

    public FontSize(float fontSize) {
        this.fontSize = fontSize;
    }

    public float getFontSize() {
        return fontSize;
    }
}
