package com.thesis.thesis.misc;

import javax.imageio.ImageIO;
import java.awt.image.RenderedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UncheckedIOException;
import java.util.Base64;

public class Converter {

    public static String imgToString(final RenderedImage img) {
        final ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        try (final OutputStream b64os = Base64.getEncoder().wrap(outputStream)) {
            ImageIO.write(img, "png", b64os);
        } catch (final IOException ioe) {
            throw new UncheckedIOException(ioe);
        }
        return outputStream.toString();
    }

}
