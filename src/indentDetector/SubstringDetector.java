package indentDetector;

import java.util.regex.Pattern;

/**
 * Tool that observes input and notifies
 * whether regex it is listening for matches
 */
class SubstringDetector {
    private Pattern pattern;
    private int maxLength;
    private StringBuilder words;

    SubstringDetector(String pattern, int maxLength) {
        this.pattern = Pattern.compile(pattern);
        this.maxLength = maxLength;
        words = new StringBuilder();
        clear();
    }

    /**
     * @return {@link #isMatch} after adding
     */
    boolean nextChar(char next) {
        if (words.length() >= maxLength) {
            words.deleteCharAt(0);
        }
        words.append(next);
        return isMatch();
    }

    boolean isMatch() {
        return pattern.matcher(words).matches();
    }

    void clear() {
        words.setLength(0);
        words.append(' ');
    }
}
