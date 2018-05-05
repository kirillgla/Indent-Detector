package indentDetector;

/**
 * State of {@link Parser}
 */
public enum ParseMode {
    Ordinary,
    MultilineComment,
    String,
    Char
}
