package indentDetector;

/**
 * Contains information
 * about the way braces are placed
 * in file line
 */
public class BraceLayout {
    private int openingBraces;
    private int closingBraces;
    private boolean startsWithClosingBrace;
    // TODO: 'boolean terminatesSwitch;' for more accurate results

    BraceLayout(int openingBraces, int closingBraces, boolean startsWithClosingBrace) {
        this.openingBraces = openingBraces;
        this.closingBraces = closingBraces;
        this.startsWithClosingBrace = startsWithClosingBrace;
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof BraceLayout)) {
            return false;
        }

        BraceLayout other = (BraceLayout) obj;
        return openingBraces == other.openingBraces && closingBraces == other.closingBraces;
    }

    @Override
    public String toString() {
        return "" + openingBraces + " opening, " + closingBraces + " closing" + (startsWithClosingBrace? ", starts with closing": "");
    }

    int getOpeningBraces() {
        return openingBraces;
    }

    int getClosingBraces() {
        return closingBraces;
    }

    boolean startsWithClosing() {
        return startsWithClosingBrace;
    }

    int getBraceDifference() {
        return openingBraces - closingBraces;
    }
}
