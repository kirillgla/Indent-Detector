package indentDetector;

public final class CodeLine {
    private IndentType indentType;
    private int indentSize;
    private BraceLayout braceLayout;

    public IndentType getIndentType() {
        return indentType;
    }

    public int getIndentSize() {
        return indentSize;
    }

    public BraceLayout getBraceLayout() {
        return braceLayout;
    }

    CodeLine(IndentType indentType, int indentSize, BraceLayout braceLayout) {
        this.indentType = indentType;
        this.indentSize = indentSize;
        this.braceLayout = braceLayout;
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof CodeLine)) {
            return false;
        }

        CodeLine other = (CodeLine) obj;

        return indentType == other.indentType &&
                indentSize == other.indentSize &&
                braceLayout.equals(other.braceLayout);
    }

    // input is assumed to contain one line only
    static CodeLine parse(String input) throws InvalidIndentationException {
        if (Program.isNullOrWhitespace(input)) {
            throw new IllegalArgumentException("Empty line cannot be parsed into CodeLine");
        }

        IndentType indentType = getIndentType(input);

        if (indentType == IndentType.Mixed) {
            throw new InvalidIndentationException();
        }

        int indentSize = 0;
        if (indentType != IndentType.Unknown) {
            indentSize = getIndentSize(input);
        }

        BraceLayout braceLayout = getBraceLayout(input);

        return new CodeLine(indentType, indentSize, braceLayout);
    }

    static IndentType getIndentType(String input) {
        IndentType result = IndentType.Unknown;

        for (int i = 0; i < input.length(); i++) {
            char current = input.charAt(i);

            if (result == IndentType.Unknown) {
                if (current == ' ') {
                    result = IndentType.Spaces;
                } else if (current == '\t') {
                    result = IndentType.Tabs;
                } else {
                    return IndentType.Unknown;
                }
            } else if (result == IndentType.Tabs && current == ' ' || result == IndentType.Spaces && current == '\t') {
                return IndentType.Mixed;
            } else {
                return result;
            }
        }

        return result;
    }

    /**
     * input is assumed to be valid: not white-only,
     * contains either only spaces or only tabs,
     * contains at least one whitespace character
     */
    static int getIndentSize(String input) {
        int count = 0;
        while (input.charAt(count) == ' ' || input.charAt(count) == '\t') {
            count++;
        }
        return count;
    }

    static BraceLayout getBraceLayout(String input) {
        int openingBraces = 0;
        int closingBraces = 0;

        for (int i = 0; i < input.length(); i++) {
            char current = input.charAt(i);

            if (current == '{') {
                openingBraces++;
            } else if (current == '}') {
                closingBraces++;
            }
        }

        return new BraceLayout(openingBraces, closingBraces, input.trim().charAt(0) == '}');
    }
}
