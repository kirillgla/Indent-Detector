package indentDetector;

/**
 * Contains information about file line
 */
public final class CodeLine {
    private IndentType indentType;
    private int indentSize;
    private BraceLayout braceLayout;

    IndentType getIndentType() {
        return indentType;
    }

    int getIndentSize() {
        return indentSize;
    }

    BraceLayout getBraceLayout() {
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
}
