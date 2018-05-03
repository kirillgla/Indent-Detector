package indentDetector;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;

@RunWith(Parameterized.class)
public class ParseTester {
    private String input;
    private String nextLine;
    private ArrayList<OpenBraceType> context;
    private CodeLine expected;

    public ParseTester(String input, String nextLine, ArrayList<OpenBraceType> context, CodeLine expected) {
        this.input = input;
        this.nextLine = nextLine;
        this.context = context;
        this.expected = expected;
    }

    @Parameterized.Parameters
    public static Object[][] inputExamples() {
        return new Object[][]{
                {
                        "    System.out.println(\"Hello, world!\");",
                        "",
                        new ArrayList<>(),
                        new CodeLine(IndentType.Spaces, 4, new BraceLayout(0, 0, false))
                },
                {
                        "public class Program {",
                        "",
                        new ArrayList<>(),
                        new CodeLine(IndentType.Unknown, 0, new BraceLayout(1, 0, false))
                },
                {
                        "\t\t}",
                        "",
                        new ArrayList<OpenBraceType>() {{
                            add(OpenBraceType.Other);
                        }},
                        new CodeLine(IndentType.Tabs, 2, new BraceLayout(0, 1, true))
                }
        };
    }

    @Test
    public void testParseWithSpaces() throws Exception {
        CodeLine result = CodeLine.parse(input, nextLine, context);

        assertEquals(expected, result);
    }
}
