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
            // TODO
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

    static CodeLine[] parseLines(String[] inputs) throws InvalidIndentationException {
        ArrayList<CodeLine> lines = new ArrayList<>();
        for (String input : inputs) {
            if (input.isEmpty())
            {
                continue;
            }

            lines.add(CodeLine.parse(input));
        }
        return lines.toArray(new CodeLine[0]);
    }

    static boolean isNullOrWhitespace(String s)
    {
        return s == null || s.isEmpty() || s.trim().isEmpty();
    }
}
