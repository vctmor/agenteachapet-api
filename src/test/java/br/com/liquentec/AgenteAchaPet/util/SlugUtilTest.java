package br.com.liquentec.AgenteAchaPet.util;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class SlugUtilTest {


    @Test
    void testToSlug_RemovesWhitespaceAndSpecialCharacters() {
        
        String input = "Olá mundo!";
        String expectedPattern = "ola-mundo";

        String result = SlugUtil.toSlug(input);

        assertEquals(expectedPattern, result);
    }

    @Test
    void testToSlug_HandlesAccents() {
        String input = "Coração Café";
        String result = SlugUtil.toSlug(input);

        assertEquals("coracao-cafe", result);
    }

    @Test
    void testToSlug_ConvertsToLowerCase() {
        String input = "Texto EM MAIÚSCULO";
        String result = SlugUtil.toSlug(input);

        assertEquals("texto-em-maiusculo", result);
    }

    @Test
    void testToSlug_RemovesNonLatinCharacters() {
        String input = "T@x! #123";
        String result = SlugUtil.toSlug(input);

        assertEquals("tx-123", result);
    }

    @Test
    void testToSlug_EmptyString() {
        String input = "";
        String result = SlugUtil.toSlug(input);

        assertEquals("", result);
    }

    @Test
    void testToSlug_OnlySpaces() {
        String input = "     ";
        String result = SlugUtil.toSlug(input);

        assertEquals("-", result);
    }

    @Test
    void testToSlug_NumericString() {
        String input = "123 456";
        String result = SlugUtil.toSlug(input);

        assertEquals("123-456", result);
    }
}
