package indentDetector;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class Program {
    public static void main(String[] args) {
        if (args.length == 0) {
            System.out.println("Please, provide path to file to analyze");
            return;
        }

        if (args.length >= 2) {
            System.out.println("Too many arguments provided");
        }

        try {
            String[] lines = getLines(args[0]);
            CodeLine[] codeLines = parseLines(lines);
            IndentType indentType = getIndentType(codeLines);
            switch (indentType) {
                case Unknown:
                    System.out.println("File seems to have no indents at all...");
                    break;
                case Spaces:
                    int bestMatch = getBestMatch(codeLines, 8);
                    System.out.println("Default indentation in file seems to be " + bestMatch + " spaces");
                    break;
                case Tabs:
                    bestMatch = getBestMatch(codeLines, 2);
                    System.out.println("Default indentation in file seems to be " + bestMatch + " tabs");
                    break;
            }
        } catch (IOException e) {
            System.err.println("Could not access file");
        } catch (InvalidIndentationException e) {
            System.err.println("File has mixed space and tab indents");
        }
    }

    private static String[] getLines(String path) throws IOException {
        File file = new File(path);
        FileReader reader = new FileReader(file);
        BufferedReader bufferedReader = new BufferedReader(reader);
        ArrayList<String> lines = new ArrayList<>();
        while (true) {
            String line = bufferedReader.readLine();
            if (line == null) {
                break;
            }

            lines.add(line);
        }
        return lines.toArray(new String[0]);
    }

    private static CodeLine[] parseLines(String[] inputs) throws InvalidIndentationException {
        Parser parser = new Parser();
        ArrayList<CodeLine> lines = new ArrayList<>();
        for (String input : inputs) {
            if (Parser.isNullOrWhitespace(input)) {
                continue;
            }

            lines.add(parser.parse(input));
        }
        return lines.toArray(new CodeLine[0]);
    }

    private static IndentType getIndentType(CodeLine[] lines) throws InvalidIndentationException {
        // By this moment no line can have 'mixed' indent type,
        // so it's enough to check only cross-line consistency
        IndentType indentType = IndentType.Unknown;

        for (CodeLine line : lines) {
            if (line.getIndentType() == IndentType.Unknown) {
                continue;
            }

            if (indentType == IndentType.Unknown) {
                indentType = line.getIndentType();
                continue;
            }

            if (line.getIndentType() != indentType) {
                throw new InvalidIndentationException();
            }
        }

        return indentType;
    }

    private static int getNumberOfMismatches(CodeLine[] lines, int singleIndentSize) {
        int openedBraces = 0;
        int mismatchesFound = 0;

        for (CodeLine line : lines) {
            if (line.getBraceLayout().startsWithClosing()) {
                openedBraces--;
            }

            int expectedIndentSize = openedBraces * singleIndentSize;
            int actualIndentSize = line.getIndentSize();

            mismatchesFound += Math.abs(expectedIndentSize - actualIndentSize);

            openedBraces += line.getBraceLayout().getBraceDifference();
            if (line.getBraceLayout().startsWithClosing()) {
                openedBraces++;
            }
        }

        return mismatchesFound;
    }

    private static int getBestMatch(CodeLine[] lines, int maxIndent) {
        if (maxIndent < 1) {
            throw new RuntimeException("Error: at least one indentation should be possible");
        }

        int minNumberOfMismatches = Integer.MAX_VALUE;
        int bestIndent = -1;

        for (int indent = 1; indent <= maxIndent; indent++) {
            int numberOfMismatches = getNumberOfMismatches(lines, indent);

            System.out.println("Number of mismatches for indentation of " + indent + ": " + numberOfMismatches);

            if (numberOfMismatches < minNumberOfMismatches) {
                minNumberOfMismatches = numberOfMismatches;
                bestIndent = indent;
            }

            // DEBUG
            // throw new RuntimeException("Stopped iterating");
        }

        return bestIndent;
    }
}
