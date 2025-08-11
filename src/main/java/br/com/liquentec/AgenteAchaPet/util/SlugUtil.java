package br.com.liquentec.AgenteAchaPet.util;

import java.text.Normalizer;
import java.util.Locale;
import java.util.regex.Pattern;

public class SlugUtil {
    // Espaços e underscores serão tratados como separadores
    private static final Pattern SEPARATORS = Pattern.compile("[\\s_]+");
    private static final Pattern NONLATIN = Pattern.compile("[^\\p{Alnum}-]");
    private static final Pattern HYPHENS = Pattern.compile("-+");

    public static String toSlug(String input) {
        if (input == null)
            return "";

        // Substitui espaços e underscores por hífen
        String slug = SEPARATORS.matcher(input).replaceAll("-");

        // Normaliza e remove acentos
        slug = Normalizer.normalize(slug, Normalizer.Form.NFD);

        // Remove caracteres não-latinos, exceto hífen
        slug = NONLATIN.matcher(slug).replaceAll("");

        // Reduz múltiplos hífens para um único
        slug = HYPHENS.matcher(slug).replaceAll("-");

        return slug.toLowerCase(Locale.ROOT);
    }
}
