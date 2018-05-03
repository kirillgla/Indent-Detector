package indentDetector;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import static org.junit.Assert.assertEquals;

@RunWith(Parameterized.class)
public class ParseTester {
    private String input;
    private CodeLine expected;

    public ParseTester(String input, CodeLine expected) {
        this.input = input;
        this.expected = expected;
    }

    @Parameterized.Parameters
    public static Object[][] inputExamples() {
        return new Object[][]{
                {"    System.out.println(\"Hello, world!\");",
                        new CodeLine(IndentType.Spaces, 4, new BraceLayout(0, 0))},
                {"public class Program {",
                        new CodeLine(IndentType.Unknown, 0, new BraceLayout(1, 0))},
                {"\t\t}",
                        new CodeLine(IndentType.Tabs, 2, new BraceLayout(0, 1))}
        };
    }

    @Test
    public void testParseWithSpaces() throws Exception {
        CodeLine result = CodeLine.parse(input);

        assertEquals(expected, result);
    }
}
