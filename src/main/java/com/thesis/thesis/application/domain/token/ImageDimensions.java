package com.thesis.thesis.application.domain.token;

public class ImageDimensions extends Attribute {

    private final float width;
    private final float height;

    public ImageDimensions(float width, float height) {
        this.width = width;
        this.height = height;
    }

    public float getWidth() {
        return width;
    }

    public float getHeight() {
        return height;
    }
}
