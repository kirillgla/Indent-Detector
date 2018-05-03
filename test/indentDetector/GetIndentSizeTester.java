package indentDetector;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import static org.junit.Assert.assertEquals;

@RunWith(Parameterized.class)
public class GetIndentSizeTester {
    private String input;
    private int expected;

    public GetIndentSizeTester(String input, int expected)
    {
        this.input = input;
        this.expected = expected;
    }

    @Parameterized.Parameters
    public static Object[][] indentationExamples() {
        return new Object[][]{
                {"    System.out.println(\"Hello, world!\");", 4},
                {"   System.out.println(\"Hello, world!\");", 3},
                {"  System.out.println(\"Hello, world!\");", 2},
                {" System.out.println(\"Hello, world!\");", 1},
                {"\tSystem.out.println(\"Hello, world!\");", 1},
                {"\t\tSystem.out.println(\"Hello, world!\");", 2}
        };
    }

    @Test
    public void testGetIndentSize() {
        int result = CodeLine.getIndentSize(input);

        assertEquals(expected, result);
    }
}
