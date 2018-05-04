package indentDetector;

import java.util.ArrayList;

class Parser {
    private ParseMode parseMode;

    ArrayList<OpenBraceType> openBraces;

    // It isn't really necessary to store these
    // in between parsing operations, though
    private SubstringDetector singleLineCommentDetector;
    private SubstringDetector openCommentDetector;
    private SubstringDetector closingCommentDetector;
    private SubstringDetector stringDetector;
    private SubstringDetector charDetector;
    private SubstringDetector caseDetector;
    private SubstringDetector breakDetector;

    Parser() {
        parseMode = ParseMode.Ordinary;

        singleLineCommentDetector = new SubstringDetector("//", 2);
        openCommentDetector = new SubstringDetector("/\\*", 2);
        closingCommentDetector = new SubstringDetector("\\*/", 2);
        stringDetector = new SubstringDetector("[^\\\\]\"", 2);
        charDetector = new SubstringDetector("[^\\\\]'", 2);
        caseDetector = new SubstringDetector("(.*\\Wcase\\W)|(.*\\Wdefault\\s*:)", Integer.MAX_VALUE);
        breakDetector = new SubstringDetector("\\Wbreak\\W", 7);

        openBraces = new ArrayList<>();
    }

    private void invalidateDetectors(char nextChar) {
        singleLineCommentDetector.nextChar(nextChar);
        openCommentDetector.nextChar(nextChar);
        closingCommentDetector.nextChar(nextChar);
        stringDetector.nextChar(nextChar);
        charDetector.nextChar(nextChar);
        caseDetector.nextChar(nextChar);
        breakDetector.nextChar(nextChar);
    }

    /**
     * input is assumed to contain one line only
     */
    CodeLine parse(String input) throws InvalidIndentationException {
        if (Parser.isNullOrWhitespace(input)) {
            throw new IllegalArgumentException("Empty line cannot be parsed into CodeLine");
        }

        openCommentDetector.clear();
        closingCommentDetector.clear();
        caseDetector.clear();
        breakDetector.clear();

        IndentType indentType = getIndentType(input);

        if (indentType == IndentType.Mixed) {
            throw new InvalidIndentationException();
        }

        int indentSize = getIndentSize(input);

        BraceLayout braceLayout = getBraceLayout(input);

        return new CodeLine(indentType, indentSize, braceLayout);
    }

    BraceLayout getBraceLayout(String input) {
        int trueOpenBraces = 0;
        int trueClosingBraces = 0;

        for (int i = 0; i < input.length(); i++) {
            char current = input.charAt(i);

            invalidateDetectors(current);

            // Don't parse anything while dealing with non-code data
            switch (parseMode) {
                case MultilineComment:
                    if (closingCommentDetector.isMatch()) {
                        parseMode = ParseMode.Ordinary;
                    }
                    continue;
                case String:
                    if (stringDetector.isMatch()) {
                        parseMode = ParseMode.Ordinary;
                    }
                    continue;
                case Char:
                    if (charDetector.isMatch()) {
                        parseMode = ParseMode.Ordinary;
                    }
                    continue;
                default:
                    break;
            }

            if (singleLineCommentDetector.isMatch()) {
                break;
            }

            if (openCommentDetector.isMatch()) {
                parseMode = ParseMode.MultilineComment;
                continue;
            }

            if (stringDetector.isMatch()) {
                parseMode = ParseMode.String;
                continue;
            }

            if (charDetector.isMatch()) {
                parseMode = ParseMode.Char;
                continue;
            }

            if (caseDetector.isMatch()) {
                // If input file contains valid java code,
                // this will not throw an exception
                if (openBraces.get(openBraces.size() - 1) != OpenBraceType.Case) {
                    // previous label must've been terminated without `break`
                    trueOpenBraces++;
                    openBraces.add(OpenBraceType.Case);
                }
            }

            if (breakDetector.isMatch())
                // If input file contains valid java code,
                // this will not throw an exception
                if (openBraces.get(openBraces.size() - 1) == OpenBraceType.Case) {
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
                    trueClosingBraces++;
                }
                openBraces.remove(openBraces.size() - 1);
            }
        }
        return new BraceLayout(trueOpenBraces, trueClosingBraces, input.trim().charAt(0) == '}');
    }

    static boolean isNullOrWhitespace(String s) {
        return s == null || s.isEmpty() || s.trim().isEmpty() || s.trim().startsWith("//");
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
     * input is assumed to contain
     * either only spaces or only tabs
     */
    static int getIndentSize(String input) {
        int count = 0;
        while (input.charAt(count) == ' ' || input.charAt(count) == '\t') {
            count++;
        }
        return count;
    }

    @Override
    public String toString() {
        return "Parser: " + parseMode;
    }
}
