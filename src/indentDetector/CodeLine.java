package indentDetector;

import java.util.ArrayList;

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
    //
    // nextLine is required to determine whether 'case' label
    // should be treated as opening brace or not
    // (i.e. has content or is directly followed by another case label)
    //
    // openBraces are required to determine whether 'break' operator
    // should be treated as closing brace or not (i.e. terminates 'case' label or not)
    static CodeLine parse(String input, String nextLine, ArrayList<OpenBraceType> openBraces) throws InvalidIndentationException {
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

        BraceLayout braceLayout = getBraceLayout(input, nextLine, openBraces);

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

    static BraceLayout getBraceLayout(String input, String nextLine, ArrayList<OpenBraceType> openBraces) {
        int trueOpenBraces = 0;
        int trueClosingBraces = 0;

        SubstringDetector caseDetector = new SubstringDetector("\\Wcase\\W", 6);
        SubstringDetector defaultDetector = new SubstringDetector("\\Wdefault\\s*:", Integer.MAX_VALUE);
        SubstringDetector breakDetector = new SubstringDetector("\\Wbreak\\W", 7);

        for (int i = 0; i < input.length(); i++) {
            char current = input.charAt(i);

            if (caseDetector.nextChar(current)) {
                // TODO: when case is directly followed by another case it should not be treated as open brace
                trueOpenBraces++;
                openBraces.add(OpenBraceType.Case);
            }
            if (defaultDetector.nextChar(current)) {
                trueOpenBraces++;
                openBraces.add(OpenBraceType.Case);
            }
            if (breakDetector.nextChar(current) && openBraces.get(openBraces.size() - 1) == OpenBraceType.Case) {
                trueClosingBraces++;
                openBraces.remove(openBraces.size() - 1);
            }

            if (current == '{') {
                trueOpenBraces++;
                openBraces.add(OpenBraceType.Other);
            } else if (current == '}') {
                trueClosingBraces++;
                if (openBraces.get(openBraces.size() - 1) == OpenBraceType.Case) {
                    openBraces.remove(openBraces.size() - 1);
                }
                openBraces.remove(openBraces.size() - 1);
            }
        }
        return new BraceLayout(trueOpenBraces, trueClosingBraces, input.trim().charAt(0) == '}');
    }
}
