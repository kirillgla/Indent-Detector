package indentDetector;

import java.util.regex.Pattern;

public class SubstringDetector {
    private Pattern pattern;
    private int maxLength;
    private StringBuilder words;

    public SubstringDetector(String pattern, int maxLength) {
        this.pattern = Pattern.compile(pattern);
        this.maxLength = maxLength;
        words = new StringBuilder();
        words.append(' ');
    }

    /**
     * @return isMatch after adding
     */
    public boolean nextChar(char next) {
        if (words.length() >= maxLength) {
            words.deleteCharAt(0);
        }
        words.append(next);
        return isMatch();
    }

    public boolean isMatch() {
        return pattern.matcher(words).matches();
    }
}
