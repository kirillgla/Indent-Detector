package indentDetector;

public class BraceLayout {
    private int openingBraces;
    private int closingBraces;

    BraceLayout(int openingBraces, int closingBraces) {
        this.openingBraces = openingBraces;
        this.closingBraces = closingBraces;
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof BraceLayout))
        {
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

}
