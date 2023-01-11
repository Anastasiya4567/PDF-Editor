package com.thesis.thesis.application.domain;

import com.thesis.thesis.application.domain.token.KeyWords;
import com.thesis.thesis.application.domain.token.Token;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class DocumentScannerTest {

    private DocumentScanner documentScanner;

    @BeforeEach
    public void setup() {
        documentScanner = new DocumentScanner();
    }

    @Test
    void scanText() {
        // given
        documentScanner.setSourceText("\\text test text\0");

        // when
        List<Token> tokens = documentScanner.scan();

        // then
        assertThat(tokens.size()).isEqualTo(1);
        assertThat(tokens.get(0).getKeyWord()).isEqualTo(KeyWords.TEXT);
        assertThat(tokens.get(0).getValue()).isEqualTo("test text");
    }

    @Test
    void scanImage() {
        // given
        documentScanner.setSourceText("\\img image.png\0");

        // when
        List<Token> tokens = documentScanner.scan();

        // then
        assertThat(tokens.size()).isEqualTo(1);
        assertThat(tokens.get(0).getKeyWord()).isEqualTo(KeyWords.IMG);
        assertThat(tokens.get(0).getValue()).isEqualTo("image.png");
    }

    @Test
    void scanTitle() {
        // given
        documentScanner.setSourceText("\\title Hello World\0");

        // when
        List<Token> tokens = documentScanner.scan();

        // then
        assertThat(tokens.size()).isEqualTo(1);
        assertThat(tokens.get(0).getKeyWord()).isEqualTo(KeyWords.TITLE);
        assertThat(tokens.get(0).getValue()).isEqualTo("Hello World");
    }

    @Test
    void scanSomeElements() {
        // given
        documentScanner.setSourceText("""
                \\title test title
                \\text test text
                \\img image.png
                \\text test text again\0""");

        // when
        List<Token> tokens = documentScanner.scan();

        // then
        assertThat(tokens.size()).isEqualTo(4);
        assertThat(tokens.get(0).getKeyWord()).isEqualTo(KeyWords.TITLE);
        assertThat(tokens.get(0).getValue()).isEqualTo("test title");
        assertThat(tokens.get(1).getKeyWord()).isEqualTo(KeyWords.TEXT);
        assertThat(tokens.get(1).getValue()).isEqualTo("test text");
        assertThat(tokens.get(2).getKeyWord()).isEqualTo(KeyWords.IMG);
        assertThat(tokens.get(2).getValue()).isEqualTo("image.png");
        assertThat(tokens.get(3).getKeyWord()).isEqualTo(KeyWords.TEXT);
        assertThat(tokens.get(3).getValue()).isEqualTo("test text again");
    }
}
