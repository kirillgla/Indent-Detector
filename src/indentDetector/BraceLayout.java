package indentDetector;

public class BraceLayout {
    private int openingBraces;
    private int closingBraces;
    private boolean startsWithClosingBrace;

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

    public int getOpeningBraces() {
        return openingBraces;
    }

    public int getClosingBraces() {
        return closingBraces;
    }

    public boolean startsWithClosing() {
        return startsWithClosingBrace;
    }

    int getBraceDifference() {
        return openingBraces - closingBraces;
    }
}
