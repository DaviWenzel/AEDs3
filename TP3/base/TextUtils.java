
package base;
import java.text.Normalizer;
import java.util.*;

public class TextUtils {

    private static final Set<String> STOP_WORDS = new HashSet<>(Arrays.asList(
        "a", "o", "os", "as", "de", "da", "do", "das", "dos", "e",
        "é", "ser", "são", "um", "uma", "umas", "uns", "em", "no", "na",
        "nos", "nas", "por", "para", "com", "como", "que", "se", "ao",
        "à", "às", "aos", "ou", "mais", "mas", "quando", "onde", "porém",
        "então", "tanto", "desde", "sem", "sobre", "sob", "isso", "isto",
        "aquela", "aquele", "aquilo", "essa", "esse", "seu", "sua", "seus",
        "suas", "meu", "minha", "meus", "minhas", "teu", "tua", "teus",
        "tuas", "dele", "dela", "deles", "delas"
    ));

    public static String normalize(String str) {
        if (str == null) return "";
        String n = Normalizer.normalize(str, Normalizer.Form.NFD)
                .replaceAll("\\p{InCombiningDiacriticalMarks}+", "");
        return n.toLowerCase();
    }

    public static boolean isStopWord(String term) {
        return STOP_WORDS.contains(term);
    }

    public static List<String> getValidTerms(String texto) {
        List<String> termos = new ArrayList<>();
        if (texto == null) return termos;
        String normalized = normalize(texto);
        String[] partes = normalized.split("[^a-z0-9]+");
        for (String p : partes) {
            if (p == null || p.isEmpty()) continue;
            if (!isStopWord(p)) termos.add(p);
        }
        return termos;
    }
}
